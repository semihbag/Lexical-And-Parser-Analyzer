package parserProject;

import java.io.PrintWriter;

import scannerProject.Token;

public class ParserErrorException extends Exception{
	public ParserErrorException(Token t, String expected, PrintWriter outputParser) {
		super("SYNTAX ERROR [" + t.getRow() + ":" + t.getCol() + "] '" + expected + "'is expected");
		outputParser.println("SYNTAX ERROR [" + t.getRow() + ":" + t.getCol() + "] '" + expected + "'is expected");
		outputParser.close();
	}
}
