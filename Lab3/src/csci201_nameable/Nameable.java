package csci201_nameable;

interface Nameable {
	public void setName(String newName);
	public String getName();
}

abstract class Pet implements Nameable {
	private String name;
	public void setName(String newName)
	{
		this.name = newName;
	}
	public String getName()
	{
		return this.name;
	}
	public abstract String speak();
}

class Cat extends Pet {
	public String speak()
	{
		return ("Meow Meow");
	}
}

class Dog extends Pet {
	public String speak()
	{
		return ("Ruff Ruff");
	}
}
