package scannerProject;

public abstract class Token {
	private String name;
	private String type;
	private String word;
	private int row;
	private int col;
	
	//abstract functions
	public abstract boolean check(Walker w);
	
	//functions
	
	public boolean hasKey(Walker w , String key) {
		int i = w.getValue();
		int k = 0;
		if(w.getMax() >= key.length()) {
			for (i = w.getValue(),k = 0 ; k < key.length() && i < w.getMax(); i++, k++) {
				if(w.c(i) != key.charAt(k)) {
					break;
				}
			}
			//we cannot use i+1 or lenght-1 for comparison. because end of the for loop they have already increased 1 
			if(w.checkRest(i) && k == key.length()) {
				w.setValue(i);
				return true;
			}
		}	
		return false;
	}
	
	public Token(String type) {
		setType(type);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public String getWord() {
		return word;
	}

	public void setWord(Walker w) {
		this.word = w.getStr().substring(w.getStart(), w.getValue());
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}


}