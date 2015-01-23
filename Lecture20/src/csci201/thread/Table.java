package csci201.thread;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Table {
	
	private int tableNumber;

	private CustomerThread ct;
	private WaiterThread wt;
	private Lock lock = new ReentrantLock();
	private Condition readyCondition = lock.newCondition();
	private Vector<BusboyThread> bt;
	
	public Table(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	
	public int getTableNumber() {
		return this.tableNumber;
	}

	public CustomerThread getCustomer() {
		return ct;
	}
	
	public WaiterThread getWaiterThread() {
		return wt;
	}
	
	public Lock getLock() {
		return lock;
	}
	
	public Condition getReadyCondition() {
		return readyCondition;
	}

	public void seatTable(CustomerThread ct, WaiterThread wt, Vector<BusboyThread> bt) {
		this.ct = ct;
		this.wt = wt;
		this.bt = bt;
	}

	public Vector<BusboyThread> getBusboyThread() {
		// TODO Auto-generated method stub
		return bt;
	}


}