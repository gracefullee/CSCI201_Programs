package csci201.factory;

import java.util.ArrayList;


public class Worker {
	private FactoryWindow fw;
	private int x;
	private int y;
	private int workerID;
	private Task task;
	private ArrayList<Destination> moveList;
	private Destination currentMove;
	private int currentMoveIndex;
	private Destination prevMove = null;
	protected boolean sleep = false;
	protected boolean wait = false;
	protected boolean visible = false;
	private int price = 15;
	
	
	public Worker(int x, int y, int workerID, FactoryWindow fw)
	{
		this.x = x;
		this.y = y;
		this.workerID = workerID;
		this.fw = fw;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getID()
	{
		return workerID;
	}
	
	public void setTask(Task task)
	{
		this.task = task;
	}
	
	public Destination getMove()
	{
		return currentMove;
	}
	
	public int getDuration()
	{
		if(this.currentMove!=null)
			return currentMove.getDuration();
		else
			return 0;
	}
	
	public int getQuantity()
	{
		return currentMove.getQuantity();
	}
	
	public String getMoveName()
	{
		return currentMove.getName();
	}
	
	public void setMoveList(ArrayList<Destination> moveList)
	{
		this.moveList = moveList;
		if(!moveList.isEmpty())
		{
			this.currentMove = moveList.get(0);
			this.currentMoveIndex = 0;
		}
	}
	
	public void updateMoveList()
	{
		task = fw.getNextTask();
		if(task!=null)
		{
			moveList = fw.getMoveList(task);
			if(!moveList.isEmpty())
			{
				this.currentMove = moveList.get(0);
				this.currentMoveIndex = 0;
			}
		}
	}
	
	public void move()
	{
		if(task==null)
		{
			visible = false;
		}
		else{
			String destName = currentMove.getName();
			if(destName.matches("Task Board"))
			{
				int prevX = 0, prevY = 0, prevHallwayY = 0;
				if(prevMove!=null)
				{
					prevX = prevMove.getX();
					prevY = prevMove.getY();
					prevHallwayY = prevMove.getY() - 70;
				}
				if(prevMove!=null)
				{
					if(this.x==prevX && this.y<=prevY && this.y>prevHallwayY)
					{
						this.y -= 10;
					}
					else if(this.x==prevX && this.y==prevHallwayY)
					{
						this.x -= 10;
					}
					else if(this.x<prevX && this.x>110 && this.y==prevHallwayY)
					{
						this.x -= 10;
					}
					else if(this.x==110 && this.y==prevHallwayY)
					{
						prevMove = null;
					}
				}
	
				if(this.y==30 && this.x!=110)
				{
					this.x += 10;
				}
				else if(this.y==30 && this.x==110)
				{
					this.y += 15;
				}
				else if(this.y>30 && this.y<95 && this.x==110)
				{
					this.y += 10;
				}
				else if(this.y>95 && this.x==110)
				{
					this.y -= 10;
				}
				else if(this.y==95 && this.x==110)
				{
					this.x += 10;
				}
				else if(this.y==95 && this.x!=110 && this.x!=550)
				{
					this.x += 10;
				}
				else if(this.y==95 && this.x==550)
				{
					//End of Worker
					if(currentMoveIndex==moveList.size()-1)
					{
						if(task.isOrder)
							fw.parseJSON(task);
						fw.removeTask(task);
						updateMoveList();
					}
					if(moveList.size()>=currentMoveIndex+1 && currentMoveIndex==0)
					{
						task.setStatus("In Progress");
						currentMoveIndex++;
						currentMove = moveList.get(currentMoveIndex);
					}
				}
			}
			
			//Move to Materials
			else if(destName.matches("Wood")||destName.matches("Metal")||destName.matches("Plastic"))
			{
				prevMove = null;
				sleep = false;
				int destX = currentMove.getX() + 60;
				if(this.y==95 && this.x!=destX && this.x>destX)
				{
					this.x -= 10;
				}
				else if(this.y==95 && this.x!=destX && this.x<destX)
				{
					this.x += 10;
				}
				else if(this.y==95 && this.x==destX)
				{
					this.y -= 15;
				}
				else if(this.x==destX && this.y!=95 && this.y!=40)
				{
					this.y -=10;
				}
				else if(this.x!=destX && this.y==40)
				{
					this.y += 15;
				}
				else if(this.x!=destX && this.y!=40 && this.y!=95)
				{
					this.y += 10;
				}
				else if(this.x==destX && this.y==40)
				{
					fw.useMaterial(currentMove.getName(),currentMove.getQuantity());
					if(moveList.size()>=currentMoveIndex+1)
					{
						currentMoveIndex++;
						currentMove = moveList.get(currentMoveIndex);
					}
				}
			}
			
			//Move to Tools
			else if(destName.matches("Hammer")||destName.matches("Screwdriver")||destName.matches("Scissor")||destName.matches("Plier")||destName.contains("Paintbrush"))
			{
				sleep = false;
				int prevX = 0, prevY = 0, prevHallwayY = 0;
				if(prevMove!=null)
				{
					prevX = prevMove.getX();
					prevY = prevMove.getY();
					prevHallwayY = prevMove.getY() - 70;
				}
				int destX = currentMove.getX() + 60;
				int destY = currentMove.getY();
				if(prevMove!=null)
				{
					if(this.x==prevX && this.y<=prevY && this.y>prevHallwayY)
					{
						this.y -= 10;
					}
					else if(this.x==prevX && this.y==prevHallwayY)
					{
						this.x -= 10;
					}
					else if(this.x<prevX && this.x>destX && this.y==prevHallwayY)
					{
						this.x -= 10;
					}
					else if(this.x==destX && this.y==prevHallwayY)
					{
						prevMove = null;
					}
				}
				else if(this.y==40 && this.x!=destX)
				{
					this.y += 15;
				}
				else if(this.y!=40 && this.x!=destX && this.y<95)
				{
					this.y += 10;
				}
				else if(this.y==95 && this.x!=destX)
				{
					this.x -= 10;
				}
				else if(this.y>=95 && this.y<destY && this.x==destX)
				{
					this.y += 10;
				}
				else if(this.y>=95 && this.y>destY && this.x==destX)
				{
					this.y -= 10;
				}
				else if(this.y==destY && this.x==destX)
				{
					boolean result = fw.useTool(currentMove.getName(),currentMove.getQuantity());
					if(!result && !wait)
					{
						wait = true;
					}
					else if(result && wait)
					{	
						wait = false;
						if(moveList.size()>=currentMoveIndex+1)
						{
							currentMoveIndex++;
							currentMove = moveList.get(currentMoveIndex);
						}
					}
					else if(result && !wait)
					{
						if(moveList.size()>=currentMoveIndex+1)
						{
							currentMoveIndex++;
							currentMove = moveList.get(currentMoveIndex);
						}
					}
					
				}
			}
			
			//Move to Work Area
			else if(destName.matches("Anvil")||destName.matches("Workbench")||destName.matches("Painting Station")||destName.matches("Press")||destName.contains("Saw")||destName.matches("Furnace"))
			{
/*				if(currentMove.getX()!=x && currentMove.getX()!=y && !fw.checkWorkAreaStatus(currentMove.getX(),currentMove.getY()))
				{
					System.out.println(x + " " + y);
					System.out.println(currentMove.getName() + " " + currentMove.getX() + " " + currentMove.getY());
					currentMove.updateLocation(this);
					System.out.println(currentMove.getName() + " " + currentMove.getX() + " " + currentMove.getY());
				}
*/				int prevX = 0, prevY = 0, prevHallwayY = 0;
				if(prevMove!=null)
				{
					prevX = prevMove.getX();
					prevY = prevMove.getY();
					prevHallwayY = prevMove.getY() - 70;
				}
				int hallwayX = 110;
				int hallwayY = currentMove.getY() - 70;
				int destY = currentMove.getY();
				int destX = currentMove.getX();
				if(prevMove!=null && prevHallwayY!=hallwayY){
					if(this.x==prevX && this.y<=prevY && this.y>prevHallwayY)
					{
						this.y -= 10;
					}
					else if(this.x==prevX && this.y==prevHallwayY)
					{
						this.x -= 10;
					}
					else if(this.x<prevX && this.x>hallwayX && this.y==prevHallwayY)
					{
						this.x -= 10;
					}
					else if(this.x==hallwayX && this.y==prevHallwayY)
					{
						prevMove = null;
					}
				}
				else{
					if(this.x==hallwayX && this.y<hallwayY)
					{
						this.y += 10;
					}
					else if(this.x==hallwayX && this.y>hallwayY)
					{
						this.y -= 10;
					}
					else if(this.x==hallwayX && this.y==hallwayY)
					{
/*						if(!wait){
							boolean result = currentMove.updateLocation(this);
							if(!result && !wait)
							{
								wait = true;
							}
							else if(result && !wait){
								this.x += 10;
							}
						}
						else if(wait)
						{
							wait = false;
							this.x += 10;
						}
*/						this.x += 10;
					}
					else if(this.x>hallwayX && this.x<destX && this.y==hallwayY)
					{
						this.x += 10;
					}
					else if(this.x==destX && this.y==hallwayY)
					{
/*						if(!wait){
							boolean result = currentMove.updateLocation(this);
							if(!result && !wait)
							{
								wait = true;
							}
							else if(result && !wait){
								this.y += 10;
							}
						}
						else if(wait)
						{
							wait = false;
							this.y += 10;
						}
*/						this.y += 10;
					}
					else if(this.x==destX && this.y>hallwayY && this.y<destY)
					{
						this.y += 10;
					}
					else if(this.x!=destX && this.x>hallwayX && this.y>=destY && this.y<hallwayY)
					{	
						this.y += 10;
					}
					else if(this.x>destX && this.y==hallwayY)
					{
						this.x -= 10;
					}
					else if(this.x==destX && this.y==destY)
					{
						if(!sleep)
						{
							sleep = true;
							fw.changeWorkAreaStatus(x,y,currentMove.getDuration());
						}
						else if(sleep)
						{
							sleep = false;
							fw.changeWorkAreaStatus(x,y,0);
							if(moveList.size()-1>currentMoveIndex)
							{
								prevMove = currentMove;
								currentMoveIndex++;
								currentMove = moveList.get(currentMoveIndex);
							}
						}
					}
				}
			}
		}
	}
}
