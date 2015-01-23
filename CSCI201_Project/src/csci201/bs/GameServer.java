package csci201.bs;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import java.sql.*;

import javax.swing.JOptionPane;


public class GameServer {
	
	private static int numPlayers = 0;
	private Vector<GameThread> gtVector = new Vector<GameThread>();
	private ArrayList<Card> middleDeck = new ArrayList<Card>();
	private ArrayList<Card> recentCards = new ArrayList<Card>();
	private int currRank = -1;
	private int currentTurn = 0;
	boolean gameOver = false;
	private int [] playerDeckSize;
	
	public GameServer()
	{
		try{
			ServerSocket ss = new ServerSocket(7777);
			for(int i=0; i<numPlayers; i++)
			{
				Socket s = ss.accept();
				GameThread gt = new GameThread(s, this, i);
				gt.send("NUMBER " + i);
				gt.start();
				gtVector.add(gt);
				
			}
			if(gtVector.size()==numPlayers)
			{
				sendToAll("ALLCONNECTED:" + numPlayers);
				deal(this.numPlayers);
				sendPlayerDeckSize();
				Thread.sleep( 5000);
				sendToAll("TURN " + turn());
			}

		} catch(Exception e){
			System.out.println("Player Disconnected");
		}
	}
	
	public void bsPressed(GameThread playerPressed)
	{
		playerPressed.timesBSPressed++;
		int result = checkBS(gtVector.get(currentTurn));
		if(result == 1)
		{
			gtVector.get(currentTurn).timesCaught++;
			playerDeckSize[currentTurn] += middleDeck.size();
			getPile(this.gtVector.get(currentTurn));
			
		}
		else if(result == 0){
			for(int i=0; i<numPlayers; i++)
			{
				if(playerPressed==gtVector.get(i))
					playerDeckSize[i] += middleDeck.size();
			}
			getPile(playerPressed);
			playerPressed.timesFalselyAccused++;
		}
		
		sendPlayerDeckSize();
		sendToAll("NEWDECKSIZE " + middleDeck.size());
	}
	
	
	public int checkBS(GameThread currPlayer)
	{
		//Return 1 if BS, 0 if not, 3 if nothing
		int isBS = 0;
		for (int i = 0; i < recentCards.size(); i++)
		{
			if (recentCards.get(i).getRankNum() != currRank)
			{
				isBS = 1;
			}
		}
		if (recentCards.size() == 0){
			isBS = 3;
		}
		if (isBS == 1)
		{
			currPlayer.timesLied++;
		}
		return isBS;
	}
	
	public void sendPlayerDeckSize()
	{
		String message = "ALLPLAYERDECKSIZE:";
		for(int i=0; i<numPlayers; i++)
		{
			if(i==numPlayers-1)
				message += playerDeckSize[i];
			else
				message += playerDeckSize[i] + " ";
		}
		sendToAll(message);
	}
	
	public void getPile(GameThread player)
	{
		while(true)
		{
			if (middleDeck.isEmpty())
			{
				break;
			}
			Card card = middleDeck.get(middleDeck.size()-1);
			middleDeck.remove(middleDeck.size()-1);
			if(card != null)
				player.send("CARD:" + card.toString());
			else
				break;	
			
		}
	}
	
	public int turn(){
		if(currentTurn == numPlayers)
		{
			currentTurn = 0;
		}
		if (currRank == 13)
		{
			currRank = 0;
		}
		else
		{
			currRank++;
		}
		
		return currentTurn;
		
	}
	
	public void sendToAll(String message) {
		for(GameThread gt : gtVector){
			gt.send(message);
		}
	}
	
	public void setGameOver(int playerNum)
	{
		writeGameData();
		gameOver = true;
		JOptionPane.showMessageDialog(null, "Player "+ playerNum + " Won!",  "GAME OVER", JOptionPane.ERROR_MESSAGE); 
	}
	
	public Card getTop()
	{
		Card topCard = null;
		if(!middleDeck.isEmpty())
			topCard = middleDeck.get(middleDeck.size() - 1);
		return topCard;
	}
	
