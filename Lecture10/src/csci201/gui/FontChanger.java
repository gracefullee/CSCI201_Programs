package csci201.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FontChanger extends JFrame {
	
	private JLabel fontNameLabel, fontSizeLabel;
	private JComboBox<String> fontNameCombo, fontSizeCombo;
	private JTextField textField;
	private JCheckBox boldCheckBox, italicCheckBox, centerCheckBox;
	private int fontSize = 10;
	private String fontName = "Courier";
	private boolean isBold, isItalic;
	
	public FontChanger(){
		super("CSCI 201 Font Changer");
		setSize(400,150);
		setLocation(100,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instantiateGUIComponents();
		createGUI();
		addEventHandlers();
		setVisible(true);
	}
	
	private void instantiateGUIComponents()
	{
		fontNameLabel = new JLabel("Font Name");
		fontSizeLabel = new JLabel("Font Size");
		String fontNames[] = {"Courier","Times New Roman","Helvetica","Arial"};
		String fontSizes[] = {"10","12","18","24","36"};
		fontNameCombo = new JComboBox<String>(fontNames);
		fontSizeCombo = new JComboBox<String>(fontSizes);
		textField = new JTextField("CSCI 201 is the best class!",40);
		boldCheckBox = new JCheckBox("Bold");
		italicCheckBox = new JCheckBox("Italic");
		centerCheckBox = new JCheckBox("Center");
	}
	
	public void createGUI()
	{
		JPanel northPanel = new JPanel();
		northPanel.add(fontNameLabel);
		northPanel.add(fontNameCombo);
		northPanel.add(fontSizeLabel);
		northPanel.add(fontSizeCombo);
		add(northPanel,BorderLayout.NORTH);
		add(textField,BorderLayout.CENTER);
		JPanel southPanel = new JPanel();
		southPanel.add(centerCheckBox);
		southPanel.add(boldCheckBox);
		southPanel.add(italicCheckBox);
		add(southPanel,BorderLayout.SOUTH);
	}
	
	private void addEventHandlers()
	{
		fontNameCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				fontName = (String) fontNameCombo.getSelectedItem();
				setFont();
			}
		});
		fontSizeCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				fontSize = Integer.parseInt((String)fontSizeCombo.getSelectedItem());
				setFont();
			}
		});
		boldCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				isBold = boldCheckBox.isSelected();
			}
		});
	}
	
	public void setFont()
	{
		if(isBold && isItalic)
			textField.setFont(new Font(fontName,Font.BOLD | Font.ITALIC,fontSize));
		else if(isBold)
			textField.setFont(new Font(fontName,Font.BOLD,fontSize));
		else if(isItalic)
			textField.setFont(new Font(fontName,Font.ITALIC,fontSize));
		else{
			textField.setFont(new Font(fontName,Font.PLAIN,fontSize));
		}
	
	}
	
	public static void main(String [] args)
	{
		new FontChanger();
	}
}
