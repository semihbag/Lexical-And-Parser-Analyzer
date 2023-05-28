package parserProject;

import java.util.ArrayList;
import java.io.PrintWriter;

import scannerProject.EmpytToken;
import scannerProject.Token;

public class Parser {
	private PrintWriter outputParser;
	private ArrayList<Token> tokens;
	private Token currendToken;
	private int i;
	private String expected;
	
	public Parser(PrintWriter outputParser) {
		this.outputParser = outputParser;
		this.i = -1;
	}
	
	private void next() {
		if (i + 1 < tokens.size()) {
			this.currendToken = tokens.get(++i);
		}
		else {
			this.currendToken = null;
		}
	}
	
	private void write(Object... optionalParams) {
		if (optionalParams.length == 1 || optionalParams.length == 3) {
			if (optionalParams.length == 3) {
				String outStr = "";
				for (int k = 0; k < (int)optionalParams[2]; k++) {
					outStr += "\t";
				}
				
				outputParser.println(outStr + (String)optionalParams[1]);
				System.out.println(outStr + (String)optionalParams[1]);
			}
			
			String outStr = "";
			for (int k = 0; k < (int)optionalParams[0]; k++) {
				outStr += "\t";
			}
			
			outputParser.println(outStr + currendToken.getName() + " (" + currendToken.getWord() +")");
			System.out.println(outStr + currendToken.getName() + " (" + currendToken.getWord() +")");
		}
		
		if (optionalParams.length == 2) {			
			String outStr = "";
			for (int k = 0; k < (int)optionalParams[0]; k++) {
				outStr += "\t";
			}
			System.out.println(outStr + (String)optionalParams[1]);
			outputParser.println(outStr + (String)optionalParams[1]);
		}
	}
	
	private boolean check(String str) throws Exception {
		expected = str;
		if (currendToken == null) {
			Token t = new EmpytToken();
			t.setCol(tokens.get(tokens.size()-1).getCol() +1);
			t.setRow(tokens.get(tokens.size()-1).getRow());
			throw new ParserErrorException(t, expected, outputParser);
		}
		if (currendToken.getName().equals(str)) {
			return true;
		}
		return false;
	}
	
	
	
