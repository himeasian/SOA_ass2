package au.edu.unsw.soacourse.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	 * Creates the databases relevant to Job Service
	 */
	public static void createDb() {	
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(CREATE_JOB_POSTINGS_TABLE);
                stmt.executeQuery(CREATE_APPLICATIONS_TABLE);
                stmt.executeQuery(CREATE_REVIEWS_TABLE);
            }
 
        } catch (SQLException e) {
			System.err.println(e.getMessage());
        }
    }
}
