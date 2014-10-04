package CSCI201.shapes;

public class Rectangle extends TwoDShape
{
	private float length, width;
	public Rectangle(String name, float length, float width)
	{
		super(name);
		this.length = length;
		this.width = width;
	}
	
	public float getArea()
	{
		return this.length*this.width;
	}
}
