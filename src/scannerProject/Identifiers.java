package scannerProject;

public class Identifiers {

	public Identifiers(){}
	
	//inner classes
	public class IDENTIFIER extends Token {
		
		public IDENTIFIER() {
			super("Identifiers");
			setName("IDENTIFIER");
		}

		@Override
		public boolean check(Walker w) {
			int i = w.getValue();
			
			if(w.c(i) == '+' || w.c(i) == '-' || w.c(i) == '.') {
				if(w.checkRest(i + 1)) {
					w.add();
					return true;
				}
			}
			else {
				i++;
				while(i < w.getMax()) {
					char c = w.c(i);
					if(!((Character.isLetter(c) && Character.isLowerCase(c)) || Character.isDigit(c) || c == '+' || c == '-' || c == '.')) {
						break;
					}
					i++;
				}
				if(w.checkRest(i)) {
					w.setValue(i);
					return true;
				}
			}
			return false;
		}
	}
}