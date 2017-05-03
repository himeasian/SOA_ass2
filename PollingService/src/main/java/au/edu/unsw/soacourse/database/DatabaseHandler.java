package au.edu.unsw.soacourse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.soacourse.model.PollModel;

/**
 * Database handler for polling service
 * @author trungisme
 *
 */
public class DatabaseHandler {
	
	/**
	 * Constructor for database handler, on instantiation create a db if not existing
	 */
	public DatabaseHandler() {
		createDb();
	}
	
	/**
	 * TODO: Delete all votes
	 * Deletes a row from the table given the pid
	 * @param pid
	 */
	public void deletePoll(int pid) {
		
		try (Connection c = connect()) {
			c.createStatement().executeQuery("delete from polls where _pId = " + pid);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a poll from a model of values
	 * @param p poll model
	 * @return the pid of the poll
	 */
	public int createPoll(PollModel p) {
		
		try (Connection c = connect()) {
//			// check if exists first
//			String sel = "select _pId from polls where " +
//						"title = ? and " +
//						"description = ? and " +
//						"type = ? and " +
//						"options = ? and " +
//						"comments = ? and " +
//						"final_choice = ?";
//			
//			PreparedStatement pstmt = c.prepareStatement(sel);
//			pstmt.setString(1, p.getTitle());
//			pstmt.setString(2, p.getDescription());
//			pstmt.setString(3, p.getType());
//			pstmt.setString(4, p.getOptions());
//			pstmt.setString(5, p.getComments());
//			pstmt.setString(6, p.getFinalChoice());
//			ResultSet rs = pstmt.executeQuery();
//			
//			// if exists, return the id 
//			if (rs.next()) {
//				return rs.getInt(1);
//			}

			String ins = "insert into polls(title, "
					+ "description, type, options, "
					+ "comments, final_choice) values(?,?,?,?,?,?)";
			PreparedStatement istmt = c.prepareStatement(ins);
			istmt.setString(1, p.getTitle());
			istmt.setString(2, p.getDescription());
			istmt.setString(3, p.getType());
			istmt.setString(4, p.getOptions());
			istmt.setString(5, p.getComments());
			istmt.setString(6, p.getFinalChoice());
			istmt.executeUpdate();
			
			// get id
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * TODO: VOTES PART
	 * Get polls that match clause, if no clause return all polls
	 * @return
	 */
	public List<PollModel> getPolls(String clause) {
		
		try (Connection c = connect()) {
			Statement stmt = c.createStatement();
			
			String sql = "select * from polls";
			if (!clause.isEmpty()) 
				sql += " where " + clause;
			ResultSet rs = stmt.executeQuery(sql);
			
			List<PollModel> p = new ArrayList<PollModel>();
			while(rs.next()) {
				p.add(makePollObj(rs));
			}
			
			return p;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * TODO: VOTES PART
	 * gets a single unique poll item
	 * @param _pId
	 * @return the poll model
	 */
	public PollModel getPoll(int _pId) {
		
		try (Connection c = connect()) {
			String sql = "select * from polls where _pId = ?";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, _pId);
			ResultSet rs = pstmt.executeQuery();
			
			PollModel p = null;
			if (rs.next())
				p = makePollObj(rs);
			return p;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Helper function to create poll object from result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private PollModel makePollObj(ResultSet rs) throws SQLException {
		PollModel p = new PollModel();
		p.set_pId(rs.getInt("_pId"));
		p.setTitle(rs.getString("title"));
		p.setDescription(rs.getString("description"));
		p.setType(rs.getString("type"));
		p.setOptions(rs.getString("options"));
		p.setComments(rs.getString("comments"));
		p.setFinalChoice(rs.getString("final_choice"));
		return p;
	}
	
	/**
	 * Create the database
	 */
	private void createDb() {
		try (Connection c = connect()) {
			Statement stmt = c.createStatement();
			stmt.execute(CREATE_POLLS_TABLE);
			stmt.execute(CREATE_VOTES_TABLE);
			
		} catch (SQLException e) { // add a fault here
			System.err.println(e.getMessage());
		}
	}	
	
	/**
	 * Connect to the test.db database
	 * @return the Connection object
	 */
	private Connection connect() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(URL);
		} catch (Exception e) {	// add a fault here
			System.out.println(e.getMessage());
		}
		return c;
	}
	
	public static final String URL = "jdbc:sqlite:poll.db";
	public static final String POLLS_TABLE = "Polls";
	public static final String VOTES_TABLE = "Votes";
	
	private static final String CREATE_POLLS_TABLE = "create table if not exists " + POLLS_TABLE + " ("
														+ "_pId integer primary key,"
														+ "title text not null," 
														+ "description text,"
														+ "type text check(type = 'time' or type = 'string'),"
														+ "options text not null,"
														+ "comments text,"
														+ "final_choice text"
														+ ");";
	
	private static final String CREATE_VOTES_TABLE = "create table if not exists " + VOTES_TABLE + " ("
														+ "_voteId integer primary key,"
														+ "_pId integer not null," 
														+ "participant_name text not null,"
														+ "chosen_option text not null,"
														+ "foreign key(_pId) references Polls(_pId)"
														+ ");";
}
