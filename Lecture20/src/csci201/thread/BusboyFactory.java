package csci201.thread;

import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusboyFactory {
		private Vector<BusboyThread> busboyThreadVector = new Vector<BusboyThread>();
		private int numBusboys;
		private int numBusboysPerTable;
		private Hostess hostess;
		private Lock lock = new ReentrantLock();
		private Condition busboyAvailable = lock.newCondition();


		public BusboyFactory(Hostess hostess, int numBusboys, int numBusboysPerTable) {
			this.hostess = hostess;
			this.numBusboys = numBusboys;
			this.numBusboysPerTable = numBusboysPerTable;
			for (int i=0; i < numBusboys; i++) {
				busboyThreadVector.add(new BusboyThread(hostess, i, this, this.numBusboysPerTable));
			}
		}

		public void returnBusboy(BusboyThread bt) {
			lock.lock();
			bt.returnTable(bt.getTable(0));
			busboyAvailable.signal();
			lock.unlock();
		}

		public Vector<BusboyThread> getBusboy() {
			Vector<BusboyThread> btv = new Vector<BusboyThread>(numBusboysPerTable);
			BusboyThread bt = null;
			try {
				lock.lock();
				while (bt == null) {
					int i;
					for (i=0; i < busboyThreadVector.size(); i++) {
						if(btv.size()==numBusboysPerTable)
							break;
						bt = busboyThreadVector.get(i);
						if (bt != null) {
							// this will only allow one table per waiter
							// use a semaphore to allow more than one table per waiter
							if (bt.getNumAvailableBusboys() > 0) {
//							if (bt.getTable() == null) {
								btv.add(bt);
							}
						}
					}
					if (i == busboyThreadVector.size()) {
						// if i get here, i haven't secured a waiter yet
						bt = null;
						busboyAvailable.await();
					}
				}
			} catch(InterruptedException ie) {
				System.out.println("BusboyFactory.getBusboy(): IE : " + ie.getMessage());
			} finally {
				lock.unlock();
			}
			return btv;
		}

}


class BusboyThread extends Thread {
	private Hostess hostess;
	private Table table;
	private int busboyNumber;
	private BusboyFactory busboyFactory;
	private int numBusboysPerTable;
	private Semaphore numBusboysSemaphore;
	private Lock busboyLock = new ReentrantLock();
	private Condition tableAssignedCondition = busboyLock.newCondition();

	public BusboyThread(Hostess hostess, int busboyNumber, BusboyFactory busboyFactory, int numBusboysPerTable) {
		this.hostess = hostess;
		this.busboyNumber = busboyNumber;
		this.busboyFactory = busboyFactory;
		this.numBusboysPerTable = numBusboysPerTable;
		this.numBusboysSemaphore = new Semaphore(numBusboysPerTable);
		this.start();
	}

	public int getNumAvailableBusboys() {
		int numPermitsAvailable = numBusboysSemaphore.availablePermits();
		return numPermitsAvailable;
	}
	public Hostess getHostess() {
		return this.hostess;
	}

	public void returnTable(Table table) {
		this.table = null;
		numBusboysSemaphore.release();
	}

	public void cleanTable(Table table) {
		try {
			numBusboysSemaphore.acquire();
			this.table = table;
			this.busboyLock.lock();
			this.tableAssignedCondition.signalAll();
			this.busboyLock.unlock();
		} catch (InterruptedException ie) {
			System.out.println("BusboyFactory.cleanTable(" + table.getTableNumber() + ") IE: " + ie.getMessage());
		}
	}

	public Table getTable(int i) {
		if (table!=null) {
			return table;			
		}
		return null;
//		return this.table;
	}

	public int getBusboyNumber() {
		return this.busboyNumber;
	}

	public void run() {
		try {
			while (true) {
				this.busboyLock.lock();
				this.tableAssignedCondition.await();
				this.busboyLock.unlock();
				Thread.sleep(1000 * (int)(1000/numBusboysPerTable)); // sleep for between 0 and 10 seconds
				/*if (getTable(0) != null) {
					getTable(0).getLock().lock();
					// signal the customer who is "eating"
					getTable(0).getReadyCondition().signal();
					getTable(0).getLock().unlock();
				}
				*/
				//waiterFactory.returnWaiter(this);
			}
		} catch (InterruptedException ie) {
			System.out.println("BusboyThread.run(): InterruptedException: " + ie.getMessage());
		}
	}
}