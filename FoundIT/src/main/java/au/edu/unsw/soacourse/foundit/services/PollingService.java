package au.edu.unsw.soacourse.foundit.services;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.map.ObjectMapper;

import au.edu.unsw.soacourse.foundit.model.Poll;
import au.edu.unsw.soacourse.foundit.model.Vote;


/**
 * Polling service helper
 * @author trungisme
 *
 */
public class PollingService {
	

	public void createPoll(Poll p){
		pollClient.reset();
		pollClient.path("/polls").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		Poll poll = new Poll();
		poll.setComments(p.getComments());
		poll.setDescription(p.getDescription());
		poll.setFinalChoice(p.getFinalChoice());
		poll.setOptions(p.getOptions());
		poll.setTitle(p.getTitle());
		poll.setType(p.getType());
		
		String jsonstring= "{}";
		try{
			jsonstring = new ObjectMapper().writeValueAsString(poll);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		pollClient.post(jsonstring);
		
	}
	
	public void createVote(Vote v, int pollID){
		pollClient.reset();
		pollClient.path("/polls/"+pollID +"/vote").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		
		Vote vote = new Vote();
		vote.set_pId(pollID);
		vote.setChosenOption(v.getChosenOption());
		vote.setParticipantName(v.getParticipantName());
		
		String jsonstring= "{}";
		try{
			jsonstring = new ObjectMapper().writeValueAsString(vote);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		pollClient.post(jsonstring);
	}
	
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
