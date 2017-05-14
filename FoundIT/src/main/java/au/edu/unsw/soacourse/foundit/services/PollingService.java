package au.edu.unsw.soacourse.foundit.services;

import org.apache.cxf.jaxrs.client.WebClient;

/**
 * Polling service helper
 * @author trungisme
 *
 */
public class PollingService {
	
	/**
	 * Constructor that initialises an interaction with polling service at base url
	 */
	public PollingService() {
		setPollClient(WebClient.create(POLL_SERVICE));
	}
	
	public WebClient getPollClient() {
		return pollClient;
	}

	public void setPollClient(WebClient pollClient) {
		this.pollClient = pollClient;
	}



	public static final String POLL_SERVICE = "http://localhost:8080/PollingService";
	
	private WebClient pollClient;
}
