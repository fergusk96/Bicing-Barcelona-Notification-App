

import org.json.JSONObject;

public class Message {
	private long chat_id; //Stores the id of the chat (which will be the user's Telegram ID)
	private String text; //Stores the text from the user's message
	
	//Constructor for the item, taking a long and string for the parameters
	public Message(long chat_id, String text) {
		this.chat_id = chat_id;
		this.text = text;
	}
	
	//Getter for the chat_id
	public long getChat_id() {
		return chat_id;
	}

	//Setter for the chat_id
	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}
	
	//Getter for the text
	public String getText() {
		return text;
	}
	
	//Setter for the text
	public void setText(String text) {
		this.text = text;
	}
	
	//Generates a JSON Object for any instance of a Message when called
    @Override
    public String toString() {
        JSONObject temp = new JSONObject(this);
        return temp.toString(); 
    }

}
