package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Produtos;

public class Principal {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Produtos> list = new ArrayList<>();

		System.out.println("Enter file path: ");
		String sourceFileStr = sc.nextLine();

		File sourceFile = new File(sourceFileStr);
		String sourceFolderStr = sourceFile.getParent();

		boolean success = new File(sourceFolderStr + "\\out").mkdir();
		System.out.println("PASTA CRIADA: " + success);

		String targetFileSrc = sourceFolderStr + "\\out\\summary.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {

			String itemCsv = br.readLine();
			while (itemCsv != null) {

				String[] fields = itemCsv.split(",");
				String nome = fields[0];
				double preco = Double.parseDouble(fields[1]);
				int quantidade = Integer.parseInt(fields[2]);

				list.add(new Produtos(nome, preco, quantidade));

				itemCsv = br.readLine();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileSrc))) {
				for (Produtos item : list) {
					bw.write(item.getName() + ", " + String.format("%.2f", item.total()));
					bw.newLine();
				}

				System.out.println(targetFileSrc + " CREATED!");

			} catch (IOException e) {
				System.out.println("ERROR WRITING FILE: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("ERROR READING FILE: " + e.getMessage());
		}

		sc.close();
	}

}
