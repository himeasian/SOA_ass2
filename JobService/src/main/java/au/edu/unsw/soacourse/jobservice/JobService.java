package au.edu.unsw.soacourse.jobservice;

import javax.ws.rs.Consumes;
import au.edu.unsw.soacourse.model.*;
import au.edu.unsw.soacourse.database.DatabaseHandler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/jobs")
public class JobService {
	@GET
	@Produces("json/application")
	public String getAllJobPostings(){
		String x = "";
		return x;
	}
	
	@GET
	@Path("/{jobid}")
	@Produces("json/application")
	public Response getJobPosting(@PathParam("jobID") int jobID){
		DatabaseHandler db = new DatabaseHandler();
		JobPosting jp = db.getJobPosting(jobID);
		
		return Response.ok().entity(jp).build();
	}
}
