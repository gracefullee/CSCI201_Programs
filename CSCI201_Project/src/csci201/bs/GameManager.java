package csci201.bs;

import java.sql.DriverManager;
import java.sql.SQLException;


public class GameManager {

	Player [] players;
	Player currentPlayer;
	Deck middleDeck;
	int currentCardIndex;
	Card [] lastCards;
	
	public GameManager(int numPlayers)
	{
		this.players = new Player[numPlayers];
		this.middleDeck = new Deck();
	}
	
	protected void deal(int numPlayers)
	{
		this.middleDeck.shuffle();
		while(!middleDeck.isEmpty())
		{
			for(int i=0; i<numPlayers; i++)
			{
				Card topCard = this.middleDeck.getTop();
				if(topCard!=null)
					this.players[i].addCard(topCard);
				else
					break;
			}
		}
	}
	
	public void bsPresed(Player playerPressed)
	{
		playerPressed.timesBSPressed++;
		boolean result = checkBS();
		if(result)
		{
			getPile(this.currentPlayer);
			this.currentPlayer.timesCaught++;
		}
		else{
			getPile(playerPressed);
			playerPressed.timesFalselyAccused++;
		}
	}
	
	public boolean checkBS()
	{
		//Return true if BS, false if not
		return false;
	}
	
	public boolean getPile(Player player)
	{
		int size = player.getDeckSize() + this.middleDeck.getDeck().size();
		player.addCard(this.middleDeck.getDeck());
		this.middleDeck.getDeck().clear();
		if(player.getDeckSize()==size)
			return true;
		else
			return false;
	}
	
	public boolean gameOver() {
		for(Player current : players) {
			if(current.getCards().size() == 0) {
				return true;
			}
		}
		
		return false;
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
					+ "username varchar(100)"
					+ "userID int, "
					+ "timesBSed int, "
					+ "timesCaught int, "
					+ "timesFalselyAccused int, "
					+ "timesBSPressed int)");
			query.execute();
			
			for(Player player : players) {
				query = myConnection.prepareStatement("insert into bsdatabase(username, userID, timesBSed, timesCaught, timesFalselyAccused, timesBSPressed)"
						+ "values(?,?,?,?,?)");
				query.setString(1, player.getUsername());
				query.setInt(2, player.getID());
				query.setInt(3, player.getTimesBSed());
				query.setInt(4, player.getTimesCaught());
				query.setInt(5, player.getTimesFalselyAccused());
				query.setInt(6, player.getTimesBSPressed());
				
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
		GameManager test = new GameManager(2);
		test.writeGameData();
	}
}
