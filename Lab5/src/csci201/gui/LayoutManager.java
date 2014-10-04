package csci201.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class LayoutManager extends JFrame {

	static final long serialVersionUID = 1;
	
	public LayoutManager()
	{
		super("CSCI 201 Lab 5");
		setSize(500,500);
		setLocation(400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel borderLayoutPanel = new JPanel();
		borderLayoutPanel.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel();
		JButton jb1 = new JButton("Button 1");
		JButton jb2 = new JButton("Button 2");
		jb2.setBackground(Color.RED);
		northPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		northPanel.add(jb1);
		northPanel.add(jb2); 
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		JLabel jl1 = new JLabel("West");
		JLabel jl2 = new JLabel("East");
		JLabel jl3 = new JLabel("This is the south quadrant");
		jl3.setBorder(BorderFactory.createRaisedBevelBorder());
		southPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		southPanel.add(jl1,BorderLayout.WEST);
		southPanel.add(jl3);
		southPanel.add(jl2,BorderLayout.EAST);
		borderLayoutPanel.add(northPanel, BorderLayout.NORTH);
		borderLayoutPanel.add(southPanel, BorderLayout.SOUTH);
		
		
		JPanel ImagePanel = new JPanel();
		ImagePanel.setLayout(new BorderLayout());
		ImageIcon initial = new ImageIcon("flower.jpg");
		Image img = initial.getImage();
		Image newImg = img.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon flower = new ImageIcon(newImg);
		JLabel image = new JLabel(flower);
		
		
		ImagePanel.add(image);
		
		tabbedPane.add("First", borderLayoutPanel);
		tabbedPane.add("Flower", ImagePanel);
		add(tabbedPane, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main (String [] args)
	{
		new LayoutManager();
	}
}
