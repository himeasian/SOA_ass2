package au.edu.unsw.soacourse.foundit.services;

import org.apache.cxf.jaxrs.client.WebClient;

/**
 * Job service helper
 * @author trungisme
 *
 */
public class JobService {
	
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
