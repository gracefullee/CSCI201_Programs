package csci201.factory;

public class FactoryFile {
	
	private String path;
	private int Money = 0;
	private int Worker = 0;
	private int Screwdriver = 0;
	private int Hammer = 0;
	private int Paintbrush = 0;
	private int Pliers = 0;
	private int Scissors = 0;
	private int Wood = 0;
	private int Metal = 0;
	private int Plastic = 0;
	
	public FactoryFile(String path, int m, int w, int sd, int h, int pb, int p, int s, int wood, int metal, int plastic)
	{
		this.Money = m;
		this.path = path;
		this.Worker = w;
		this.Screwdriver = sd;
		this.Hammer = h;
		this.Paintbrush = pb;
		this.Pliers = p;
		this.Scissors = s;
		this.Wood = wood;
		this.Metal = metal;
		this.Plastic = plastic;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public int getWorkers()
	{
		return Worker;
	}
	
	public int getScrewdriver()
	{
		return Screwdriver;
	}
	
	public int getHammer()
	{
		return Hammer;
	}
	
	public int getPaintbrush()
	{
		return Paintbrush;
	}
	
	public int getPlier()
	{
		return Pliers;
	}
	
	public int getScissors()
	{
		return Scissors;
	}
	
	public int getMoney()
	{
		return Money;
	}
	
	public int getWood()
	{
		return Wood;
	}
	
	public int getMetal()
	{
		return Metal;
	}
	
	public int getPlastic()
	{
		return Plastic;
	}
}
