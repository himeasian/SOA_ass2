package au.edu.unsw.soacourse.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.soacourse.model.*;

public class DatabaseHandler {
	
	public static final String URL = "jdbc:sqlite:jobposting.db"  ;
	public static final String JOB_POSTINGS_TABLE="Jobs";
	public static final String APPLICATIONS_TABLE = "Applications";
	public static final String REVIEWS_TABLE = "Reviews";
	
	private static final String CREATE_JOB_POSTINGS_TABLE = "CREATE TABLE IF NOT EXISTS " + JOB_POSTINGS_TABLE + " ("
			+ "_JobID integer PRIMARY KEY,"
			+ "CompanyName text,"
			+ "SalaryRate real,"
			+ "PositionType text,"
			+ "Location text,"
			+ "JobDescription text,"
			+ "Status text,"
			+ "Classification text"
			+ ");";
	
	private static final String CREATE_APPLICATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + APPLICATIONS_TABLE + " ("
			+ "_AppID integer PRIMARY KEY,"
			+ "_JobID integer NOT NULL,"
			+ "CandidatesDetails text,"
			+ "CoverLetter text,"
			+ "Status text NOT NULL,"
			+ "Attachment1 text,"
			+ "Attachment2 text,"
			+ "FOREIGN KEY(_JobID) REFERENCES Jobs(_JobID)"
			+ ");";
	
	private static final String CREATE_REVIEWS_TABLE = "CREATE TABLE IF NOT EXISTS " + REVIEWS_TABLE + " ("
			+ "_reviewID PRIMARY KEY,"
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
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(URL);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * Using a result set from a sql query, a JobPosting is returned based on the JobID
	 * @param rs
	 * @return A JobPosting model
	 * @throws SQLException
	 */
	private JobPosting generateJobPosting(ResultSet rs) throws SQLException{
		JobPosting jp = new JobPosting();
		//rs.next();
		jp.set_jobID(rs.getInt("_JobID"));
		jp.setCompanyName(rs.getString("CompanyName"));
		jp.setSalaryRate(rs.getFloat("SalaryRate"));
		jp.setPositionType(rs.getString("PositionType"));
		jp.setLocation(rs.getString("Location"));
		jp.setJobDescription(rs.getString("JobDescription"));
		jp.setStatus(rs.getString("Status"));
		jp.setClassification(rs.getString("Status"));
		
		return jp;
	}
	
