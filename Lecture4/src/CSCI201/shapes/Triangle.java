package CSCI201.shapes;

public class Triangle extends TwoDShape
{
	private float base, height;
	public Triangle(String name, float base, float height)
	{
		super(name);
		this.base = base;
		this.height = height;
	}
	
	public float getArea()
	{
		return 0.5f*this.base*this.height;
	}
}
