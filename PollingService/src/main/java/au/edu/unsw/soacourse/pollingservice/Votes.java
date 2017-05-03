package au.edu.unsw.soacourse.pollingservice;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import au.edu.unsw.soacourse.database.DatabaseHandler;
import au.edu.unsw.soacourse.model.VoteModel;

/**
 * Service operations on votes
 * @author trungisme
 *
 */
@Path("/vote")
public class Votes {
	
	@POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/")
    public Response createVote(VoteModel input) throws URISyntaxException {
        int r = new DatabaseHandler().createVote(input);
        input.set_voteId(r);
        return Response.created(new URI("/vote/" + r)).entity(input).build();
    }
	
	@GET
    @Path("/{voteId}")
    @Produces("application/json")
    public Response getVote(@PathParam("voteId") int voteId) {
        return Response.ok().entity(new DatabaseHandler().getVote(voteId)).build();
    }
	
	@PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{voteId}")
    public Response updateVote(VoteModel vm, @PathParam("voteId") int voteId) throws URISyntaxException {
        int r = new DatabaseHandler().updateVote(vm, voteId);
        if (r == 0) {
        	vm.set_voteId(voteId);
        	return Response.ok().entity(vm).build();
        }
        vm.set_voteId(r);
        return Response.created(new URI("/vote/" + r)).entity(vm).build();
    }
}
