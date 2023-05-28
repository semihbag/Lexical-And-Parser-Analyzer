package scannerProject;

public class Comments {
	
	public Comments(){}
	
	
	public class COMMENT extends Token {
		
		public COMMENT() {
			super("Comments");
			setName("COMMENT");
		}

		@Override
		public boolean check(Walker w) {
			int i = w.getValue();
			
			if (w.c(i) == '~') {
				return true;
			}
			return false;
		}
	}
}
