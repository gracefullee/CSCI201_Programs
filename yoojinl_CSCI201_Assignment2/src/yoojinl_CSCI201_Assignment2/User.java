package yoojinl_CSCI201_Assignment2;

public class User {
	private String username;
	private String password;
	public CheckingAccount checkingAcct;
	public SavingsAccount savingsAcct;
	
	public User(String username, String password, double checking, double savings)
	{
		this.username = username;
		this.password = password;
		this.checkingAcct = new CheckingAccount(checking);
		double combined = savings + checking;
		if(combined <= 1000 && combined > 0)
			this.savingsAcct = new BasicSavingAccount(savings,checking);
		else if(combined <= 10000 && combined > 1000)
			this.savingsAcct = new PremiumSavingAccount(savings,checking);
		else if(combined > 10000)
			this.savingsAcct = new DeluxeSavingAccount(savings,checking);
		savingsAcct.setCombinedBalance(checking+savings);
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public String getUsername()
	{
		return this.username;
	}
}
