import java.util.Scanner;
import com.uscInc.Employee;

public class UserInput
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("How many employees are there? ");
		int numEmployees = scan.nextInt();
		scan.nextLine();
		Employee employees[] = new Employee[numEmployees];
		
		for(int i=0; i<numEmployees; i++){
			System.out.print("Enter name #" + (i+1) + ": ");
			String name = scan.nextLine();			
			System.out.print("Enter hourly rate #" + (i+1) + ": ");
			float hourlyRate = scan.nextFloat();
			scan.nextLine(); 
			employees[i] = new Employee(name, hourlyRate);
		}
		
		for(int i=0; i<numEmployees; i++){
			System.out.println(employees[i].getName() + " makes $" + employees[i].getHourlyRate() + " per hour.");
			System.out.println(employees[i].getName() + " makes $" + employees[i].getSalary() + " per year.");
		}
		
		scan.close();
	}
}
