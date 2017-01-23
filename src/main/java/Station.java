
import org.json.*;

public class Station {
		 private String id;
		 private String type;
		 private String latitude;
		 private String longitude;
		 private String streetName;
		 private String streetNumber;
		 private String altitude;
		 private String slots;
		 private String bikes;
		 private String nearbyStations;
		 private String status;
		 
		 
		 //Constructor for the class using a JSONObject to set the parameters
		 public Station(JSONObject obj) {
			 try {
			 this.id = obj.getString("id");
			 this.type = obj.getString("type");
			 this.latitude = obj.getString("latitude");
			 this.longitude = obj.getString("latitude");
			 this.streetName = obj.getString("streetName");
			 this.streetNumber = obj.getString("streetNumber");
			 this.altitude = obj.getString("altitude");
			 this.slots = obj.getString("slots");
			 this.bikes = obj.getString("bikes");
			 this.nearbyStations = obj.getString("nearbyStations");
			 this.status = obj.getString("status");	
			 }
			 catch(JSONException ex) {
				 System.out.println(ex);
			 }
		 }
		 
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the latitude
		 */
		public String getLatitude() {
			return latitude;
		}
		/**
		 * @param latitude the latitude to set
		 */
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		/**
		 * @return the longitude
		 */
		public String getLongitude() {
			return longitude;
		}
		/**
		 * @param longitude the longitude to set
		 */
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		/**
		 * @return the streetName
		 */
		public String getStreetName() {
			return streetName;
		}
		/**
		 * @param streetName the streetName to set
		 */
		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}
		/**
		 * @return the streetNumber
		 */
		public String getStreetNumber() {
			return streetNumber;
		}
		/**
		 * @param streetNumber the streetNumber to set
		 */
		public void setStreetNumber(String streetNumber) {
			this.streetNumber = streetNumber;
		}
		/**
		 * @return the altitude
		 */
		public String getAltitude() {
			return altitude;
		}
		/**
		 * @param altitude the altitude to set
		 */
		public void setAltitude(String altitude) {
			this.altitude = altitude;
		}
		/**
		 * @return the slots
		 */
		public String getSlots() {
			return slots;
		}
		/**
		 * @param slots the slots to set
		 */
		public void setSlots(String slots) {
			this.slots = slots;
		}
		/**
		 * @return the bikes
		 */
		public String getBikes() {
			return bikes;
		}
		/**
		 * @param bikes the bikes to set
		 */
		public void setBikes(String bikes) {
			this.bikes = bikes;
		}
		/**
		 * @return the nearbyStations
		 */
		public String getNearbyStations() {
			return nearbyStations;
		}
		/**
		 * @param nearbyStations the nearbyStations to set
		 */
		public void setNearbyStations(String nearbyStations) {
			this.nearbyStations = nearbyStations;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

}
