package csci201.factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class WorkerThread extends Thread{
	private RecipeFile recipe;
	private static Semaphore semaphore;
	private Worker worker;
	private DrawingPanel drawingPanel;
	private Task task;
	private FactoryWindow fw;
	private javax.swing.Timer workerTimer;
	
	public WorkerThread(int numWorker, RecipeFile recipe, Worker worker, DrawingPanel drawingPanel, Task task, FactoryWindow fw)
	{
		semaphore = new Semaphore(numWorker);
		this.worker = worker;
		this.recipe = recipe;
		this.drawingPanel = drawingPanel;
		this.task = task;
		this.fw = fw;
		this.worker.visible = true;
	}
	
	public void run()
	{
		try {
			semaphore.acquire();
			worker.setTask(task);
			worker.setMoveList(recipe.getMoveList());
			workerTimer = new javax.swing.Timer(200, new ActionListener() {
				public void actionPerformed(ActionEvent evt){
					if(worker.sleep)
					{
						workerTimer.stop();
						int duration = worker.getDuration()*1000;
						javax.swing.Timer timer = new javax.swing.Timer(duration, new ActionListener()
						{
							public void actionPerformed(ActionEvent ae) {
								workerTimer.start();
								worker.move();
							}
						});
						timer.setRepeats(false);
						timer.start();
					}
					else if(worker.wait)
					{
						workerTimer.stop();
						java.util.Timer timer = new java.util.Timer();
						TimerTask task = new TimerTask(){
							public void run(){
								if(fw.isTool(worker.getMoveName()) && fw.getToolQuantity(worker.getMoveName())>=worker.getQuantity())
								{
									worker.move();
									workerTimer.start();
									cancel();
								}
								/*
								else if(fw.isWorkArea(worker.getMoveName()))
								{
									if(worker.getMove().updateLocation(worker))
									{
										worker.move();
										workerTimer.start();
										cancel();
									}
								}
								*/
							}
						};
						timer.scheduleAtFixedRate(task, 0, 150);
					}
					else{
						worker.move();
					}
					fw.updateTaskBoard();
					drawingPanel.updateData(worker);
					drawingPanel.repaint();
					if(!worker.visible)
					{
						workerTimer.stop();
						interrupt();
						if(fw.endFactory())
							fw.timeMessage();
					}
				}
			});
			workerTimer.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			semaphore.release();
		}
	}
	
}

