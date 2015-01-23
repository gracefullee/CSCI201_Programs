package csci201.factory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OrderForm extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel titleLabel, request, image, completeLabel;
	JTextField itemTF, costTF, woodTF, plasticTF, metalTF;
	JPanel mainPanel, completePanel, instructionPanel, outerPanel, icPanel, materialsPanel, inputPanel, iPanel, buttonPanel, tempPanel, requestPanel;
	JScrollPane instructionSP;
	JLabel itemLabel, costLabel, woodLabel, metalLabel, plasticLabel, materialsLabel, instructionsLabel;
	JButton plusButton, minusButton, requestButton, backButton, doneButton;
	ArrayList<InstructionOrder> instructions = new ArrayList<InstructionOrder>();
	
	private Socket s;
	protected DataInputStream dis;
	protected DataOutputStream dos;
	
	public OrderForm()
	{
		super("Order Form");
		setSize(600,400);
		
		
		outerPanel = new JPanel();
		outerPanel.setLayout(new CardLayout());
		
		titleLabel = new JLabel("ORDER FORM");
		titleLabel.setFont(new Font("SEGOE UI", Font.BOLD , 20));
		requestButton = new JButton("SEND REQUEST");
		requestButton.setFont(new Font("SEGOE UI", Font.BOLD , 20));
		requestButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				requestPanel.removeAll();
				request = new JLabel("Waiting For Response...");
				requestPanel.add(request);
				revalidate();
				repaint();
				
				boolean error = false;
				String cost = costTF.getText().replace("$", "");
				
				if(itemTF.getText().matches("") || costTF.getText().matches("")){
					JOptionPane.showMessageDialog(null, "Please enter both item and cost",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
					error = true;
				}
				else if(!isInteger(cost) || costTF.getText().contains("$"))
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid integer for cost ",  "Missing Fields", JOptionPane.ERROR_MESSAGE);
					error = true;
				}
				else if(instructions.size()==0){
					JOptionPane.showMessageDialog(null, "Please enter at least one instruction to continue.",  "Missing Fields", JOptionPane.ERROR_MESSAGE);
					error = true;
				} 
				else if(woodTF.getText().matches("") && plasticTF.getText().matches("") && metalTF.getText().matches("")){
					JOptionPane.showMessageDialog(null, "Please enter quantity for all materials",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
					error = true;
				}
				else if(!isInteger(woodTF.getText()) || !isInteger(plasticTF.getText()) || !isInteger(metalTF.getText())){
					JOptionPane.showMessageDialog(null, "Please enter a valid integer for material quantity.",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
					error = true;
				}
				for(int i=0; i<instructions.size(); i++)
				{
					InstructionOrder io = instructions.get(i);
					
					//Missing Fields
					if(io.toolTF1.getText().matches("") && io.toolTF2.getText().matches("") && io.toolCB1.getSelectedIndex()==0 &&
							io.toolCB2.getSelectedIndex()==0 && io.workareaCB.getSelectedIndex()==0 && io.timeTF.getText().matches(""))
					{
						JOptionPane.showMessageDialog(null, "Please remove any empty instruction prior to submimtting request.",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
						error = true;
						break;
					}
					else if((!io.toolTF1.getText().matches("") && !isInteger(io.toolTF1.getText())) || (!io.toolTF2.getText().matches("") && !isInteger(io.toolTF2.getText())) || !isInteger(io.timeTF.getText())){
						JOptionPane.showMessageDialog(null, "Please enter a valid integer.",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
						error = true;
						break;
					}
					else if( (io.toolCB1.getSelectedIndex()==0 && !io.toolTF1.getText().matches("")) || (io.toolCB1.getSelectedIndex()!=0 && io.toolTF1.getText().matches("")) || (io.toolCB2.getSelectedIndex()!=0 && io.toolTF2.getText().matches("")) || (io.toolCB2.getSelectedIndex()==0 && !io.toolTF2.getText().matches(""))){
						JOptionPane.showMessageDialog(null, "Please enter both quantity and desired tools.",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
						error = true;
						break;
					}
					else if(io.workareaCB.getSelectedIndex()==0){
						JOptionPane.showMessageDialog(null, "Please choose a desired work area.",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
						error = true;
						break;
					}
					else if(io.timeTF.getText().matches("")){
						JOptionPane.showMessageDialog(null, "Please enter a valid integer for duration.",  "Missing Fields", JOptionPane.ERROR_MESSAGE); 
						error = true;
						break;
					}
				}
				
				if(!error)
				{
					try {
						generateRCP();
						CardLayout c = (CardLayout)outerPanel.getLayout();
						c.show(outerPanel, "Submit");
						dos.writeUTF("ADDORDER");
						dos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		icPanel = new JPanel();
		icPanel.setLayout(new GridBagLayout());
		itemLabel = new JLabel("ITEM: ");
		itemTF = new JTextField(25);
		costLabel = new JLabel("COST: ");
		costTF = new JTextField(25);
		gbc.insets = new Insets(3,0,3,2);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		icPanel.add(itemLabel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 2.0;
		icPanel.add(itemTF, gbc);
		gbc.weightx = 0.0;
		gbc.gridy = 1;
		gbc.gridx = 0;
		icPanel.add(costLabel, gbc);
		gbc.gridx = 1;
		gbc.weightx = 2.0;
		icPanel.add(costTF, gbc);
		
		materialsPanel = new JPanel();
		materialsPanel.setLayout(new BoxLayout(materialsPanel,BoxLayout.Y_AXIS));
		materialsLabel = new JLabel("MATERIALS");
		materialsPanel.add(materialsLabel);
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		woodLabel = new JLabel("WOOD: ");
		woodTF = new JTextField(8);
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		inputPanel.add(woodLabel, gbc);
		gbc.gridx=1;
		gbc.weightx = 1.5;
		inputPanel.add(woodTF, gbc);
		metalLabel = new JLabel("METAL: ");
		metalTF = new JTextField(8);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx = 0.0;
		inputPanel.add(metalLabel, gbc);
		gbc.gridx=1;
		gbc.weightx = 1.5;
		inputPanel.add(metalTF, gbc);
		plasticLabel = new JLabel("PLASTIC: ");
		plasticTF = new JTextField(5);
		gbc.gridx=0;
		gbc.gridy=2;
		inputPanel.add(plasticLabel, gbc);
		gbc.gridx=1;
		inputPanel.add(plasticTF, gbc);
		materialsPanel.add(inputPanel);
		
		iPanel = new JPanel();
		instructionsLabel = new JLabel("INSTRUCTIONS");
		iPanel.setLayout(new GridBagLayout());
		gbc.gridx=0;
		gbc.gridy=0;
		iPanel.add(instructionsLabel,gbc);
		tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(1,1));
		
		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BoxLayout(instructionPanel,BoxLayout.Y_AXIS));
		Dimension spSize = new Dimension(400,150);
		instructionSP = new JScrollPane(instructionPanel);
		instructionSP.setMinimumSize(spSize);
		instructionSP.setPreferredSize(spSize);
		tempPanel.add(instructionSP);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weighty=5.0;
		gbc.fill = GridBagConstraints.BOTH;
		iPanel.add(tempPanel,gbc);
		
		buttonPanel = new JPanel();
		plusButton = new JButton(" + ");
		plusButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				InstructionOrder io = new InstructionOrder();
				instructionPanel.add(io);
				instructions.add(io);
				OrderForm.this.revalidate();
				OrderForm.this.repaint();
			}
		});
		minusButton = new JButton(" - ");
		minusButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				if(instructions.size()>0)
				{
					instructionPanel.remove(instructions.size()-1);
					instructions.remove(instructions.size()-1);
					OrderForm.this.revalidate();
					OrderForm.this.repaint();
				}
			}
		});
		buttonPanel.add(plusButton);
		buttonPanel.add(minusButton);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(titleLabel, gbc);
		gbc.gridwidth=2;
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx = 2.0;
		mainPanel.add(icPanel, gbc);
		gbc.weightx = 0.0;
		gbc.gridx=3;
		gbc.gridwidth=1;
		mainPanel.add(materialsPanel,gbc);
		gbc.weightx = 3.0;
		gbc.weighty = 3.0;
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridheight=2;
		gbc.gridwidth=2;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(iPanel,gbc);
		gbc.weightx = 0.0;
		gbc.gridx=3;
		mainPanel.add(buttonPanel, gbc);
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=3;
		mainPanel.add(requestButton, gbc);
		
		requestPanel = new JPanel();
		request = new JLabel("Waiting for Response...");
		request.setFont(new Font("Segoe UI", Font.BOLD, 20));
		requestPanel.add(request);
		
		completePanel = new JPanel();
		completePanel.setLayout(new BorderLayout());
		completeLabel = new JLabel("Order Complete!");
		completeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		completePanel.add(completeLabel, BorderLayout.NORTH);
		
		outerPanel.add(mainPanel, "Order Form");
		outerPanel.add(requestPanel, "Submit");
		outerPanel.add(completePanel, "Complete");
		
		add(outerPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		try{
			s = new Socket("localhost",7777);
			this.dis = new DataInputStream(s.getInputStream());
			this.dos = new DataOutputStream(s.getOutputStream());
			while(true)
			{
				String message = dis.readUTF();
				if(message.contains("ACCEPT:"))
				{
					message = message.replace("ACCEPT:", "");
					System.out.println(itemTF.getText() + " " + message);
					if(message.matches(itemTF.getText()))
					{
						System.out.println(itemTF.getText() + " " + message);
						request.setText("Request ACCEPTED!");
						revalidate();
						repaint();
					}
				}
				else if(message.contains("DECLINE:"))
				{
					message = message.replace("DECLINE:", "");
					if(message.matches(itemTF.getText()))
					{
						request.setText("Request DENIED!");
						backButton = new JButton(new ImageIcon("back.png"));
						backButton.setBorderPainted(false);
						backButton.setContentAreaFilled(false);
						backButton.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent ae)
							{
								CardLayout c1 = (CardLayout)outerPanel.getLayout();
								c1.show(OrderForm.this.outerPanel, "Order Form");
							}
						});
						requestPanel.add(backButton);
						revalidate();
						repaint();
					}
						
				}
				else if(message.contains("ORDERCOMPLETE:"))
				{
					message = message.replace("ORDERCOMPLETE:", "");
					dos.writeUTF("ORDERCOMPLETE:" + costTF.getText());
					image = new JLabel(new ImageIcon(message));
					completePanel.add(image, BorderLayout.CENTER);
					doneButton = new JButton("Done");
					doneButton.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent ae)
						{
							System.exit(0);
						}
					});
					completePanel.revalidate();
					completePanel.repaint();
					
					
					CardLayout c1 = (CardLayout)outerPanel.getLayout();
					c1.show(OrderForm.this.outerPanel, "Complete");
				}
			}
		} catch(IOException ioe){
			System.out.println("IOE in OrderForm: " + ioe.getMessage());
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
	
	
	
	public void generateRCP()
	{
		FileWriter fw;
		try {
			fw = new FileWriter("orders/" + itemTF.getText() + ".rcp");
			PrintWriter pw = new PrintWriter(fw);
			pw.println("[" + itemTF.getText() + ":" + costTF.getText() + "]");
			if(!woodTF.getText().matches(""))
				pw.println("[Wood:" + woodTF.getText() + "]");
			if(!metalTF.getText().matches(""))
				pw.println("[Metal:" + metalTF.getText() + "]");
			if(!plasticTF.getText().matches(""))
				pw.println("[Plastic:" + plasticTF.getText() + "]");
			for(int i=0; i<instructions.size(); i++)
			{
				InstructionOrder io = instructions.get(i);
				String instruction = "[Use ";
				if(io.toolTF1.getText().matches("") && io.toolTF2.getText().matches("") && io.toolCB1.getSelectedIndex()==0 && io.toolCB2.getSelectedIndex()==0)
				{
					instruction = instruction + io.workareaCB.getSelectedItem() + " for " + io.timeTF.getText() + "s]";
				}
				else if(!io.toolTF1.getText().matches("") && !io.toolTF2.getText().matches("") && io.toolCB1.getSelectedIndex()!=0 && io.toolCB2.getSelectedIndex()!=0)
				{
					instruction = instruction + io.toolTF1.getText() + "x " + io.toolCB1.getSelectedItem() + " and " + io.toolTF2.getText() + "x " + io.toolCB2.getSelectedItem() + " at "
							+  io.workareaCB.getSelectedItem() + " for " + io.timeTF.getText() + "s]";
				}
				else if(!io.toolTF1.getText().matches("") && io.toolTF2.getText().matches("") && io.toolCB1.getSelectedIndex()!=0 && io.toolCB2.getSelectedIndex()==0)
				{
					instruction = instruction + io.toolTF1.getText() + "x " + io.toolCB1.getSelectedItem() + " at "
							+  io.workareaCB.getSelectedItem() + " for " + io.timeTF.getText() + "s]";
				}
				else if(io.toolTF1.getText().matches("") && !io.toolTF2.getText().matches("") && io.toolCB1.getSelectedIndex()==0 && io.toolCB2.getSelectedIndex()!=0)
				{
					instruction = instruction + io.toolTF2.getText() + "x " + io.toolCB2.getSelectedItem() + " at "
							+  io.workareaCB.getSelectedItem() + " for " + io.timeTF.getText() + "s]";
				}
				if(instruction.endsWith("]"))
					pw.println(instruction);
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String [] args)
	{
		new OrderForm();
	}
}
