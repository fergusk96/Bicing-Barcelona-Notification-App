
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.api.UsersResources;

public class MessagingAPI {
	private static String telegramToken = "270376755:AAHZE4h34bHk0qE3NFL1A286lj8OKUZw65Q"; //Telegram Token of our bot
	private static String telegramBotName = "@robferg_api_bot";
	private static long offset = 350528901; //The offset for the message updates, used to avoid downloading the same messages on every update
	private static Client client = ClientBuilder.newClient(); //Creates a client so we can post to the Telegram API
	
	//This method is used to interact with the Telegram API in order to send a message to a user given their ID
	public static void sendMessageToUser(Long user_id, String message) {
        Message messageTo = new Message(user_id, message); //Creates a new Message object
        try {
        	WebTarget targetSendMessage = client.target("https://api.telegram.org").path("/bot"+telegramToken+"/sendMessage"); //Sends the message to the Telegram API
        	String response = targetSendMessage.request().post(Entity.entity(messageTo.toString(), MediaType.APPLICATION_JSON_TYPE), String.class);
        } 
        catch(Exception e) {
        	e.printStackTrace();
        }
    }	
	
	//This method pulls the messages from the Telegram API and parses them into Message objects and then acts upon them according to their content. It also has sufficient error handling for incorrect strings etc
	// [IMPORTANT NOTE: THE METHOD BELOW IS ADDITIONAL FUNCTIONALITY WE ADDED AS A SURPLUS TO THE PROJECT REQUIREMENTS, ALLOWING USERS TO SIGN UP TO OUR APP USING TELEGRAM MESSAGES TO THE BOT]
	
	public static void PullMessages() {
		JSONArray in_messages = GetStations.JSONParse("https://api.telegram.org/bot" + telegramToken + "/getUpdates?offset=" + offset, "result");
		List<Message> messages_queue = new ArrayList<Message>();
	
		for(int i=0;i<in_messages.length();i++) {
			if(i == in_messages.length() - 1) {
				offset = in_messages.getJSONObject(i).getLong("update_id") + 1;
			}
			
			JSONObject temp = in_messages.getJSONObject(i).getJSONObject("message");
			long chat_id = temp.getJSONObject("chat").getLong("id");
			String text = temp.getString("text");
			messages_queue.add(new Message(chat_id,text));
		}
		
		for(Message message: messages_queue) {
			String text = message.getText();
			System.out.println(text);
			if(text.equals("/start")) {
				sendMessageToUser(message.getChat_id(),"Please use the following syntax to signup: 'SIGNUP your_phone_number [station_id_1, station_id_2, ...]'");
				
			}
			else if(text.contains("SIGNUP")) {
				String[] data = text.split(" ");
				
				if(!data[0].equals("SIGNUP")) {
					sendMessageToUser(message.getChat_id(), "Incorrect Syntax Error! Please use the following syntax to signup: 'SIGNUP your_phone_number [station_id_1, station_id_2, ...]'");	
				}
				
				try {
					List<Integer> stations = stringToIntArray(data[2]);
					User temp = new User(data[1],message.getChat_id() + "",stations);
					System.out.println(temp);
					sendMessageToUser(Long.parseLong(temp.getTelegramToken()), "You have successfully signed up! You have been subscribed to stations " + stations.toString());
					BFSN.Users.put(data[1], temp);
				}
				catch (Exception e) {
					e.printStackTrace();
					sendMessageToUser(message.getChat_id(), "There was a problem with your list of stations. Please use the following syntax to signup: 'SIGNUP your_phone_number [station_id_1, station_id_2, ...]'");
				}				
			}
			else if(text.contains("ADDSTATION")) {
				String[] data = text.split(" ");
				
				if(!data[0].equals("ADDSTATION")) {
					sendMessageToUser(message.getChat_id(),"Please use the syntax 'ADDSTATION your_phone_number station_to_add_id' to add a station");
				}
				
				try {
					int station = Integer.parseInt(data[2]);
					User temp = BFSN.Users.get(data[1]);
					temp.addStation(station);
					BFSN.Users.replace(data[1], temp);	
					sendMessageToUser(message.getChat_id(), "You have successfully added a station. Your list of subscribed stations is now " + temp.getInputStations().toString());
				}
				catch (Exception e) {
					e.printStackTrace();
					sendMessageToUser(message.getChat_id(),"Please use the syntax 'ADDSTATION your_phone_number station_to_add_id' to add a station");
				}
			}
			else {
				sendMessageToUser(message.getChat_id(), "Incorrect Syntax Error! Please use the following syntax to signup: 'SIGNUP your_phone_number [station_id_1, station_id_2, ...]'");	
			}
		}
	}
	
	//Converts a string to a list of integers (this is used in the additional functionality for parsing the message from the user)
	public static List<Integer> stringToIntArray(String str){
	    	return Arrays.stream(str.replaceAll("\\s", "").substring(1, str.length()-1).split(",")).map(String::trim).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
	}
}
