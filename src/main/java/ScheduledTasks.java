
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fergusk on 24/11/2016.
 */
public class ScheduledTasks implements Job {
	public static Map<Integer,Station> Stations = new HashMap<Integer,Station>(); //Contains a cached map of all stations, callable by their station ID
    public static JSONArray output = new JSONArray(); //Contains a cache of all stations in JSON Array format, so that we can publish them via the REST API


    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ": Pulling stations, getting messages & messaging users"); //For logging purposes
        Map<Integer,Station> new_pull = GetStations.GetStations(); //Runs the GetStations method, getting a fresh list of stations from the Bicing API
        Stations = new HashMap<Integer,Station>(new_pull); //Sets the downloaded Stations as the cached Map of Stations used across our application
        String occupancy = StatisticsUtils.getStatistics(); //Runs the getStatistic method, which retrieves the current occupancy percentage for the Bicing stationd
        System.out.println("-------NEW MESSAGES---------"); // For logging purposes
        
        try {
            MessagingAPI.PullMessages(); //Calls the MessagingAPI to pull new messages to our bot and to handle them (see class for more)
            TwitterUtils.updateTwitterStatus(occupancy); //Runs the TwitterUtils class to publish occupancy statistics
        } catch (Exception e) {
            e.printStackTrace(); //Error handling
        }
        
        output = new JSONArray(); //Clears the existing JSON Output array
        for(Integer key: Stations.keySet()) {
            output.put((new JSONObject(Stations.get(key)))); //Saves each of the freshly downloaded stations in the JSONArray cache of stations
        } 
        
        for(String phoneNumber: BFSN.Users.keySet()){
        	ClientService.NotifyUser(phoneNumber); //Runs through every User and sends a message with details of their subscribed stations (see class for more)
        }
        
        System.out.println("-------END OF CURRENT JOB EXECUTION---------\n"); //For logging purposes

    }


}
