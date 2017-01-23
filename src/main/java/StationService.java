
import org.json.JSONArray;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by fergusk on 24/11/2016.
 */
public class StationService {
	
	//Gets the occupancy statistics in order to publish via the REST API
    public static String GetOccupancy() {
        String occupancyOutput = StatisticsUtils.getStatistics();
        return occupancyOutput;
    }

    //Gets the JSONArray of all stations to publish via the REST API
    public static JSONArray GetStations() {
        JSONArray output = ScheduledTasks.output;
        return output;
    }


}
