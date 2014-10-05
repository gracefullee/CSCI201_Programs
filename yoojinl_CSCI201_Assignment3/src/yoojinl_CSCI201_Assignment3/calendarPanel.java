package yoojinl_CSCI201_Assignment3;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;


public class calendarPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	private JPanel outerPanel;
	private JButton todayButton;
	private createEventPanel cePanel;
	private Calendar today, currentDate, selectedDate, prevMonth, nextMonth;
	private JPanel [] dayHolder = new JPanel[42];
	private JLabel monthTitle = new JLabel("");
	private JLabel yearTitle = new JLabel("");
	private JPanel monthLayoutPanel;
	private JButton buttonSelected = null;
	private userCalendar uCal;
	private DefaultListModel<String> eventsModel = new DefaultListModel<String>();
	private JList<String> events = new JList<String>(eventsModel);
	private ArrayList<Event> monthEventList;
	
	public calendarPanel(JPanel outerPanel, createEventPanel cePanel, editDeleteEventPanel ePanel, eventManagerPanel emPanel, userCalendar uCal)
	{
		this.uCal = uCal;
		this.cePanel = cePanel;
		this.outerPanel = outerPanel;
		this.today = Calendar.getInstance();
		this.currentDate = Calendar.getInstance();
		this.selectedDate = Calendar.getInstance();
		this.monthEventList = uCal.getEventList().get(currentDate.get(Calendar.YEAR)-1900).get(currentDate.get(Calendar.MONTH));
		prevMonth = Calendar.getInstance();
		prevMonth.roll(Calendar.MONTH, false);
		nextMonth = Calendar.getInstance();
		nextMonth.roll(Calendar.MONTH, true);
		
		setLayout(new BorderLayout());
		JPanel TopPanel = new JPanel();
		TopPanel.setLayout(new BorderLayout());
		TopPanel.setBackground(Color.WHITE);
		//Menu Bar
		JToolBar menuBar = new JToolBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			JButton eventManagerButton = new JButton("Event Manager");
			eventManagerButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					emPanel.clearContent();
					emPanel.setCurrentDate(selectedDate);
					CardLayout c1 = (CardLayout)outerPanel.getLayout();
					c1.show(outerPanel, "event manager");
				}
			});
			JButton exportButton = new JButton("Export");
			exportButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					exportEvents(); 
					JOptionPane.showMessageDialog(calendarPanel.this, 
							"Events have been exported to 'CalendarEvents.cvs'", 
							"Export", 
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
			JButton aboutButton = new JButton("About");
			aboutButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					displayAbout();					
				}
			});
			menuBar.add(eventManagerButton);
			menuBar.add(exportButton);
			menuBar.add(aboutButton);
			menuBar.setFloatable(false);
		TopPanel.add(menuBar, BorderLayout.NORTH);	
		
		
		
		JPanel monthTitlePanel = new JPanel();
		monthTitlePanel.setBackground(Color.WHITE);
			JPanel monthYearTitle = new JPanel();
			monthYearTitle.setLayout(new FlowLayout());
			monthYearTitle.setBackground(Color.WHITE);
				monthTitle.setText(currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault()));
				yearTitle.setText(" " + currentDate.get(Calendar.YEAR));
				monthTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
				yearTitle.setFont(new Font("Segoe UI", Font.PLAIN, 36));
				monthYearTitle.add(monthTitle);
				monthYearTitle.add(yearTitle);
			JButton prevMonthButton = new JButton(new ImageIcon("arrow.png"));
			prevMonthButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					calendarPanel.this.prevMonth();
				}
			});
			JButton nextMonthButton = new JButton(new ImageIcon("arrowR.png"));
			nextMonthButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					calendarPanel.this.nextMonth();
				}
			});
			prevMonthButton.setBorderPainted(false);
			nextMonthButton.setBorderPainted(false);
			nextMonthButton.setContentAreaFilled(false);
			prevMonthButton.setContentAreaFilled(false);
			monthTitlePanel.add(prevMonthButton);
			monthTitlePanel.add(monthYearTitle);
			monthTitlePanel.add(nextMonthButton);
		TopPanel.add(monthTitlePanel, BorderLayout.CENTER );

		//Days
		JPanel daysPanel = new JPanel();
		daysPanel.setLayout(new GridLayout(1,7));
		daysPanel.setBackground(Color.WHITE);
			JLabel sun = new JLabel("SUN",SwingConstants.CENTER);
			sun.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			JLabel mon = new JLabel("MON",SwingConstants.CENTER);
			mon.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			JLabel tue = new JLabel("TUE",SwingConstants.CENTER);
			tue.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			JLabel wed = new JLabel("WED",SwingConstants.CENTER);
			wed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			JLabel thu = new JLabel("THU",SwingConstants.CENTER);
			thu.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			JLabel fri = new JLabel("FRI",SwingConstants.CENTER);
			fri.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			JLabel sat = new JLabel("SAT",SwingConstants.CENTER);
			sat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			daysPanel.add(sun);
			daysPanel.add(mon);
			daysPanel.add(tue);
			daysPanel.add(wed);
			daysPanel.add(thu);
			daysPanel.add(fri);
			daysPanel.add(sat);
		TopPanel.add(daysPanel, BorderLayout.SOUTH);
		monthLayoutPanel = new JPanel();
		monthLayoutPanel.setLayout(new GridLayout(6,7));
		monthLayoutPanel.setFont(new Font("Segoe UI", Font.PLAIN, 12));


		for(int i=0; i<42; i++)
		{
			dayHolder[i] = new JPanel();
			dayHolder[i].setLayout(new BorderLayout());
			monthLayoutPanel.add(dayHolder[i]);
		}
		
		drawDays();
		
		monthLayoutPanel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		monthLayoutPanel.setBackground(Color.WHITE);
		displayEvents(selectedDate);
		
		events.setVisibleRowCount(5);
		events.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList<String> theList = (JList<String>) mouseEvent.getSource();
				if(mouseEvent.getClickCount() == 2) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						String lString = theList.getModel().getElementAt(index);
						monthEventList = uCal.getEventList().get(currentDate.get(Calendar.YEAR)-1900).get(currentDate.get(Calendar.MONTH));
						for(int i=0; i<monthEventList.size();i++)
						{
							if(monthEventList.get(i).getListString()==lString){
								Object [] options = {"Delete", "Edit", "Cancel"};
								int selection = JOptionPane.showOptionDialog(calendarPanel.this, 
										"Would you like to edit or delete this event?", 
										"Select an Option", JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
								if(selection==0)
								{
									//Delete Clicked Event
									Calendar sDate = monthEventList.get(i).getEventDate();
									uCal.removeEvent(monthEventList.get(i));
									int count = 0;
									int newSize = monthEventList.size();
									for(int j=0; j<newSize; j++){
										if(monthEventList.get(j).getEventDate().get(Calendar.YEAR)==selectedDate.get(Calendar.YEAR))
										{
											if(monthEventList.get(j).getEventDate().get(Calendar.MONTH)==selectedDate.get(Calendar.MONTH))
											{
												if(monthEventList.get(j).getEventDate().get(Calendar.DATE)==selectedDate.get(Calendar.DATE))
													count++;
											}		
										}
									}
									if(buttonSelected!=null && count==0){
										buttonSelected.setFont(new Font("Segoe UI", Font.PLAIN, 12));
									}
									displayEvents(sDate);
									drawDays();
								}
								else if(selection==1)
								{	//Edit Clicked Event
									ePanel.setEvent(monthEventList.get(i));
									CardLayout c1 = (CardLayout)outerPanel.getLayout();
									c1.show(outerPanel,"edit");
								}
							}
						}
	            }
	          }
	        }
	      });
		
		JScrollPane eventScroll = new JScrollPane(events);
		JPanel eventManagerPanel = new JPanel();
		eventManagerPanel.setLayout(new BoxLayout(eventManagerPanel, BoxLayout.Y_AXIS));
		eventManagerPanel.setBackground(Color.WHITE);
		
		add(TopPanel, BorderLayout.NORTH);
		add(monthLayoutPanel);
		add(eventScroll, BorderLayout.SOUTH);
	}
	
	public void prevMonth()
	{
		if(nextMonth.get(Calendar.MONTH)==Calendar.JANUARY)
			nextMonth.roll(Calendar.YEAR,false);
		if(currentDate.get(Calendar.MONTH)==Calendar.JANUARY)
			currentDate.roll(Calendar.YEAR,false);
		if(prevMonth.get(Calendar.MONTH)==Calendar.JANUARY)
			prevMonth.roll(Calendar.YEAR,false);
		currentDate.roll(Calendar.MONTH, false);
		prevMonth.roll(Calendar.MONTH, false);
		nextMonth.roll(Calendar.MONTH, false);
		drawDays();
		monthTitle.setText("" + currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
		yearTitle.setText("" + currentDate.get(Calendar.YEAR));
	}
	
	public void nextMonth()
	{
		if(nextMonth.get(Calendar.MONTH)==Calendar.DECEMBER)
			nextMonth.roll(Calendar.YEAR,true);
		if(currentDate.get(Calendar.MONTH)==Calendar.DECEMBER)
			currentDate.roll(Calendar.YEAR,true);
		if(prevMonth.get(Calendar.MONTH)==Calendar.DECEMBER)
			prevMonth.roll(Calendar.YEAR,true);
		currentDate.roll(Calendar.MONTH, true);
		prevMonth.roll(Calendar.MONTH, true);
		nextMonth.roll(Calendar.MONTH, true);
		drawDays();
		monthTitle.setText("" + currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
		yearTitle.setText("" + currentDate.get(Calendar.YEAR));
		
	}
	
	public void drawDays()
	{

		this.monthEventList = uCal.getEventList().get(currentDate.get(Calendar.YEAR)-1900).get(currentDate.get(Calendar.MONTH));
		monthLayoutPanel.removeAll();
		for(int i=0; i<42; i++)
		{
			dayHolder[i].removeAll();
		}
		
		Calendar fDay = currentDate;
		fDay.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), 1);
		int firstDayofWeek = fDay.get(Calendar.DAY_OF_WEEK);
		int lastDay = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i=firstDayofWeek-1; i>0; i--)
		{
			JButton p = new JButton(" " + (prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)-i+1));
			p.setEnabled(false);
			float[] hsbVal = new float[3];
			hsbVal = Color.RGBtoHSB(232,238, 231, hsbVal);
			p.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
			p.setBorderPainted(false);
			dayHolder[firstDayofWeek-i-1].add(p);
		}
		
		//Initialize currentMonth's day buttons
		for(int i=0; i<lastDay; i++)
		{
			int count = 0;
			JButton day = new JButton("" + (i+1));
			day.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			int size = monthEventList.size();
			for(int j=0; j<size; j++)
			{	
				int eDate = monthEventList.get(j).getEventDate().get(Calendar.DATE);
				if(eDate==(i+1)) 
					count++;
			}
			if(count>0)
				day.setFont(new Font("Segoe UI", Font.BOLD, 12));
				
			day.setBorderPainted(false);
			day.setBackground(Color.WHITE);
			if(currentDate.get(Calendar.YEAR)==today.get(Calendar.YEAR) && currentDate.get(Calendar.MONTH)==today.get(Calendar.MONTH) && today.get(Calendar.DATE)==(i+1)){
				day.setBackground(Color.YELLOW);
				todayButton = day;
			}
		
			if(currentDate.get(Calendar.YEAR)==selectedDate.get(Calendar.YEAR))
			{
				if(currentDate.get(Calendar.MONTH)==selectedDate.get(Calendar.MONTH))
				{
					if(buttonSelected!=null && Integer.parseInt(buttonSelected.getText())==(i+1))
					{
						day.setBackground(Color.CYAN);
						buttonSelected = day;
					}
				}		
			}
					
			day.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					if(buttonSelected==todayButton)
						buttonSelected.setBackground(Color.YELLOW);
					if(buttonSelected!=null && buttonSelected!=todayButton){
						buttonSelected.setBackground(Color.WHITE);
					}
					buttonSelected = day;
					int date = Integer.parseInt(buttonSelected.getText());
					selectedDate = Calendar.getInstance();
					selectedDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), date);
					day.setBackground(Color.CYAN);
					displayEvents(selectedDate);
				}
			});
			day.addMouseListener(new MouseAdapter(){		
				public void mouseClicked(MouseEvent event){
					if(event.getClickCount()==2){
						cePanel.setDate(selectedDate);
						CardLayout c1 = (CardLayout)outerPanel.getLayout();
						c1.show(outerPanel, "create");
					}	
				}
			});
			dayHolder[firstDayofWeek-1+i].add(day);
			
		}
		
		int count = lastDay + firstDayofWeek -1;
		count = 42 - count;	
		for(int i=0; i<count; i++)
		{
			JButton n = new JButton("" + (i+1));
			n.setBorderPainted(false);
			float[] hsbVal = new float[3];
			hsbVal = Color.RGBtoHSB(232,238, 231, hsbVal);
			n.setBackground(Color.getHSBColor(hsbVal[0], hsbVal[1], hsbVal[2]));
			n.setEnabled(false);
			dayHolder[42-count+i].add(n);
		}
		
		for(int i=0; i<42; i++){
			monthLayoutPanel.add(dayHolder[i]);
		}
	}
	
	public void displayEvents(Calendar nowDate)
	{
		eventsModel.removeAllElements();
		ArrayList<ArrayList<ArrayList<Event>>> updatedList = uCal.getEventList();
		int currYear = nowDate.get(Calendar.YEAR);
		int currMonth = nowDate.get(Calendar.MONTH);
		currYear = currYear - 1900;
		ArrayList<Event> thisMonthEvent = updatedList.get(currYear).get(currMonth);
		ArrayList<String> eList = new ArrayList<String>();
		monthEventList = thisMonthEvent;
		int size = monthEventList.size();
		for(int i=0; i<size; i++)
		{	
			
			int eventDate = thisMonthEvent.get(i).getEventDate().get(Calendar.DATE);
			if(eventDate == nowDate.get(Calendar.DATE)){
				Event e = thisMonthEvent.get(i);
				String lString = e.getListString();
				eList.add(lString);
			}
		}
		if(eList.size()>0){
			String [] fullList = new String [eList.size()];
			fullList = eList.toArray(fullList);
			for(int i=0; i<fullList.length; i++)
				eventsModel.addElement(fullList[i]);
			events.setEnabled(true);
		}
		else{
			String noEvent = "No Event";
			eventsModel.removeAllElements();
			eventsModel.addElement(noEvent);
			events.setEnabled(false);
		}
	}
	
	public JPanel[] getDayHolder()
	{
		return this.dayHolder;
	}
	
	
	public Calendar getCurrentDate()
	{
		return this.currentDate;
	}
	
	private void exportEvents()
	{
		try {
			try{
				FileWriter fw = new FileWriter("CalendarEvents.csv");
				ArrayList<ArrayList<ArrayList<Event>>> eventList =calendarPanel.this.uCal.getEventList();
				
				fw.append("YEAR,MONTH,DAY,DAY OF WEEK,START TIME,END TIME,TITLE,LOCATION\n");
				
				for(int i=0; i<200; i++)
				{
					for(int j=0; j<12; j++)
					{
						ArrayList<Event> monthList = eventList.get(i).get(j);
						for(int k=0; k<monthList.size(); k++)
						{
							Event e = monthList.get(k);
							String [] data = new String [8];
							data[0] = Integer.toString(e.getEventDate().get(Calendar.YEAR));
							data[1] = e.getEventDate().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
							data[2] = Integer.toString(e.getEventDate().get(Calendar.DATE));
							data[3] = e.getEventDate().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
							
							String sMin, eMin;
							if(e.getStartTime().get(Calendar.MINUTE)==0)
								sMin = "00";
							else{
								sMin = Integer.toString(e.getStartTime().get(Calendar.MINUTE));
							}
							if(e.getEndTime().get(Calendar.MINUTE)==0)
								eMin = "00";
							else{
								eMin = Integer.toString(e.getEndTime().get(Calendar.MINUTE));
							}
							
							String s = new String(e.getStartTime().get(Calendar.HOUR)
									+ ":" + sMin + e.getStartTime().getDisplayName(Calendar.AM_PM,Calendar.SHORT,Locale.getDefault()));
							String end = new String(e.getEndTime().get(Calendar.HOUR)
									+ ":" + eMin + e.getEndTime().getDisplayName(Calendar.AM_PM,Calendar.SHORT,Locale.getDefault()));
							
							data[4] = s;
							data[5] = end;
							data[6] = e.getEventTitle();
							data[7] = e.getEventLocation();
							for(int l=0; l<8; l++)
							{
								fw.append(data[l]);
								if(l!=7)
									fw.append(',');
							}
							fw.append('\n');
						}
					}
				}
			
				fw.flush();
				fw.close();
				
			}	catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(calendarPanel.this, 
						"Please close the 'CalendarEvenets.csv'", 
						"ERROR", 
						JOptionPane.ERROR_MESSAGE);
			}	
		} catch (IOException e) {
			System.out.println("IOException occured: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void displayAbout()
	{
		JDialog aboutDialog = new JDialog();
		aboutDialog.setTitle("About");
		aboutDialog.setLayout(new BorderLayout());
		aboutDialog.setLocationRelativeTo(calendarPanel.this);
		aboutDialog.setSize(400,400);
		aboutDialog.setModal(true);
		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		ImageIcon initial = new ImageIcon("banner.png");
		Image img = initial.getImage();
		Image newImg = img.getScaledInstance(aboutDialog.getWidth(),50,Image.SCALE_SMOOTH);
		ImageIcon banner = new ImageIcon(newImg);
		JPanel name = new JPanel();
		name.setBackground(Color.WHITE);
		JLabel topImage = new JLabel(banner);
		aboutDialog.add(topImage,BorderLayout.NORTH);
		JLabel fname = new JLabel("Yoo Jin");
		fname.setBackground(Color.WHITE);
		fname.setFont(new Font("Segoe UI", Font.BOLD, 30));
		JLabel lname = new JLabel("Lee");
		lname.setBackground(Color.WHITE);
		lname.setFont(new Font("Segoe UI", Font.PLAIN, 28));
		name.add(fname);
		name.add(lname);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets = new Insets(2,3,2,3);
		aboutPanel.add(name,gbc);
		JPanel description = new JPanel();
			initial = new ImageIcon("picture.jpg");
			img = initial.getImage();
			newImg = img.getScaledInstance(150,266,Image.SCALE_SMOOTH);
			ImageIcon p = new ImageIcon(newImg);
			JLabel picture = new JLabel(p);
			description.add(picture);
			DefaultListModel<String> infoModel = new DefaultListModel<String>();
			JList<String> info = new JList<String>(infoModel);
			infoModel.addElement("Junior at");
			infoModel.addElement("University of Southern California");
			infoModel.addElement(" ");
			infoModel.addElement("Major: Computer Science and");
			infoModel.addElement("Business Administration");
			infoModel.addElement(" ");
			infoModel.addElement("Section: Professor Miller's");
			infoModel.addElement("8:00AM - 9:20AM");
			infoModel.addElement(" ");
			infoModel.addElement("Program Completed On");
			infoModel.addElement("October 3, 2014");
			infoModel.addElement(" ");
			infoModel.addElement("http://github.com/yoojinl");
			info.setAlignmentX(JList.CENTER_ALIGNMENT);
			info.setAlignmentY(JList.CENTER_ALIGNMENT);
			info.setEnabled(false);
			description.setBackground(Color.WHITE);
			description.add(info);
		gbc.gridx=0;
		gbc.gridy=1;
		aboutPanel.add(description,gbc);
		aboutPanel.setBackground(Color.WHITE);
		aboutDialog.add(aboutPanel);
		aboutDialog.setVisible(true);
	}
}
