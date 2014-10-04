package yoojinl_CSCI201_Assignment3;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class createEventPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
		private boolean result;
		private Calendar currentDate;
		private JLabel cDate;
		private JTextField titleTF, locationTF;
		private JComboBox<Integer> startHour, endHour;
		private JComboBox<String> startAMPM, endAMPM, startMin, endMin;
		
	public createEventPanel(Calendar currentDate, JPanel outerPanel, userCalendar uCal){
		setBackground(Color.WHITE);
		this.currentDate = currentDate;
		setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		cDate = new JLabel(currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " + currentDate.get(Calendar.DAY_OF_MONTH) + ", " + currentDate.get(Calendar.YEAR));
		cDate.setFont(new Font("Segoe UI", Font.BOLD, 20));
		gbc1.gridx=0;
		gbc1.gridy=0;
		gbc1.insets = new Insets(2,3,2,3);
		add(cDate,gbc1);
		JPanel titleLocationPanel = new JPanel();
		titleLocationPanel.setBackground(Color.WHITE);
		titleLocationPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
			JLabel titleLabel = new JLabel("Title");
			gbc.insets = new Insets(3,5,3,5);
			gbc.gridx=0;
			gbc.gridy=0;
			titleLocationPanel.add(titleLabel, gbc);
			JLabel locationLabel = new JLabel("Location");
			gbc.gridx=0;
			gbc.gridy=1;
			titleLocationPanel.add(locationLabel,gbc);
			titleTF = new JTextField(25);
			gbc.gridx=1;
			gbc.gridy=0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			titleLocationPanel.add(titleTF,gbc);
			locationTF = new JTextField(25);
			gbc.gridx=1;
			gbc.gridy=1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			titleLocationPanel.add(locationTF,gbc);
		gbc1.gridx=0;
		gbc1.gridy=1;
		add(titleLocationPanel,gbc1);
		JPanel startEndPanel = new JPanel();
		startEndPanel.setLayout(new GridBagLayout());
		startEndPanel.setBackground(Color.WHITE);
			JLabel startLabel = new JLabel("Start");
			gbc.gridx=0;
			gbc.gridy=2;
			gbc.fill = GridBagConstraints.NONE;
			startEndPanel.add(startLabel,gbc);
			JLabel endLabel = new JLabel("End");
			gbc.gridx=0;
			gbc.gridy=3;
			startEndPanel.add(endLabel,gbc);
			startHour = new JComboBox<Integer>();
			endHour = new JComboBox<Integer>();
				for(int j=0; j<12; j++){
					startHour.addItem(j+1);
					endHour.addItem(j+1);
				}
			gbc.gridx=1;
			gbc.gridy=2;
			startEndPanel.add(startHour,gbc);
			gbc.gridx=1;
			gbc.gridy=3;
			startEndPanel.add(endHour,gbc);
			startMin = new JComboBox<String>();
			endMin = new JComboBox<String>();
			startMin.addItem("00");
			endMin.addItem("00");
			for(int i=1; i<4; i++){
				String time = Integer.toString(i*15);
				startMin.addItem(time);
				endMin.addItem(time);
			}
			gbc.gridx=2;
			gbc.gridy=2;
			startEndPanel.add(startMin,gbc);
			gbc.gridx=2;
			gbc.gridy=3;
			startEndPanel.add(endMin,gbc);
			startAMPM = new JComboBox<String>();
			endAMPM = new JComboBox<String>();
				startAMPM.addItem("AM");	startAMPM.addItem("PM");
				endAMPM.addItem("AM");		endAMPM.addItem("PM");
			gbc.gridx=3;
			gbc.gridy=2;
			startEndPanel.add(startAMPM,gbc);
			gbc.gridx=3;
			gbc.gridy=3;
			startEndPanel.add(endAMPM,gbc);
		gbc1.gridx=0;
		gbc1.gridy=2;
		add(startEndPanel,gbc1);
		JPanel createCancelPanel = new JPanel();
		createCancelPanel.setLayout(new GridBagLayout());
		createCancelPanel.setBackground(Color.WHITE);
			JButton createButton = new JButton("Create Event");
			createButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					result = true;
					Event createdEvent = createEvent();
					if(result==true){
						uCal.addEvent(createdEvent, createdEvent.getEventDate());
						CardLayout c1 = (CardLayout)outerPanel.getLayout();
						c1.show(outerPanel,"calendar");
					}
				}
			});
			gbc.gridx=0;
			gbc.gridy=4;
			gbc.weightx = 1.0;
			createCancelPanel.add(createButton,gbc);
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					CardLayout c1 = (CardLayout)outerPanel.getLayout();
					c1.show(outerPanel,"calendar");
				}
			});
			gbc.gridx=1;
			gbc.gridy=4;
			createCancelPanel.add(cancelButton,gbc);
		gbc1.gridx=0;
		gbc1.gridy=3;
		add(createCancelPanel,gbc1);
	}
	
	public void setDate(Calendar date)
	{
		this.currentDate = date;
		cDate.setText(currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " + currentDate.get(Calendar.DAY_OF_MONTH) + ", " + currentDate.get(Calendar.YEAR));
		clearContent();
	}
	
	private void clearContent()
	{
		titleTF.setText("");
		locationTF.setText("");
		startHour.setSelectedIndex(0);
		endHour.setSelectedIndex(0);
		startMin.setSelectedIndex(0);
		endMin.setSelectedIndex(0);
		startAMPM.setSelectedIndex(0);
		endAMPM.setSelectedIndex(0);
	}
	
	private Event createEvent()
	{
		String checkTitle = titleTF.getText().replaceAll(" ", "");
		String checkLocation = locationTF.getText().replaceAll(" ", "");
		if(checkTitle.equals("") || checkLocation.equals("")){
			JOptionPane.showMessageDialog(createEventPanel.this, 
					"Please fill in the following required fields: Title & Location", 
					"ERROR", 
					JOptionPane.ERROR_MESSAGE);
			this.result = false;
			Event emptyEvent = new Event("","",currentDate,currentDate,currentDate);
			return emptyEvent;
		}
		else{
			Calendar newStart = Calendar.getInstance();
			Calendar newEnd = Calendar.getInstance();
			newStart.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
			newStart.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
			newStart.set(Calendar.DATE, currentDate.get(Calendar.DATE));
			newStart.set(Calendar.HOUR, (int) startHour.getSelectedItem());
			newStart.set(Calendar.MINUTE, startMin.getSelectedIndex()*15);
			newEnd.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
			newEnd.set(Calendar.MONTH, currentDate.get(Calendar.MONTH));
			newEnd.set(Calendar.DATE, currentDate.get(Calendar.DATE));
			newEnd.set(Calendar.HOUR, (int) endHour.getSelectedItem());
			newEnd.set(Calendar.MINUTE, endMin.getSelectedIndex()*15);
			if(startAMPM.getSelectedIndex()==0)
				newStart.set(Calendar.AM_PM, Calendar.AM);
			else if(startAMPM.getSelectedIndex()==1)
				newStart.set(Calendar.AM_PM, Calendar.PM);
			if(endAMPM.getSelectedIndex()==0)
				newEnd.set(Calendar.AM_PM, Calendar.AM);
			else if(endAMPM.getSelectedIndex()==1)
				newEnd.set(Calendar.AM_PM, Calendar.PM);
			
			if(newStart.after(newEnd))
			{
				JOptionPane.showMessageDialog(createEventPanel.this, 
						"Start time cannot be after end time! Please try again", 
						"ERROR", 
						JOptionPane.ERROR_MESSAGE);
				this.result = false;
				Event emptyEvent = new Event("","",currentDate,currentDate,currentDate);
				return emptyEvent;
			}
			
			String sMin, eMin;
			if(newStart.get(Calendar.MINUTE)==0)
				sMin = "00";
			else{
				sMin = Integer.toString(newStart.get(Calendar.MINUTE));
			}
			if(newEnd.get(Calendar.MINUTE)==0)
				eMin = "00";
			else{
				eMin = Integer.toString(newEnd.get(Calendar.MINUTE));
			}
			Event newEvent = new Event(titleTF.getText(),locationTF.getText(),currentDate,newStart,newEnd);
			String newListString = new String(newEvent.getEventTitle() + " - " + newEvent.getEventLocation() + " From " + newStart.get(Calendar.HOUR)
					+ ":" + sMin + newStart.getDisplayName(Calendar.AM_PM,Calendar.SHORT,Locale.getDefault())
					+ " - " + newEnd.get(Calendar.HOUR) + ":" + eMin + newEnd.getDisplayName(Calendar.AM_PM,Calendar.SHORT,Locale.getDefault()) );
			newEvent.setListString(newListString);
			return newEvent;
		}
	}
}