	/**
	 * Check if job posting exists in Jobs based on JobPosting passed
	 * @param jp
	 * @return _JobID
	 */
	private int checkJobPostingExists(JobPosting jp){
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
			stmtcheck.setFloat(2, jp.getSalaryRate());
			stmtcheck.setString(3, jp.getPositionType());
			stmtcheck.setString(4, jp.getLocation());
			stmtcheck.setString(5, jp.getJobDescription());
			stmtcheck.setString(6, jp.getStatus());
			stmtcheck.setString(7, jp.getClassification());
			
			//String stmt = "SELECT * FROM Jobs";
			//PreparedStatement stme = conn.prepareStatement(stmtcheck);
			//ResultSet rs = stme.executeQuery();
			ResultSet rs =stmtcheck.executeQuery();
			
			if(rs.next()){
				return rs.getInt(1);
			}
			else{
				return -2;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public int createJobPosting(JobPosting jp){
		
		// Check to see if the JobPosting already exists in the JobPosting Table
		try(Connection conn = connect()){
			
			/*int checkid = checkJobPostingExists(jp);
			if(checkid>0){
				return checkid; 
			}
			else{*/
				String stmtstrinsert = "INSERT INTO Jobs(CompanyName, "
						+ "SalaryRate, "
						+ "PositionType, "
						+ "Location, "
						+ "JobDescription, "
						+ "Status, "
						+ "Classification) "
						+ "VALUES (?,?,?,?,?,?,?)";
				PreparedStatement stmtinsert = conn.prepareStatement(stmtstrinsert);
				stmtinsert.setString(1, jp.getCompanyName());
				stmtinsert.setFloat(2, jp.getSalaryRate());
				stmtinsert.setString(3, jp.getPositionType());
				stmtinsert.setString(4, jp.getLocation());
				stmtinsert.setString(5, jp.getJobDescription());
				stmtinsert.setString(6, jp.getStatus());
				stmtinsert.setString(7, jp.getClassification());
				stmtinsert.executeUpdate();	
				
				return conn.createStatement().executeQuery("SELECT last_insert_rowid();").getInt(1);
				
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
			
		}
		
	}

	public JobPosting getJobPosting(int _JobID){
		JobPosting jp = new JobPosting();
		
		try(Connection conn = connect()){
			String query = "SELECT * FROM Jobs WHERE _JobID = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, _JobID);
			ResultSet rs = pstmt.executeQuery();
			//rs.next();
			jp = generateJobPosting(rs);
			return jp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets all the JobPostings from the Jobs table and stores them in an arraylist.
	 * @return List<JobPosting>
	 */
	public List<JobPosting> getJobPostings(){
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Jobs";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlquery);
			while(rs.next()){
				jplist.add(generateJobPosting(rs));
			}
			
			return jplist;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// NEED TO MODIFY TO HANDLE THAT IF STATUS IS SAY "IN-REVIEW" then it can't be updated to "OPEN"
	public boolean updateJobPosting(JobPosting jp){
		
		int JobID = jp.get_jobID();
		try(Connection conn = connect()){
			// Need to check if JobPosting exists first.
			String sqlquery = "SELECT * FROM Jobs WHERE _JobID = ?";
			PreparedStatement stmtcheck = conn.prepareStatement(sqlquery);
			stmtcheck.setInt(1, JobID);
			ResultSet rs = stmtcheck.executeQuery();
			
			if(rs.next()){
				String updatequery = "UPDATE Jobs SET CompanyName = ?, SalaryRate = ?, PositionType = ?, Location = ?, JobDescription = ?, Status = ?, Classification = ? WHERE _JobID = ?";
				PreparedStatement stmt = conn.prepareStatement(updatequery);
				stmt.setString(1, jp.getCompanyName());
				stmt.setFloat(2, jp.getSalaryRate());
				stmt.setString(3, jp.getPositionType());
				stmt.setString(4, jp.getLocation());
				stmt.setString(5, jp.getJobDescription());
				stmt.setString(6, jp.getStatus());
				stmt.setString(7, jp.getClassification());
				stmt.executeUpdate();
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public List<JobPosting> searchJobPostings(JobPosting searchjp){
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		try(Connection conn = connect()){
		String sqlquery = "SELECT * FROM Jobs WHERE";
		if(!searchjp.getCompanyName().equals(null)){
			sqlquery+="CompanyName = " + searchjp.getCompanyName();
		}
		if(searchjp.getSalaryRate()!=0){
			sqlquery+="AND SalaryRate = " + searchjp.getSalaryRate();
		}
		if(!searchjp.getPositionType().equals(null)){
			sqlquery+="AND PositionType = " + searchjp.getPositionType();
		}
		if(!searchjp.getLocation().equals(null)){
			sqlquery+="AND Location = " + searchjp.getLocation();
		}
		if(!searchjp.getJobDescription().equals(null)){
			sqlquery+="AND JobDescription = " + searchjp.getJobDescription();
		}
		if(!searchjp.getStatus().equals(null)){
			sqlquery+="AND Status = " + searchjp.getStatus();
		}
		if(!searchjp.getClassification().equals(null)){
			sqlquery+="AND Classification = " + searchjp.getClassification();
		}
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlquery);
		
		while(rs.next()){
			JobPosting temp = new JobPosting();
			temp.set_jobID(rs.getInt("_JobID"));
			temp.setCompanyName(rs.getString("CompanyName"));
			temp.setSalaryRate(rs.getInt("SalaryRate"));
			temp.setPositionType(rs.getString("PositionType"));
			temp.setLocation(rs.getString("Location"));
			temp.setJobDescription(rs.getString("JobDescription"));
			temp.setStatus(rs.getString("Status"));
			temp.setClassification(rs.getString("Classification"));
			jplist.add(temp);
		}
		return jplist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteJobPosting(int _JobID){
		try(Connection conn = connect()){
			// Check if job posting exists
			String sqlquery = "SELECT COUNT(*) AS total FROM Jobs WHERE _JobID = ?";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, _JobID);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				int total = rs.getInt("total");
				if(total>0){
					String sqlquerydelete = "DELETE FROM Jobs WHERE _JobID = ?";
					PreparedStatement stmt2 = conn.prepareStatement(sqlquerydelete);
					stmt.setInt(1, _JobID);
					ResultSet rs2 = stmt.executeQuery();
					return true;
				}
				return false;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
}
