package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import entities.Produto;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		File path = new File("C:\\temp\\produtos.csv");
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			String line = br.readLine();
			List<Produto> produtos = new ArrayList<Produto>();
			
			while(line != null) {
				String[] params = line.split(",");
				
				produtos.add(new Produto(params[0], Double.valueOf(params[1])));
				line = br.readLine();
			}
			
			Double precoMedio = produtos
				.stream()
				.map(x -> x.getPreco())
				.reduce(0.0, (x,y) -> x+y) / produtos.size()
			;
			
			System.out.printf("Average price: %.2f\n", precoMedio);
			
			List<String> nomes = produtos.stream()
				.filter(x -> x.getPreco() < precoMedio)
				.map(x -> x.getNome())
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList())
			;
			
			for(String nome : nomes) {
				System.out.println(nome);
			}
			
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
