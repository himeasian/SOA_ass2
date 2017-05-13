package au.edu.unsw.soacourse.pollingservice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;	

@Path("/hello")
public class HelloWorld {

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        return input;
    }
    
    @GET
    @Path("/test")
    @Produces("text/plain")
    public String getDb() {
    	try {
    		Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			conn.createStatement().execute("create table if not exists Test ("
														+ "_pId integer primary key autoincrement,"
														+ "title text not null," 
														+ "description text not null,"
														+ "type text check(type = 'time' or type = 'string'),"
														+ "options text not null,"
														+ "comments text not null,"
														+ "final_choice text not null"
														+ ");");
			
			String sql = "insert into Test(title, description, type, options, comments, final_choice) values(?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "title");
			pstmt.setString(2, "description");
			pstmt.setString(3, "string");
			pstmt.setString(4, "options");
			pstmt.setString(5, "comments");
			pstmt.setString(6, "final_choice");
			pstmt.executeUpdate();
			
			sql = "select * from Test";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("id=" + rs.getString(1)
									+ " title=" + rs.getString(2)
									+ " description=" + rs.getString(3)
									+ " type=" + rs.getString(4)
									+ " options=" + rs.getString(5)
									+ " comments=" + rs.getString(6)
									+ " final_choice=" + rs.getString(7));
			}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "It could be working";
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

