package csci201.factory;

public class MaterialContainer extends Material{
	private int locationX;
	private int locationY;
	private int buyPrice;
	private int sellPrice;
	
	public MaterialContainer(String name, int quantity)
	{
		super(name,quantity);
	}
	
	public void setLocation(int x, int y)
	{
		this.locationX = x;
		this.locationY = y;
	}
	
	public void setPrice(int buy, int sell)
	{
		this.buyPrice = buy;
		this.sellPrice = sell;
	}
	
	public int getBuyPrice()
	{
		return this.buyPrice;
	}
	
	public int getSellPrice()
	{
		return this.sellPrice;
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
