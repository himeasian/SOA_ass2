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
import au.edu.unsw.soacourse.model.VoteModel;

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
	 * Counts num votes for current poll
	 * @param pid
	 * @return
	 */
	public int countVotes(int pid) {
		
		try (Connection c = connect()) {
			String sql = "select count(*) from polls where _pId = ?;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, pid);
			
			return pstmt.executeQuery().getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Update a vote if it exists, if it doesn't exist, then delete it
	 * @param vm vote model 
	 * @param voteId id of the vote to be updated
	 * @return new id if inserted else 0
	 */
	public int updateVote(VoteModel vm, int voteId) {
		
		try (Connection c = connect()) {
			// update if exists
			String sql = "update votes "
					+ "set participant_name = ?, chosen_option = ? "
					+ "where _voteId = ? and _pId = ?;";
			
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, vm.getParticipantName());
			pstmt.setString(2, vm.getChosenOption());
			pstmt.setInt(3, voteId);
			pstmt.setInt(4, vm.get_pId());
			pstmt.executeUpdate();
			
			// if nothing happened.. insert
			sql = "insert into votes(_pId, participant_name, chosen_option) "
					+ "select ?, ?, ? "
					+ "where (select Changes() = 0);";
			
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, vm.get_pId());
			pstmt.setString(2, vm.getParticipantName());
			pstmt.setString(3, vm.getChosenOption());
			pstmt.executeUpdate();
			
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Gets a vote requested by id
	 * @param voteId the id
	 * @return a model of the vote
	 */
	public VoteModel getVote(int voteId) {
		
		try (Connection c = connect()) {
			String sql = "select * from votes where _voteId = ?;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, voteId);
			ResultSet rs = pstmt.executeQuery();
			
			VoteModel vm = null;
			if (rs.next()) {
				vm = new VoteModel();
				vm.set_voteId(rs.getInt("_voteId"));
				vm.set_pId(rs.getInt("_pId"));
				vm.setParticipantName(rs.getString("participant_name"));
				vm.setChosenOption(rs.getString("chosen_option"));
			}
			return vm;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Creates a vote from model of values
	 * @param v vote model POJO
	 * @return id of new vote item in table
	 */
	public int createVote(VoteModel v) {
		
		try (Connection c = connect()) {
			String sql = "insert into votes(_pId,"
					+ "participant_name, chosen_option) "
					+ "values(?,?,?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, v.get_pId());
			pstmt.setString(2, v.getParticipantName());
			pstmt.setString(3, v.getChosenOption());
			pstmt.executeUpdate();
			
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
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
	 * Updates a poll, if doesn't exist creates a new one
	 * @param p model of poll to update with
	 * @param pid poll id to update
	 * @return id of new poll or 0
	 */
	public int updatePoll(PollModel p, int pid) {
		
		try (Connection c = connect()) {
			// update if exists
			String sql = "update polls "
					+ "set title = ?, description = ?, type = ?, options = ?, comments = ? "
					+ "where _pId = ?;";
			
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, p.getTitle());
			pstmt.setString(2, p.getDescription());
			pstmt.setString(3, p.getType());
			pstmt.setString(4, p.getOptions());
			pstmt.setString(5, p.getComments());
			pstmt.setInt(6, pid);
			pstmt.executeUpdate();
			
			// if nothing happened.. insert
			sql = "insert into polls(title, description, type, options, comments) "
					+ "select ?, ?, ?, ?, ? "
					+ "where (select Changes() = 0);";
			
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, p.getTitle());
			pstmt.setString(2, p.getDescription());
			pstmt.setString(3, p.getType());
			pstmt.setString(4, p.getOptions());
			pstmt.setString(5, p.getComments());
			pstmt.executeUpdate();
			
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Create a poll from a model of values
	 * @param p poll model
	 * @return the pid of the poll
	 */
	public int createPoll(PollModel p) {
		
		try (Connection c = connect()) {
			String ins = "insert into polls(title, "
					+ "description, type, options, "
					+ "comments, final_choice) values(?,?,?,?,?,?);";
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
			ResultSet rs = stmt.executeQuery(sql + ";");
			
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
			String sql = "select * from polls where _pId = ?;";
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
