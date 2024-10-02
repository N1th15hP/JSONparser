package com.jsonparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class JSONParser {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			String command = scanner.nextLine();
			String[] commandParts = command.split(" ");
			String jsonString = null;
			
			if ("parse".equalsIgnoreCase(commandParts[0])) {
				StringBuilder builder = new StringBuilder();
				try (BufferedReader reader = new BufferedReader(new FileReader(commandParts[1]))) {
					String line;

					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					jsonString = builder.toString();
					Lexer lexer = new Lexer(jsonString);
					List<Token> tokens = lexer.tokenize();
					System.out.println(tokens);
					Parser parser = new Parser(tokens);
					Object jsonObject = parser.parse();
					System.out.println(jsonObject);
				} catch (FileNotFoundException e) {
					System.err.print("Unable to find the file");
					System.exit(0);
				} catch (IOException e) {
					System.err.print("Unable to open the file");
					System.exit(0);
				} catch(InvalidJSONException ex) {
					System.err.print(ex.getErrorMessage());
					System.exit(0);
				}
			}
		}
	}
}
