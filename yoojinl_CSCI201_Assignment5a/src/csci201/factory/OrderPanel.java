package csci201.factory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OrderPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FactoryWindow fw;
	private JButton backButton;
	private JLabel moneyLabel;
	private JPanel outerPanel, topPanel;
	private JScrollPane orderSP;
	private JPanel orderList;
	private File OrderDirectory;
	private File [] OrderFiles;
	private RecipeFile [] Orders;
	
	public OrderPanel(FactoryWindow fw, JPanel outerPanel)
	{
		this.fw = fw;
		this.outerPanel = outerPanel;
		this.topPanel = new JPanel();

		this.backButton = new JButton(new ImageIcon("back.png"));
		this.backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				CardLayout c1 = (CardLayout)outerPanel.getLayout();
				c1.show(OrderPanel.this.outerPanel, "factory");
			}
		});
		this.setLayout(new BorderLayout());
		this.backButton.setBorderPainted(false);
		this.backButton.setContentAreaFilled(false);
		this.moneyLabel = new JLabel("MONEY: $" + fw.money);
		this.moneyLabel.setFont(new Font("SEGOE UI", Font.PLAIN , 20));
		this.topPanel.add(moneyLabel);
		this.topPanel.add(backButton);
		this.add(topPanel, BorderLayout.NORTH);
		
		Dimension spSize = new Dimension(400,400);
		orderList = new JPanel();
		orderList.setLayout(new BoxLayout(orderList,BoxLayout.Y_AXIS));
		orderSP= new JScrollPane(orderList);
		orderSP.setMinimumSize(spSize);
		orderSP.setPreferredSize(spSize);
		orderSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(orderSP, BorderLayout.CENTER);
		
		
		OrderDirectory = new File("orders/");
		OrderFiles = OrderDirectory.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".rcp");
		    }
		});
		
		if(OrderFiles != null)
		{
			Orders = new RecipeFile [OrderFiles.length];
			for(int i=0; i<OrderFiles.length; i++)
			{
				parseRCP(OrderFiles[i].getPath(), i);
				orderList.add(new Order(Orders[i],this.fw, this));
				revalidate();
				repaint();
			}
		}
		
	}
	
	
	
	private void parseRCP(String FileName, int i)
	{
		try {
			FileReader fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			String name = br.readLine();
				String [] rcpFirst = name.split(":");
				rcpFirst[0] = rcpFirst[0].replaceAll("\\[", "");
				rcpFirst[1] = rcpFirst[1].replaceAll("\\]", "");
				rcpFirst[1] = rcpFirst[1].replace("$","");
				
				name = rcpFirst[0];
				int cost = Integer.parseInt(rcpFirst[1]);
			ArrayList<Instruction> instructions = new ArrayList<Instruction>();
			int [] materials = new int[3];
			while(br.ready())
			{
				String line = br.readLine();
				line = line.replaceAll("\\[","");
				line = line.replaceAll("\\]","");
				//Check for materials
				if(line.startsWith("Metal") || line.startsWith("Plastic") || line.startsWith("Wood"))
				{
					String[] m = line.split(":");
					m[1] = m[1].replaceAll(" ","");
					int num = Integer.parseInt(m[1]);
					if(m[0].matches("Wood"))
					{
						materials[0]=num;
					}
					else if(m[0].matches("Metal"))
						materials[1]=num;
					else if(m[0].matches("Plastic"))
						materials[2]=num;
				}
				//Check for instructions
				else if(line.startsWith("Use"))
				{
					String location = null;
					int duration = -1;
					ArrayList<Tool> tools = new ArrayList<Tool>();
					String [] l = line.split(" ");
					for(int j=0; j<l.length; j++)
					{
						//quantity of tools
						if(l[j].contains("x") && isInteger(l[j].charAt(0)))
						{
							int q = Integer.parseInt(l[j].replaceAll("x", ""));
							String toolName = l[j+1];
							if(toolName.contains("Screwdriver") || toolName.contains("Paintbrush") || toolName.contains("Hammer") || toolName.contains("Plier") || toolName.contains("Scissor"))
							{
								tools.add(new Tool(toolName,q));
								j++;
							}
						}
						else if(l[j].matches("at"))
						{
							String place = l[j+1];
							if(place.contains("Saw")||place.contains("Press")||place.contains("Anvil")||place.contains("Painting")||place.contains("Furnace")||place.contains("Workbench"))
							{
								if(place.contains("Painting"))
								{
									location = place + " Station";
									if(l[j+2].matches("Station") &&l [j+3].matches("for") && isInteger(l[j+4].charAt(0)))
									{
										l[j+4] = l[j+4].replaceAll("s", "");
										duration = Integer.parseInt(l[j+4]);
										j+=4;
									}
								}
								else{
									location = place;
									if(l[j+2].matches("for") && isInteger(l[j+3].charAt(0)))
									{
										l[j+3] = l[j+3].replaceAll("s", "");
										duration = Integer.parseInt(l[j+3]);
										j += 3;
									}
								}
							}
						}
						else if(l[j].contains("Saw")||l[j].contains("Press")||l[j].contains("Anvil")||l[j].contains("Painting")||l[j].contains("Furnace")||l[j].contains("Workbench"))
						{
							if(l[j].contains("Painting"))
							{
								location = l[j] + " Station";
								if(l[j+1].matches("Station") && l[j+2].matches("for") && isInteger(l[j+3].charAt(0)))
								{
									l[j+3] = l[j+3].replaceAll("s", "");
									duration = Integer.parseInt(l[j+3]);
									j+=3;
								}
							}
							else{
								location = l[j];
								if(l[j+1].matches("for") && isInteger(l[j+2].charAt(0)))
								{
									l[j+2] = l[j+2].replaceAll("s", "");
									duration = Integer.parseInt(l[j+2]);
									j += 2;
								}
							}
						}
					}
					
					if(location!=null && duration!=-1)
					{
						Tool [] t = new Tool[tools.size()];
						t = tools.toArray(t);
						Instruction inst = new Instruction(t, location, duration, fw);
						instructions.add(inst);
					}
				}
			}
			Orders[i] = new RecipeFile(FileName,name,cost,materials,fw,instructions);
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeOrder(Order order)
	{
		for(int i=0; i<orderList.getComponentCount(); i++)
		{
			if(order.rcp.getName()==((Order)orderList.getComponent(i)).rcp.getName())
			{
				orderList.remove(i);
				revalidate();
				repaint();
			}
		}
	}
	
	public void updateOrders()
	{
		orderList.removeAll();
		OrderDirectory = new File("orders/");
		OrderFiles = OrderDirectory.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".rcp");
		    }
		});
		
		System.out.println(OrderFiles.length);
		
		if(OrderFiles != null)
		{
			Orders = new RecipeFile [OrderFiles.length];
			for(int i=0; i<OrderFiles.length; i++)
			{
				parseRCP(OrderFiles[i].getPath(), i);
				orderList.add(new Order(Orders[i],this.fw, this));
				revalidate();
				repaint();
			}
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static boolean isInteger(char c) {
	    try { 
	    	String cStr = c + "";
	        Integer.parseInt(cStr); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}

}
