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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StorePanel extends JPanel{
	
	private FactoryWindow fw;
	private DrawingPanel dp;
	private JPanel outerPanel;
	private JButton backButton;
	protected int money = 0;
	private MaterialContainer [] mc;
	private ToolContainer [] tools;
	private JButton [] buyButtons = new JButton [9];
	private JButton [] sellButtons = new JButton [9];
	private JButton hireButton; JButton fireButton;
	
	public StorePanel(FactoryWindow fw, DrawingPanel dp, MaterialContainer[] mc, ToolContainer[] tools, int money, JPanel outerPanel)
	{
		this.fw = fw;
		this.dp = dp;
		this.outerPanel = outerPanel;
		this.backButton = new JButton(new ImageIcon("back.png"));
		this.backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				CardLayout c1 = (CardLayout)outerPanel.getLayout();
				c1.show(outerPanel, "factory");
			}
		});
		this.setLayout(null);
		Dimension backButtonDim = backButton.getPreferredSize();
		this.backButton.setBorderPainted(false);
		this.backButton.setContentAreaFilled(false);
		this.backButton.setBounds(80, 20, backButtonDim.width, backButtonDim.height);
		this.add(backButton);
		this.mc = mc;
		this.tools = tools;
		this.money = money;
		
		int x = 190;
		int y = 110;
		
		for(int i=0; i<9; i++)
		{
			Dimension buttonDim;
			if(i!=8)
			{
				buyButtons[i] = new JButton("Buy");
				sellButtons[i] = new JButton("Sell");
				buttonDim = sellButtons[i].getPreferredSize();
			}
			else{
				buyButtons[i] = new JButton("Hire");
				sellButtons[i] = new JButton("Fire");
				buttonDim = buyButtons[i].getPreferredSize();
			}
			buyButtons[i].setBounds(x, y, buttonDim.width, buttonDim.height);
			int price = 0;
			boolean isTool = false;
			if(i>=0 && i<5)
			{
				price = tools[i].getBuyPrice();
				isTool = true;
			}
			else if(i>=5 && i<8)
			{
				price = mc[i-5].getBuyPrice();
				isTool = false;
			}
			else{
				price = 15;
			}
			if(isTool)
				buyButtons[i].addActionListener(new toolBuyButtonClicked(price,this,tools[i],fw));
			else if(!isTool && price!=15)
				buyButtons[i].addActionListener(new materialBuyButtonClicked(price,this,mc[i-5],fw));
			else if(!isTool && price==15)
				buyButtons[i].addActionListener(new workerBuyButtonClicked(price,this,fw));
			sellButtons[i].setBounds(x, y+35, buttonDim.width, buttonDim.height);
			if(i>=0 && i<5)
			{
				price = tools[i].getSellPrice();
				isTool = true;
			}
			else if(i>=5 && i<8)
			{
				price = mc[i-5].getSellPrice();
				isTool = false;
			}
			else{
				price = 15;
			}
			if(isTool)
				buyButtons[i].addActionListener(new toolSellButtonClicked(price,this,tools[i],fw));
			else if(!isTool && price!=15)
				buyButtons[i].addActionListener(new materialSellButtonClicked(price,this,mc[i-5],fw));
			else if(!isTool && price==15)
				buyButtons[i].addActionListener(new workerSellButtonClicked(price,this,fw));
			this.add(buyButtons[i]);
			this.add(sellButtons[i]);
			y += 80;
			if(i==4)
			{
				x = 430;
				y = 110;
			}
		}
	}
	
	public void updateData(MaterialContainer[] mc, ToolContainer[] tools, int money)
	{
		this.mc = mc;
		this.tools = tools;
		this.money = money;
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
		
		x = 50;
		y = 120;
		int distance = 30;
		for(int i=0; i<9; i++)
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
			else if(i==5)
				g.drawString("WOOD", x+4, y);
			else if(i==6)
				g.drawString("METAL", x+4, y);
			else if(i==7)
				g.drawString("PLASTIC", x+1, y);
			else if(i==8)
				g.drawString("WORKER", x, y);
			y += 50 + distance;
			if(i==4)
			{
				x = 300;
				y = 120;
			}
		}
		
		x = 160;
		y = 130;
		for(int i=0; i<9; i++)
		{
			g.setFont(new Font("Arial", Font.PLAIN , 14));
			if(i==0)
			{
				g.drawString("$10", x, y);
				g.drawString("$7", x, y + 30);
				tools[i].setPrice(10, 7);
			}
			else if(i==1)
			{
				g.drawString("$12", x, y);
				g.drawString("$9", x, y + 30);
				tools[i].setPrice(12, 9);
			}
			else if(i==2)
			{
				g.drawString("$5", x, y);
				g.drawString("$3", x, y + 30);
				tools[i].setPrice(5, 3);
			}
			else if(i==3)
			{
				g.drawString("$11", x, y);
				g.drawString("$9", x, y + 30);
				tools[i].setPrice(11, 9);
			}
			else if(i==4)
			{
				g.drawString("$9", x, y);
				g.drawString("$7", x, y + 30);
				tools[i].setPrice(9, 7);
			}
			else if(i==5)
			{
				g.drawString("$1", x, y);
				g.drawString("$1", x, y + 30);
				mc[i-5].setPrice(1, 1);
			}
			else if(i==6)
			{
				g.drawString("$3", x, y);
				g.drawString("$2", x, y + 30);
				mc[i-5].setPrice(3, 2);
			}
			else if(i==7)
			{
				g.drawString("$2", x, y);
				g.drawString("$1", x, y + 30);
				mc[i-5].setPrice(2, 1);
			}
			else if(i==8)
			{
				g.drawString("$15", x, y);
				g.drawString("$15", x, y + 30);
			}
			y += 50 + distance;
			if(i==4)
			{
				x = 400;
				y = 130;
			}
		}
		
		x = 50;
		y = 125;
		for(int i=0; i<9; i++)
		{
			g.setColor(Color.BLACK);
			if(i==0)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("screwdriver.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==1)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("hammer.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==2)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("paintbrush.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==3)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("pliers.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==4)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("scissors.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==5)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("wood.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==6)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("metal.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==7)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("plastic.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==8)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("worker.png");
				g.drawImage(img,x,y,this);
			}
			y += 50 + distance;
			if(i==4)
			{
				x = 300;
				y = 125;
			}
		}
		
		for(int i=0; i<5; i++)
		{
			int capacity = tools[i].getCapacity();
			int mcX = tools[i].getLocationX();
			int mcY = tools[i].getLocationY();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN , 20));
			g.drawString(capacity + "", mcX + 15, mcY + 32);
		}
		
		x = 310;
		y = 150;
		
		for(int i=0; i<3; i++)
		{
			int quantity = mc[i].getQuantity();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN , 20));
			g.drawString(quantity + "", x, y+8);
			y += 80;
		}
			
	}
}

