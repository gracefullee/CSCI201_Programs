package csci201.factory;

import java.util.ArrayList;

public class Instruction{
	private FactoryWindow fw;
	private Tool [] tools;
	private String location;
	private int duration;
	ArrayList<Destination> moveList = new ArrayList<Destination>();
	
	public Instruction(Tool [] tools, String location, int duration, FactoryWindow fw)
	{
		this.fw = fw;
		this.tools = tools;
		this.location = location;
		this.duration = duration;
		
		for(int i=0; i<tools.length; i++)
		{
			if(tools[i].getName().contains("Screwdriver"))
				moveList.add(new Destination("Screwdriver", tools[i].getQuantity(), fw));
			else if(tools[i].getName().contains("Hammer"))
				moveList.add(new Destination("Hammer", tools[i].getQuantity(), fw));
			else if(tools[i].getName().contains("Plier"))
				moveList.add(new Destination("Plier", tools[i].getQuantity(), fw));
			else if(tools[i].getName().contains("Scissor"))
				moveList.add(new Destination("Scissors", tools[i].getQuantity(), fw));
			if(tools[i].getName().contains("Paintbrush"))
				moveList.add(new Destination("Paintbrush", tools[i].getQuantity(), fw));
		}
		
		moveList.add(new Destination(location, duration, 1, fw));
		
		for(int i=0; i<tools.length; i++)
		{
			if(tools[i].getName().contains("Screwdriver"))
				moveList.add(new Destination("Screwdriver", 0-tools[i].getQuantity(), fw));
			else if(tools[i].getName().contains("Hammer"))
				moveList.add(new Destination("Hammer", 0-tools[i].getQuantity(), fw));
			else if(tools[i].getName().contains("Plier"))
				moveList.add(new Destination("Plier", 0-tools[i].getQuantity(), fw));
			else if(tools[i].getName().contains("Scissor"))
				moveList.add(new Destination("Scissors", 0-tools[i].getQuantity(), fw));
			if(tools[i].getName().contains("Paintbrush"))
				moveList.add(new Destination("Paintbrush", 0-tools[i].getQuantity(), fw));
		}
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public Tool [] getTools()
	{
		return tools;
	}
	
	public ArrayList<Destination> getMoveList()
	{
		return moveList;
	}
	
}
