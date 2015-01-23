package csci201.factory;

public class ToolContainer extends Tool{
	private int capacity;
	private int locationX;
	private int locationY;
	
	public ToolContainer(String name, int quantity)
	{
		super(name, quantity);
		this.capacity = quantity;
	}
	
	public void setLocation(int x, int y)
	{
		this.locationX = x;
		this.locationY = y;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public void updateCapacity(int c)
	{
		this.capacity = c;
	}
	
	public int getLocationX()
	{
		return locationX;
	}
	
	public int getLocationY()
	{
		return locationY;
	}
}

