package scannerProject;

public class CharacterLiterals {

	public CharacterLiterals(){}
	
	// inner class
	public class CHAR extends Token{
		
		public CHAR() {
			super("Character Literals");
			setName("CHAR");
		}

		@Override
		public boolean check(Walker w) {
			int i = w.getValue();
			
			if (w.c(i) == '\'') {
				if(w.c(i + 1) == '\\') {
					if((w.c(i + 2) == '\'' || w.c(i + 2) == '\\') && w.c(i + 3) == '\'' && w.checkRest(i + 4)) {
						w.setValue(w.getValue() + 4);
						return true;
					}
				}
				else if(w.c(i + 2) == '\'' && w.checkRest(i + 3)) {
					w.setValue(w.getValue() + 3);

					return true;
				}
			}
			return false;
		}
	}
}