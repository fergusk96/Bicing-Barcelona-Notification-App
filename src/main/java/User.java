

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class User {
    private String phoneNumber;
    private String telegramToken;
    private List<Integer> inputStations;
    
    //Getter for the inputStations list
	public List<Integer> getInputStations() {
		return inputStations;
	}

	//Setter for the inputStations list
	public void setInputStations(List<Integer> inputStations) {
		this.inputStations = inputStations;
	}
    
	//Getter for the Phone Number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //Setter for the Phone Number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Getter for the Telegram Token
	public String getTelegramToken() {
        return telegramToken;
    }

	//Setter for the Telegram Token
    public void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }
    
    //Constructor for the User, taking the three arguments as input
    public User(String phoneNumber, String telegramToken, List<Integer> inputStations) {
    	this.phoneNumber = phoneNumber;
    	this.telegramToken = telegramToken;
    	this.inputStations = inputStations;
    }
  
    //Method to add an additional station to the list of subscribed stations for the user.
	 public void addStation(int station){
        this.inputStations.add(station);
    }

	//Generate a JSON string representing the User object on which this method is called
    @Override
    public String toString() {
        JSONObject temp = new JSONObject(this);
        return temp.toString(); 
    }
    
    public User() {
    	super();
    }
}
