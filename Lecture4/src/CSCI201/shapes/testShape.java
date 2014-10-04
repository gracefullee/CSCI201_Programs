package CSCI201.shapes;

public class testShape
{
	public static void printShape(Shape s)
	{
		System.out.println(s.getName() + " has area " s.getArea());
	}
	
	public static void main(String [] args)
	{
		Rectangle r = new Rectangle("rectangle",3.0f, 4.0f);
		printShape(r);
		Triangle t = new Triangle("triangle", 2.0f, 3.0f);
		printShape(t);
		Square sq = new Square("square", 5.0f);
		printShape(sq);
		Shape sh = new Rectangle("rect", 4.0f, 5.0f);
		printShape(sh);
	}
}
