package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String follow(@FormParam("myname") int UserID1,
			@FormParam("follow") int UserID2) {
		
		boolean user =  UserModel.Follow(UserID1 , UserID2);
		JSONObject json = new JSONObject();
		json.put("done", user);
		return json.toJSONString();
	}
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("myname") int UserID1,
			@FormParam("unfollow") int UserID2) {
		boolean user =  UserModel.unfollow(UserID1 , UserID2);
		JSONObject json = new JSONObject();
		json.put("done", user);
		return json.toJSONString();
	}
		@POST
		@Path("/GetFollowers")
		@Produces(MediaType.TEXT_PLAIN)
		public String GetFollowers(@FormParam("UserID2")  int UserID2 ){
			
			ArrayList<String> Following=new ArrayList<String>();
			JSONObject json = new JSONObject();
	      Following=UserModel.ListAllFollowers(UserID2);
	      for(int i=0;i<Following.size();i++)
	      {
	    	  json.put(i, Following.get(i));
	      }
			return json.toJSONString();
		}

	
		@POST
		@Path("/updatePosition")
		@Produces(MediaType.TEXT_PLAIN)
		public String updatePosition(@FormParam("id") String id,
				@FormParam("lat") String lat, @FormParam("long") String lon) {
			Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
			JSONObject json = new JSONObject();
			json.put("status", status ? 1 : 0);
			return json.toJSONString();
		}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}
