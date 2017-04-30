package au.edu.unsw.soacourse.pollingservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Service operations on polls
 * @author trungisme
 *
 */
@Path("/polls")
public class Polls {
	
	@POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response createPolls(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
	
	@GET
    @Path("/{input}")
    @Produces("application/json")
    public Response getPolls(@PathParam("input") String input) {
        return Response.ok().build();
    }
	
	@PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{input}")
    public Response updatePolls(JsonBean input, @PathParam("input") String poll) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
	
	@GET
    @Path("/")
	@Consumes("application/json")
    @Produces("application/json")
    public String searchPolls(@PathParam("input") String input) {
        return input;
    }
	
	@DELETE
    @Path("/{input}")
	@Produces("")
    public Response deletePolls(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().build();
    }
}
