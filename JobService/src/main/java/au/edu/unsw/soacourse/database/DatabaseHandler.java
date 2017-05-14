package au.edu.unsw.soacourse.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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
			+ "_ReviewID integer PRIMARY KEY,"
			+ "_AppID integer NOT NULL,"
			+ "ReviewerDetails text,"
			+ "Comments text,"
			+ "Decision text,"
			+ "FOREIGN KEY(_AppID) REFERENCES Applications(_AppID)"
			+ ");";
	
	private static final String[] statusarray = {"Created","Open","In-Review", "Processed", "Sent-Invitations","Completed"};
	private static final List<String> statuslist = Arrays.asList(statusarray);
	
	
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
                //stmt.execute("DROP TABLE Reviews");
                //stmt.execute("DROP TABLE Applications");
                //stmt.execute("DROP TABLE Jobs");
                stmt.execute(CREATE_JOB_POSTINGS_TABLE);
                stmt.execute(CREATE_APPLICATIONS_TABLE);
                stmt.execute(CREATE_REVIEWS_TABLE);
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
		jp.setClassification(rs.getString("Classification"));
		
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
			if(jp.getStatus().equals("Created")){
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
				//stmtinsert.setString(6, jp.getStatus());
				stmtinsert.setString(6, "Created");
				stmtinsert.setString(7, jp.getClassification());
				stmtinsert.executeUpdate();	
				
				return conn.createStatement().executeQuery("SELECT last_insert_rowid();").getInt(1);
			}
			else{
				return -1;
			}
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
	
	
	public int updateJobPosting(JobPosting jp){
		String statustemp = jp.getStatus();
		int JobID = jp.get_jobID();
		try(Connection conn = connect()){
			
				
				// Also need to check if JOBID status isn't in review or later
				/*List<String> statuslist = new ArrayList<String>();
				statuslist.add("Created");
				statuslist.add("Open");
				statuslist.add("In-Review");
				statuslist.add("Processed");
				statuslist.add("Sent-Invitations");
				statuslist.add("Completed");*/
				
				String jobcheckquery = "SELECT * FROM Jobs WHERE _JobID = ?";
				PreparedStatement stmt2 = conn.prepareStatement(jobcheckquery);
				stmt2.setInt(1,JobID);
				ResultSet rs2 = stmt2.executeQuery();
				rs2.next();
				String statuscheck = rs2.getString("Status");
				
				int ucheck = statuslist.indexOf(statustemp);
				int dbcheck = statuslist.indexOf(statuscheck);
				
				// Also need to check if no applications are submitted against a JOB ID
				
				String appquery = "SELECT COUNT(*) as total FROM Applications WHERE _JobID = ?";
				PreparedStatement stmtapp = conn.prepareStatement(appquery);
				stmtapp.setInt(1, JobID);
				ResultSet rsapp = stmtapp.executeQuery();
				int appcheck=0;
				if(rsapp.next()){
					appcheck = rsapp.getInt("total");
				}
				// Now need to add a check that allows status to be changed to processing, closed etc only if its not open
				// also need to compared the current status in the db vs the status being passed/wanting to change to
				if(ucheck >= dbcheck && dbcheck<2 && appcheck==0){
					String updatequery = "UPDATE Jobs SET CompanyName = ?, SalaryRate = ?, PositionType = ?, Location = ?, JobDescription = ?, Status = ?, Classification = ? WHERE _JobID = ?";
					PreparedStatement stmt = conn.prepareStatement(updatequery);
					stmt.setString(1, jp.getCompanyName());
					stmt.setFloat(2, jp.getSalaryRate());
					stmt.setString(3, jp.getPositionType());
					stmt.setString(4, jp.getLocation());
					stmt.setString(5, jp.getJobDescription());
					stmt.setString(6, jp.getStatus());
					stmt.setString(7, jp.getClassification());
					stmt.setInt(8, JobID);
					stmt.executeUpdate();
					return 1;
				}
				if (ucheck>=dbcheck && appcheck>0){
					String updatequery = "UPDATE Jobs SET Status = ? WHERE _JobID = ?";
					PreparedStatement stmt = conn.prepareStatement(updatequery);
					stmt.setString(1, jp.getStatus());
					stmt.setInt(2, JobID);
					stmt.executeUpdate();
					return 2;
				}
				
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public List<JobPosting> searchJobPostings(String companyName, float salaryRate, String positionType, String location, String jobDescription, String status, String classification){
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		try(Connection conn = connect()){
			List<String> querylist = new ArrayList<String>();
			String sqlquery = "SELECT * FROM Jobs WHERE ";
		if(companyName!=null){
			querylist.add("CompanyName like '%" + companyName +"'%");
		}
		if(salaryRate!=0){
			querylist.add("SalaryRate >= " + salaryRate);
		}
		if(positionType!=null){
			querylist.add("PositionType like '%" + positionType +"%'");
		}
		if(location!=null){
			querylist.add("Location like '%" + location +"%'");
		}
		if(jobDescription!=null){
			querylist.add("JobDescription like '%" + jobDescription +"%'");
		}
		if(status!=null){
			querylist.add("Status = " + status +"'");
		}
		if(classification!=null){
			querylist.add("Classification like '%" + classification +"%'");
		}
		for(int i = 0; i<querylist.size();i++){
			if(i>0){
				sqlquery+=" AND " + querylist.get(i);
			}
			else{
				sqlquery+=querylist.get(i);
			}
		}
		sqlquery+=";";
		System.out.println("query:" + sqlquery);
		
		/*String sqlquery = "SELECT * FROM Jobs WHERE "
			+ "(CompanyName = ? or ? is null) "
			+ "AND (SalaryRate = ? or ? is null) "
			+ "AND (PositionType = ? or ? is null) "
			+ "AND (Location = ? or ? is null) "
			+ "AND (JobDescription = ? or ? is null)"
			+ "AND (Status = ? or ? is null)"
			+ "AND (Classification = ? or ? is null)";
		PreparedStatement stmt = conn.prepareStatement(sqlquery);
		stmt.setString(1, companyName);
		stmt.setString(2, companyName);
		stmt.setFloat(3, salaryRate);
		stmt.setFloat(4, salaryRate);
		stmt.setString(5, positionType);
		stmt.setString(6, positionType);
		stmt.setString(7, location);
		stmt.setString(8, location);
		stmt.setString(9, jobDescription);
		stmt.setString(10, jobDescription);
		stmt.setString(11, status);
		stmt.setString(12, status);
		stmt.setString(13, classification);
		stmt.setString(14, classification);*/
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlquery);
		
		while(rs.next()){
			JobPosting temp = new JobPosting();
			temp.set_jobID(rs.getInt("_JobID"));
			temp.setCompanyName(rs.getString("CompanyName"));
			temp.setSalaryRate(rs.getFloat("SalaryRate"));
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
					stmt2.setInt(1, _JobID);
					int result = stmt2.executeUpdate();
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
	
	public Application getApplication(int jobid, int appid){
		Application app = new Application();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Applications WHERE _AppID = ? AND _JobID = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, appid);
			stmt.setInt(2, jobid);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			app.set_appID(rs.getInt("_AppID"));
			app.set_jobID(rs.getInt("_JobID"));
			app.setCandidatesDetails(rs.getString("CandidatesDetails"));
			app.setCoverLetter(rs.getString("CoverLetter"));
			app.setStatus(rs.getString("Status"));
			app.setAttachment1(rs.getString("Attachment1"));
			app.setAttachment2(rs.getString("Attachment2"));
			
			return app;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Application> getApplicationForJobPosting(int jobid){
		List<Application> applist = new ArrayList<Application>();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Applications WHERE _JobID = ?;";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, jobid);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Application app = new Application();
				app.set_appID(rs.getInt("_AppID"));
				app.set_jobID(rs.getInt("_JobID"));
				app.setCandidatesDetails(rs.getString("CandidatesDetails"));
				app.setCoverLetter(rs.getString("CoverLetter"));
				app.setStatus(rs.getString("Status"));
				app.setAttachment1(rs.getString("Attachment1"));
				app.setAttachment2(rs.getString("Attachment2"));
				applist.add(app);	
				}
			return applist;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Application> getAllApplications(){
		List<Application> applist = new ArrayList<Application>();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Applications;";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);

			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Application app = new Application();
				app.set_appID(rs.getInt("_AppID"));
				app.set_jobID(rs.getInt("_JobID"));
				app.setCandidatesDetails(rs.getString("CandidatesDetails"));
				app.setCoverLetter(rs.getString("CoverLetter"));
				app.setStatus(rs.getString("Status"));
				app.setAttachment1(rs.getString("Attachment1"));
				app.setAttachment2(rs.getString("Attachment2"));
				applist.add(app);
			}
			return applist;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int createApplication(Application app){
		
		try(Connection conn = connect()){
			String sqlquery = "INSERT INTO Applications("
					+ "_JobID, "
					+ "CandidatesDetails, "
					+ "CoverLetter, "
					+ "Status, "
					+ "Attachment1, "
					+ "Attachment2) "
					+ "VALUES (?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, app.get_jobID());
			stmt.setString(2, app.getCandidatesDetails());
			stmt.setString(3, app.getCoverLetter());
			stmt.setString(4, app.getStatus());
			stmt.setString(5, app.getAttachment1());
			stmt.setString(6, app.getAttachment2());
			stmt.executeUpdate();
			
			return conn.createStatement().executeQuery("SELECT last_insert_rowid();").getInt(1);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public int updateApplication(Application app){
		int JobID = app.get_jobID();
		int AppID = app.get_appID();
		
		try(Connection conn = connect()){
			// Need to check if Application exists first.
			/*String sqlquery = "SELECT COUNT(*) AS total FROM Applications WHERE _AppID = ? AND _JobID = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sqlquery);
			stmt1.setInt(1, AppID);
			stmt1.setInt(2, JobID);
			
			ResultSet rs = stmt1.executeQuery();
			
			if(rs.next()){
				int total = rs.getInt("total");
				
				List<String> statuslist = new ArrayList<String>();
				statuslist.add("Created");
				statuslist.add("Open");
				statuslist.add("In-Review");
				statuslist.add("Processed");
				statuslist.add("Sent-Invitations");
				statuslist.add("Completed");
				*/
				String jobcheckquery = "SELECT * FROM Jobs WHERE _JobID = ?";
				PreparedStatement stmt2 = conn.prepareStatement(jobcheckquery);
				stmt2.setInt(1,JobID);
				ResultSet rs2 = stmt2.executeQuery();
				rs2.next();
				String statuscheck = rs2.getString("Status");
				
				int dbcheck = statuslist.indexOf(statuscheck);
				
				if(dbcheck<2){
					String updatequery = "UPDATE Applications SET CandidatesDetails = ?, CoverLetter = ?, Status = ?, Attachment1 = ?, Attachment2 = ? WHERE _AppID = ? AND _JobID = ?";
					PreparedStatement stmt = conn.prepareStatement(updatequery);
					stmt.setString(1, app.getCandidatesDetails());
					stmt.setString(2, app.getCoverLetter());
					stmt.setString(3, app.getStatus());
					stmt.setString(4, app.getAttachment1());
					stmt.setString(5, app.getAttachment2());
					stmt.setInt(6, app.get_appID());
					stmt.setInt(7, app.get_jobID());
					stmt.executeUpdate();
					return 1;
				}
				
				return 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public int createReview(Review rev){
		try(Connection conn = connect()){
			String sqlquery = "INSERT INTO Reviews("
					+ "_AppID, "
					+ "Reviewerdetails, "
					+ "Comments, "
					+ "Decision) "
					+ "VALUES (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, rev.get_appID());
			stmt.setString(2, rev.getReviewerDetails());
			stmt.setString(3, rev.getComments());
			stmt.setString(4, rev.getDecision());
			stmt.executeUpdate();
			
			return conn.createStatement().executeQuery("SELECT last_insert_rowid();").getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
		
	
	public Review getReview(int appid, int reviewid){
		Review review = new Review();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Reviews WHERE _ReviewID = ? AND _AppID = ?";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, reviewid);
			stmt.setInt(2, appid);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			review.set_reviewID(rs.getInt("_ReviewID"));
			review.set_appID(rs.getInt("_AppID"));
			review.setReviewerDetails(rs.getString("ReviewerDetails"));
			review.setComments(rs.getString("Comments"));
			review.setDecision(rs.getString("Decision"));
			
			return review;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getReviewsForApplication(int appid){
		List<Review> revlist = new ArrayList<Review>();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Reviews WHERE _AppID = ?";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			stmt.setInt(1, appid);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Review review = new Review();
				review.set_reviewID(rs.getInt("_ReviewID"));
				review.set_appID(rs.getInt("_AppID"));
				review.setReviewerDetails(rs.getString("ReviewerDetails"));
				review.setComments(rs.getString("Comments"));
				review.setDecision(rs.getString("Decision"));
			
				revlist.add(review);
			}
			return revlist;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getReviewsForReviewer(String reviewer){
		List<Review> revlist = new ArrayList<Review>();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Reviews WHERE ReviewerDetails = ?";
			PreparedStatement stmt = conn.prepareStatement(sqlquery);
			//String reviewermod = "'" + reviewer + "'"; 
			stmt.setString(1, reviewer);
			//System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Review review = new Review();
				review.set_reviewID(rs.getInt("_ReviewID"));
				review.set_appID(rs.getInt("_AppID"));
				review.setReviewerDetails(rs.getString("ReviewerDetails"));
				review.setComments(rs.getString("Comments"));
				review.setDecision(rs.getString("Decision"));
			
				revlist.add(review);
			}
			return revlist;
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getReviews(){
		List<Review> lrev = new ArrayList<Review>();
		try(Connection conn = connect()){
			String sqlquery = "SELECT * FROM Reviews";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlquery);
			while(rs.next()){
				lrev.add(generateReview(rs));
			}
			
			return lrev;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Review generateReview(ResultSet rs) throws SQLException {
		Review rev = new Review();
		//rs.next();
		rev.set_reviewID(rs.getInt("_ReviewID"));
		rev.set_appID(rs.getInt("_AppID"));
		rev.setReviewerDetails(rs.getString("ReviewerDetails"));
		rev.setComments(rs.getString("Comments"));
		rev.setDecision(rs.getString("Decision"));
		
		
		return rev;
	}

	public int updateReview(Review rev){
		int ReviewID = rev.get_reviewID();
		int AppID = rev.get_appID();
		/*List<String> statuslist = new ArrayList<String>();
		statuslist.add("Created");
		statuslist.add("Open");
		statuslist.add("In-Review");
		statuslist.add("Processed");
		statuslist.add("Sent-Invitations");
		statuslist.add("Completed");*/
		
		// Need to check if job posting is still in review, otherwise reject update
		//generate query that gets the job id from applications using appid from reviews. then use jobid to get posting from jobs and check status
		
		
		try(Connection conn = connect()){
			// Need to check if Application exists first.
			/*String sqlquery = "SELECT COUNT(*) AS total FROM Reviews WHERE _ReviewID = ? AND _AppID = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sqlquery);
			stmt1.setInt(1, ReviewID);
			stmt1.setInt(2, AppID);
			
			ResultSet rs = stmt1.executeQuery();
			*/
			
			// Need to check if job posting is still in review, otherwise reject update. Need to craft a query that links 3 tables to get 
			// the correct jobid
			String appquery = "SELECT * FROM Applications WHERE _AppID = ?";
			PreparedStatement pstmt = conn.prepareStatement(appquery);
			pstmt.setInt(1, AppID);
			ResultSet rssearch = pstmt.executeQuery();
			rssearch.next();
			int jobid = rssearch.getInt("_JobID");
			
			String jobquery = "SELECT * FROM Jobs WHERE _JobID = ?";
			PreparedStatement jstmt =conn.prepareStatement(jobquery);
			jstmt.setInt(1, jobid);
			ResultSet rsjob = jstmt.executeQuery();
			rsjob.next();
			String jobstatus = rsjob.getString("Status");
			int dbcheck = statuslist.indexOf(jobstatus);
			
			
				if(dbcheck<2){
					String updatequery = "UPDATE Reviews SET ReviewerDetails = ?, Comments = ?, Decision = ? WHERE _AppID = ? AND _ReviewID = ?";
					PreparedStatement stmt = conn.prepareStatement(updatequery);
					stmt.setString(1, rev.getReviewerDetails());
					stmt.setString(2, rev.getComments());
					stmt.setString(3, rev.getDecision());
					stmt.setInt(4, AppID);
					stmt.setInt(5, ReviewID);
					stmt.executeUpdate();
					return 1;
				}
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}
