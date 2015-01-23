package csci201.bs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameThread extends Thread {
	protected int timesLied = 0;
	protected int timesCaught = 0;
	protected int timesFalselyAccused = 0;
	protected int timesBSPressed = 0;
	
	private int id;
	private Socket s;
	private GameServer gs;
	private PrintWriter writer;
	private BufferedReader reader;
	
	public GameThread(Socket s, GameServer gs, int id)
	{
		try {
			this.s = s;
			this.gs = gs;
			this.id = id;
			writer = new PrintWriter(s.getOutputStream());
			
		} catch (IOException ioe){
			System.out.println("ioe in GameThread: " + ioe.getMessage());
		}
	}
	
	public void send(String message) 
	{
		writer.println(message);
		writer.flush();
	}
	
	
	public void run()
	{
		try {
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(true) {
				String message = reader.readLine();
				if(message.contains("GAMEOVER")){
					message = message.replace("GAMEOVER", "");
					gs.setGameOver(Integer.parseInt(message));
				}
				else if(message.contains("ADDTOMIDDLE:")){
					gs.addToMid(message);
				}else if(message.contains("NEXTTURN")){
					gs.nextTurn();
	
				}else if(message.contains("CHECKBS")){
					gs.bsPressed(this);
				}else if(message.contains("PLAYERDECKSIZE:"))
					gs.updatePlayerSize(this, message);
				else
					gs.sendToAll(message);
			}
		} catch (IOException ioe) {
			System.out.println("Client disconnected from " + s. getInetAddress());
		}
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getTimesLied() 
	{
		return timesLied;
	}
	
	public int getTimesFalselyAccused() 
	{
		return timesFalselyAccused;
	}
	
	public int getTimesCaught() 
	{
		return timesCaught;
	}
	
	public int getTimesBSPressed() 
	{
		return timesBSPressed;
	}
}