package scannerProject;

// This class represent empty token
public class EmpytToken extends Token{
	
	public EmpytToken() {
		super("Empty");			
	}
	
	@Override
	public boolean check(Walker counter) {
		return false;
	}
}