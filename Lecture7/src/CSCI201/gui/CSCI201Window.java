package CSCI201.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class CSCI201Window extends JFrame {
	
	public CSCI201Window()
	{
		super("My First GUI"); //	Title of your Frame
		setSize(500,150);      //	Horizontal, vertical
		setLocation(100,100);  //	Starting from top-left corner
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel name = new JLabel("Name");
		JLabel email = new JLabel("Email");
		JTextField nameText = new JTextField(25);
		JTextField emailText = new JTextField(25);
		
		JButton verifyButton = new JButton("Verify"); //	NORTH/SOUTH... is final variable (also public & static)
		JButton submitButton = new JButton("Submit");
		JPanel northPanel = new JPanel();
		northPanel.add(name);
		northPanel.add(nameText);
		northPanel.add(verifyButton);
		add(northPanel, BorderLayout.NORTH);
		JPanel southPanel = new JPanel();
		southPanel.add(email);
		southPanel.add(emailText);
		southPanel.add(submitButton);
		add(southPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public static void main(String [] args)
	{
		new CSCI201Window();
	}
}
