package twitter_source;

import java.util.HashMap;
import java.util.Map;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class UserInfo {
		public static Twitter TWITTER_INSTANCE ;
		public static boolean IS_Exception =false;
		public static String Exception_MSG ="";
		public static int    ERROR_CODE = 0;
		public static User userInfo;
		
		public UserInfo(Twitter twitter) {
			UserInfo.TWITTER_INSTANCE = twitter;
		}
		
		public void getUserInfo(String screenName) {
			
			 try {          
				     
			      User thisUser = UserInfo.TWITTER_INSTANCE.showUser(screenName);
			      UserInfo.userInfo = thisUser;
		      
			 }catch(TwitterException twex) {
		    	  UserInfo.IS_Exception = true;
		    	  UserInfo.Exception_MSG = String.format("unable to get UserInfo about user  '%s'  because ", screenName,twex.getMessage());
		    	  UserInfo.ERROR_CODE = twex.getErrorCode();
		}

	 }// end get stiky info 
		
		public void reset() {
			UserInfo.IS_Exception =false;
			UserInfo.Exception_MSG = "";
			UserInfo.ERROR_CODE = 0;
			User user = null;
			UserInfo.userInfo = user;
		}
	}