	public void parse() throws Exception {
		int s = 0;
		next();
		boolean b =program(s);

		if(!b) {
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
	}
	
	
	
	
	
	
	////////////////////////////
	private boolean program(int s) throws Exception {
		write(s, "<Program>");
		s++;
		
		// empty form
		if (currendToken == null) {
			write(s, "__");
			return true;
		}
				
		//call topLevelForm
		if (topLevelForm(s)) {
			if (program(s)) {
				return true;
			}
		}		
		return false;
	}
	
	
	private boolean topLevelForm(int s) throws Exception{
		s++;
		
		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s , "<TopLevelForm>", s-1);
			next();
			
			// call secondLevelForm
			if (secondLevelForm(s)) {
				
				// check RIGHTPAR
				if (check("RIGHTPAR")) {
					write(s);
					next();
					return true;
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
	
	
	private boolean secondLevelForm(int s) throws Exception {
		write(s, "<SecondLevelForm>");
		s++;
		
		// call definition
		if (definition(s)) {
			return true;
		}
		
		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s);
			next();
			
			// call funCall
			if (funCall(s)) {
				
				// check right par
				if (check("RIGHTPAR")) {
					write(s);
					next();
					return true;
				}
			}	
			throw new ParserErrorException(currendToken, expected, outputParser);
		}		
		return false;
	}

	
	private boolean definition(int s) throws Exception {
		s++;
		
		// check DEFINE
		if (check("DEFINE")) {
			write(s, "<Definition>", s-1);
			next();
			
			// call definitionRight
			if (definitionRight(s)) {
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
	
	
	private boolean definitionRight(int s) throws Exception {
		s++;
		
		// check IDENTIFIER
		if (check("IDENTIFIER")) {
			write(s, "<DefinitionRight>", s-1);
			next();
			
			// call expression
			if (expression(s)) {
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}

		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s, "<DefinitionRight>", s-1);
			next();
			
			// check IDENTIFIER
			if (check("IDENTIFIER")) {
				write(s);
				next();
				
				// call argList
				if (argList(s)) {
					
					// check RIGHTPAR
					if (check("RIGHTPAR")) {
						write(s);
						next();
						
						// call statements
						if (statements(s)) {
							return true;
						}
					}
				}				
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
	
	
	private boolean argList(int s) throws Exception {
		write(s, "<ArgList>");
		s++;
		
		// check IDENTIFIER
		if (check("IDENTIFIER")) {
			write(s);
			next();
			
			if (argList(s)) {
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		
		// this func always return true because it has epsilon (it can be empty)
		write(s, "__");
		return true;
	}
	
	
	
	private boolean statements(int s) throws Exception {
		write(s, "<Statements>");
		s++;
		
		// call expression
		if (expression(s)) {
			return true;
		}

		// call definition
		if (definition(s)) {
			return true;
		}
		return false;
	}
	
	
	private boolean expressions (int s) throws Exception {
		write(s, "<Expressions>");
		s++;
		
		// call expression
		if (expression(s)) {

			// call expressions
			if (expressions(s)) {
				return true;
			}
			return false;
		}
		
		// this func always return true because it has epsilon (it can be empty)
		write(s, "__");
		return true;
	}
	
	
	
	private boolean expression(int s) throws Exception {
		s++;
		
		// check IDENTIFIER
		if (check("IDENTIFIER")) {
			write(s, "<Expression>", s-1);
			next();
			return true;
		}
		
		// check NUMBER
		if (check("NUMBER")) {
			write(s, "<Expression>", s-1);
			next();
			return true;
		}
		
		// check CHAR
		if (check("CHAR")) {
			write(s, "<Expression>", s-1);
			next();
			return true;
		}
		
		// check BOOLEAN
		if (check("BOOLEAN")) {
			write(s, "<Expression>", s-1);
			next();
			return true;
		}
		
		// check STRING
		if (check("STRING")) {
			write(s, "<Expression>", s-1);
			next();
			return true;
		}
		
		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s, "<Expression>", s-1);
			next();
			
			// call expr
			if (expr(s)) {
				
				// check RIGHTPAR
				if (check("RIGHTPAR")) {
					write(s);
					next();
					return true;
				}
			}	
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
	
	
	private boolean expr(int s) throws Exception {
		write(s, "<Expr>");
		s++;
		
		// call letExpression
		if (letExpression(s)) {
			return true;
		}
		
		// call condExpression
		if (condExpression(s)) {
			return true;
		}
		
		// call ifExpression
		if (ifExpression(s)) {
			return true;
		}
		
		// call beginExpression
		if (beginExpression(s)) {
			return true;
		}
		
		// call funCall
		if (funCall(s)) {
			return true;
		}
		
		return false;
	}
	
	
	private boolean funCall(int s) throws Exception {
		s++;
		
		// check IDENTIFIER
		if (check("IDENTIFIER")) {
			write(s, "<FunCall>", s-1);
			next();
		
			// call expression
			if (expressions(s)){
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
	
	
	private boolean letExpression(int s) throws Exception {
		s++;
		
		// check LET
		if (check("LET")) {
			write(s, "<LetExpression>", s-1);
			next();
			
			// call letExpr
			if (letExpr(s)){
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
	
	
	private boolean letExpr(int s) throws Exception {
		s++;
		
		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s, "<LetExpr>", s-1);
			next();
		
			// call varDefs
			if (varDefs(s)){
			
				// check RIGHTPAR
				if (check("RIGHTPAR")) {
					write(s);
					next();
					
					// call statements
					if (statements(s)) {
						return true;
					}
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		
		// check IDENTIFIER
		if (check("IDENTIFIER")) {
			write(s, "<LetExpr>", s-1);
			next();
			
			// check LEFTPAR
			if (check("LEFTPAR")) {
				write(s);
				next();
				
				// call varDefs
				if (varDefs(s)){
					
					// check RIGHTPAR
					if (check("RIGHTPAR")) {
						write(s);
						next();
						
						// call statements
						if (statements(s)) {
							return true;
						}
					}
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;		
	}

	private boolean varDefs(int s) throws Exception {
		s++;
		
		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s, "<VarDefs>", s-1);
			next();
		
			// check IDENTIFIER
			if (check("IDENTIFIER")) {
				write(s);
				next();
			
				// call expression
				if (expression(s)){
					
					// check RIGHTPAR
					if (check("RIGHTPAR")) {
						write(s);
						next();
						
						// call varDef
						if (varDef(s)) {
							return true;
						}
					}
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}				
		return false;
	}

	
	private boolean varDef(int s) throws Exception {
		write(s, "<VarDef>");
		s++;
		
		// call varDefs
		if (varDefs(s)) {
			return true;
		}
		
		// this func always return true because it has epsilon (it can be empty)
		write(s, "__");
		return true;
	}

	
	private boolean condExpression(int s) throws Exception {
		s++;

		// check COND
		if (check("COND")) {
			write(s, "<CondExpression>", s-1);
			next();
			
			// call condBranches
			if (condBranches(s)){
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}
		

	private boolean condBranches(int s) throws Exception {
		s++;

		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s, "<CondBranches>", s-1);
			next();
			
			// call expression
			if (expression(s)){
				
				// call statements
				if (statements(s)){
				
					// check RIGHTPAR
					if (check("RIGHTPAR")) {
						write(s);
						next();

						// call condBranches
						if (condBranch(s)){
							return true;
						}
					}
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}

	
	private boolean condBranch(int s) throws Exception {
		s++;
		
		// check LEFTPAR
		if (check("LEFTPAR")) {
			write(s, "<CondBranch>", s-1);
			next();
			
			// call expression
			if (expression(s)){
				
				// call statements
				if (statements(s)){
				
					// check RIGHTPAR
					if (check("RIGHTPAR")) {
						write(s);
						next();
						return true;
					}
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		
		// this func always return true because it has epsilon (it can be empty)
		write(s, "__");
		return true;	
	}

	
	private boolean ifExpression(int s) throws Exception {
		s++;
		
		// check IF
		if (check("IF")) {
			write(s, "<IfExpression>", s-1);
			next();
			
			// call expression
			if (expression(s)){
				
				// call expression
				if (expression(s)){
					
					// call expression
					if (endExpression(s)){
						return true;
					}
				}
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}

	
	private boolean endExpression(int s) throws Exception {
		write(s, "<EndExpression>");
		s++;
		
		// call expression
		if (expression(s)){
			return true;
		}
		

		// this func always return true because it has epsilon (it can be empty)
		write(s, "__");
		return true;
	}

	
	private boolean beginExpression(int s) throws Exception {
		s++;
		
		// check BEGIN
		if (check("BEGIN")) {
			write(s, "<BeginExpression>", s-1);
			next();
			
			// call statements
			if (statements(s)){
				return true;
			}
			throw new ParserErrorException(currendToken, expected, outputParser);
		}
		return false;
	}

	
		
	//////////////////////////////////
	// GETTER SETTER
	
	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
}