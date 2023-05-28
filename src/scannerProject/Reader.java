package scannerProject;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Reader {
	public Walker w;
	private PrintWriter outputScanner;
	public ArrayList<Token> outputTokenList = new ArrayList<>();
	
	private String BRC_DIGIT = "[](){}";
	private String KEY_DIGIT = "dlcib";
	private String ID_DIGIT = "+-.!*/:<=>?";
	private int row = 0;
	private boolean hasError;
	
	public Reader(PrintWriter outputScanner) {
		this.outputScanner = outputScanner;
		this.w = new Walker();
		setHasError(false);
	}	
	
	public boolean checkAndWrite(Token token, Walker w) {
		// check is it valid
		w.setStart(w.getValue());
		if(token.check(w)) {
			token.setRow(row);
			token.setCol(w.getStart() + 1);
			token.setWord(w);
			outputTokenList.add(token);
			String out = token.getName() + " " + row + ":" + (w.getStart() + 1);
			System.out.println(out);
			outputScanner.println(out);
			return true;
		}
		return false;
	}
	
	
	public void findTokens(String str) {		
		w.setValue(0);
		w.setStr(str);
		row++;
		while(w.getValue() < w.getMax()) {

			char c = w.c(w.getValue());	
			if(c == ' ' || c == '\t') {
				w.add();
				continue;
			}
			
			//this token is empty, it will updated later (polymorphism)
			Token token = new EmpytToken();
			
			//if it is commend
			if(c == '~') {
				Comments cm = new Comments();
				token = cm.new COMMENT();
				if(checkAndWrite(token, w)) {
					break;
				}
			}
			
			//if it is bracket
			if(BRC_DIGIT.contains(String.valueOf(c))) {
				Brackets br = new Brackets();
				token = br.findBracket(c);	
				if(checkAndWrite(token, w)) {
					continue;
				}
			}

			//if it is number
			if(c == '-' || c == '+' || c == '.' || Character.isDigit(c)) {
				NumberLiterals nm = new NumberLiterals();
				token = nm.new NUMBER();
				if(checkAndWrite(token, w)) {
					continue;
				}
			}
			
			//if it is boolean
			if(c == 't' || c == 'f') {
				BooleanLiterals bl = new BooleanLiterals();
				token = bl.new BOOLEAN();
				if(checkAndWrite(token, w)) {
					continue;
				}
 			}
			
			//if it is character
			if(c == '\'') {
				CharacterLiterals ch = new CharacterLiterals();
				token = ch.new CHAR();
				if(checkAndWrite(token, w)) {
					continue;
				}
			}
			
			//if it is string
			if(c == '"') {
				StringLiterals st = new StringLiterals();
				token = st.new STRING();
				if(checkAndWrite(token, w)) {
					continue;
				}
			}
			
			//if it is keyword
			if(KEY_DIGIT.contains(String.valueOf(c))) {
				Keywords ky = new Keywords();
				token = ky.findKeywor(c);
				if(checkAndWrite(token, w)) {
					continue;
				}
			}
			
			//if it is identifier
			if(ID_DIGIT.contains(String.valueOf(c)) || (Character.isLetter(c) && Character.isLowerCase(c))) {
				Identifiers id = new Identifiers();
				token = id.new IDENTIFIER();
				if(checkAndWrite(token, w)) {
					continue;
				}
			}
			String out = "LEXICAL ERROR [" + row + ":" + (w.getStart() + 1) + "]: Invalid token " + w.findError();
			System.out.println(out);
			outputScanner.println(out);	
			setHasError(true);
			break;
		}
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
}