package lab11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

public class MostTimesPlacedCommand extends SQLCommand{
	
	private int place;
	private Connection conn;

	public MostTimesPlacedCommand(ReentrantLock queryLock, int place) {
		super(queryLock);
		this.place = place;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute() {
		try {
			Class.forName(DRIVER);
			this.conn = DriverManager.getConnection(DB_ADDRESS + DB_NAME,USER,PASSWORD);
		
			Statement ss = this.conn.createStatement();
			PreparedStatement ps = this.conn.prepareStatement("SELECT DISTINCT race_result.horse_id FROM race_result,horse WHERE race_result.horse_id=horse.horse_id AND place=" + place + " GROUP BY horse_id");
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			int max = 0;
			int max_ID = 0;
			
			while(rs.next())
			{
				int count = 0;
				int id = rs.getInt("horse_id");
				ResultSet cs = ss.executeQuery("SELECT COUNT(*) AS count FROM race_result WHERE horse_id="+ id + " AND place=" + place);
				if(cs.next())
					count = cs.getInt("count");
				if(count>max)
				{
					max = count;
					max_ID = id;
				}
				
			}
			
			ResultSet hs = ss.executeQuery("SELECT horse.name FROM horse WHERE horse_id="+ max_ID);
			if(hs.next())
			{
				String hname = hs.getString("name");
				System.out.println(hname + " finished number " + this.place + " the most times at " + max + " times");
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
