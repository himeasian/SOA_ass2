package au.edu.unsw.soacourse.pollingservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        /* TODO Modify to add HATEOAS additional links */
		int r = new DatabaseHandler().createPoll(input);
		input.set_pId(r);
        return Response.created(new URI("/polls/" + r)).entity(input).build();
    }
	
	@GET
    @Path("/{pid}")
    @Produces("application/json")
    public Response getPoll(@PathParam("pid") int pid) {
		/* TODO Modify to add HATEOAS additional links i.e. related votes*/
        return Response.ok().entity(new DatabaseHandler().getPoll(pid)).build();
    }
	
	@GET
    @Path("/")
    @Produces("application/json")
    public Response searchPolls(@QueryParam("pid") String pid,
    						@QueryParam("title") String title,
    						@QueryParam("description") String description,
							@QueryParam("type") String type,
							@QueryParam("options") String options,
							@QueryParam("comments") String comments,
							@QueryParam("finalChoice") String finalChoice) {
		/* TODO Modify to add HATEOAS additional links i.e. related votes*/
		List<String> str = new ArrayList<String>();
		
		if (pid != null)
			str.add("_pId = " + pid);
		if (title != null)
			str.add("title = '" + title + "'");
		if (description != null)
			str.add("description = '" + description + "'");
		if (type != null)
			str.add("type = '" + type + "'");
		if (options != null)
			str.add("options = '" + options + "'");
		if (comments != null)
			str.add("comments = '" + comments + "'");
		if (finalChoice != null)
			str.add("final_choice = '" + finalChoice + "'");
		
		String query = "";
		for (int i = 0; i < str.size(); i++) {
			query += (i == 0) ? str.get(i) : " AND " + str.get(i);
		}
        return Response.ok().entity(new DatabaseHandler().getPolls(query)).build();
    }
	
	@PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/{pid}")
    public Response updatePolls(PollModel input, @PathParam("pid") String pid) {
		/* TODO get from database entity _pId and update all entries*/
		// if votes for poll == 0, allow update		
        return Response.ok().entity(input).build();
    }
	
	
	@DELETE
    @Path("/{pid}")
	@Produces("")
    public Response deletePolls(@PathParam("pid") int pid) {
		new DatabaseHandler().deletePoll(pid);
        return Response.noContent().build();
    }
}