class toolBuyButtonClicked implements ActionListener
{
	private int price;
	private StorePanel sp;
	private ToolContainer tool;
	private FactoryWindow fw;
	
	public toolBuyButtonClicked(int price,StorePanel sp, ToolContainer tool, FactoryWindow fw)
	{
		this.price = price;
		this.sp = sp;
		this.tool = tool;
		this.fw = fw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(sp.money>=price)
		{
			tool.updateQuantity(tool.getQuantity()+1);
			tool.updateCapacity(tool.getCapacity()+1);
			fw.decreaseMoney(price);
			sp.repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money to purchase this item",  "Insufficient Funds", JOptionPane.ERROR_MESSAGE); 
		}
	}
	
}

class materialBuyButtonClicked implements ActionListener
{
	private int price;
	private StorePanel sp;
	private MaterialContainer mc;
	private FactoryWindow fw;
	
	public materialBuyButtonClicked(int price,StorePanel sp, MaterialContainer mc, FactoryWindow fw)
	{
		this.price = price;
		this.sp = sp;
		this.mc = mc;
		this.fw = fw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(sp.money>=price)
		{
			mc.setQuantity(mc.getQuantity()+1);
			fw.decreaseMoney(price);
			sp.repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money to purchase this item",  "Insufficient Funds", JOptionPane.ERROR_MESSAGE); 
		}
	}
}

