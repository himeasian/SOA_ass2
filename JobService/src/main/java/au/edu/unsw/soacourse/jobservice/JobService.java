package au.edu.unsw.soacourse.jobservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import au.edu.unsw.soacourse.model.*;
import au.edu.unsw.soacourse.database.DatabaseHandler;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	
	
	
	// Gets a specific review from a specific application for all jobs
	@GET
	@Path("/application/{appID}/review/{reviewID}")
	@Produces("application/json")
	public Response getReview(@PathParam("appID") int appID, @PathParam("reviewID") int reviewID){
		DatabaseHandler db = new DatabaseHandler();
		Review r = db.getReview(appID, reviewID);
		return Response.ok().entity(r).build();
		
	}
	
	@GET
	@Path("/application/{appID}/review")
	@Produces("application/json")
	public Response getReviewsForApplication(@PathParam("appID") int appID){
		DatabaseHandler db = new DatabaseHandler();
		List<Review> r = db.getReviewsForApplication(appID);
		return Response.ok().entity(r).build();
		
	}
	
	@GET
	@Path("/application/review/{reviewer}")
	@Produces("application/json")
	public Response getReviewsForReviewer(@PathParam("reviewer") String reviewer){
		DatabaseHandler db = new DatabaseHandler();
		List<Review> r = db.getReviewsForReviewer(reviewer);
		return Response.ok().entity(r).build();
		
	}
	
	// Gets all reviews in the table
	@GET
	@Path("/application/review")
	@Produces("application/json")
	public Response getAllReviews(){
		DatabaseHandler db = new DatabaseHandler();
		List<Review> lr = db.getReviews();
		return Response.ok().entity(lr).build();
	}
	@GET
	@Path("/{jobID}/application/{appID}")
	@Produces("application/json")
	public Response getApplication(@PathParam("jobID") int jobID, @PathParam("appID") int appID){
		DatabaseHandler db = new DatabaseHandler();
		Application app = db.getApplication(jobID, appID);
		return Response.ok().entity(app).build();
	}
	
	@GET
	@Path("/application")
	@Produces("application/json")
	public Response getAllApplications(){
		DatabaseHandler db = new DatabaseHandler();
		List<Application> app = db.getAllApplications();
		return Response.ok().entity(app).build();
	}
	
	@GET
	@Path("/{jobID}/application")
	@Produces("application/json")
	public Response getApplicationForJobPosting(@PathParam("jobID") int jobID){
		DatabaseHandler db = new DatabaseHandler();
		List<Application> app = db.getApplicationForJobPosting(jobID);
		return Response.ok().entity(app).build();
	}
	
	@GET
	@Path("/search")
	@Produces("application/json")
	public Response searchJobPostings(@QueryParam("companyName") String companyName, 
			@QueryParam("salaryRate") float salaryRate,
			@QueryParam("positionType") String positionType,
			@QueryParam("location") String location,
			@QueryParam("jobDescription") String jobDescription, 
			@QueryParam("status") String status, 
			@QueryParam("classification") String classification){
		DatabaseHandler db = new DatabaseHandler();
		List<JobPosting> ljp = db.searchJobPostings(companyName, salaryRate, positionType, location, jobDescription, status, classification);
		
		return Response.ok().entity(ljp).build();
	}
	
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createJobPosting(JobPosting source) throws URISyntaxException{
		DatabaseHandler db = new DatabaseHandler();
		int newid = db.createJobPosting(source);
		source.set_jobID(newid);
		if(newid!=-1){
			return Response.created(new URI("/jobs/" + newid)).entity(source).build();
		}
		else{
			return Response.ok(newid).build();
		}
	}
	
	@POST
	@Path("/application")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createApplication(Application source) throws URISyntaxException{
		DatabaseHandler db = new DatabaseHandler();
		int newid = db.createApplication(source);
		source.set_appID(newid);
		return Response.created(new URI("/jobs/" + newid)).entity(source).build();
	}
	
	@POST
	@Path("/application/review")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createReview(Review source) throws URISyntaxException{
		DatabaseHandler db = new DatabaseHandler();
		int newid = db.createReview(source);
		source.set_reviewID(newid);
		return Response.created(new URI("/jobs/" + newid)).entity(source).build();
	}
	
	@DELETE
	@Path("/{jobID}")
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
	
	@PUT
	@Path("/application")
	@Consumes("application/json")
	public Response updateApplication(Application uapp){
		DatabaseHandler db = new DatabaseHandler();
		boolean answer = db.updateApplication(uapp);
		
		return Response.ok(answer).build();
	}
	
	@PUT
	@Path("/application/review")
	@Consumes("application/json")
	public Response updateReview(Review urev){
		DatabaseHandler db = new DatabaseHandler();
		boolean answer = db.updateReview(urev);
		
		return Response.ok(answer).build();
	}
}
