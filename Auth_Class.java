/**
 * 
 */
package twitter_source;

import java.util.Map;

import database.DBClass;
import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * @author orbit
 *
 */
public class Auth_Class {

	/**
	 * 
	 */
	public static int REMAINING_CALLS ;
	//private String AUTH_ID;
	public static Twitter TWITTER_INSTANCE;
	private Map<String,RateLimitStatus> REMNIG_RATE_LIMT;
	public static boolean IS_Exception = false;
	public static String Exception_MSG = "";
	public static boolean IS_OK =false;
	
	// constructor for setting db and creating auth
	public Auth_Class(DBClass db,String Resource)  {
        //Twitter Conf. 
		//this.AUTH_ID = db_credentials.get("twitter_user_id").toString();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setJSONStoreEnabled(true);
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(database.DBClass.CONSUMER_KEY)
            .setOAuthConsumerSecret(database.DBClass.CONSUMER_SECRET)
            .setOAuthAccessToken(database.DBClass.ACCESS_KEY)
            .setOAuthAccessTokenSecret(database.DBClass.ACCESS_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        
        Auth_Class.TWITTER_INSTANCE = tf.getInstance();
        try {
        Map<String, RateLimitStatus> rateLimitStatusApp =  Auth_Class.TWITTER_INSTANCE.getRateLimitStatus();
        //System.out.println("LimitRate" + rateLimitStatusApp.size());
        REMAINING_CALLS = rateLimitStatusApp.get(Resource).getRemaining();
        this.REMNIG_RATE_LIMT = rateLimitStatusApp;
        IS_OK =true;
        }catch(TwitterException twEX) {
        	IS_Exception = true;
        	Exception_MSG ="Error in getting Rate Limits Status beacuse "+twEX.getErrorMessage();
        }
	
	}
	// getting the currently auth instance of twitter 
	public Twitter get_twitter_instance() {
		return Auth_Class.TWITTER_INSTANCE;
	}
	
	// get remaining call for specific resource ex:follower_calls,friends_call
	public int get_RemainigCalls(String resourceType) {
		//System.out.println("resourceType"+resourceType);
		int  remApiLimits = 0;
		
		RateLimitStatus	AppiRateLimit = this.REMNIG_RATE_LIMT.get(resourceType);
		remApiLimits = AppiRateLimit.getRemaining();
		//System.out.println("For followerids"+remApiLimits);
		/*this.REMNIG_RATE_LIMT.entrySet().forEach((e)->{
			System.out.println(e.getKey()+"\n"+e.getValue());
		});*/
		//remApiLimits = AppiRateLimit.getRemaining();
		return remApiLimits;
	}
	

}
