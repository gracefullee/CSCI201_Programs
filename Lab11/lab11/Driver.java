package lab11;

import java.util.concurrent.locks.ReentrantLock;

public class Driver {

	private static ReentrantLock lock = new ReentrantLock();
	private static Thread [] ahc = new Thread[8];
	private static Thread [] rc = new Thread[10];
	private static Thread [] mtpc = new Thread[8];
	
	public static void main(String [] args)
	{
		for(int i=0; i<8; i++)
		{
			ahc[i] = new Thread(new AddHorseCommand(lock));
			ahc[i].start();
		}
		
		for(int i=0; i<10; i++)
		{
			rc[i] = new Thread(new RaceCommand(lock));
			rc[i].start();
		}
		
		for(int i=0; i<8; i++)
		{
			mtpc[i] = new Thread(new MostTimesPlacedCommand(lock,(i+1)));
			mtpc[i].start();
		}
	}
}
