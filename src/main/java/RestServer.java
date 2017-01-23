
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sun.net.httpserver.HttpServer;

public class RestServer {
	
	//The main method of the project, this starts executing all of the code
	public static void main(String[] args) throws IOException {
		StartSchedule(); //Calls the method which sets up and runs the Quartz Scheduled Task (ie. downloading stations and messaging users)
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(1500).build(); 
		ResourceConfig config = new ResourceConfig(BFSN.class);
		HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config); //Sets up a REST API on port 1500 using the BFSN class
		System.out.println("Server started..."); //For logging purposes
	}
	
	public static void StartSchedule() {
		JobDetail cache_stations = JobBuilder.newJob(ScheduledTasks.class).withIdentity("pullData").build(); //Creates a new Job using the execute method in ScheduledTasks class
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever()).build(); //Sets the trigger to run every 60 seconds
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			Scheduler sched = sf.getScheduler();
			sched.start();
			sched.scheduleJob(cache_stations, trigger); //Schedules the Job cache_stations to run every time the Trigger of 60 seconds elapses
		} catch (SchedulerException e) {
			e.printStackTrace(); //Error handling
		}
	}
}
