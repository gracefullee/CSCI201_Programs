USC ID: 3272884875/yoojinl
Name: Yoo Jin Lee

Assignment #1: Bank Account System

CheckingAccount and SavingAccount both inherit from BaseAccount, which only has deposit and withdrawal methods implemented.
There are three types of SavingAccount: Base, Premium, and Deluxe which all inherit from SavingAccount.
All three types are included in SavingsAccount.java because of their short length and convenience.

Please run BankSystem.java

Program displays a message for the user to try again if there is no existing users.
There is InputMismatchException handling for each user input made.

Under BankSystem.java, I created six separate methods.
main function of this program is very short and simple.
accessUser(Scanner, BankSystem) is equivalent to option 1 on initial menu, and returns a boolean. (returns false if user inputs q)
createUser(Scanner, BankSystem) is equivalent to option 2 on initial menu, also returning a boolean (Returns false if user inputs q)
checkDuplicate(String) checks if username desired in option 2 currently exist in the system and returns a boolean
mainMenu(Scanner, BankSystem) covers all these options, and is the main start of the program (no return value).
printUsers() is performed at the end when user inputs 5 to log out AND when a new account is created through option 2.
readAccounts() is performed intially when program launches, reading for "userInfo.txt" if it exists.
Program will generate "userInfo.txt" upon new account holder if "userInfo.txt" does not exist.

"userInfo.txt" is formatted that..
	int that represents current number of account holders
	for(int i=0; i<numUsers; i++){
		username
		password
		checking account balance
		saving account balance
	}

Thank you!