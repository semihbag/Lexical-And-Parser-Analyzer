package scannerProject;

public class Keywords {

	public Keywords(){}
	
	public Token findKeywor(char c) {
		switch (c) {
		case 'd':
			return new DEFINE();
		case 'l':
			return new LET();
		case 'c':
			return new COND();
		case 'i':
			return new IF();
		case 'b':
			return new BEGIN();
		default:
			return null;
		}
	}
	
	// inner classes
	// define
	public class DEFINE extends Token{
		
		public DEFINE() {
			super("Keywords");
			setName("DEFINE");
		}

		@Override
		public boolean check(Walker w) {
			return hasKey(w, "define");
		}
	}

	// let
	public class LET extends Token{
		
		public LET() {
			super("Keywords");
			setName("LET");
		}

		@Override
		public boolean check(Walker w) {
			return hasKey(w, "let");
		}
	}
	
	// cond
	public class COND extends Token{
		
		public COND() {
			super("Keywords");
			setName("COND");
		}

		@Override
		public boolean check(Walker w) {
			return hasKey(w, "cond");
		}
	}

	// if
	public class IF extends Token{
		
		public IF() {
			super("Keywords");
			setName("IF");
		}

		@Override
		public boolean check(Walker w) {
			return hasKey(w, "if");
		}
	}

	// begin
	public class BEGIN extends Token{
		
		public BEGIN() {
			super("Keywords");
			setName("BEGIN");
		}

		@Override
		public boolean check(Walker w) {
			return hasKey(w, "begin");
		}
	}
}