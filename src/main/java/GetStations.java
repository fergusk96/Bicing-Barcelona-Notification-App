

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetStations {
	//Gets the data from JSONParse as a JSONArray and converts it to a MAP object mapping station IDs to Station objects
	public static Map<Integer,Station> GetStations() {
		JSONArray test = JSONParse("http://wservice.viabicing.cat/v2/stations", "stations");
		Map<Integer,Station> Stations = new HashMap<Integer,Station>();
		for(int i = 0; i < test.length(); i++) Stations.put(Integer.parseInt(test.getJSONObject(i).getString("id")),new Station(test.getJSONObject(i)));
		return Stations;			
	}
	
	//Pulls the HTML from the API given as a URL String, with added error handling for exceptions
	public static String HTMLPull(String url) {
		if(url == null) new IllegalArgumentException("URL is null");
	    StringBuilder sb = new StringBuilder();
	    InputStream inStream = null;
	    try {
	        inStream = (new URL(url)).openStream();
	        int i;
	        byte[] buffer = new byte[8 * 1024];
	        while((i=inStream.read(buffer)) != -1) sb.append(new String(buffer,0,i));
	        }
	    catch(Exception e ) {
	        e.printStackTrace();
	        return null;
	    }
	    return sb.toString();
	}
	
	//Converts the data from HTML pull to a JSONArray, given a 'param' to pull the array from a JSON Object, with added error handling
	public static JSONArray JSONParse(String url, String param) {
		String data = HTMLPull(url);
		try {
			JSONArray dataJSON = (new JSONObject(data)).getJSONArray(param);
			return dataJSON;
		}
		catch(JSONException e) {
			e.printStackTrace();
			return null;
		}		
	}
}