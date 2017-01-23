
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.*;
import org.quartz.impl.*;

@Path("server")
public class BFSN {
	public static Map <String, User> Users = new HashMap<String, User>(); //Create new map from String (phone number) to User object
	private static String telegramToken = "270376755:AAHZE4h34bHk0qE3NFL1A286lj8OKUZw65Q"; //Our Telegram Token
	private static String telegramBotName = "@robferg_api_bot";
    
	//Makes a call to the ClientServer to create new user instance, with error handling for Exceptions
	@POST
    @Path("/get/user/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user){
		try {
			ClientService.createUser(user, Users);
        	return Response.status(200).entity(user).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
    }

	//Makes a call to add a station to a pre-created users list of subscribed stations, with error handling for Exceptions or User not found
	@GET
    @Path("/get/user/{phone}/stations/add/{station}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStationSubscription(@PathParam("phone") String phone,@PathParam("station") String station){
		try {
			if(!Users.containsKey(phone)) {
				return Response.status(404).build();
			}
			User user = ClientService.addStationSubscription(phone,station,Users);
			return Response.status(200).entity(user).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
    }

	//Makes a call to retrieve a user's list of subscribed stations, with error handling for Exceptions or User not found
    @GET
    @Path("/get/user/{phone}/stations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserStations(@PathParam("phone") String phone){
    	try {
    		if(!Users.containsKey(phone)) {
				return Response.status(404).build();
			}
    		List<Integer> subscribedStations = ClientService.getUserStations(phone, Users);
    		return Response.ok(subscribedStations.toString()).build();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return Response.status(500).build();
    	}
    }
    
    //Makes a call to retrieve a user's Telegram ID Token, with error handling for Exceptions for User not found
    @GET
    @Path("/get/user/{phone}/telegram")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTelegramToken(@PathParam("phone") String phone){
    	try {
    		if(!Users.containsKey(phone)) {
				return Response.status(404).build();
			}
    		String temp = ClientService.getTelegramToken(phone, Users);
    		return Response.ok(temp).build();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return Response.status(500).build();
    	}
    }
    
    //Makes a call to get a list of ALL existing users and does some formatting on the returned JSON, with error handling for Exceptions
    @GET
    @Path("/get/user/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersSubscribedToService() {
    	try {
    		JSONObject users_out = ClientService.getUsersSubscribedToService(Users);
    		return Response.ok((users_out.toString().replace("\\", "")).replace("\"{", "{").replace("}\"","}")).build();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return Response.status(500).build();
    	}
    }
    
    //Makes a call to get the current Occupancy statistic for the stations, with error handling for Exceptions
    @GET
	@Path("/get/Occupancy")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetOccupancy() {
    	try {
    		String occupancyOutput = StationService.GetOccupancy();		
    		return Response.ok(occupancyOutput).build();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return Response.status(500).build();
    	}
	}
	
    //Gets a list of all of the Stations and publishes it to the API, with error handling for Exceptions
	@GET
	@Path("/get/Stations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetStations() {
		try {
			return Response.ok(StationService.GetStations().toString()).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	

	
}
