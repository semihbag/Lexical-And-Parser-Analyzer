package scannerProject;

public class NumberLiterals {
	
	public NumberLiterals() {}
	
	//inner class
	public class NUMBER extends Token {
		private String HEX_DIGIT = "0123456789abcdefABCDEF";
		private String BIN_DIGIT = "01";
		private boolean hasSign = false;
		private boolean hasEuler = false;
		private boolean hasPoint = false;

		
		public NUMBER() {
			super("Number Literal");
			setName("NUMBER");
		}
		
		private boolean getBool(char c) {
			return (c == '+' || c == '-')? hasSign : (c == 'e' || c == 'E')? hasEuler : hasPoint;
		}
		
		private void setBoolTrue(char c) {
			hasSign = (c == '+' || c == '-')? true : hasSign;
			hasEuler = (c == 'e' || c == 'E')? true : hasEuler;
			hasPoint = (c == '.')? true : hasPoint;
		}
		
		@Override
		public boolean check(Walker w) {
			int i = w.getValue();
			String str;
			
			//binary and hex check
			if(w.getMax() - i >= 3) {
				if(w.c(i) == '0' && (w.c(i+1) == 'b' || w.c(i+1) == 'x')) {
					str = (w.c(++i) == 'b')? BIN_DIGIT : HEX_DIGIT;		
					i++;
					while(i < w.getMax()) {
						if(!str.contains(String.valueOf(w.c(i)))) {
							break;
						}
						i++;
					}
					if(w.checkRest(i)) {
						w.setValue(i);
						return true;
					}
				}
			}
			
			//to set walker index initial value because if the execution is here it means number is not hex or binary
			i = w.getValue();		
			
			// floating point and decimal check
			if (w.c(i) == '.' || w.c(i) == '+' || w.c(i) == '-' || Character.isDigit(w.c(i))) {
				hasPoint = (w.c(i) == '.');
				i++;
				while(i < w.getMax()) {
					if(!Character.isDigit(w.c(i))) {
						char c = w.c(i);
						if(c == '.' || c == '+' || c == '-' || c == 'e' || c == 'E') {
							if(getBool(c)) {												//check if operands used more than one
								break;
							}
							if((c == '+' || c == '-') && !hasEuler) {						//if c is sign epsilon must used already
								break;
							}
							if ((c == 'e' || c == 'E') && !Character.isDigit(w.c(i-1))) {	//if c is euler str must have at least one digit before euler
								break;
							}
							setBoolTrue(c);													//set the correct bool to true if it is used first time
							i++;
							continue;
						}
						break;					
					}
					i++;
				}
				if(Character.isDigit(w.c(i -1))) {		// this condition for ending with non digit str. it is not valid
					if(w.checkRest(i)) {								
						w.setValue(i);
						return true;
					}
				}
			}
			return false;
		}
	}
}