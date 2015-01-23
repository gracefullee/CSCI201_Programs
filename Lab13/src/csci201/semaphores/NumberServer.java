package csci201.semaphores;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class NumberServer 
{
	private static ExecutorService pool;
	static Semaphore connectionPermits = new Semaphore(5);
	Random rand = new Random(System.currentTimeMillis());
	boolean isAllowed = true;
	int counter = 0;
	private static Lock lock = new ReentrantLock();
	private static Condition allThreadsTaken = lock.newCondition();
	private static ArrayList<Integer> numbers = new ArrayList<Integer>();
	
	public NumberServer()
	{
		pool = Executors.newFixedThreadPool(10);
	}
	
	public void addInteger(int num)
	{
		numbers.add(num);
	}
	
	public void ban()
	{
		isAllowed = false;
		
	}

	public boolean isAllowed() 
	{
		return isAllowed;
	}
	
	
	public Integer getNumber()
	{
		try 
		{
			//If you have not been banned, and there is currently a permit available
			if(isAllowed())
			{
				connectionPermits.acquire();
				//get the next number
				int val = counter++;
				// wait .1 - 1 second
				Thread.sleep(rand.nextInt(10) * 100);
				//release the permit
				connectionPermits.release();
				addInteger(val);
				//return the number
				return val;
			}
			else
			{
				System.out.println("You're banned, stop making requests");
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	public static void main(String [] args)
	{
		
		try {
			NumberServer ns = new NumberServer();
			for(int i=0; i<10; i++)
				pool.execute(new ConnectionRunnable(ns));
			pool.shutdown();
			boolean result = false;
			while(!result)
				result = pool.awaitTermination(20, TimeUnit.SECONDS);
			List<Integer> list = numbers;
			if(list!=null)
				Collections.sort(list);
			Collections.synchronizedList(list);
			for(int i=0; i<list.size(); i++)
				System.out.print(list.get(i) + " ");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
	}
	
	
}

