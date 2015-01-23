package csci201.semaphores;

public class ConnectionRunnable implements Runnable {

	private NumberServer ns;
	
	public ConnectionRunnable(NumberServer ns)
	{
		this.ns = ns;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ns.getNumber();
	}

}
