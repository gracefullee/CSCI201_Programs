
package csci201.bs;

import java.awt.Image;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class Player extends Thread {
	
	String avatarPath;
	private int playerID;
	private ArrayList<Card> playerDeck = new ArrayList<Card>();
	private int [] playersDeckSize;
	private int numPlayers;
	
	protected BufferedReader reader;
	protected PrintWriter writer;
	private GameGUI gui;
	private Socket s;
	
	public Player()
	{
		try {
			s = new Socket("localhost", 7777);
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			writer = new PrintWriter(s.getOutputStream());
			setupAccount();
			while (true) {
				String message = reader.readLine();
				if(message.contains("ALLCONNECTED"))
				{
					writer.println("AVATAR"+playerID+" "+avatarPath);
					writer.flush();
					message = message.replace("ALLCONNECTED:", "");
					this.numPlayers = Integer.parseInt(message);
					this.gui = new GameGUI(this, numPlayers);
					this.playersDeckSize = new int [numPlayers];
					this.gui.updateStatus("All Players Have Joined! Game will start in 5 seconds");
				}
				else if(message.contains("NUMBER")) {
					playerID =  Integer.parseInt(message.replace("NUMBER ", ""));
				}
				else if(message.contains("TURN"))
				{
					int currentTurn = Integer.parseInt(message.replace("TURN ", "")); //message should be something like "Turn 10"
					
					if(currentTurn == playerID) {
						this.gui.updateStatus("My turn!");
						gui.enableCardButtons(true);
					}
					else {
						this.gui.updateStatus("Player " + currentTurn + "'s Turn!");
						gui.enableCardButtons(false);
					}
				}
				else if(message.contains("CHAT:")) 
				{
					message = message.substring(5);
					gui.chatDLM.addElement(message);
					gui.revalidate();
					gui.repaint();
				}
				else if(message.substring(0, 5).equals("CARD:")) {
					message = message.substring(5);
					Card newCard = toCard(message);
					if(newCard.getSuit()!='A')
					{
						playerDeck.add(newCard);
						this.gui.addCard(newCard);
					}
					gui.revalidate();
					gui.repaint();
				}
				else if(message.contains("AVATAR")) {
					message = message.substring(6);
					String[] info = message.split(" ");
					int index = Integer.parseInt(info[0]);
					String path = info[1];
					ImageIcon avatar = new ImageIcon(path);
					Image tempImage = avatar.getImage();
					tempImage = tempImage.getScaledInstance(96, 120, Image.SCALE_SMOOTH);
					avatar = new ImageIcon(tempImage);
					if(index != playerID)
						gui.players[index].setIcon(avatar);
					else
						gui.myCharacter.setIcon(avatar);
					gui.revalidate();
					gui.repaint();
				}
				else if(message.contains("ALLPLAYERDECKSIZE:"))
				{
					message = message.replace("ALLPLAYERDECKSIZE:", "");
					String [] m = message.split(" ");
					for(int i=0; i<numPlayers; i++)
						playersDeckSize[i] = Integer.parseInt(m[i]);
					this.gui.updatePlayersDeckSize(playersDeckSize);
				}
				else if(message.contains("NEWDECKSIZE")) {
					gui.updateDeckSize(Integer.parseInt(message.replace("NEWDECKSIZE ", "")));
				}
				else if(message.contains("RANK")) {
					System.out.println(message);
					message = message.substring(4);
					int i = Integer.parseInt(message);
					String rank = ""; 
					if(i == 0)
						rank = "Ace";
					else if(i == 11)
						rank = "Jack";
					else if(i == 12)
						rank = "Queen";
					else if(i == 13)
						rank = "King";
					else
						rank = i + 1 + "";
					gui.rankField.setText("Play: " + rank);
					gui.revalidate();
					gui.repaint();
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in Player: " + ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	public void setupAccount()
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
            avatarPath = file.getName();
		}
		else
			avatarPath = "minion.png";
	}
	
	public void chat(String message) {
		writer.println("CHAT:"+" Player " + playerID + ": " + message);
		writer.flush();	
	}
	
	public void updateDeck(ArrayList<Card> newDeck)
	{
		this.playerDeck = newDeck;
	}
	
	public ArrayList<Card> getDeck()
	{
		return this.playerDeck;
	}
	
	public int getDeckSize()
	{
		return this.playerDeck.size();
	}
	
	public void addCard(Card card)
	{
		this.playerDeck.add(card);
	}
	
	public void addCard(ArrayList<Card> card)
	{
		for(int i=0; i<card.size(); i++)
			this.playerDeck.add(card.get(i));
	}
	
	public void submitCards(ArrayList<Card> selectedCards)
	{
		for (int i = 0; i < selectedCards.size(); i++){
			System.out.println("adding to middle " + selectedCards.get(i).getSuit()+ "_" + selectedCards.get(i).getRankNum());
			writer.println("ADDTOMIDDLE:" + selectedCards.get(i).getSuit()+ "_" + selectedCards.get(i).getRankNum());
			writer.flush();
		}
		
		updateSize();
	}
	public void endTurn(){
		if(playerDeck.size()==0){
			writer.println("GAMEOVER" + playerID);
			writer.flush();
			writer.close();
		}
		writer.println("NEXTTURN");
		writer.flush();
	}
	public Card toCard(String s)
	{
		Card newCard = new Card('A',0);
		String [] c = s.split("_");
		char suit = c[1].charAt(0);
		int value = -1;
		String [] cardValues = newCard.getCardValues();
		for(int i=0; i<cardValues.length;i++)
		{
			if(cardValues[i].matches(c[0]))
				value = i;
		}
		if(validSuit(suit) && value!=-1)
		{
			newCard.setCard(suit,value);
		}
		return newCard;
	}
	
	public boolean validSuit(char s)
	{
		if(s=='C' || s=='D' || s=='S' || s=='H')
			return true;
		return false;
	}
	
	public void removeCard(Card card)
	{
		for(int i=0; i<this.playerDeck.size(); i++)
		{
			if(card==this.playerDeck.get(i))
				this.playerDeck.remove(i);
		}
	}
	
	public void removeCard(ArrayList<Card> card)
	{
		for(int j=0; j<card.size(); j++)
		{
			for(int i=0; i<this.playerDeck.size(); i++)
			{
				if(card.get(j)==this.playerDeck.get(i))
					this.playerDeck.remove(i);
			}
		}
	}
	
	public void checkBS(){
		writer.println("CHECKBS");
		writer.flush();
	}
	
	public void updateSize()
	{
		writer.println("PLAYERDECKSIZE:" + playerDeck.size());
		writer.flush();
	}
	
	public int getID()
	{
		return playerID;
	}
	
	public static void main(String [] args)
	{
		new Player();
	}
}
