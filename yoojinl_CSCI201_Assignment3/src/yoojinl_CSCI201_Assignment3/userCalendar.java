package yoojinl_CSCI201_Assignment3;

import java.util.ArrayList;
import java.util.Calendar;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class userCalendar extends JFrame{
	
	static final long serialVersionUID = 1;
	
	Calendar currentCal = Calendar.getInstance();
	ArrayList<ArrayList<ArrayList<Event>>> eventList = new ArrayList<ArrayList<ArrayList<Event>>>(200);
	private calendarPanel cPanel;
	private eventManagerPanel emPanel;
	private createEventPanel cePanel;
	private editDeleteEventPanel ePanel;
	
	public userCalendar()
	{
		super("Calendar");
		
		for(int i=0; i<200; i++){
			ArrayList<ArrayList<Event>> newList = new ArrayList<ArrayList<Event>>(12);
			eventList.add(i,newList);
			for(int j=0; j<12; j++){
				ArrayList<Event> monthList = new ArrayList<Event>();
				eventList.get(i).add(j,monthList);
			}
		}
		
		setSize(500,500);
		setLocation(200,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		
		JPanel outerPanel = new JPanel();
		Event emptyEvent = new Event("","",currentCal,currentCal,currentCal);
		outerPanel.setLayout(new CardLayout());
		
		emPanel = new eventManagerPanel(outerPanel, this);
		cePanel = new createEventPanel(currentCal, outerPanel, this);
		ePanel = new editDeleteEventPanel(emptyEvent,outerPanel,this);
		cPanel = new calendarPanel(outerPanel, cePanel, ePanel, emPanel, this);
		
		
		
		outerPanel.add(cPanel,"calendar");
		outerPanel.add(emPanel,"event manager");
		outerPanel.add(cePanel,"create");
		outerPanel.add(ePanel,"edit");
		
		add(outerPanel);
		
		setVisible(true);
	}
	
	public void addEvent(Event newEvent,Calendar newDate)
	{
		Calendar eventDate = newEvent.getEventDate();
		int year = eventDate.get(Calendar.YEAR);
		int month = eventDate.get(Calendar.MONTH);
		year = year - 1900;
		eventList.get(year).get(month).add(eventList.get(year).get(month).size(), newEvent);
		cPanel.displayEvents(newDate);
		cPanel.drawDays();
	}
	
	public void removeEvent(Event deleteEvent)
	{
		Calendar eventDate = deleteEvent.getEventDate();
		int year = eventDate.get(Calendar.YEAR);
		int month = eventDate.get(Calendar.MONTH);
		year = year - 1900;
		int size = eventList.get(year).get(month).size();
		for(int i=0; i<size; i++){
			if(eventList.get(year).get(month).get(i).getListString()==deleteEvent.getListString()){
				eventList.get(year).get(month).remove(i);
				break;
			}
		}
		cPanel.displayEvents(eventDate);

	}

	public void updateEvent(Event newEvent)
	{
		Calendar eventDate = newEvent.getEventDate();
		int year = eventDate.get(Calendar.YEAR);
		int month = eventDate.get(Calendar.MONTH);
		year = year - 1900;
		int size = eventList.get(year).get(month).size();
		for(int i=0; i<size; i++){
			if(eventList.get(year).get(month).get(i).getListString()==newEvent.getListString())
			{
				eventList.get(year).get(month).remove(i);
				eventList.get(year).get(month).add(i,newEvent);
			}
		}
		cPanel.displayEvents(newEvent.getEventDate());
		cPanel.drawDays();
	}
	
	public ArrayList<ArrayList<ArrayList<Event>>> getEventList()
	{
		return eventList;
	}
	
	public void setEditPanel(editDeleteEventPanel ePanel)
	{
		this.ePanel = ePanel;
	}
	
	public void setCreatePanel(createEventPanel cPanel)
	{
		this.cePanel = cPanel;
	}
	
	public static void main(String [] args)
	{
		new userCalendar();
	}

	
}
