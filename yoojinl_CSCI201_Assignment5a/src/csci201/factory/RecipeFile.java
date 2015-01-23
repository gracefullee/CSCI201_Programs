package csci201.factory;

import java.util.ArrayList;

public class RecipeFile {
	private int cost;
	private String path;
	private String name;
	private int quantity = 1;
	private int duration = 0;
	protected Material [] materials = new Material[3];
	private FactoryWindow fw;
	private ArrayList<Instruction> instructions;
	private ArrayList<Destination> fullMoveList = new ArrayList<Destination>();
	
	public RecipeFile(String path, String name, int cost, int [] materials, FactoryWindow fw, ArrayList<Instruction> instructions)
	{
		this.instructions = instructions;
		this.path = path;
		this.name = name;
		this.cost = cost;
		this.fw = fw;
	
		for(int i=0; i<materials.length; i++)
		{
			if(i==0)
				this.materials[i] = new Material("Wood",materials[i]);
			else if(i==1)
				this.materials[i] = new Material("Metal",materials[i]);
			else if(i==2)
				this.materials[i] = new Material("Plastic",materials[i]);
		}
		
		fullMoveList.add(new Destination("Task Board",fw));
		
		for(int i=0; i<this.materials.length; i++)
		{
			if(this.materials[i].getQuantity()>0)
				fullMoveList.add(new Destination(this.materials[i].getName(), this.materials[i].getQuantity(), fw));
		}
		
		for(int i=0; i<this.instructions.size(); i++)
		{
			ArrayList<Destination> ml = this.instructions.get(i).getMoveList();
			for(int j=0; j<ml.size(); j++)
			{
				if(ml.get(j).getDuration()>0)
					this.duration += ml.get(j).getDuration();
				fullMoveList.add(ml.get(j));
			}
		}
		
		fullMoveList.add(new Destination("Task Board",fw));
		
		
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getQuantity()
	{
		return this.quantity;
	}
	
	public int getCost()
	{
		return this.cost;
	}
	
	public int getDuration()
	{
		return this.duration;
	}
	
	public ArrayList<Destination> getMoveList()
	{
		return this.fullMoveList;
	}
}
