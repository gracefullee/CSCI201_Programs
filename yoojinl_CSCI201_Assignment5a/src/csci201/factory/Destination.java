package csci201.factory;

public class Destination {
	private int x;
	private int y;
	private int duration;
	private String name;
	private FactoryWindow fw;
	private int quantity;
	private Worker currentWorker = null;
	
	public Destination(String name, int quantity, FactoryWindow fw)
	{
		this.fw = fw;
		this.name = name;
		this.duration = 0;
		this.quantity = quantity;
		this.x = fw.getX(name, currentWorker);
		this.y = fw.getY(name, currentWorker);
	}
	
	public Destination(String name, FactoryWindow fw)
	{
		this.fw = fw;
		this.name = name;
		this.duration = 0;
		this.quantity = 0;
		this.x = 550;
		this.y = 95;
	}
	
	public Destination(String name, int duration, int quantity, FactoryWindow fw)
	{		
		this.fw = fw;
		this.name = name;
		this.duration = duration;
		this.quantity = quantity;
		this.x = fw.getX(name, currentWorker);
		this.y = fw.getY(name, currentWorker);
	}
	
	public boolean updateLocation(Worker currentWorker)
	{
		this.currentWorker = currentWorker;
		this.x = fw.getX(name, currentWorker);
		this.y = fw.getY(name, currentWorker);
		if(this.x==-1||this.y==-1)
			return false;
		else{
			return true;
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
}
