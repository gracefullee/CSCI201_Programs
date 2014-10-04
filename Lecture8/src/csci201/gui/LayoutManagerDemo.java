package csci201.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class LayoutManagerDemo extends JFrame{
	
	static final long serialVersionUID = 1;
	
	public LayoutManagerDemo()
	{
		super("CSCI 201 Layout Managers");
		setSize(500,400);
		setLocation(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel borderLayoutPanel = new JPanel();
		borderLayoutPanel.setLayout(new BorderLayout());
		JTextField jtf = new JTextField(25);
		borderLayoutPanel.add(jtf, BorderLayout.NORTH);
		JTextField jtf1 = new JTextField(25);
		borderLayoutPanel.add(jtf1, BorderLayout.SOUTH);
		JLabel j1 = new JLabel("This is a border layout");
		borderLayoutPanel.add(j1, BorderLayout.CENTER);
		tabbedPane.add("BorderLayout",borderLayoutPanel);
		
		JPanel flowLayoutPanel = new JPanel();
		flowLayoutPanel.setLayout(new FlowLayout());
		JLabel jl1 = new JLabel("This is a flow layout");
		JButton jb = new JButton("Click Me!");
		flowLayoutPanel.add(jl1);
		flowLayoutPanel.add(jb);
		tabbedPane.add("FlowLayout", flowLayoutPanel);		
		
		JPanel gridLayoutPanel = new JPanel();
		gridLayoutPanel.setLayout(new GridLayout(3,3));	//GridLayout(# of rows, # of col) - each grid has same height width
		for(int i=1; i<10; i++)
		{
			JButton jb1 = new JButton("" + i);
			gridLayoutPanel.add(jb1);		//left-to-right, top-to-bottom
		}
		tabbedPane.add("GridLayout", gridLayoutPanel);
		
		JPanel boxLayoutPanel = new JPanel();
		boxLayoutPanel.setLayout(new BoxLayout(boxLayoutPanel,BoxLayout.Y_AXIS));  //BoxLayout(Container,BoxLayout._____)
		JButton jb2 = new JButton("Button 2");
		boxLayoutPanel.add(jb2);
		JButton jb3 = new JButton("Button 3");
		boxLayoutPanel.add(jb3);
		boxLayoutPanel.add(Box.createGlue());
		JButton jb4 = new JButton("Button 4");
		boxLayoutPanel.add(jb4);
		tabbedPane.add("BoxLayout", boxLayoutPanel);
		
		JPanel nullLayoutPanel = new JPanel();
		nullLayoutPanel.setLayout(null);	//gets rid of layout - even flow layout (default behavior)
		JLabel jl2 = new JLabel("This is a null layout");
		Dimension jl2Dimension = jl2.getPreferredSize();
		jl2.setBounds(25,25,jl2Dimension.width, jl2Dimension.height);
		nullLayoutPanel.add(jl2);
		JButton jb5 = new JButton("Hello World!");
		Dimension jb5Dimension = jb5.getPreferredSize();
		jb5.setBounds(200,100, jb5Dimension.width, jb5Dimension.height);
		nullLayoutPanel.add(jb5);
		tabbedPane.add("NullLayout", nullLayoutPanel);
		
		JPanel gridBagLayoutPanel = new JPanel();
		gridBagLayoutPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JButton jb6 = new JButton("Button 6");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gridBagLayoutPanel.add(jb6, gbc);
		JButton jb7 = new JButton("Button 7");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 50;
		gridBagLayoutPanel.add(jb7, gbc);
		JButton jb8 = new JButton("Button 8");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2; //takes up two columns
		gbc.ipadx = 0;
		gbc.ipady = 100;
		gridBagLayoutPanel.add(jb8, gbc);
		tabbedPane.add("GridBagLayout", gridBagLayoutPanel);
		
		
		add(tabbedPane, BorderLayout.CENTER);		
		setVisible(true);
	}
	
	public static void main(String [] args)
	{
		new LayoutManagerDemo();
	}
}
