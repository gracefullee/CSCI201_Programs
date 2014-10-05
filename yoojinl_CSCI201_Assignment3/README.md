##CSCI 201 Assignment 3

###Student Information
  + Name: Yoo Jin Lee
  + USC Email: yoojinl@usc.edu
  + USC ID: 3272884875
  
###Program Information
> Program has been separated into _four_ different panels: Calendar, createEvent, edit/deleteEvent, and eventManager.   
> Calendar Panel  
>> calendarPanel contains the monthly layout of days, prev/next button and title of current page's month and year.
>> It also contains a menu bar with following options: Event Manager, Export, About.
>> Export and About buttons will open a JDialog box with corresponding message and information.
>> Event Manager on the other hand, will switch calendarPanel to eventManagerPanel (via CardLayout)
>> and allow user to create an event with any date they wish to choose.  
  
> createEvent Panel, editDeleteEvent Panel, and eventManager Panel
>> all three panels are very similar that they both add or edit events to the calendar.
>> createEvent only allows you to add event to the date you double-clicked on,
>> while eventManager allows you to choose a date you wish to add event to.
>> editDeleteEvent Panel occurs by double-clicking on currently existing event on the bottom panel (text area) of the calendar.
>> Double-click will trigger a JDialog box, allowing user to choose whether to delete, edit, or cancel.  
>> When an event has been added to the calendar, it will make the date __bold__ to indicate that an event exists under that certain date
  
> userCalendar has been simply added to perform CardLayout on all the panel and make the program to avoid lengthy constructors.