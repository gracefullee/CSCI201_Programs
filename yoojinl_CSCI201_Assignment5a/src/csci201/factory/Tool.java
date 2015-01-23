package csci201.factory;

public class Tool {
	private String name;
	private int quantity;
	private int buyPrice;
	private int sellPrice;
	
	public Tool(String name, int quantity)
	{
		this.name = name;
		this.quantity = quantity;
	}
	
	public void updateQuantity(int q)
	{
		this.quantity = q;
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
	
	public boolean incrementQuantity(int i)
	{
		quantity = quantity + i;
		return true;
	}
	
	public boolean decrementQuantity(int i)
	{
		if(i>quantity)
			return false;
		quantity = quantity - i;
		return true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
}
