
import java.text.SimpleDateFormat;
import java.util.Date;
import twitter4j.*;
import twitter4j.auth.AccessToken;


public class TwitterUtils {

	//Publishes a Tweet with occupancy statistics, code simply sets up the Twitter publisher and relevant tokens and publishes the message via the Twitter API
    public static void updateTwitterStatus(String twitterStatus) throws Exception {
        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer("Toti6ahBLRh3OokwKDS6B9iQU", "HjRQXPKkgdPrYcBteJtLcTAnP9BcOcTglMisRCxKWnyTZOrMBL");
        twitter.setOAuthAccessToken(new AccessToken("799774060179755008-XHp4wwrRENJ2X2d4Z4Zvk65cVmRym4V", "MvqH5MbDgImrUesGM8D6dxpblPZUJgOuMFWu9vzTunBCK"));
        Status status = twitter.updateStatus(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ": " + twitterStatus);
    }
}
