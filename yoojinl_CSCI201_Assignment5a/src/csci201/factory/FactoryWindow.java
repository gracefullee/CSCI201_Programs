package csci201.factory;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class FactoryWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vector<FactoryThread> ftVector = new Vector<FactoryThread>();
	private Vector<Socket> clientList = new Vector<Socket>();
	private ServerSocket ss;
	
	private JMenuBar menuBar = new JMenuBar();
	private JFileChooser fc;
	private File FactoryDirectory, ff;
	private File [] FactoryFiles, RecipeFiles;
	private JPanel mainPanel, TaskBoardPanel, outerPanel;
	private DrawingPanel drawingPanel;
	private StorePanel storePanel;
	private OrderPanel orderPanel;
	private JScrollPane sp;
	private DefaultListModel<String> taskModel = new DefaultListModel<String>();
	private JList<String> TaskBoardList = new JList<String>(taskModel);
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private RecipeFile [] rcp;
	private MaterialContainer [] mc = new MaterialContainer[3];
	private ToolContainer [] tools = new ToolContainer[5];
	private WorkArea [] workAreas = new WorkArea[15];
	private ArrayList<WorkerThread> wts = new ArrayList<WorkerThread>();
	private FactoryFile factory;
	protected ArrayList<Worker> workers;
	protected int money = 100;
	private int TaskIndex = 0;
	private int workerIndex = 0;
	private java.util.Timer totalTimer;
	private long startTime, stopTime;
	
	public FactoryWindow()
	{
		super("Factory");
		setSize(800,600);
		setLocation(100,50);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//mainPanel.setBackground(Color.WHITE);
		
		for(int i=0; i<5; i++)
		{
			if(i==0){
				mc[i] = new MaterialContainer("Wood",0);
				mc[i].setPrice(1, 1);
				tools[i] = new ToolContainer("Screwdriver",0);
				tools[i].setPrice(10, 7);
			}
			else if(i==1){
				mc[i] = new MaterialContainer("Metal",0);
				mc[i].setPrice(3, 2);
				tools[i] = new ToolContainer("Hammer",0);
				tools[i].setPrice(12, 9);
			}
			else if(i==2){
				mc[i] = new MaterialContainer("Plastic",0);
				mc[i].setPrice(2, 1);
				tools[i] = new ToolContainer("Paintbrush",0);
				tools[i].setPrice(5, 3);
			}
			else if(i==3)
			{
				tools[i] = new ToolContainer("Plier",0);
				tools[i].setPrice(11, 9);
			}
			else if(i==4)
			{
				tools[i] = new ToolContainer("Scissor",0);
				tools[i].setPrice(9, 7);
			}
		}
		
		for(int i=0; i<15; i++)
		{
			if(i==0 || i==1)
				workAreas[i] = new WorkArea("Anvil","OPEN");
			else if(i==2 || i==3 || i==4)
				workAreas[i] = new WorkArea("Workbench","OPEN");
			else if(i==5 || i==6)
				workAreas[i] = new WorkArea("Furnace","OPEN");
			else if(i==7 || i==8 || i==9)
				workAreas[i] = new WorkArea("Saw","OPEN");
			else if(i>=10 && i<=13)
				workAreas[i] = new WorkArea("Painting Station", "OPEN");
			else if(i==14)
				workAreas[i] = new WorkArea("Press","OPEN");
		}
		
		
		
		
		JMenuItem openMenu = new JMenuItem("Start Factory...");
		//menuBar.setBackground(Color.WHITE);
		openMenu.setMnemonic(KeyEvent.VK_S);
		openMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				tasks.clear();
				taskModel.clear();
				TaskIndex = 0;
				workerIndex = 0;
				FactoryDirectory = new File("src/");
				try {
					FactoryFiles = FactoryDirectory.listFiles(new FilenameFilter() {
					    public boolean accept(File dir, String name) {
					        return name.toLowerCase().endsWith(".factory");
					    }
					});
					
					if(FactoryFiles.length!=0)
					{
						ff = FactoryFiles[0];
						parseFactory(ff.getPath());
						money = 0;
					}
					else{
						factory = new FactoryFile("/src/factory0.factory",100,0,0,0,0,0,0,0,0,0);
						createFactory(factory);
						money = 100;
					}
					
				    RecipeFiles = FactoryDirectory.listFiles(new FilenameFilter() {
				    public boolean accept(File dir, String name) {
				        return name.toLowerCase().endsWith(".rcp");
				    	}
				    });
				    
				    if(RecipeFiles.length>0)
				    {
					    rcp = new RecipeFile[RecipeFiles.length];
					    for(int i=0; i<RecipeFiles.length; i++)
					    {
					    	parseRCP(RecipeFiles[i].getPath(), i);
					    }
					    drawingPanel.updateData(mc, tools, workAreas, workers, money);
						drawingPanel.repaint();
						addTasks();
					    updateTaskBoard();
				    }
				    
				  
				    totalTimer = new java.util.Timer();
				    TimerTask task = new TimerTask(){
				    	public void run(){
				    		if(!tasks.isEmpty()){
							    String name = tasks.get(TaskIndex).getName();
							    RecipeFile recipe = null;
						    	for(int j=0; j<rcp.length; j++)
						    	{
						    		if(rcp[j].getName().matches(name))
						    			recipe = rcp[j];
						    	}
						    	if(recipe!=null)
						    	{
						    		WorkerThread wt = new WorkerThread(factory.getWorkers(), recipe, workers.get(workerIndex),
						    				drawingPanel, tasks.get(TaskIndex), FactoryWindow.this);
						    		wts.add(wt);
						    		wt.start();
						    	}
						    	workerIndex++;
						    	if(workerIndex==workers.size())
						    		workerIndex=0;
						    	TaskIndex++;
						    	if(TaskIndex==tasks.size())
						    	{
						    		cancel();
						    	}
				    		}
				    	}
				    };
				    totalTimer.scheduleAtFixedRate(task, 0, 900);
				    startTime = System.nanoTime();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		outerPanel = new JPanel();
		outerPanel.setLayout(new CardLayout());
		
		drawingPanel = new DrawingPanel(mc,tools,workAreas, workers, money, outerPanel);
		Dimension dpSize = new Dimension(600,600);
		//drawingPanel.setBackground(Color.WHITE);
		drawingPanel.setPreferredSize(dpSize);
		drawingPanel.setMinimumSize(dpSize);
		storePanel = new StorePanel(this, drawingPanel, mc,tools,money, outerPanel);
		storePanel.setPreferredSize(dpSize);
		storePanel.setMinimumSize(dpSize);
		orderPanel = new OrderPanel(this, outerPanel);
		
		TaskBoardPanel = new JPanel();
		//TaskBoardPanel.setBackground(Color.WHITE);
		TaskBoardPanel.setLayout(new BoxLayout(TaskBoardPanel,BoxLayout.Y_AXIS));
		Dimension tbSize = new Dimension(200,600);
		JLabel TaskBoardLabel = new JLabel("     TASK BOARD");
		TaskBoardLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		sp = new JScrollPane(TaskBoardList);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		sp.setBorder(emptyBorder);
		TaskBoardPanel.add(TaskBoardLabel);
		TaskBoardPanel.add(sp);
		TaskBoardPanel.setPreferredSize(tbSize);
		TaskBoardPanel.setMinimumSize(tbSize);
		
		outerPanel.add(drawingPanel, "factory");
		outerPanel.add(storePanel, "store");
		outerPanel.add(orderPanel, "order");
		
		menuBar.add(openMenu);
		setJMenuBar(menuBar);
		gbc.gridx=0;
		gbc.gridy=0;
		mainPanel.add(outerPanel, gbc);
		gbc.gridx=1;
		mainPanel.add(TaskBoardPanel,gbc);
		
		add(mainPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		setResizable(false);
		repaint();
		setVisible(true);
		
		try{
			ServerSocket ss = new ServerSocket(7777);
			while(true)
			{
				Socket s= ss. accept();
				FactoryThread ft = new FactoryThread(s, this, ftVector.size());
				ft.start();
				ftVector.add(ft);
				clientList.add(s);
			}
		} catch(Exception e){
			System.out.println("Player Disconnected: " + e.getMessage());
		}
	}
	
	
	
	public static void main (String [] args)
	{
		new FactoryWindow();
	}
	
	private void createFactory(FactoryFile factory)
	{
		
		try {
			FileWriter fw = new FileWriter(factory.getPath());
			PrintWriter pw = new PrintWriter(fw);
			pw.println("[Money:" + factory.getMoney() + "]");
			pw.println("[Workers:" + factory.getWorkers() + "]");
			pw.println("[Hammers:" + factory.getHammer() + "]");
			pw.println("[Screwdrivers:" + factory.getScrewdriver() + "]");
			pw.println("[Pliers:" + factory.getPlier() + "]");
			pw.println("[Scissors:" + factory.getScissors() + "]");
			pw.println("[Paintbrushes:" + factory.getPaintbrush() + "]");
			pw.println("[Wood:" + factory.getWood() + "]");
			pw.println("[Metal:" + factory.getMetal() + "]");
			pw.println("[Plastic:" + factory.getPlastic() + "]");
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException occured: " + e.getMessage());
		}
		
		
	}
	
	protected void increaseMoney(int amount)
	{
		this.money = this.money + amount;
		storePanel.money = storePanel.money + amount;
		drawingPanel.money = drawingPanel.money + amount;
	}
	
	protected void decreaseMoney(int amount)
	{
		this.money = this.money - amount;
		storePanel.money = storePanel.money - amount;
		drawingPanel.money = drawingPanel.money - amount;
	}
	
	protected void updateMoney()
	{
		storePanel.money = this.money;
		drawingPanel.money = this.money;
	}
	
	private void parseFactory(String FileName)
	{
		try {
			FileReader fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			int w=0, h=0, sd=0, pb=0, p=0, s= 0;
			while(br.ready())
			{
				String line = br.readLine();
				String [] l = line.split("\\:");
				l[0] = l[0].replaceAll("\\[", "");
				l[0] = l[0].replaceAll(" ","");
				
				l[1] = l[1].replaceAll("\\]","");
				l[1] = l[1].replaceAll(" ","");
				String name = l[0];
				int quantity = Integer.parseInt(l[1]);
				if(name.matches("Workers"))
				{
					w = quantity;
					workers = new ArrayList<Worker>();
					for(int i=0; i<w; i++)
					{
						workers.add(new Worker(50,30,i,this));
					}
				}
				else if(name.matches("Screwdrivers"))
				{
					tools[0].updateQuantity(quantity);
					tools[0].updateCapacity(quantity);
					sd = quantity;
				}
				else if(name.matches("Hammers"))
				{
					tools[1].updateQuantity(quantity);
					tools[1].updateCapacity(quantity);
					h = quantity;
				}
				else if(name.matches("Paintbrushes"))
				{
					tools[2].updateQuantity(quantity);
					tools[2].updateCapacity(quantity);
					pb = quantity;
				}
				else if(name.matches("Pliers"))
				{
					tools[3].updateQuantity(quantity);
					tools[3].updateCapacity(quantity);
					p = quantity;
				}
				else if(name.matches("Scissors"))
				{
					tools[4].updateQuantity(quantity);
					tools[4].updateCapacity(quantity);
					s = quantity;
				}
			}
			factory = new FactoryFile(FileName,0,w,sd,h,pb,p,s,1000,1000,1000);
			for(int i=0; i<3; i++)
			{
				mc[i].setQuantity(1000);
			}
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
	
	private void parseRCP(String FileName, int i)
	{
		try {
			FileReader fr = new FileReader(FileName);
			BufferedReader br = new BufferedReader(fr);
			String name = br.readLine();
				String [] rcpFirst = name.split("\\]");
				rcpFirst[0] = rcpFirst[0].replaceAll("\\[", "");
				rcpFirst[0] = rcpFirst[0].replaceAll(" ","");
				
				rcpFirst[1] = rcpFirst[1].replaceAll("x","");
				rcpFirst[1] = rcpFirst[1].replaceAll(" ","");
				name = rcpFirst[0];
				int quantity = Integer.parseInt(rcpFirst[1]);
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
						Instruction inst = new Instruction(t, location, duration, this);
						instructions.add(inst);
					}
				}
			}
			rcp[i] = new RecipeFile(FileName,name,quantity,materials,this,instructions);
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
	
	
	private void addTasks()
	{
		for(int i=0; i<rcp.length; i++)
		{
			String name = rcp[i].getName();
			int quantity = rcp[i].getQuantity();
			for(int j=0; j<quantity; j++)
			{
				tasks.add(new Task(name));
			}
		}
	}
	
	protected int getToolQuantity(String name)
	{
		for(int i=0; i<tools.length; i++)
		{
			if(tools[i].getName().matches(name))
				return tools[i].getQuantity();
		}
		return -1;
	}
	
	protected void changeWorkAreaStatus(int x, int y, int duration)
	{
		for(int i=0; i<workAreas.length; i++)
		{
			if(workAreas[i].getLocationX()==x && workAreas[i].getLocationY()==y)
			{
				if(workAreas[i].getStatus().matches("OPEN"))
				{
					workAreas[i].setStatus("IN USE");
					workAreas[i].setDuration(duration);
				}
				//
				else if(workAreas[i].getStatus().matches("OPEN") && duration!=0)
				{
					workAreas[i].setStatus("IN USE");
					workAreas[i].setDuration(duration);
				}
				//
				else{
					workAreas[i].setStatus("OPEN");
					workAreas[i].setDuration(duration);
				}
			}
		}
	}
	
	protected boolean checkWorkAreaStatus(int x, int y)
	{
		for(int i=0; i<workAreas.length; i++)
		{
			if(workAreas[i].getLocationX()==x && workAreas[i].getLocationY()==y)
			{
				if(workAreas[i].getStatus().matches("IN USE"))
					return false;
				else{
					return true;
				}
			}
		}
		return false;
	}
	
	protected void updateTaskBoard()
	{
		taskModel.clear();
		for(int i=0; i<tasks.size(); i++)
		{
			taskModel.addElement(tasks.get(i).getName() + " ... " + tasks.get(i).getStatus());
		}
	}
	
	public static boolean isInteger(char c) {
	    try { 
	    	String ch = c + "";
	        Integer.parseInt(ch);
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public ArrayList<Destination> getMoveList(Task task)
	{
		if(task==null)
			return null;
		for(int i=0; i<rcp.length; i++)
		{
			if(rcp[i].getName().matches(task.getName()))
			{
				return rcp[i].getMoveList();
			}
		}
		return null;
	}
	
	public boolean isTool(String name)
	{
		for(int i=0; i<tools.length; i++)
		{
			if(name.matches(tools[i].getName()))
				return true;
		}
		return false;
	}
	
	public boolean isWorkArea(String name)
	{
		for(int i=0; i<workAreas.length; i++)
		{
			if(name.matches(workAreas[i].getName()))
				return true;
		}
		return false;
	}
	
	public boolean useTool(String name, int quantity)
	{
		ToolContainer tc = null;
		boolean result = false;
		for(int i=0; i<tools.length; i++)
		{
			if(name.matches(tools[i].getName()))
				tc = tools[i];
		}
		if(tc!=null)
		{
			if(quantity<0)
				result = tc.incrementQuantity(quantity);
			else
				result = tc.decrementQuantity(quantity);
		}
		return result;
	}
	
	public boolean useMaterial(String name, int quantity)
	{
		boolean result = false;
		MaterialContainer mc = null;
		for(int i=0; i<this.mc.length; i++)
		{
			if(name.matches(this.mc[i].getName()))
				mc = this.mc[i];
		}
		if(mc!=null)
			result = mc.decrementQuantity(quantity);
		return result;
	}
	
	public Task getNextTask()
	{
		for(int i=0; i<tasks.size(); i++)
		{
			if(tasks.get(i).getStatus().matches("Not Built"))
			{
				TaskIndex++;
				return tasks.get(i);
			}

		}
		return null;
	}
	
	public int getX(String name, Worker currentWorker)
	{
		if(name.matches("Anvil")||name.matches("Workbench")||name.matches("Painting Station")||name.matches("Press")||name.contains("Saw")||name.matches("Furnace"))
		{
			for(int i=0; i<workAreas.length; i++)
			{
				if(workAreas[i].getName().matches(name) && currentWorker!=null && currentWorker.getMove()!=null && currentWorker.getX()==workAreas[i].getLocationX())
				{
					return currentWorker.getX();
				}
				if(workAreas[i].getName().matches(name) && workAreas[i].getStatus().matches("OPEN"))
				{
					return workAreas[i].getLocationX();
				}
			}
		}
		
		for(int i=0; i<tools.length; i++)
		{
			if(name.contains(tools[i].getName()))
				return tools[i].getLocationX();
		}
		for(int i=0; i<mc.length; i++)
		{
			if(name.matches(mc[i].getName()))
				return mc[i].getLocationX();
		}
		return -1;
	}
	
	public void removeTask(Task task)
	{
		for(int i=0; i<taskModel.getSize(); i++)
		{
			if(taskModel.get(i)==(task.getName() +  " ... " + task.getStatus()))
			{
				taskModel.remove(i);
			}
		}
		for(int i=0; i<tasks.size(); i++)
		{
			if(tasks.get(i)==task)
				tasks.remove(i);
		}
	}
	
	public int getY(String name, Worker currentWorker)
	{
		if(name.matches("Anvil")||name.matches("Workbench")||name.matches("Painting Station")||name.matches("Press")||name.contains("Saw")||name.matches("Furnace"))
		{
			for(int i=0; i<workAreas.length; i++)
			{
				if(workAreas[i].getName().matches(name) && currentWorker!=null && currentWorker.getMove()!=null && currentWorker.getY()==workAreas[i].getLocationY())
				{
					return currentWorker.getY();
				}
				if(workAreas[i].getName().matches(name) && workAreas[i].getStatus().matches("OPEN"))
				{
					return workAreas[i].getLocationY();
				}
			}
		}
		
		for(int i=0; i<tools.length; i++)
		{
			if(name.contains(tools[i].getName()))
				return tools[i].getLocationY();
		}
		for(int i=0; i<mc.length; i++)
		{
			if(name.contains(mc[i].getName()))
				return mc[i].getLocationY();
		}
		return -1;
	}
	
	public boolean endFactory()
	{
		boolean result = true;
		for(int i=0; i<tasks.size(); i++)
		{
			if(!tasks.get(i).getStatus().matches("Complete"))
				return false;
		}
		return result;
	}
	
	public boolean removeWorker(int index)
	{
		for(int i=0; i<workers.size(); i++)
		{
			if(i==index && !workers.get(i).visible)
			{
				workers.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public void addWorker()
	{
		int workerID = workers.size();
		workers.add(new Worker(50,30,workerID,this));
	}
	
	public void acceptOrder(Order order)
	{
		try {
			String [] p = order.rcp.getPath().split("\\\\");
			Path source = Paths.get(p[0],p[1]);
			Path target = Paths.get("src",p[1]);
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
			for(FactoryThread ft : ftVector)
			{
				ft.send("ACCEPT:" + order.rcp.getName());
			}
			tasks.add(new Task(order.getName(), true));
			taskModel.addElement(order.getName() + " ... " + "Not Built" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void declineOrder(Order order)
	{
		
		try {
			String [] p = order.rcp.getPath().split("\\\\");
			Path path = Paths.get(p[0],p[1]);
			Files.delete(path);
			for(FactoryThread ft : ftVector)
			{
				ft.send("DECLINE:" + order.rcp.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parseJSON(Task task)
	{
		try {
			URL website = new URL(": https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + task.getName().toLowerCase());
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(task.getName() + ".json");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			FileReader reader = new FileReader(task.getName()+ ".json");
			JSONParser parser = new JSONParser();
			JSONObject file = (JSONObject) parser.parse(reader);
			
			JSONObject responseData = (JSONObject) file.get("responseData");
			JSONArray results = (JSONArray) responseData.get("results");
			String ImageURL = null;
			for(int i=0; i<results.size(); i++)
			{
				JSONObject image = (JSONObject) results.get(i);
				JSONObject url = (JSONObject) image.get("url");
				if(url!=null)
				{
					ImageURL = url.toString();
					break;
				}
			}
			for(FactoryThread ft : ftVector)
			{
				if(ImageURL!=null)
					ft.send("ORDERCOMPLETE:" + ImageURL);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addOrder()
	{
		orderPanel.updateOrders();
	}
	
	public void timeMessage()
	{
		stopTime = System.nanoTime();
		long elapsedTime = stopTime - startTime;
		int min = (int) TimeUnit.NANOSECONDS.toMinutes(elapsedTime);
		long sec = TimeUnit.NANOSECONDS.toSeconds(elapsedTime) - (min*60);
		JOptionPane.showMessageDialog(FactoryWindow.this, 
				"Factory took " + min + " minutes and " + sec + " seconds to finish", 
				"Factory Finished!", 
				JOptionPane.INFORMATION_MESSAGE);
	}
}