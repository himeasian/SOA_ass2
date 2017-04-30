package au.edu.unsw.soacourse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database handler for polling service
 * @author trungisme
 *
 */
public class DatabaseHandler {
	
	public static final String URL = "jdbc:sqlite:poll.db";
	public static final String POLLS_TABLE = "Polls";
	public static final String VOTES_TABLE = "Votes";
	
	private static final String CREATE_POLLS_TABLE = "create table if not exists " + POLLS_TABLE + " ("
														+ "_pId integer primary key autoincrement,"
														+ "title text not null," 
														+ "description text not null,"
														+ "type text check(type = 'time' or type = 'string'),"
														+ "options text not null,"
														+ "comments text not null,"
														+ "final_choice text not null"
														+ ");";
	
	private static final String CREATE_VOTES_TABLE = "create table if not exists " + VOTES_TABLE + " ("
														+ "_voteId integer primary key autoincrement,"
														+ "_pId integer not null," 
														+ "participant_name text not null,"
														+ "chosen_option text not null"
														+ "foreign key(_pId) references Polls(_pId)"
														+ ")'";
	
	/**
	 * Create the database
	 */
	public static void createDb() {
		try (Connection c = connect()) {
			Statement stmt = c.createStatement();
			stmt.execute(CREATE_POLLS_TABLE);
			stmt.execute(CREATE_VOTES_TABLE);
			
		} catch (SQLException e) { 
			System.err.println(e.getMessage());
		}
	}	
	
	/**
	 * Connect to the test.db database
	 * @return the Connection object
	 */
	private static Connection connect() {
		Connection c = null;
		try {
			c = DriverManager.getConnection(URL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return c;
	}
}
