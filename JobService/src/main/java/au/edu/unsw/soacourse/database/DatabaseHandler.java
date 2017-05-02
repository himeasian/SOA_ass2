package au.edu.unsw.soacourse.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import au.edu.unsw.soacourse.model.*;

public class DatabaseHandler {
	
	public static final String URL = "jdbc:sqlite:jobposting.db"  ;
	public static final String JOB_POSTINGS_TABLE="Jobs";
	public static final String APPLICATIONS_TABLE = "Applications";
	public static final String REVIEWS_TABLE = "Reviews";
	
	private static final String CREATE_JOB_POSTINGS_TABLE = "CREATE TABLE IF NOT EXISTS" + JOB_POSTINGS_TABLE + " ("
			+ "_JobID integer PRIMARY KEY autoincrement,"
			+ "CompanyName text,"
			+ "SalaryRate real,"
			+ "PositionType text,"
			+ "Location text,"
			+ "JobDescription,"
			+ "Status,"
			+ "Classification"
			+ ");";
	
	private static final String CREATE_APPLICATIONS_TABLE = "CREATE TABLE IF NOT EXISTS" + APPLICATIONS_TABLE + " ("
			+ "_AppID integer PRIMARY KEY autoincrement,"
			+ "_JobID integer NOT NULL,"
			+ "CandidatesDetails text,"
			+ "CoverLetter text,"
			+ "Status text NOT NULL,"
			+ "Attachment1 text,"
			+ "Attachment2 text,"
			+ "FOREIGN KEY(_JobID) REFERENCES Jobs(_JobID)"
			+ ");";
	
	private static final String CREATE_REVIEWS_TABLE = "CREATE TABLE IF NOT EXISTS" + REVIEWS_TABLE + ""
			+ "_reviewID PRIMARY KEY autoincrement,"
			+ "_appID integer NOT NULL,"
			+ "ReviewDetails text,"
			+ "Comments text,"
			+ "Decision text,"
			+ "FOREIGN KEY(_AppID) REFERENCES Applications(_AppID)"
			+ ");";
	
	/**
	 * Creates a database upon loading of class.
	 */
	public DatabaseHandler(){
		createDb();
	}
	/**
	 * Creates the databases relevant to Job Service
	 */
	private void createDb() {	
        try (Connection conn = connect()) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(CREATE_JOB_POSTINGS_TABLE);
                stmt.executeQuery(CREATE_APPLICATIONS_TABLE);
                stmt.executeQuery(CREATE_REVIEWS_TABLE);
        } catch (SQLException e) {
			System.err.println(e.getMessage());
        }
    }
	
	private Connection connect(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(URL);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public int createJobPosting(JobPosting jp){
		//int result=0;
		
		// Check to see if the JobPosting already exists in the JobPosting Table
		try(Connection conn = connect()){
			String stmtstrcheck = "SELECT _JobID FROM Jobs WHERE CompanyName = ? "
					+ "AND SalaryRate = ? "
					+ "AND PositionType = ?"
					+ "AND Location = ? "
					+ "AND JobDescription = ? "
					+ "AND Status = ? "
					+ "AND Classification = ?";
			PreparedStatement stmtcheck = conn.prepareStatement(stmtstrcheck);
			stmtcheck.setString(1, jp.getCompanyName());
			stmtcheck.setString(2, jp.getSalaryRate());
			stmtcheck.setString(3, jp.getPositionType());
			stmtcheck.setString(4, jp.getLocation());
			stmtcheck.setString(5, jp.getJobDescription());
			stmtcheck.setString(6, jp.getStatus());
			stmtcheck.setString(7, jp.getClassification());
			
			ResultSet rs =stmtcheck.executeQuery();
			
			if(rs.next()){
				return rs.getInt(1);
			}
			else{
				String stmtstrinsert = "INSERT INTO Jobs (?,?,?,?,?,?,?)";
				PreparedStatement stmtinsert = conn.prepareStatement(stmtstrinsert);
				stmtinsert.setString(1, jp.getCompanyName());
				stmtinsert.setString(2, jp.getSalaryRate());
				stmtinsert.setString(3, jp.getPositionType());
				stmtinsert.setString(4, jp.getLocation());
				stmtinsert.setString(5, jp.getJobDescription());
				stmtinsert.setString(6, jp.getStatus());
				stmtinsert.setString(7, jp.getClassification());
				stmtinsert.executeUpdate();	
				
				ResultSet rscheck = stmtcheck.executeQuery();
				rscheck.next();
				return rscheck.getInt(1);
			}
		} catch (SQLException e) {
			return -1;
			//e.printStackTrace();
		}
		
	}
}
