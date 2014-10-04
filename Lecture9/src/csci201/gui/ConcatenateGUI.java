package csci201.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class ConcatenateGUI extends JFrame{
	
	private JTextField input1TF, input2TF, resultTF;
	
	public ConcatenateGUI()
	{
		super("Concatenate GUI");
		setSize(400,200);
		setLocation(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		JLabel text1Label = new JLabel("Text 1");
		input1TF = new JTextField("",20);
		topPanel.add(text1Label);
		topPanel.add(input1TF);
		mainPanel.add(topPanel);
		
		JPanel middlePanel = new JPanel();
		JLabel text2Label = new JLabel("Text 2");
		input2TF = new JTextField("",20);
		middlePanel.add(text2Label);
		middlePanel.add(input2TF);
		mainPanel.add(middlePanel);
		
		JPanel thirdPanel = new JPanel();
		JButton concatenateButton = new JButton("Concatenate");
		thirdPanel.add(concatenateButton);
		mainPanel.add(thirdPanel);
		
		JPanel bottomPanel = new JPanel();
		JLabel resultLabel = new JLabel("Result");
		resultTF = new JTextField("",20);
		bottomPanel.add(resultLabel);
		bottomPanel.add(resultTF);
		mainPanel.add(bottomPanel);
		
		/*
		class ConcatenateAdapter implements ActionListener{		
			public void actionPerformed(ActionEvent ae)
			{
				ConcatenateGUI.this.resultTF.setText(ConcatenateGUI.this.input1TF.getText() + ConcatenateGUI.this.input2TF.getText());
			}
		}
		*/
		
		concatenateButton.addActionListener(new ActionListener(){		
			public void actionPerformed(ActionEvent ae)
			{
				ConcatenateGUI.this.resultTF.setText(ConcatenateGUI.this.input1TF.getText() + ConcatenateGUI.this.input2TF.getText());
			}
		});
		
		add(mainPanel);
		
		JMenuBar jmb = new JMenuBar();
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.setMnemonic('A');
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		ImageIcon ii = new ImageIcon("big_icon_02.png");
		aboutMenuItem.setIcon(ii);
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JDialog jd = new JDialog();
				jd.setTitle("About");
				jd.setLocationRelativeTo(ConcatenateGUI.this);
				jd.setSize(150,100);
				jd.setModal(true);
				JPanel jp = new JPanel();
				jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
				JLabel jl = new JLabel("Help->About");
				JLabel jl1 = new JLabel("For CSCI 201");
				jp.add(jl);
				jp.add(jl1);
				jd.add(jp);
				jd.setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);
		jmb.add(helpMenu);
		setJMenuBar(jmb);
		
		
		JButton firefoxButton = new JButton(new ImageIcon("firefox.png"));
		firefoxButton.setBorderPainted(false);
		firefoxButton.setToolTipText("FireFox");
		JButton mushroomButton = new JButton(new ImageIcon("mushroom.png"));
		mushroomButton.setToolTipText("Mushroom");
		mushroomButton.setBorderPainted(false);
		JToolBar jtb = new JToolBar();
		jtb.setFloatable(false);
		jtb.add(firefoxButton);
		jtb.add(mushroomButton);
		add(jtb, BorderLayout.NORTH);
		
		
		setVisible(true);
	}
	
	public static void main(String [] args)
	{
		new ConcatenateGUI();
	}
	
}




