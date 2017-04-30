package au.edu.unsw.soacourse.pollingservice;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import au.edu.unsw.soacourse.database.DatabaseHandler;
import au.edu.unsw.soacourse.model.PollModel;

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
    public Response createPolls(PollModel input) throws URISyntaxException {
        /* TODO insert into database if doesn't exist CHECK THIS*/
		int r = new DatabaseHandler().createPoll(input);
		input.set_pId(r);
        return Response.created(new URI("/polls/" + r)).entity(input).build();
    }
	
	@GET
    @Path("/{input}")
    @Produces("application/json")
    public Response getPoll(@PathParam("input") int _pId) {
		/* TODO get specific entry from polls table */
		DatabaseHandler dbh = new DatabaseHandler();
		System.out.println("pid = " + _pId);
        return Response.ok().entity(dbh.getPoll(_pId)).build();
    }
	
	@GET
    @Path("/")
    @Produces("application/json")
    public Response getPolls() {
		/* TODO get entries from polls table */
		DatabaseHandler dbh = new DatabaseHandler();
        return Response.ok().entity(dbh.getPolls()).build();
    }
	
	@PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{_pId}")
    public Response updatePolls(PollModel input, @PathParam("_pId") String _pId) {
		/* TODO get from database entity _pId and update all entries*/
        return Response.ok().entity(input).build();
    }
	
	@GET
    @Path("/se")
	@Consumes("application/json")
    @Produces("application/json")
    public String searchPolls(@PathParam("input") String input) {
		/* TODO search for specific item */
        return input;
    }
	
	@DELETE
    @Path("/{input}")
	@Produces("")
    public Response deletePolls(JsonBean input) {
        /* TODO find specified item and delete */
        return Response.ok().build();
    }
}
