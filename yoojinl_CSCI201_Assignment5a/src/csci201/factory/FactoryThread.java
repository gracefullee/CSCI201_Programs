package csci201.factory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FactoryThread extends Thread {
	
	private int id;
	private Socket s;
	private FactoryWindow fw;
	private DataOutputStream dos;
	private DataInputStream dis;
	
	public FactoryThread(Socket s, FactoryWindow fw, int id)
	{
		try {
			this.s = s;
			this.fw = fw;
			this.id = id;
			this.dos = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(String message)
	{
		try {
			dos.writeUTF(message);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try {
			this.dis = new DataInputStream(s.getInputStream());
			while(true){
				String message = dis.readUTF();
				if(message.contains("ADDORDER"))
					fw.addOrder();
				else if(message.contains("ORDERCOMPLETE:"))
				{
					int cost = 0;
					message = message.replace("ORDERCOMPLETE:","");
					if(isInteger(message))
						cost = Integer.parseInt(message);
					fw.increaseMoney(cost);
					fw.repaint();
				}
			}
		} catch (IOException ioe) {
			System.out.println("Client disconnected from " + s.getInetAddress());
		}
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s);
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
