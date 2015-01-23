package csci201.factory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InstructionOrder extends JPanel{
	
	protected JLabel useLabel, atLabel, forLabel, secLabel;
	protected JComboBox<String> toolCB1, toolCB2;
	protected JTextField toolTF1 = new JTextField(5);
	protected JTextField toolTF2 = new JTextField(5);
	protected JComboBox<String> workareaCB;
	protected JTextField timeTF = new JTextField(5);
	protected String [] tools = {"","Screwdriver","Hammer","Plier","Scissor","Paintbrush"};
	protected String [] workareas = {"","Saw","Anvil","Painting Station", "Press", "Furnace", "Workbench"};
	
	
	public InstructionOrder()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(3,2,3,2);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight = 2;
		useLabel = new JLabel("USE");
		add(useLabel,gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.gridheight=1;
		add(toolTF1,gbc);
		gbc.gridx=2;
		toolCB1 = new JComboBox<String>(tools);
		add(toolCB1,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		add(toolTF2,gbc);
		gbc.gridx=2;
		toolCB2 = new JComboBox<String>(tools);
		add(toolCB2,gbc);
		atLabel = new JLabel("AT");
		gbc.gridx=3;
		gbc.gridy=0;
		gbc.gridheight=2;
		add(atLabel,gbc);
		gbc.gridx=4;
		workareaCB = new JComboBox<String>(workareas);
		add(workareaCB,gbc);
		forLabel = new JLabel("FOR");
		gbc.gridx=5;
		add(forLabel,gbc);
		gbc.gridx=6;
		add(timeTF,gbc);
		secLabel = new JLabel("S");
		gbc.gridx=7;
		add(secLabel,gbc);
	}
}
