import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import parserProject.Parser;
import scannerProject.Reader;

public class Main {

	public static void main(String[] args) {

		String line, file;
		try {
			System.out.println("Please enter input file name: (filename.txt)");
			Scanner scanner = new Scanner(System.in);
			file = scanner.nextLine();
			scanner.close();
			
			Scanner input = new Scanner(new File(file));
			PrintWriter outputScanner = new PrintWriter(new File("outputScanner.txt"));
			PrintWriter outputParser = new PrintWriter(new File("outputParser.txt"));
			
			Reader r = new Reader(outputScanner);					
			while(input.hasNextLine() && !r.isHasError()) {
				line = input.nextLine();
				r.findTokens(line);
			}
			input.close();
			outputScanner.close();

			System.out.println("________________________");
			
			Parser p = new Parser(outputParser);
			p.setTokens(r.outputTokenList);
			p.parse();

			outputParser.close();
			
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}	
	}
}