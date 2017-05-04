package au.edu.unsw.soacourse.jobservice;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import au.edu.unsw.soacourse.model.JobPosting;
import au.edu.unsw.soacourse.database.DatabaseHandler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/jobs")
public class JobService {
	@GET
	@Produces("application/json")
	public Response getAllJobPostings(){
		
		return Response.ok().entity(new DatabaseHandler().getJobPostings()).build();
	}
	
	@GET
	@Path("/{jobID}")
	@Produces("application/json")
	public Response getJobPosting(@PathParam("jobID") int jobID){
		DatabaseHandler db = new DatabaseHandler();
		JobPosting jp = db.getJobPosting(jobID);
		
		return Response.ok().entity(jp).build();
	}
	
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createJobPosting(JobPosting source) throws URISyntaxException{
		DatabaseHandler db = new DatabaseHandler();
		int newid = db.createJobPosting(source);
		return Response.created(new URI("/jobs/" + newid)).entity(source).build();
	}
	
	@DELETE
	@Path("/{jobID}")
	@Consumes("application/json")
	public Response deleteJobPosting(@PathParam("jobID") int jobID){
		DatabaseHandler db = new DatabaseHandler();
		boolean answer = db.deleteJobPosting(jobID);
		
		return Response.ok(answer).build();
	}
	
	@PUT
	@Path("/")
	@Consumes("application/json")
	public Response updateJobPosting(JobPosting ujp){
		DatabaseHandler db = new DatabaseHandler();
		boolean answer = db.updateJobPosting(ujp);
		
		return Response.ok(answer).build();
	}
}
