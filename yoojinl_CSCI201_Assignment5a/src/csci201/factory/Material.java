package csci201.factory;

public class Material {
	private String name;
	private int quantity;
	
	public Material(String name, int quantity)
	{
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public boolean decrementQuantity(int i)
	{
		if(i>quantity)
			return false;
		quantity = quantity - i;
		return true;
	}
}
