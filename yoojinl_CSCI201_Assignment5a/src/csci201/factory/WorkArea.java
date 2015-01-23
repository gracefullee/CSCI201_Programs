package csci201.factory;

public class WorkArea {
	private String name;
	private String status;
	private int locationX;
	private int locationY;
	private int duration;
	
	public WorkArea(String name, String status)
	{
		this.name = name;
		this.status = status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	public int getDuration()
	{
		return this.duration;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public void setLocation(int x, int y)
	{
		this.locationX = x;
		this.locationY = y;
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
