package au.edu.unsw.soacourse.foundit.services;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import au.edu.unsw.soacourse.foundit.model.JobPosting;

/**
 * Job service helper
 * @author trungisme
 *
 */
public class JobService {
	
	public List<JobPosting> getJobPosts(JobPosting j) {
		jobClient.reset();
		jobClient.path("/jobs/search").accept(MediaType.APPLICATION_JSON);
		
		return null;
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
	
	private WebClient jobClient;
}
