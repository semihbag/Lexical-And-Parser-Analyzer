package scannerProject;

public class Walker {
	private int start;
	private int count;
	private int max;
	private String str;
	private String TERMINATION_DIGIT = " \t)]}~";
	
	
	public Walker() {
		this.count = 0;
	}
	
	public boolean add() {
		if (count < max) {
			count++;
			return true;
			
		}
		return false;
	}
	
	
	public char c(int i) {
		return str.charAt(i);
	}

	public boolean checkRest(int i) {
		if (i == max) {
			return true;
		}	
		return TERMINATION_DIGIT.contains(String.valueOf(c(i)));
	}

	public String findError() {
		String error = "";
		int i = getStart();
		while(i < getMax()) {
			if(TERMINATION_DIGIT.contains(String.valueOf(c(i)))) {
				break;
			}
			error += String.valueOf(c(i));
			i++;
		}
		return error;
	}
	
	
	// getter and setter functions
	public int getValue() {
		return count;
	}

	public void setValue(int v) {
			count = v;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
		this.max = str.length();
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
