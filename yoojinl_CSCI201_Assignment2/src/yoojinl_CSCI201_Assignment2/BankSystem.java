package yoojinl_CSCI201_Assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class BankSystem {
	
	protected static ArrayList<String> usernames = new ArrayList<String>();
	protected static ArrayList<User> users = new ArrayList<User>();
	protected static int numUsers = 0;
	protected static User currentUser = null;
	
	public void mainMenu(Scanner scan, BankSystem bank)
	{
		int option = 0, acctOption = 0;
		while(option!=1 && acctOption!=5)
		{
			System.out.println("Welcome to Viterbi Bank!");
			System.out.println('\t' + "1. Existing Account Holder");
			System.out.println('\t' + "2. Open a New Account");
			System.out.print("What would you like to do? ");
			
			boolean notInt = false;
			while((option!=1 && option!=2) || notInt){
				try{
					//Check for valid integer (InputMismatchException & 1 or 2)
					option = scan.nextInt();
					notInt = false;
					scan.nextLine();
					while(option!=1 && option!=2){
						System.out.print("Please enter a valid option: ");
						option = scan.nextInt();
						scan.nextLine();
					}
				}	catch(InputMismatchException ime){
					notInt = true;
					scan.nextLine();
					System.out.print("Please enter a valid option: ");
				}
			}
			
			if(option==1){
				//Log-in
				option = 0;
				if(numUsers==0){
					System.out.println("Currently, there is no existing account holder. Please try again.");
					System.out.print('\n');
					continue;
					
				}
				if(!bank.accessUser(scan,bank)){
					continue;
				}
				
					
				while(acctOption!=5){
					//Menu after successful log-in
					System.out.println('\t' + "1) View Account Information");
					System.out.println('\t' + "2) Make a Deposit");
					System.out.println('\t' + "3) Make a Withdrawal");
					System.out.println('\t' + "4) Determine Balance in x Years");
					System.out.println('\t' + "5) Logout");
					System.out.print("What would you like to do? ");
					
					notInt = false;
					while(acctOption<1 || acctOption>5 || notInt){
						//Check for valid integer (InputMismatchException & 1-5)
						try{
							acctOption = scan.nextInt();
							notInt = false;
							scan.nextLine();
							while(acctOption<1 || acctOption>5){
								System.out.println("Please choose one of the following options (1-5)! Try again");
								System.out.print("What would you like to do? ");
								acctOption = scan.nextInt();
								scan.nextLine();
							}
						}	catch(InputMismatchException ime){
							notInt = true;
							scan.nextLine();
							System.out.println("Please choose one of the following options (1-5)! Try again");
							System.out.print("What would you like to do? ");
						}
						
					}
					
					if(acctOption==5){
						System.out.println('\n' + "Thanks for coming into Viterbi Bank!");
						return;
					}
										
					
					if(acctOption==1){
						String checking = String.format("%.2f",currentUser.checkingAcct.getBalance());
						String saving = String.format("%.2f",currentUser.savingsAcct.getBalance());
						System.out.println("You have a " + currentUser.checkingAcct.getAccountType() + " account "
								+ "with a balance of $" + checking + ".");
						System.out.println("You have a " + currentUser.savingsAcct.getAccountType() + " account "
								+ "with a balance of $" + saving + ".");
						System.out.print('\n');
						acctOption = 0;
					}
					
					else if(acctOption==2){
						System.out.println("Here are the accounts you have: ");
						System.out.println('\t' + "1) " + currentUser.checkingAcct.getAccountType());
						System.out.println('\t' + "2) " + currentUser.savingsAcct.getAccountType());
						System.out.print("Into which account would you like to make a deposit? ");
						
						notInt = false;
						int depositOption = 0;
						while((depositOption!=1 && depositOption!=2) || notInt){
							try{
								depositOption = scan.nextInt();
								notInt = false;
								scan.nextLine();
								while(depositOption!=1 && depositOption!=2){
									System.out.print("Please enter a valid option: ");
									depositOption = scan.nextInt();
									scan.nextLine();
								}
							}	catch(InputMismatchException ime){
								notInt = true;
								scan.nextLine();
								System.out.print("Please enter a valid option: ");
							}
						}
						
						if(depositOption==1){
							double depositAmt = 0;
							boolean depComplete = false;
							while(!depComplete){
								notInt = false;
								System.out.print("How much to deposit into your " + currentUser.checkingAcct.getAccountType() + "? ");
								try{
									depositAmt = scan.nextDouble();
									notInt = false;
									scan.nextLine();
								}	catch(InputMismatchException ime){
									notInt = true;
									scan.nextLine();
									System.out.println("Please enter a valid amount for deposit: " + ime.getMessage());
								}
								depComplete = currentUser.checkingAcct.deposit(depositAmt);
							}
							currentUser.savingsAcct.setCombinedBalance(currentUser.savingsAcct.getBalance() + currentUser.checkingAcct.getBalance());
							double combined = currentUser.savingsAcct.getCombinedBalance();
							if(combined <= 1000 && combined > 0)
								currentUser.savingsAcct = new BasicSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined <= 10000 && combined > 1000)
								currentUser.savingsAcct = new PremiumSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined > 10000)
								currentUser.savingsAcct = new DeluxeSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							System.out.print('\n');
						}
						else if(depositOption==2){
							double depositAmt = 0;
							boolean depComplete = false;
							while(!depComplete){
								notInt = false;
								System.out.print("How much to deposit into your " + currentUser.savingsAcct.getAccountType() + "? ");
								try{
									depositAmt = scan.nextDouble();
									notInt = false;
									scan.nextLine();
								}	catch(InputMismatchException ime){
									notInt = true;
									scan.nextLine();
									System.out.println("Please enter a valid amount for deposit: " + ime.getMessage());
								}
								depComplete = currentUser.savingsAcct.deposit(depositAmt);
							}
							currentUser.savingsAcct.setCombinedBalance(currentUser.savingsAcct.getBalance() + currentUser.checkingAcct.getBalance());
							double combined = currentUser.savingsAcct.getCombinedBalance();
							if(combined <= 1000 && combined > 0)
								currentUser.savingsAcct = new BasicSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined <= 10000 && combined > 1000)
								currentUser.savingsAcct = new PremiumSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined > 10000)
								currentUser.savingsAcct = new DeluxeSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							System.out.print('\n');
						}
						acctOption = 0;
					}
					
					else if(acctOption==3){
						int withdrawalOption = 0;
						System.out.println("Here are the accounts you have: ");
						System.out.println('\t' + "1) " + currentUser.checkingAcct.getAccountType());
						System.out.println('\t' + "2) " + currentUser.savingsAcct.getAccountType());
						System.out.print("Into which account would you like to make a withdrawal? ");
						
						notInt = false;
						while((withdrawalOption!=1 && withdrawalOption!=2) || notInt){
							try{
								withdrawalOption = scan.nextInt();
								notInt = false;
								scan.nextLine();
								while(withdrawalOption!=1 && withdrawalOption!=2){
									System.out.print("Please enter a valid option: ");
									withdrawalOption = scan.nextInt();
									scan.nextLine();
								}
							}	catch(InputMismatchException ime){
								notInt = true;
								scan.nextLine();
								System.out.print("Please enter a valid option: ");
							}
						}
						
						if(withdrawalOption==1){
							double withdrawAmt = 0;
							boolean withComplete = false;
							while(!withComplete){
								notInt = false;
								System.out.print("How much to withdraw from your " + currentUser.checkingAcct.getAccountType() + "? ");
								try{
									withdrawAmt = scan.nextDouble();
									notInt = false;
									scan.nextLine();
								}	catch(InputMismatchException ime){
									notInt = true;
									scan.nextLine();
									System.out.println("Please enter a valid amount for withdrawal: " + ime.getMessage());
								}
								withComplete = currentUser.checkingAcct.withdraw(withdrawAmt);
							}
							currentUser.savingsAcct.setCombinedBalance(currentUser.savingsAcct.getBalance() + currentUser.checkingAcct.getBalance());
							double combined = currentUser.savingsAcct.getCombinedBalance();
							if(combined <= 1000 && combined > 0)
								currentUser.savingsAcct = new BasicSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined <= 10000 && combined > 1000)
								currentUser.savingsAcct = new PremiumSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined > 10000)
								currentUser.savingsAcct = new DeluxeSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							System.out.print('\n');
						}
						else if(withdrawalOption==2){
							double withdrawAmt = 0;
							boolean withComplete = false;
							while(!withComplete){
								notInt = false;
								System.out.print("How much to withdraw from your " + currentUser.savingsAcct.getAccountType() + "? ");
								try{
									withdrawAmt = scan.nextDouble();
									notInt = false;
									scan.nextLine();
								}	catch(InputMismatchException ime){
									notInt = true;
									scan.nextLine();
									System.out.println("Please enter a valid amount for withdrawal: " + ime.getMessage());
								}
								withComplete = currentUser.savingsAcct.withdraw(withdrawAmt);
							}
							currentUser.savingsAcct.setCombinedBalance(currentUser.savingsAcct.getBalance() + currentUser.checkingAcct.getBalance());
							double combined = currentUser.savingsAcct.getCombinedBalance();
							if(combined <= 1000 && combined > 0)
								currentUser.savingsAcct = new BasicSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined <= 10000 && combined > 1000)
								currentUser.savingsAcct = new PremiumSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							else if(combined > 10000)
								currentUser.savingsAcct = new DeluxeSavingAccount(currentUser.savingsAcct.getBalance(), currentUser.checkingAcct.getBalance());
							System.out.print('\n');
							
						}
						acctOption = 0;
					}
					else if(acctOption==4)
					{
						int years = 0;
						System.out.print('\n');
						notInt = false;
						System.out.print("In how many years? ");
						while(years<1 || notInt){
							try{
								years = scan.nextInt();
								notInt = false;
								scan.nextLine();
								while(years<1){
									System.out.println("Please enter a positive number for years");
									System.out.print("In how many years? ");
									years = scan.nextInt();
									scan.nextLine();
								}
							}	catch(InputMismatchException ime){
								notInt = true;
								scan.nextLine();
								System.out.println("Please enter a valid number of years in integers");
								System.out.print("In how many years? ");
							}
						}
						String balance[] = new String[years+1];
						String interest[] = new String[years];
						for(int i=0; i<=years; i++)
						{
							String a = String.format("%.2f",currentUser.savingsAcct.getBalanceAfterNumYears(i));
							balance[i] = a;
						}
						for(int j=0; j<years; j++)
						{
							double intr = currentUser.savingsAcct.getBalanceAfterNumYears(j+1) - currentUser.savingsAcct.getBalanceAfterNumYears(j);
							String a = String.format("%.2f",intr);
							interest[j] = a;
						}
						
						//print
						final Object[][] table = new String[years+3][];
						table[0] = new String[] {"Year", "Amount", "Interest"};
						table[1] = new String[] { "----", "------", "--------"};
						
						for(int i=0; i<years+1; i++)
						{
							if(i==years)
								table[i+2] = new String[] {String.valueOf(i), balance[i]," "};
							else{
							table[i+2] = new String[] {String.valueOf(i), balance[i], interest[i]};
							}								
						}
						
						for(final Object[] row : table)
							System.out.format("%15s%15s%15s\n", row);
						System.out.print('\n');
						acctOption = 0;
					}
				}
				option = 0;
			}
			else if(option==2){
				if(!bank.createUser(scan,bank)){
					continue;
				}
				option = 0;
					

		}
	}
		
	return;
}
	
	public void printUsers()
	{
		try{
			FileWriter fw = new FileWriter("userInfo.txt");
			PrintWriter pw = new PrintWriter(fw);
			pw.println(numUsers);
			for(int i=0; i<users.size(); i++)
			{
				pw.println(users.get(i).getUsername());
				pw.println(users.get(i).getPassword());
				pw.println(users.get(i).checkingAcct.getBalance());
				pw.println(users.get(i).savingsAcct.getBalance());
			}
			pw.close();
			fw.close();
		}	catch(IOException ioe){
			System.out.println("IOException occured: " + ioe.getMessage());
		}
	}
	
	public boolean accessUser(Scanner scan, BankSystem bank)
	{
		//Existing Account Holder
		String username = null, password = null;
		User input = null;
		while(input==null || (input!=null && password!=null && !input.getPassword().matches(password))){
			System.out.print('\t' + "Username: ");
			username = scan.nextLine();
			if(username.matches("q")){
				return false;
			}
			System.out.print('\t' + "Password: ");
			password = scan.nextLine();
			for(int i=0; i<numUsers; i++){
				if(users.get(i).getUsername().matches(username)){
					input = users.get(i);
				}
			}
			if(input==null || !input.getPassword().matches(password))
				System.out.println("I¡¯m sorry, but that username and password does not match any at our bank. Please try again (or enter ¡®q¡¯ to return to the main menu).");	
		}
		
		currentUser = input;
		System.out.print('\n');
		System.out.println("Welcome to your account, " + currentUser.getUsername() + "!");
		return true;
	}
	
	public boolean createUser(Scanner scan, BankSystem bank)
	{
		//Open a New Account
		System.out.print('\n');
		System.out.print('\t' + "Username: ");
		String username = scan.nextLine();
		
		String [] checkUsername = username.split(" ");

		while(checkUsername.length != 1 || checkDuplicate(username)){
			if(checkUsername.length!=1)
				System.out.println("Username cannot contain blank space. Please enter another username.");
			System.out.print('\t' + "Username: ");
			username = scan.nextLine();
			if(username.matches("q")){
				return false;
			}
			checkUsername = username.split(" ");
		}
		
		System.out.println("Great! \""+ username +  "\" is not in use!");
		System.out.print('\t' + "Password: ");
		String password = scan.nextLine();
		while(password.length()<1)
		{
			System.out.println("Password must contain at least one character. Please try again.");
			System.out.print('\t' + "Password: ");
			password = scan.nextLine();
		}
			
		System.out.print("How much would you like to deposit in Checking? ");
		double checking = scan.nextDouble();
			//EXCEPTION HANDLING FOR DOUBLE
		scan.nextLine();
		System.out.print("How much would you like to deposit in Saving? ");
		double savings = scan.nextDouble();
			//EXCEPTION HANDLING FOR DOUBLE
		scan.nextLine();
		
		User newUser = new User(username,password,checking,savings);
		users.add(newUser);
		usernames.add(username);
		numUsers++;
		System.out.print('\n');
		
		//Write the file to userInfo.txt
		printUsers();
		return true;
	}
	
	public void readAccounts()
	{
		//Read in existing (if any) user file
		try{
			FileReader fr = new FileReader("userInfo.txt");
			BufferedReader br = new BufferedReader(fr);
			String n = br.readLine();
			numUsers = Integer.valueOf(n);
			int num = numUsers;
			for(int i=0; i<num; i++){
				String username = br.readLine();
				String password = br.readLine();
				String c = br.readLine();
				double checking = Double.parseDouble(c);
				String s = br.readLine();
				double savings = Double.parseDouble(s);
				User u1 = new User(username, password, checking, savings);
				users.add(i,u1);
				usernames.add(i,username);
			}
			br.close();
			fr.close();
		}	catch(FileNotFoundException fnfe){
			return;
		}
			catch(IOException ioe){
			System.out.println("IOException occured: " + ioe.getMessage());
		}
	}
	
	public boolean checkDuplicate(String username)
	{
		for(int i=0; i<usernames.size(); i++){						
			if(username.matches(usernames.get(i))){
				System.out.println("I¡¯m sorry, but the username " + username + " is already associated with an account. Please try again (or enter ¡®q¡¯ to return to the main menu).");
				return true;
			}
		}
		return false;
	}
	
	public static void main(String [] args)
	{
		Scanner scan = new Scanner(System.in);
		BankSystem bank = new BankSystem();
		bank.readAccounts();
		bank.mainMenu(scan, bank);
		bank.printUsers();
	}
}
