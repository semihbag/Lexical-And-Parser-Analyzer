package scannerProject;

public class Brackets{
	
	public Brackets(){}
	
	public Token findBracket(char c) {
		switch (c) {
		case '(':
			return new LEFTPAR();
		case ')':
			return new RIGHTPAR();
		case '[':
			return new LEFTSQUAREB();
		case ']':
			return new RIGHTSQUAREB();
		case '{':
			return new LEFTCURLYB();
		case '}':
			return new RIGHTCURLYB();
		default:
			return null;
		}
	}
	
	// inner classes
	// left parentheses
	public class LEFTPAR extends Token{
		
		public LEFTPAR() {
			super("Bracket");
			setName("LEFTPAR");
		}

		@Override
		public boolean check(Walker w) {
			if (w.c(w.getValue()) == '(') {
				w.add();
				return true;
			}
			return false;
		}
	}
	
	// right parentheses
	public class RIGHTPAR extends Token{
		public RIGHTPAR() {
			super("Bracket");
			setName("RIGHTPAR");
		}

		@Override
		public boolean check(Walker w) {
			if (w.c(w.getValue()) == ')') {
				w.add();
				return true;
			}
			return false;
		}
	}
	
	// left square bracket
	public class LEFTSQUAREB extends Token{
		public LEFTSQUAREB() {
			super("Bracket");
			setName("LEFTSQUAREB");
		}

		@Override
		public boolean check(Walker w) {
			if (w.c(w.getValue()) == '[') {
				w.add();
				return true;
			}
			return false;
		}
	}
	
	// right square bracket
	public class RIGHTSQUAREB extends Token{
		public RIGHTSQUAREB() {
			super("Bracket");
			setName("RIGHTSQUAREB");
		}

		@Override
		public boolean check(Walker w) {
			if (w.c(w.getValue()) == ']') {
				w.add();
				return true;
			}
			return false;
		}
	}
	
	// left curly bracket
	public class LEFTCURLYB extends Token{
		public LEFTCURLYB() {
			super("Bracket");
			setName("LEFTCURLYB");
		}

		@Override
		public boolean check(Walker w) {
			if (w.c(w.getValue()) == '{') {
				w.add();
				return true;
			}
			return false;
		}
	}
	
	// right curly bracket
	public class RIGHTCURLYB extends Token{
		public RIGHTCURLYB() {
			super("Bracket");
			setName("RIGHTCURLYB");
		}

		@Override
		public boolean check(Walker w) {
			if (w.c(w.getValue()) == '}') {
				w.add();
				return true;
			}
			return false;
		}
	}
}