	public void addToMid(String message){
		message = message.replace("ADDTOMIDDLE:", "");
		String[] suitNrank = message.split("_");
		Card tempCard = new Card(suitNrank[0].charAt(0), Integer.parseInt(suitNrank[1]));
		middleDeck.add(tempCard);
		recentCards.add(tempCard);
		sendToAll("NEWDECKSIZE " + middleDeck.size());
	}
	
	protected void deal(int numPlayers)
	{
		playerDeckSize = new int [numPlayers];
		for(int i=0; i<numPlayers; i++)
		{
			playerDeckSize[i] = 0;
		}
		
		Deck deck = new Deck();
		deck.shuffle();
		while(!deck.isEmpty())
		{
			for(int i=0; i<numPlayers; i++)
			{
				Card topCard = deck.getTop();
				if(topCard != null)
				{
					gtVector.get(i).send("CARD:" + topCard.toString());
					playerDeckSize[i]++;
				}
				else
					break;
			}
		}
	}
	
	public void updatePlayerSize(GameThread player, String message)
	{
		message = message.replace("PLAYERDECKSIZE:", "");
		int size = Integer.parseInt(message);
		for(int i=0; i<numPlayers; i++)
		{
			if(player==gtVector.get(i))
				playerDeckSize[i] = size;
		}
		sendPlayerDeckSize();
	}
	
	public void nextTurn(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentTurn++;
		sendToAll("TURN " + turn());
		sendToAll("RANK" + currRank);
		recentCards.clear();
	}
	
	public void writeGameData() {
		//CHANGE THE SQL PASSWORD FOR WHOEVER IS RUNNING THIS - Josh
		try {
			java.sql.Connection myConnection =  DriverManager.getConnection(
					"jdbc:mysql://localhost/", "root", "mysqlpass");
			java.sql.PreparedStatement query = myConnection.prepareStatement(
					"create database if not exists BSDatabase");
			query.execute();
			
			myConnection =  DriverManager.getConnection(
					"jdbc:mysql://localhost/BSDatabase", "root", "mysqlpass");
			query = myConnection.prepareStatement("create table if not exists BSData("
					+ "gameNumber int,"
					+ "userID int, "
					+ "timesBSed int, "
					+ "timesCaught int, "
					+ "timesFalselyAccused int, "
					+ "timesBSPressed int)");
			query.execute();
			
			query = myConnection.prepareStatement("select max(gameNumber) from BSData");
			ResultSet gameNumberResult = query.executeQuery();
			gameNumberResult.next();
			int gameNumber = gameNumberResult.getInt(1) + 1;
			
			for(GameThread thread : gtVector) {
				System.out.println("thread #" + thread.getID());
				query = myConnection.prepareStatement("insert into bsdata(gameNumber, userID, timesBSed, timesCaught, timesFalselyAccused, timesBSPressed)"
						+ "values(?,?,?,?,?,?)");
				query.setInt(1, gameNumber);
				query.setInt(2, thread.getID());
				query.setInt(3, thread.getTimesLied());
				query.setInt(4, thread.getTimesCaught());
				query.setInt(5, thread.getTimesFalselyAccused());
				query.setInt(6, thread.getTimesBSPressed());
				
				query.execute();
			}
			
			myConnection.close();
		} catch (SQLException e) {
			System.out.println("You probably need to change the password in the code to match your sql database -Josh");
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args)
	{
		boolean notInt = true;
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Specify Number of Players : ");
		while(numPlayers <= 1 || numPlayers >= 4|| notInt)
		{
			try{
				numPlayers = scan.nextInt();
				notInt = false;
				scan.nextLine();
				while(numPlayers<=1 || numPlayers >= 4)
				{
					System.out.print("Please enter an integer greater than 1 and less than 5: ");
					numPlayers = scan.nextInt();
					scan.nextLine();
				}
			} catch (InputMismatchException ime){
				notInt = true;
				scan.nextLine();
				System.out.print("Please enter a valid integer: ");
			}
		}
		new GameServer();			
		scan.close();
	}
}
