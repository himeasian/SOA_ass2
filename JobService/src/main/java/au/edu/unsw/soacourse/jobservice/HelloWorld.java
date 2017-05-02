package au.edu.unsw.soacourse.jobservice;
import javax.ws.rs.Consumes;
import au.edu.unsw.soacourse.model.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/jobpostings")
public class HelloWorld {




	@GET
	@Produces("json/application")
	public String getAllJobPostings(){
		String x = "";
		return x;
	}
	
    @GET
    @Path("/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("jobID") String input) {
        JobPosting jp = new JobPosting();
    	
    	return input;
    }
    
    

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/jsonBean")
    public Response modifyJson(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
}

