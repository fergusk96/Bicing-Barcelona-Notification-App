
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fergusk on 24/11/2016.
 */
public class ClientService {

	//Creates a user by taking the phone number and adding the User object to Map object linking the phone number to the User
    public static void createUser(User user, Map<String,User> Users){
        Users.put(user.getPhoneNumber(), user);
    }

    //Adds an additional station to the list of stations which the user has subscribed to
    public static User addStationSubscription(String userPhoneNumber,String station, Map<String,User> Users ){
        int stationNumber = Integer.parseInt(station);
        User user = Users.get(userPhoneNumber);
        user.addStation(stationNumber);
        Users.replace(userPhoneNumber, user);
        return user;
    }
    
    //Gets the List of a User's Subscribed Stations from the Map object by taking their phone number as input
    public static List<Integer> getUserStations(String phone, Map<String,User> Users){
        List<Integer> subscribedStations = Users.get(phone).getInputStations();
        return subscribedStations;
    }

    //Gets the User's Telegram ID Token from the Map Object by taking their phone number as input
    public static String getTelegramToken(String phone,Map<String,User> Users){
        return Users.get(phone).getTelegramToken();
    }

    //Gets a list of ALL USERS subscribed to the service by converting the User Map to a JSON Object and returning it
    public static JSONObject getUsersSubscribedToService(Map<String,User> Users) {
        JSONArray users = new JSONArray();
        for ( String key : Users.keySet() ) {
            User temp = Users.get(key);
            users.put(temp);
        }
        JSONObject users_out = new JSONObject();
        users_out.put("users", users);
        return users_out;

    }
    
    //This method takes the phone number of the User and messages them with their list of subscribed stations
    public static void NotifyUser(String phoneNumber) {
        try {
            User user = BFSN.Users.get(phoneNumber); //Pull the user object from the Users map
            String message = "";
            String userTelegramToken = user.getTelegramToken(); //Get the Telegram Token (chatID) for the user
            List<Integer> subscribedStations = user.getInputStations(); //Get the list of subscribed stations for the user
            System.out.println("Messaging user " + user.getPhoneNumber() + " with their subscriptions"); //Print to the terminal for status updates
            for (Integer stationId : subscribedStations) {
                Station station = ScheduledTasks.Stations.get(stationId); //Get the Station object from the Stations Map
                message = message + station.getId() + " (" + station.getStreetName() + ") \nFree Slots: " + station.getSlots() + " | Available Bikes: " + station.getBikes() + "\n\n";
            }
            MessagingAPI.sendMessageToUser(Long.parseLong(userTelegramToken), message); //Send the message to the user
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
