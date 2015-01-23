package lab11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

public class RaceCommand extends SQLCommand{

	private Connection conn;
	
	public RaceCommand(ReentrantLock queryLock) {
		super(queryLock);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		try {
			Class.forName(DRIVER);
			this.conn = DriverManager.getConnection(DB_ADDRESS + DB_NAME,USER,PASSWORD);
		
			Statement ss = this.conn.createStatement();
			ResultSet rs = ss.executeQuery("SELECT * FROM horse ORDER BY RAND() LIMIT 8");
			
			int place = 1;
			int count = 0;
			PreparedStatement ps1 = this.conn.prepareStatement("SELECT MAX(race_number) AS currRace FROM race_result");
			ps1.execute();
			ResultSet ms = ps1.getResultSet();
			if(!ms.next())
				count = 1;
			else{
				count = ms.getInt("currRace") + 1;
			}
				
			while(rs.next())
			{
				int id = rs.getInt("horse_id");
				PreparedStatement ps = this.conn.prepareStatement("INSERT INTO race_result (race_number,horse_id,place) VALUES (?,?,?)");
				ps.setInt(1,count);
				ps.setInt(2,id);
				ps.setInt(3, place);
				ps.execute();
				place++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
