package scannerProject;

public class BooleanLiterals {
	
	public BooleanLiterals(){}
	
	// inner class
	public class BOOLEAN extends Token {
		
		public BOOLEAN() {
			super("Boolean Literals");
			setName("BOOLEAN");
		}

		@Override
		public boolean check(Walker w) {
			return hasKey(w, "true") || hasKey(w, "false");
		}
	}
}