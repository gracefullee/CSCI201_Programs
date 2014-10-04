package csci201_nameable;

public class testNameable 
{
	public static void main(String [] args)
	{
		Pet d = new Dog();
		d.setName("Macaron");
		Pet c = new Cat();
		c.setName("Jamong");
		System.out.println(d.getName() + " says " + d.speak());
		System.out.println(c.getName() + " says "+ c.speak());
		
		Dog myDog = new Dog();
		myDog.setName("Macaron");
		
		if(myDog instanceof Nameable)
			System.out.println(myDog.getName() + " is a Nameable!");
		
		if(myDog instanceof Pet)
			System.out.println(myDog.getName() + " is a Pet!");
		
		if(myDog instanceof Dog)
			System.out.println(myDog.getName() + " is a Dog!");
		else
			System.out.println(myDog.getName() + " is NOT a Dog!");

		if(!myDog notinstanceof Cat)
			System.out.println(myDog.getName() + " is a Cat!");
		else
			System.out.println(myDog.getName() + " is NOT a Cat!");

	}
}
