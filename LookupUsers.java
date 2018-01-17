package twitter_source;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
public class LookupUsers {
	
	public static Twitter TWITTER_INSTANCE ;
	public static boolean IS_Exception =false;
	public static String Exception_MSG ="";
	public static int    ERROR_CODE = 0;
	public static ResponseList<User> UserInfo;
	
	// constructor 
	public LookupUsers(Twitter twitter) {
		LookupUsers.TWITTER_INSTANCE = twitter;
	}
	
	
	//method to lookup for users
	public void lookupUsers(long[] ids) {

		try {
			
			ResponseList<User> users = LookupUsers.TWITTER_INSTANCE.lookupUsers(ids);
			
		    LookupUsers.UserInfo = users;
		}catch(TwitterException twex) {
			LookupUsers.IS_Exception =true;
			LookupUsers.Exception_MSG = String.format("Unable to lookupuers because '%s' ", twex.getMessage());
			LookupUsers.ERROR_CODE = twex.getErrorCode();
			
		}
	}
	
	public void reset() {
		LookupUsers.IS_Exception = false;
		LookupUsers.Exception_MSG = "";
		LookupUsers.ERROR_CODE = 0;
		ResponseList<User> user = null;
		LookupUsers.UserInfo = user;
	}

}
