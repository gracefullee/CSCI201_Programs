package csci201.factory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Order extends JPanel{
	private JButton acceptButton, declineButton;
	private JLabel itemCostLabel, durationLabel;
	protected RecipeFile rcp;
	private FactoryWindow fw;
	private OrderPanel op;
	
	public Order(RecipeFile rcp, FactoryWindow fw, OrderPanel op)
	{
		Dimension orderSize = new Dimension(550,80);
		this.setMinimumSize(orderSize);
		this.setPreferredSize(orderSize);
		this.setMaximumSize(orderSize);
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.rcp = rcp;
		this.fw = fw;
		this.op = op;
		itemCostLabel = new JLabel(rcp.getName() + " - $" + rcp.getCost());
		itemCostLabel.setFont(new Font("SEGOE UI", Font.PLAIN , 16));
		durationLabel = new JLabel(rcp.getDuration() + "s  ");
		durationLabel.setFont(new Font("SEGOE UI", Font.PLAIN , 16));
		acceptButton = new JButton("ACCEPT");
		acceptButton.setFont(new Font("SEGOE UI", Font.PLAIN , 14));
		acceptButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				
				fw.acceptOrder(Order.this);
				op.removeOrder(Order.this);
			}
		});
		declineButton = new JButton("DECLINE");
		declineButton.setFont(new Font("SEGOE UI", Font.PLAIN , 14));
		declineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				fw.declineOrder(Order.this);
				op.removeOrder(Order.this);
			}
		});
		add(itemCostLabel);
		add(Box.createGlue());
		add(durationLabel);
		add(acceptButton);
		add(declineButton);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//Draw Material Containers
		int x = 125;
		int y = 15;
		int distance = 20;
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
		
		x = 125;
		y = 20;
		for(int i=0; i<3; i++)
		{
			g.setColor(Color.BLACK);
			if(i==0)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("wood.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==1)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("metal.png");
				g.drawImage(img,x,y,this);
			}
			else if(i==2)
			{
				Image img = Toolkit.getDefaultToolkit().getImage("plastic.png");
				g.drawImage(img,x,y,this);
				//MaterialContainer m = getMaterial("Plastic");
				//quantity = m.getQuantity();
			}
			x += 50 + distance;
		}
		
		x = 125;
		y = 20;
		for(int i=0; i<3; i++)
		{
			int quantity = rcp.materials[i].getQuantity();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN , 20));
			g.drawString("" + quantity, x + 19, y + 32);
			x += 50 + distance;
		}
	}
}