class workerBuyButtonClicked implements ActionListener
{
	private int price;
	private StorePanel sp;
	private FactoryWindow fw;
	
	public workerBuyButtonClicked(int price,StorePanel sp, FactoryWindow fw)
	{
		this.price = price;
		this.sp = sp;
		this.fw = fw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(sp.money>=price)
		{
			fw.addWorker();
			fw.decreaseMoney(price);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sorry! You do not have enough money to purchase a worker",  "Insufficient Funds", JOptionPane.ERROR_MESSAGE); 
			
		}
	}
}

class toolSellButtonClicked implements ActionListener
{
	private int price;
	private StorePanel sp;
	private ToolContainer tool;
	private FactoryWindow fw;
	
	public toolSellButtonClicked(int price,StorePanel sp, ToolContainer tool, FactoryWindow fw)
	{
		this.price = price;
		this.sp = sp;
		this.tool = tool;
		this.fw = fw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(tool.getQuantity()>1)
		{
			tool.updateQuantity(tool.getQuantity()-1);
			tool.updateCapacity(tool.getCapacity()-1);
			fw.increaseMoney(price);
			sp.repaint();
		}
		else if(tool.getCapacity()==0)
		{
			JOptionPane.showMessageDialog(null, "Sorry! You do not have enough " + tool.getName() + " to sell",  "Insufficient Tools", JOptionPane.ERROR_MESSAGE); 
		}
		else if(tool.getQuantity()==0)
		{
			JOptionPane.showMessageDialog(null, "Sorry! all " + tool.getName() + " is in use by the workers",  "All Tools in Use", JOptionPane.ERROR_MESSAGE); 
		}
	}
	
}

class materialSellButtonClicked implements ActionListener
{
	private int price;
	private StorePanel sp;
	private MaterialContainer mc;
	private FactoryWindow fw;
	
	public materialSellButtonClicked(int price,StorePanel sp, MaterialContainer mc, FactoryWindow fw)
	{
		this.price = price;
		this.sp = sp;
		this.mc = mc;
		this.fw = fw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(mc.getQuantity()>1)
		{
			mc.setQuantity(mc.getQuantity()-1);
			fw.increaseMoney(price);
			sp.repaint();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sorry! You do not have enough material to sell",  "Insufficient Materials", JOptionPane.ERROR_MESSAGE); 
		}
	}
}

class workerSellButtonClicked implements ActionListener
{
	private int price;
	private StorePanel sp;
	private FactoryWindow fw;
	
	public workerSellButtonClicked(int price,StorePanel sp, FactoryWindow fw)
	{
		this.price = price;
		this.sp = sp;
		this.fw = fw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean found = false;
		for(int i=0; i<fw.workers.size(); i++)
		{
			if(!fw.workers.get(i).visible)
			{
				found = true;
				found = fw.removeWorker(i);
				if(found)
					fw.decreaseMoney(price);
			}
		}
		if(fw.workers.size()==0 && !found)
		{
			JOptionPane.showMessageDialog(null, "Sorry! You do not have any workers to sell",  "No Workers Available", JOptionPane.ERROR_MESSAGE); 
		}
		else if(!found)
		{
			JOptionPane.showMessageDialog(null, "Sorry! All workers are working at the moment. Please try again later",  "No Workers Available", JOptionPane.ERROR_MESSAGE); 
		}
	}
}



