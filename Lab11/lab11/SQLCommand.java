package lab11;

import java.util.concurrent.locks.ReentrantLock;

public abstract class SQLCommand implements Runnable
{
	public static final String DB_ADDRESS = "jdbc:mysql://localhost/";
	public static final String DB_NAME = "lab11";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASSWORD = "fhffl1804";
	protected ReentrantLock queryLock;
	public SQLCommand(ReentrantLock queryLock)
	{
		this.queryLock = queryLock;
	}
	
	@Override
	public void run() {
		queryLock.lock();
		System.out.print("Executing... ");
		execute();
		System.out.println("Done");
		queryLock.unlock();
	}
	
	public abstract boolean execute();
}
