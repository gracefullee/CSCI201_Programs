package yoojinl_CSCI201_Assignment3;

import java.util.Calendar;

public class Event {
	private String eventTitle;
	private String eventLocation;
	private Calendar eventDate;
	private Calendar startTime;
	private Calendar endTime;
	private String listString;

	public Event(String eventTitle, String eventLocation, Calendar eventDate, Calendar startTime, Calendar endTime)
	{
		this.eventTitle = eventTitle;
		this.eventLocation = eventLocation;
		this.eventDate = eventDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Calendar getEventDate()
	{
		return this.eventDate;
	}
	
	public String getEventTitle()
	{
		return this.eventTitle;
	}
	
	public String getEventLocation()
	{
		return this.eventLocation;
		
	}
	
	public Calendar getStartTime()
	{
		return this.startTime;
	}
	
	public String getListString()
	{
		return this.listString;
	}

	public Calendar getEndTime()
	{
		return this.endTime;
	}
	
	public void setEventDate(Calendar EventDate)
	{
		this.eventDate = EventDate;
	}
	
	public void setEventTitle(String EventTitle)
	{
		this.eventTitle = EventTitle;
	}
	
	public void setListString(String listString)
	{
		this.listString = listString;
	}
	
	public void setEventLocation(String EventLocation)
	{
		this.eventLocation = EventLocation;
	}
	
	public void setStartTime(Calendar startTime)
	{
		this.startTime = startTime;
	}
	
	public void setEndTime(Calendar endTime)
	{
		this.endTime = endTime;
	}
}
