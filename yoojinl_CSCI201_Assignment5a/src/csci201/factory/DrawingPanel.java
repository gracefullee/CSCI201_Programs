package csci201.factory;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel{

	/**
	 * 
	 */
	private JPanel outerPanel;
	private JButton storeButton;
	private JButton orderButton;
	private static final long serialVersionUID = 1L;
	private MaterialContainer [] mc;
	private ToolContainer [] tools;
	private WorkArea [] workArea;
	private ArrayList<Worker> workers;
	protected int money = 0;
	
	public DrawingPanel(MaterialContainer[] mc, ToolContainer[] tools, WorkArea [] workArea, ArrayList<Worker> workers, int money, JPanel outerPanel)
	{
		this.outerPanel = outerPanel;
		this.storeButton = new JButton(new ImageIcon("shop.png"));
		this.storeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				CardLayout c1 = (CardLayout)outerPanel.getLayout();
				c1.show(outerPanel, "store");
			}
		});
		this.orderButton = new JButton(new ImageIcon("orders.png"));
		this.orderButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				CardLayout c1 = (CardLayout)outerPanel.getLayout();
				c1.show(outerPanel, "order");
			}
		});
		this.setLayout(null);
		Dimension storeButtonDim = storeButton.getPreferredSize();
		this.storeButton.setBorderPainted(false);
		this.storeButton.setContentAreaFilled(false);
		this.storeButton.setBounds(80, 30, storeButtonDim.width, storeButtonDim.height);
		this.add(storeButton);
		Dimension orderButtonDim = orderButton.getPreferredSize();
		this.orderButton.setBorderPainted(false);
		this.orderButton.setContentAreaFilled(false);
		this.orderButton.setBounds(510, 30, orderButtonDim.width, orderButtonDim.height);
		this.add(orderButton);
		this.mc = mc;
		this.tools = tools;
		this.workArea = workArea;
		this.workers = workers;
		this.money = money;
	}

	public void updateData(Worker worker)
	{
		for(int i=0; i<workers.size(); i++)
		{
			if(workers.get(i).getID()==worker.getID())
				workers.add(i, worker);
		}
	}
	
	public void updateData(MaterialContainer[] mc, ToolContainer[] tools, WorkArea [] workArea, ArrayList<Worker> workers, int money)
	{
		this.mc = mc;
		this.tools = tools;
		this.workArea = workArea;
		this.workers = workers;
		this.money = money;
	}
	
	public MaterialContainer getMaterial(String name)
	{
		for(int i=0; i<mc.length; i++)
		{
			if(name==mc[i].getName())
				return mc[i];
		}
		return null;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//Draw Money
		int x = 20;
		int y = 40;
		g.setFont(new Font("SEGOE UI", Font.PLAIN , 20));
		g.drawString("MONEY", x, y);
		y = 60;
		g.drawString("$" + money, x, y);
		
		//Draw Material Containers
		x = 170;
		y = 35;
		int distance = 80;
		for(int i=0; i<3; i++)
		{
			g.setFont(new Font("Arial", Font.PLAIN , 14));
			if(i==0)
				g.drawString("WOOD", x+4, y);
			else if(i==1)
				g.drawString("METAL", x+4, y);
			else if(i==2)
				g.drawString("PLASTIC", x+1, y);
			x += 50 + distance;
		}
		
		x = 170;
		y = 40;
		for(int i=0; i<3; i++)
		{
			g.setColor(Color.BLACK);
			if(i==0)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("wood.png");
				mc[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(i==1)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("metal.png");
				mc[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(i==2)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("plastic.png");
				mc[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
				//MaterialContainer m = getMaterial("Plastic");
				//quantity = m.getQuantity();
			}
			x += 50 + distance;
		}
		
		for(int i=0; i<3; i++)
		{
			int quantity = mc[i].getQuantity();
			int mcX = mc[i].getLocationX();
			int mcY = mc[i].getLocationY();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN , 20));
			if(quantity>10)
				g.drawString("" + quantity, mcX + 14, mcY + 32);
			else
				g.drawString("" + quantity, mcX + 19, mcY + 32);
		}
		
		//Draw Tools
		x = 50;
		y = 120;
		distance = 30;
		for(int i=0; i<5; i++)
		{
			g.setFont(new Font("Arial", Font.PLAIN , 14));
			if(i==0)
				g.drawString("SCREWDRIVERS", x-20, y);
			else if(i==1)
				g.drawString("HAMMERS", x-7, y);
			else if(i==2)
				g.drawString("PAINTBRUSHES", x-16, y);
			else if(i==3)
				g.drawString("PLIERS", x+3, y);
			else if(i==4)
				g.drawString("SCISSORS", x-5, y);
			y += 50 + distance;
		}
		
		
		x = 50;
		y = 125;
		for(int i=0; i<5; i++)
		{
			g.setColor(Color.BLACK);
			if(i==0)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("screwdriver.png");
				tools[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(i==1)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("hammer.png");
				tools[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(i==2)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("paintbrush.png");
				tools[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(i==3)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("pliers.png");
				tools[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(i==4)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("scissors.png");
				tools[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			y += 50 + distance;
		}
		
		for(int i=0; i<5; i++)
		{
			int capacity = tools[i].getCapacity();
			int quantity = tools[i].getQuantity();
			int mcX = tools[i].getLocationX();
			int mcY = tools[i].getLocationY();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN , 20));
			if(quantity>10)
				g.drawString(quantity + "/" + capacity , mcX + 5, mcY + 32);
			else
				g.drawString(quantity + "/" + capacity, mcX + 10, mcY + 32);
		}
		
		
		//Draw Work Areas
		g.setFont(new Font("Arial", Font.PLAIN , 14));
		x = 210;
		y = 230;
		g.drawString("ANVILS", x, y);
		x = 350;
		g.drawString("WORKBENCHES", x, y);
		x = 200;
		y = 380;
		g.drawString("FURNACES", x, y);
		x = 360;
		g.drawString("TABLE SAWS", x, y);
		x = 235;
		y = 530;
		g.drawString("PAINTING STATIONS", x, y);
		x = 452;
		g.drawString("PRESS", x, y);
		
		x = 170;
		y = 165;
		for(int i=0; i<15; i++)
		{
			if(workArea[i].getName().matches("Anvil"))
			{
				Image img = Toolkit.getDefaultToolkit().getImage("anvil.png");
				workArea[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(workArea[i].getName().matches("Workbench"))
			{
				Image img = Toolkit.getDefaultToolkit().getImage("workbench.png");
				workArea[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(workArea[i].getName()=="Furnace")
			{
				Image img = Toolkit.getDefaultToolkit().getImage("furnace.png");
				workArea[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(workArea[i].getName()=="Saw")
			{
				Image img = Toolkit.getDefaultToolkit().getImage("tablesaw.png");
				workArea[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(workArea[i].getName()=="Painting Station")
			{
				Image img = Toolkit.getDefaultToolkit().getImage("paintingstation.png");
				workArea[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			else if(workArea[i].getName()=="Press")
			{
				Image img = Toolkit.getDefaultToolkit().getImage("press.png");
				workArea[i].setLocation(x, y);
				g.drawImage(img,x,y,this);
			}
			if(workArea[i].getStatus()!=null)
			{
				if(workArea[i].getStatus().matches("OPEN"))
				{
					g.setColor(Color.GREEN);
					g.drawString(workArea[i].getStatus(), x+7, y-5);
				}
				else if(workArea[i].getStatus().matches("IN USE"))
				{
					g.setColor(Color.RED);
					g.drawString(Integer.toString(workArea[i].getDuration())+"S", x+17, y-5);
				}
			}
			x += 70;
			if(i==4 || i==9)
			{
				y += 150;
				x = 170;
			}
			
		}
		
		if(workers!=null)
		{
			for(int i=0; i<workers.size(); i++)
			{
				x = workers.get(i).getX();
				y = workers.get(i).getY();
				if(workers.get(i).visible)
				{
					Image img = Toolkit.getDefaultToolkit().getImage("worker.png");
					g.drawImage(img,x,y,this);
				}
			}
		}
	}


}
