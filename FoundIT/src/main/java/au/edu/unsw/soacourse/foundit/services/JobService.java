package au.edu.unsw.soacourse.foundit.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		Map<String, Object> params = new HashMap<>();
		params.put("salaryRate", j.getSalaryRate());
		params.put("jobDescription", j.getJobDescription());
		params.put("location", j.getLocation());
		
		for (Entry<String, Object> entry : params.entrySet()) {
			jobClient.query(entry.getKey(), entry.getValue());
		}
		
		
		
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
