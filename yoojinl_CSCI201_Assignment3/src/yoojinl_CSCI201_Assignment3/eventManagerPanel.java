package yoojinl_CSCI201_Assignment3;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class eventManagerPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
		private Calendar currentDate;
		private boolean result;
		private JTextField titleTF, locationTF;
		private JComboBox<Integer> startHour, endHour, year, day;
		private JComboBox<String> startAMPM, endAMPM, startMin, endMin, month;
		private int selectedYear = 1900;
		
	public eventManagerPanel(JPanel outerPanel, userCalendar uCal){
		currentDate = Calendar.getInstance();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		JPanel titleLocationPanel = new JPanel();
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
		gbc1.gridy=0;
		gbc1.gridwidth=2;
		add(titleLocationPanel,gbc1);
		
		JPanel startEndPanel = new JPanel();
		startEndPanel.setLayout(new GridBagLayout());
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
			startHour.setSelectedItem(currentDate.get(Calendar.HOUR));
			endHour.setSelectedItem(currentDate.get(Calendar.HOUR)+1);
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
			startMin.setSelectedIndex((currentDate.get(Calendar.MINUTE))/15);
			endMin.setSelectedIndex((currentDate.get(Calendar.MINUTE))/15);
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
			if(currentDate.get(Calendar.AM_PM)==Calendar.AM)
				startAMPM.setSelectedIndex(0);
			else{
				startAMPM.setSelectedIndex(1);
			}
			Calendar nextHour = currentDate;
			nextHour.roll(Calendar.HOUR,true);
			if(nextHour.get(Calendar.AM_PM)==Calendar.AM)
				endAMPM.setSelectedIndex(0);
			else{
				endAMPM.setSelectedIndex(1);
			}
			gbc.gridx=3;
			gbc.gridy=2;
			startEndPanel.add(startAMPM,gbc);
			gbc.gridx=3;
			gbc.gridy=3;
			startEndPanel.add(endAMPM,gbc);
		gbc1.gridx=1;
		gbc1.gridy=1;
		gbc1.gridwidth=1;
		add(startEndPanel,gbc1);
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridBagLayout());
			JLabel yearLabel = new JLabel("Year");
			gbc.gridx=0;
			gbc.gridy=2;
			gbc.fill = GridBagConstraints.NONE;
			datePanel.add(yearLabel,gbc);
			JLabel monthLabel = new JLabel("Month");
			gbc.gridx=0;
			gbc.gridy=3;
			datePanel.add(monthLabel,gbc);
			JLabel dayLabel = new JLabel("Day");
			gbc.gridy=4;
			datePanel.add(dayLabel,gbc);
			year = new JComboBox<Integer>();
			month = new JComboBox<String>();
			day = new JComboBox<Integer>();
			Calendar c = Calendar.getInstance();
			for(int i=1900; i<2100; i++)
				year.addItem(i);
			year.setSelectedItem(currentDate.get(Calendar.YEAR));
			year.addItemListener(new ItemListener(){		
				public void itemStateChanged(ItemEvent ie)
				{
					selectedYear = (int) year.getSelectedItem();
				}
			});
			for(int i=0; i<12; i++){
				c.set(selectedYear,i,1);
				month.addItem(c.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()));
			}
			month.setSelectedIndex(currentDate.get(Calendar.MONTH));
			month.addItemListener(new ItemListener(){		
				public void itemStateChanged(ItemEvent ie)
				{
					day.removeAll();
					int selectedMonth = month.getSelectedIndex();
					c.set(selectedYear, selectedMonth, 1);
					for(int i=1; i<=c.getActualMaximum(Calendar.DATE); i++)
						day.addItem(i);
				}
			});
			for(int i=1; i<=currentDate.getActualMaximum(Calendar.DATE); i++)
				day.addItem(i);
			day.setSelectedItem(currentDate.get(Calendar.DATE));
			gbc.gridx=1;
			gbc.gridy=2;
			datePanel.add(year,gbc);
			gbc.gridx=1;
			gbc.gridy=3;
			datePanel.add(month,gbc);
			gbc.gridy=4;
			datePanel.add(day,gbc);
		gbc1.gridx=0;
		gbc1.gridy=1;
		add(datePanel,gbc1);
		
		JPanel createCancelPanel = new JPanel();
		createCancelPanel.setLayout(new GridBagLayout());
			JButton createButton = new JButton("Create Event");
			createButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					result = true;
					Event createdEvent = createEvent();
					if(result){
						uCal.addEvent(createdEvent, createdEvent.getEventDate());
						CardLayout c1 = (CardLayout)outerPanel.getLayout();
						c1.show(outerPanel,"calendar");
					}
				}
			});
			gbc.gridx=0;
			gbc.gridy=0;
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
			gbc.gridy=0;
			createCancelPanel.add(cancelButton,gbc);
		gbc1.gridx=0;
		gbc1.gridy=2;
		gbc1.gridwidth=2;
		add(createCancelPanel,gbc1);
	}
	
	private void clearContent()
	{
		currentDate = Calendar.getInstance();
		titleTF.setText("");
		locationTF.setText("");
		startHour.setSelectedItem(currentDate.get(Calendar.HOUR));
		endHour.setSelectedItem(currentDate.get(Calendar.HOUR)+1);
		startMin.setSelectedIndex((currentDate.get(Calendar.MINUTE))/15);
		endMin.setSelectedIndex((currentDate.get(Calendar.MINUTE))/15);
		if(currentDate.get(Calendar.AM_PM)==Calendar.AM)
			startAMPM.setSelectedIndex(0);
		else{
			startAMPM.setSelectedIndex(1);
		}
		Calendar nextHour = currentDate;
		nextHour.roll(Calendar.HOUR,true);
		if(nextHour.get(Calendar.AM_PM)==Calendar.AM)
			endAMPM.setSelectedIndex(0);
		else{
			endAMPM.setSelectedIndex(1);
		}
		year.setSelectedItem(currentDate.get(Calendar.YEAR));
		month.setSelectedIndex(currentDate.get(Calendar.MONTH));
		day.setSelectedItem(currentDate.get(Calendar.DATE));
	}
	
	private Event createEvent()
	{
		clearContent();
		String checkTitle = titleTF.getText().replaceAll(" ", "");
		String checkLocation = locationTF.getText().replaceAll(" ", "");
		if(checkTitle.equals("") || checkLocation.equals("")){
			JOptionPane.showMessageDialog(eventManagerPanel.this, 
					"Please fill in the following required fields: Title & Location", 
					"ERROR", 
					JOptionPane.ERROR_MESSAGE);
			this.result = false;
			Event emptyEvent = new Event("","",currentDate,currentDate,currentDate);
			return emptyEvent;
		}
		else{
			Calendar selectedDate = Calendar.getInstance();
			selectedDate.set((int)year.getSelectedItem(),month.getSelectedIndex(),(int)day.getSelectedItem(),(int)startHour.getSelectedItem(),(int)startMin.getSelectedItem());
			Calendar newStart = Calendar.getInstance();
			Calendar newEnd = Calendar.getInstance();
			newStart.set(Calendar.YEAR, (int)year.getSelectedItem());
			newStart.set(Calendar.MONTH, month.getSelectedIndex());
			newStart.set(Calendar.DATE, (int)day.getSelectedItem());
			newStart.set(Calendar.HOUR, (int) startHour.getSelectedItem());
			newStart.set(Calendar.MINUTE, startMin.getSelectedIndex()*15);
			newEnd.set(Calendar.YEAR, (int)year.getSelectedItem());
			newEnd.set(Calendar.MONTH, month.getSelectedIndex());
			newEnd.set(Calendar.DATE, (int)day.getSelectedItem());
			newEnd.set(Calendar.HOUR, (int) endHour.getSelectedItem());
			newEnd.set(Calendar.MINUTE, endMin.getSelectedIndex()*15);
			if(startAMPM.getSelectedIndex()==0){
				newStart.set(Calendar.AM_PM, Calendar.AM);
				selectedDate.set(Calendar.AM_PM, Calendar.AM);
			}
			else if(startAMPM.getSelectedIndex()==1){
				newStart.set(Calendar.AM_PM, Calendar.PM);
				selectedDate.set(Calendar.AM_PM, Calendar.AM);
			}
			if(endAMPM.getSelectedIndex()==0)
				newEnd.set(Calendar.AM_PM, Calendar.AM);
			else if(endAMPM.getSelectedIndex()==1)
				newEnd.set(Calendar.AM_PM, Calendar.PM);
			
			
			if(newStart.after(newEnd))
			{
				JOptionPane.showMessageDialog(eventManagerPanel.this, 
						"Start time cannot be after end time!", 
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
