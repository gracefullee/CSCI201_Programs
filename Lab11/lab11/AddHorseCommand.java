package lab11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

public class AddHorseCommand extends SQLCommand{

	private Connection conn;
	
	public AddHorseCommand(ReentrantLock queryLock) {
		super(queryLock);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute() {
		try {
			Class.forName(DRIVER);
			this.conn = DriverManager.getConnection(DB_ADDRESS + DB_NAME,USER,PASSWORD);
		
			Statement ss = this.conn.createStatement();
			ResultSet rs = ss.executeQuery("SELECT word FROM words ORDER BY RAND() LIMIT 2");
			
			String newName = null;
			while(rs.next())
			{
				if(newName==null)
					newName = rs.getString("word");
				else{
					newName = newName + " " + rs.getString("word");
				}
			}
			
			PreparedStatement ps1 = this.conn.prepareStatement("INSERT INTO horse (name) VALUES (?)");
			ps1.setString(1, newName);
			ps1.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return true;
	}

	
}
