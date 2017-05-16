package au.edu.unsw.soacourse.foundit.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.JSONObject;

import au.edu.unsw.soacourse.foundit.bean.JobApplication;
import au.edu.unsw.soacourse.foundit.model.Application;
import au.edu.unsw.soacourse.foundit.model.JobPosting;
import au.edu.unsw.soacourse.foundit.model.Review;

/**
 * Job service helper
 * @author trungisme
 *
 */
public class JobService {
	
	/**
	 * Updates a pre-existing job application as long as the application application status is still 'received'
	 * @param ja
	 */
	public void updateApplication(JobApplication ja) {
		jobClient.reset();
		jobClient.path("/jobs/application").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		Application a = new Application();
		a.set_jobID(ja.get_jobID());
		a.setAttachment1(ja.getAttachment1());
		a.setAttachment2(ja.getAttachment2());
		a.setCandidatesDetails(ja.getCandidatesDetails());
		a.setCoverLetter(ja.getCoverLetter());
		a.setStatus(APP_STATUSES[0]);
		
		String jsonString = "{}";	
		try {
			jsonString = new ObjectMapper().writeValueAsString(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jobClient.post(jsonString);
	}
	
	/**
	 * Creates an application for the applicant
	 * @param ja
	 */
	public void createApplication(JobApplication ja) {
		jobClient.reset();
		jobClient.path("/jobs/application").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		Application a = new Application();
		a.set_jobID(ja.get_jobID());
		a.setAttachment1(ja.getAttachment1());
		a.setAttachment2(ja.getAttachment2());
		a.setCandidatesDetails(ja.getCandidatesDetails());
		a.setCoverLetter(ja.getCoverLetter());
		a.setStatus(APP_STATUSES[0]);
		
		String jsonString = "{}";	
		try {
			jsonString = new ObjectMapper().writeValueAsString(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jobClient.post(jsonString);
	}
	
	
	public void createJobPosting(JobPosting jp) {
		jobClient.reset();
		jobClient.path("/jobs").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		JobPosting p = new JobPosting();
		p.setStatus("Created");
		//p.set_jobID(jp.get_jobID());
		p.setClassification(jp.getClassification());
		p.setCompanyName(jp.getCompanyName());
		p.setJobDescription(jp.getJobDescription());
		p.setLocation(jp.getLocation());
		p.setPositionType(jp.getPositionType());
		p.setSalaryRate(jp.getSalaryRate());
		
		String jsonstring= "{}";
		try{
			jsonstring = new ObjectMapper().writeValueAsString(p);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		jobClient.post(jsonstring);
	}
	
	public void updateJobPosting(JobPosting jp) {
		jobClient.reset();
		jobClient.path("/jobs").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		JobPosting p = new JobPosting();
		p.setStatus(jp.getStatus());
		p.set_jobID(jp.get_jobID());
		p.setClassification(jp.getClassification());
		p.setCompanyName(jp.getCompanyName());
		p.setJobDescription(jp.getJobDescription());
		p.setLocation(jp.getLocation());
		p.setPositionType(jp.getLocation());
		p.setSalaryRate(jp.getSalaryRate());
		
		String jsonstring= "{}";
		try{
			jsonstring = new ObjectMapper().writeValueAsString(p);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		jobClient.put(jsonstring);
	}
	
	/**
	 * Gets list of current user's applications including company name and position type
	 * for the post they are applying for
	 * @param email unique indentifier that we are relying on
	 * @return
	 */
	public List<JobApplication> getCurrentApplications(String email) {
		jobClient.reset();
		jobClient.path("/jobs/application").accept(MediaType.APPLICATION_JSON);
		String jsonString = jobClient.get(String.class);
		
		List<JobApplication> results = new ArrayList<>();
		
		// get all applications
		List<Application> appList = new ArrayList<>();
		try {
			appList = new ObjectMapper().readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, Application.class));
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		// filter by applicant email and get all job postings based on job id
		for (Application a: appList) {
			if (a.getCandidatesDetails().contains(email)) {
				JobPosting jp = getJobPost(a.get_jobID());
				if (jp != null) {
					JobApplication ja = new JobApplication();
					ja.set_jobID(a.get_jobID());
					ja.setStatus(a.getStatus());
					ja.setCompanyName(jp.getCompanyName());
					ja.setPositionType(jp.getPositionType());
					results.add(ja);
				}
			}
		}
		
		return results;
	}
	
	
	/**
	 * Get a particular job post from the job service
	 * @param id id of particular job post
	 * @return a job posting object
	 */
	public JobPosting getJobPost(int id) {
		jobClient.reset();
		jobClient.path("/jobs/" + id).accept(MediaType.APPLICATION_JSON);
		try {
			return new ObjectMapper().readValue(jobClient.get(String.class), JobPosting.class);
		} catch (IOException e) {
			return null;			
		}
	}
	
	public void archiveJobPost(int id) {
		jobClient.reset();
		jobClient.path("/jobs/" + id).accept(MediaType.APPLICATION_JSON);
		jobClient.delete();
		
	}
	
	public List<JobPosting> getAllJobPosts(String companyname){
		List<JobPosting> results = new ArrayList<>();
		jobClient.reset();
		jobClient.path("/jobs/search").query("companyName", companyname).accept(MediaType.APPLICATION_JSON);
		String jsonString = jobClient.get(String.class);
		try{
			results = new ObjectMapper().readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, JobPosting.class));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return results;
	}
	
	public List<Application> getAllApplications(){
		List<Application> results = new ArrayList<Application>();
		jobClient.reset();
		jobClient.path("/jobs/application").accept(MediaType.APPLICATION_JSON);
		String jsonString = jobClient.get(String.class);
		try{
			results = new ObjectMapper().readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, Application.class));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return results;
	}
	
	public Application getApplication(int jobid, int appid) {
		jobClient.reset();
		jobClient.path("/jobs/" + jobid + "/application/" + appid).accept(MediaType.APPLICATION_JSON);
		try {
			return new ObjectMapper().readValue(jobClient.get(String.class), Application.class);
		} catch (IOException e) {
			return null;			
		}
	}
	
	public List<Application> getApplicationPerJob(int jobid) {
		List<Application> results = new ArrayList<Application>();
		jobClient.reset();
		jobClient.path("/jobs/" + jobid + "/application/").accept(MediaType.APPLICATION_JSON);
		String jsonString = jobClient.get(String.class);
		try {
			results = new ObjectMapper().readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, Application.class));
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return results;
	}
	
	public void createReview(Review review){
		jobClient.reset();
		jobClient.path("/jobs/application/review").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		Review rev = new Review();
		rev.set_appID(review.get_appID());
		rev.setReviewerDetails(review.getReviewerDetails());
		
		String jsonString = "{}";	
		try {
			jsonString = new ObjectMapper().writeValueAsString(rev);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jobClient.post(jsonString);
		
	}
	
	public Review getReview(int appid, int reviewid) {
		jobClient.reset();
		jobClient.path("/jobs/application/" + appid + "/review/" + reviewid).accept(MediaType.APPLICATION_JSON);
		try {
			return new ObjectMapper().readValue(jobClient.get(String.class), Review.class);
		} catch (IOException e) {
			return null;			
		}
	}
	
	public List<Review> getAllReviews(){
		List<Review> results = new ArrayList<Review>();
		jobClient.reset();
		jobClient.path("/jobs/application/review").accept(MediaType.APPLICATION_JSON);
		String jsonString = jobClient.get(String.class);
		try{
			results = new ObjectMapper().readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, Review.class));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Returns a list of job postings based on a search for the applicant to view
	 * @param j job posting object mapped to search parameters
	 * @return list of job postings
	 */
	public List<JobPosting> getJobPosts(JobPosting j) {
		jobClient.reset();
		jobClient.path("/jobs/search").accept(MediaType.APPLICATION_JSON);
		Map<String, Object> params = new HashMap<>();
		params.put("salaryRate", j.getSalaryRate());
		params.put("jobDescription", j.getJobDescription());
		params.put("location", j.getLocation());
		
		for (Entry<String, Object> entry : params.entrySet()) {
			jobClient.query(entry.getKey(), entry.getValue());
		}
		
		String jsonString = jobClient.get(String.class);
//		System.out.println(jsonString);
		
		List<JobPosting> results = new ArrayList<>();
		
		try {
			results = new ObjectMapper().readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, JobPosting.class));
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		return results;
	}
	
	/**
	 * Constructor that initialises an interaction with job service at base url
	 */
	public JobService() {
		setJobclient(WebClient.create(JOB_SERVICE));
	}

	public WebClient getJobClient() {
		return jobClient;
	}

	public void setJobclient(WebClient jobClient) {
		this.jobClient = jobClient;
	}
	
	public static final String JOB_SERVICE = "http://localhost:8080/JobService";
	private final static String[] APP_STATUSES = {"Received", "In-Review", "Accept", "Reject", "Interview"};
	private WebClient jobClient;

	
}
