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

	/**
	 * Create the database
	 */
	private void createDb() {
		try (Connection c = connect()) {
			Statement stmt = c.createStatement();
			stmt.execute(CREATE_USERS_TABLE);
			stmt.execute(CREATE_NOTIFICATION_TABLE);

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

	private static final String CREATE_USERS_TABLE = "create table if not exists " + USERS_TABLE + " ("
			+ "_uid integer primary key," + "fname text not null," + "lname text not null,"
			+ "email text not null unique," + "password text not null," + "role text not null," + "company text" + ");";

	private static final String CREATE_NOTIFICATION_TABLE = "create table if not exists " + NOTIFICATION_TABLE + " ("
			+ "_nid integer primary key," + "email references Users(email)," + "message text not null);";
}
