
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by fergusk on 19/11/2016.
 */
public class StatisticsUtils {
	
	//Generates the occupancy percentage from all current station data
    public static String getStatistics(){
    	Map<Integer,Station> Stations = ScheduledTasks.Stations; //Pulls a copy of the cached Map of Stations
        int total = 0;
        int occupied = 0;
        for(Integer key: Stations.keySet()) { // Runs through the Map of Stations, generating the occupied and slot totals
        	Station temp = Stations.get(key);
            occupied += Integer.parseInt(temp.getBikes());
            total += Integer.parseInt(temp.getBikes());
            total += Integer.parseInt(temp.getSlots());
        }

        double occupancy = ((double) occupied / (double) total) * 100; //Generate a percentage
        return "Occupancy Rate: " + occupancy + "%" + "\n" + "This refers to the number of slots taken by bicycles"; //Return a string for the Tweet


    }
}
