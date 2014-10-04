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

public class editDeleteEventPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	private Event currentEvent;
	private JPanel outerPanel;
	private JTextField titleTF, locationTF;
	private JComboBox<Integer> startHour, endHour;
	private JComboBox<String> startAMPM, endAMPM, startMin, endMin;
	private JLabel cDate;
	
	public editDeleteEventPanel(Event currentEvent, JPanel outerPanel, userCalendar uCal){
		setBackground(Color.WHITE);
		this.currentEvent = currentEvent;
		this.outerPanel = outerPanel;
		Calendar currentDate = currentEvent.getEventDate();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		cDate = new JLabel(currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " + currentDate.get(Calendar.DAY_OF_MONTH) + ", " + currentDate.get(Calendar.YEAR));
		cDate.setFont(new Font("Segoe UI", Font.BOLD, 20));
		gbc1.gridx=0;
		gbc1.gridy=0;
		gbc1.insets = new Insets(2,3,2,3);
		add(cDate,gbc1);
		JPanel titleLocationPanel = new JPanel();
		titleLocationPanel.setLayout(new GridBagLayout());
		titleLocationPanel.setBackground(Color.WHITE);
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
			titleTF = new JTextField(currentEvent.getEventTitle(), 25);
			gbc.gridx=1;
			gbc.gridy=0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			titleLocationPanel.add(titleTF,gbc);
			locationTF = new JTextField(currentEvent.getEventLocation(), 25);
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
			Calendar startTime = currentEvent.getStartTime();
			Calendar endTime = currentEvent.getEndTime();
			int sHour = startTime.get(Calendar.HOUR);
			int eHour = endTime.get(Calendar.HOUR);
			int sMin = startTime.get(Calendar.MINUTE);
			int eMin = endTime.get(Calendar.MINUTE);
			int sAMPM = startTime.get(Calendar.AM_PM);
			int eAMPM = endTime.get(Calendar.AM_PM);
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
			startHour.setSelectedItem(sHour);
			endHour.setSelectedItem(eHour);
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
			startMin.setSelectedItem(sMin);
			endMin.setSelectedItem(eMin);
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
			if(sAMPM==Calendar.AM)
				startAMPM.setSelectedItem("AM");
			else if(sAMPM==Calendar.PM)
				startAMPM.setSelectedItem("PM");
			if(eAMPM==Calendar.AM)
				endAMPM.setSelectedItem("AM");
			else if(eAMPM==Calendar.PM)
				endAMPM.setSelectedItem("PM");
			gbc.gridx=3;
			gbc.gridy=2;
			startEndPanel.add(startAMPM,gbc);
			gbc.gridx=3;
			gbc.gridy=3;
			startEndPanel.add(endAMPM,gbc);
		gbc1.gridx=0;
		gbc1.gridy=2;
		add(startEndPanel,gbc1);
		JPanel editCancelPanel = new JPanel();
		editCancelPanel.setLayout(new GridBagLayout());
		editCancelPanel.setBackground(Color.WHITE);
			JButton editButton = new JButton("Save Event");
			editButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					boolean result = saveEvent(editDeleteEventPanel.this.currentEvent);
					if(result){
						CardLayout c1 = (CardLayout)editDeleteEventPanel.this.outerPanel.getLayout();
						c1.show(editDeleteEventPanel.this.outerPanel,"calendar");
						uCal.updateEvent(editDeleteEventPanel.this.currentEvent);
					}
				}
			});
			gbc.gridx=0;
			gbc.gridy=4;
			gbc.weightx = 1.0;
			editCancelPanel.add(editButton,gbc);
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
			editCancelPanel.add(cancelButton,gbc);
		gbc1.gridx=0;
		gbc1.gridy=3;
		add(editCancelPanel,gbc1);
	}
	
	public void setEvent(Event currentEvent)
	{
		clearContent();
		this.currentEvent = currentEvent;
		Calendar currentDate = currentEvent.getEventDate();
		Calendar startTime = currentEvent.getStartTime();
		Calendar endTime = currentEvent.getEndTime();
		cDate.setText(currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()) + " " + currentDate.get(Calendar.DAY_OF_MONTH) + ", " + currentDate.get(Calendar.YEAR));
		titleTF.setText(currentEvent.getEventTitle());
		locationTF.setText(currentEvent.getEventLocation());
		startHour.setSelectedItem(startTime.get(Calendar.HOUR));
		endHour.setSelectedItem(endTime.get(Calendar.HOUR));
		startMin.setSelectedIndex(startTime.get(Calendar.MINUTE)/15);
		endMin.setSelectedIndex(endTime.get(Calendar.MINUTE)/15);
		if(startTime.get(Calendar.AM_PM)==Calendar.AM)
			startAMPM.setSelectedItem("AM");
		else{
			startAMPM.setSelectedItem("PM");
		}
		if(endTime.get(Calendar.AM_PM)==Calendar.AM)
			endAMPM.setSelectedItem("AM");
		else{
			endAMPM.setSelectedItem("PM");
		}
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
	
	public boolean saveEvent(Event currentEvent)
	{
		String checkTitle = titleTF.getText().replaceAll(" ", "");
		String checkLocation = locationTF.getText().replaceAll(" ", "");
		if(checkTitle.equals("") || checkLocation.equals("")){
			JOptionPane.showMessageDialog(editDeleteEventPanel.this, 
					"Please fill in the following required fields: Title & Location", 
					"ERROR", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else{
			currentEvent.setEventTitle(titleTF.getText());
			currentEvent.setEventLocation(locationTF.getText());
			Calendar currentDate = currentEvent.getEventDate();
			Calendar newStart = currentEvent.getStartTime();
			Calendar newEnd = currentEvent.getEndTime();
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
				JOptionPane.showMessageDialog(editDeleteEventPanel.this, 
						"Start time cannot be after end time! Please try again", 
						"ERROR", 
						JOptionPane.ERROR_MESSAGE);
				return false;
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
			currentEvent.setEndTime(newEnd);
			currentEvent.setStartTime(newStart);
			String newEvent = new String(currentEvent.getEventTitle() + " - " + currentEvent.getEventLocation() + " From " + newStart.get(Calendar.HOUR)
					+ ":" + sMin + newStart.getDisplayName(Calendar.AM_PM,Calendar.SHORT,Locale.getDefault())
					+ " - " + newEnd.get(Calendar.HOUR) + ":" + eMin + newEnd.getDisplayName(Calendar.AM_PM,Calendar.SHORT,Locale.getDefault()) );
			currentEvent.setListString(newEvent);
			return true;
		}
	}
}
