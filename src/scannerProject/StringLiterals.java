package scannerProject;

public class StringLiterals {

	public StringLiterals(){}

	// inner class
	public class STRING extends Token{
		
		public STRING() {
			super("String Literals");
			setName("STRING");
		}

		@Override
		public boolean check(Walker w) {
			int i = w.getValue();

			if (w.c(i) == '"') {
				i++;
				while(i < w.getMax()) {
					if (w.c(i) == '"') {
						if(w.checkRest(i + 1) && w.c(i - 1) != '\\') {
							w.setValue(i + 1);
							return true;
						}
					}
					i++;
				}
			}
			return false;
		}
	}
}