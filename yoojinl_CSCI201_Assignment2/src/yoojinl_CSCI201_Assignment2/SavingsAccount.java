package yoojinl_CSCI201_Assignment2;

public abstract class SavingsAccount extends BaseAccount {
	
	protected double combinedBalance;
	
	public SavingsAccount(double balance, double checking)
	{
		super(balance);
		combinedBalance = balance+checking;
	}
	
	public void setCombinedBalance(double balance)
	{
		this.combinedBalance = balance;
	}
	
	public double getCombinedBalance()
	{
		return this.combinedBalance;
	}
	
	public abstract String getAccountType();
	
	protected abstract double getBalanceAfterNumYears(int numYears);
	
}

class BasicSavingAccount extends SavingsAccount
{
	public BasicSavingAccount(double balance, double checking)
	{
		super(balance,checking);
	}
	
	protected double getBalanceAfterNumYears(int numYears)
	{
		double iBalance = getBalance();
		for(int i=0; i<numYears; i++){
			iBalance = iBalance*1.001;
		}
		return iBalance;
	}
	
	public String getAccountType()
	{
		return "Basic Saving";
	}
}

class PremiumSavingAccount extends SavingsAccount
{
	public PremiumSavingAccount(double balance, double checking)
	{
		super(balance,checking);
	}
	
	protected double getBalanceAfterNumYears(int numYears)
	{
		double iBalance = getBalance();
		for(int i=0; i<numYears; i++){
			iBalance = iBalance*1.01;
		}
		return iBalance;
	}
	
	public String getAccountType()
	{
		return "Premium Saving";
	}
}

class DeluxeSavingAccount extends SavingsAccount
{
	public DeluxeSavingAccount(double balance,double checking)
	{
		super(balance,checking);
	}
	
	protected double getBalanceAfterNumYears(int numYears)
	{
		double iBalance = getBalance();
		for(int i=0; i<numYears; i++){
			iBalance = iBalance*1.05;
		}
		return iBalance;
	}
	
	public String getAccountType()
	{
		return "Deluxe Saving";
	}
}