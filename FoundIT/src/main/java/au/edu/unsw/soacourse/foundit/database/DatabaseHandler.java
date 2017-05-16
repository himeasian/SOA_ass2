package au.edu.unsw.soacourse.foundit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.soacourse.foundit.bean.Register;
import au.edu.unsw.soacourse.foundit.model.User;
import au.edu.unsw.soacourse.foundit.model.Notification;
import au.edu.unsw.soacourse.foundit.model.Poll;

/**
 * Database handler specific for foundit app
 * 
 * @author trungisme
 *
 */
public class DatabaseHandler {

	/**
	 * Constructor for database handler, on instantiation create a db if not
	 * existing
	 */
	public DatabaseHandler() {
		createDb();
	}
	
	/**
	 * get users notifications
	 * @param email
	 * @return
	 */
	public List<Notification> getNotifications(String email) {
		try (Connection c = connect()) {
			
			ResultSet rs = c.createStatement().executeQuery("select count(*) from notifications where email = '"+email+"';");
			
			List<Notification> notifs = new ArrayList<>();
			while(rs.next()) {
				Notification n = new Notification();
				n.set_nid(rs.getInt("_nid"));
				n.setEmail(rs.getString("email"));
				n.setMessage(rs.getString("message"));
				n.setFrom(rs.getString("from"));
			}
			
			return notifs;
			
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * get number of notifications on user
	 * @param email
	 * @return
	 */
	public int getNumNotifications(String email) {
		try (Connection c = connect()) {
			
			return c.createStatement().executeQuery("select count(*) from notifications where email = '"+email+"';").getInt(1);
			
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**
	 * insert an reviewer allocation into the allocations table
	 * @param email
	 * @param jid
	 * @return
	 */
	public int insertAllocation(String email, int jid) {
		try (Connection c = connect()) {
			String sql = "insert into allocations(email, _jid) values(?, ?);";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setInt(2, jid);
			pstmt.executeUpdate();
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);
			
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**
	 * Get all jobs allocations referencing the email
	 * @param email
	 * @return
	 */
	public List<Integer> getAllocations(String email) {
		try (Connection c = connect()) {
			
			ResultSet r1 = c.createStatement().executeQuery("select * from allocations;");
			while (r1.next()) {
				System.out.println(r1.getInt(1) + r1.getString(2) + r1.getString(3));
			}
			
			
			String sql = "select _jid from allocations where email = ?;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			
			List<Integer> jobs = new ArrayList<>();
			while (rs.next()) {
				jobs.add(rs.getInt(1));
			}
			return jobs;
			
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Returns a list of reviewers
	 * @return
	 */
	public List<User> getReviewers() {
		
		try (Connection c = connect()) {
			String sql = "select * from users where role = ?;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, "reviewer");
			ResultSet rs = pstmt.executeQuery();
			
			List<User> reviewers = new ArrayList<>();
			while (rs.next()) {
				User u = new User();
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setRole(rs.getString("role"));
				u.setCompany(rs.getString("company"));
				reviewers.add(u);
			}
			return reviewers;
			
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Returns a user object from an email request
	 * 
	 * @param email
	 * @return
	 */
	public User getUser(String email) {

		try (Connection c = connect()) {
			String sql = "select * from users where email = ?;";
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			User u = null;
			if (rs.next()) {
				u = new User();
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setRole(rs.getString("role"));
				u.setCompany(rs.getString("company"));
			}
			return u;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create a user from a model of values
	 * 
	 * @param model
	 *            new user model
	 * @return
	 */
	public int createUser(Register model) {

		try (Connection c = connect()) {
			String ins = "insert into users(fname, " + "lname, email, password, "
					+ "role, company) values(?,?,?,?,?,?);";
			PreparedStatement istmt = c.prepareStatement(ins);
			istmt.setString(1, model.getFname());
			istmt.setString(2, model.getLname());
			istmt.setString(3, model.getEmail());
			istmt.setString(4, model.getPassword());
			istmt.setString(5, model.getRole());
			istmt.setString(6, model.getCompany());
			istmt.executeUpdate();

			// get id
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int createPoll(Poll p, String candidateDetails){
		try (Connection c = connect()) {
			String ins = "insert into polls(candidateDetails,_pid) values(?,?);";
			PreparedStatement istmt = c.prepareStatement(ins);
			istmt.setString(1, candidateDetails);
			istmt.setInt(2, p.get_pId());
			istmt.executeUpdate();
			
			return c.createStatement().executeQuery("select last_insert_rowid();").getInt(1);
		}
		catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
		
	}

	/**
	 * Create the database
	 */
	private void createDb() {
		try (Connection c = connect()) {
			Statement stmt = c.createStatement();
			stmt.execute(CREATE_USERS_TABLE);
			stmt.execute(CREATE_NOTIFICATION_TABLE);
			stmt.execute(CREATE_POLLS_TABLE);
			stmt.execute(CREATE_ALLOC_TABLE);

		} catch (SQLException e) { // add a fault here
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Connect to the test.db database
	 * 
	 * @return the Connection object
	 */
	private Connection connect() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(URL);
		} catch (Exception e) { // add a fault here
			System.out.println(e.getMessage());
		}
		return c;
	}

	private static final String URL = "jdbc:sqlite:poll.db";
	private static final String USERS_TABLE = "Users";
	private static final String NOTIFICATION_TABLE = "Notifications";
	private static final String POLL_TABLE = "Polls";
	private static final String JOB_ALLOC_TABLE = "Allocations";

	private static final String CREATE_USERS_TABLE = "create table if not exists " + USERS_TABLE + " ("
			+ "_uid integer primary key," + "fname text not null," + "lname text not null,"
			+ "email text not null unique," + "password text not null," + "role text not null," + "company text" + ");";

	private static final String CREATE_NOTIFICATION_TABLE = "create table if not exists " + NOTIFICATION_TABLE + " ("
			+ "_nid integer primary key," + "email references Users(email)," + "message text not null, from text not null);";
	
	private static final String CREATE_POLLS_TABLE = "create table if not exists " + POLL_TABLE + " ("
			+ "_interviewid integer primary key," + "candidateDetails text not null," + "_pid integer not null);";
	
	private static final String CREATE_ALLOC_TABLE = "create table if not exists " + JOB_ALLOC_TABLE + " ("
													+ "_aid integer primary key,"
													+ "email references Users(email),"
													+ "_jid integer);";
	
}
