package csci201.thread;

import java.util.Vector;

public class Hostess {
	private Tables tables;
	private int numTablesPerBusboy;
	
	public Hostess(int numTables, int numTablesPerBusboy) {
		this.tables = new Tables(numTables);
		this.numTablesPerBusboy = numTablesPerBusboy;
	}
	
	public Table seatCustomer(CustomerThread customerThread) {
		Table table = null;
		try {
			Restaurant.addMessage("Hostess is trying to seat customer " + customerThread.getCustomerNumber());
			/* Here, customer is about to wait for a table... add him to waiting label*/
			Restaurant.addCustomerToWaitingLabel(customerThread.getCustomerNumber());
			table = tables.getTable();
			Vector<BusboyThread> busboys = Restaurant.getBusboyFactory().getBusboy();
			WaiterThread waiter = Restaurant.getWaiterFactory().getWaiter();
			waiter.setTable(table);
			table.seatTable(customerThread, waiter, busboys);
			
			/* Here, customer is seated */
			Restaurant.addCustomerToSeatedLabel(customerThread.getCustomerNumber());
			Restaurant.addWaiterMessage("Customer " + customerThread.getCustomerNumber() + " is seated at table " + table.getTableNumber(), waiter.getWaiterNumber());
			Restaurant.addMessage("Hostess seated customer " + customerThread.getCustomerNumber() + " at table " + table.getTableNumber() + " with waiter " + waiter.getWaiterNumber());
		
			
		} catch (InterruptedException ie) {
			System.out.println("HostessThread.seatCustomer():InterruptedException: " + ie.getMessage());
		}
		return table;
	}
	
	public void customerLeaving(CustomerThread customerThread) {
		Restaurant.addCustomerToLeavingLabel(customerThread.getCustomerNumber());
		Restaurant.addWaiterMessage("Customer " + customerThread.getCustomerNumber() + " is done eating and is leaving.", customerThread.getTable().getWaiterThread().getWaiterNumber());
		Restaurant.addMessage("Customer " + customerThread.getCustomerNumber() + " is done eating and is leaving.");
		customerThread.getTable().getWaiterThread().returnTable(customerThread.getTable());
		Vector<BusboyThread> bt = customerThread.getTable().getBusboyThread();
		String busboyNum = "";
		for(int i=0; i<bt.size(); i++)
		{
			if(i==bt.size()-1)
				busboyNum += bt.get(i).getBusboyNumber() + "";
			else
				busboyNum += bt.get(i).getBusboyNumber() + ", ";
		}
		Restaurant.addMessage("Busboy " + busboyNum + " are starting to clean.");
		for(int i=0; i<bt.size(); i++)
			bt.get(i).cleanTable(customerThread.getTable());
		Restaurant.addMessage("Busboys cleaned table " + customerThread.getTable().getTableNumber());
		tables.returnTable(customerThread.getTable());
		
	}

}