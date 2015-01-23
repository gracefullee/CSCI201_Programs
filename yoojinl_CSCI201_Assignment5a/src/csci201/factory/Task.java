package csci201.factory;

public class Task {
	private String name;
	private String status;
	protected boolean isOrder;
	
	public Task(String name)
	{
		this.name = name;
		this.status = "Not Built";
		this.isOrder = false;
	}
	
	public Task(String name, boolean t)
	{
		this.name = name;
		this.status = "Not Built";
		this.isOrder = t;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getStatus()
	{
		return this.status;
	}
}
