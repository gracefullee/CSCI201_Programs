package yoojinl_CSCI201_Assignment2;

public class CheckingAccount extends BaseAccount {
	
	public CheckingAccount (double balance){
		super(balance);
	}
	
	public String getAccountType()
	{
		return "Checking";
	}
	
	protected double getBalanceAfterNumYears(int numYears)
	{
		return this.getBalance();
	}
}
