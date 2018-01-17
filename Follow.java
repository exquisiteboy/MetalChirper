package twitter_source;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class Follow {
   public static boolean IS_Exception =false;
   public static boolean IS_followed =false;
   public static String Exception_MSG = "";
   public static int twitter_excp_code = -1 ;
   private  User response;
   private static String follow_Response = "";
   private Twitter TWITTER_INSTANCE;

	
	public Follow(Twitter twitter) {
	   this.TWITTER_INSTANCE = twitter;	
		
	}
	
	public void follow_User(String UserID) {
		    try {
		    	long userid = Long.getLong(UserID);
		    	this.response =this.TWITTER_INSTANCE.createFriendship(userid);
		    	follow_Response = String.format("followed User '%s'",this.response.getName());
		    	//System.out.println("followed User"+this.response.getName());
		    	Follow.IS_followed =true;
		    }catch(TwitterException twterex) {
		    	 Follow.Exception_MSG = String.format("unable to follow user  '%s' because  '%s'",UserID ,twterex.getMessage());
		    	 Follow.IS_Exception = true;
		    	 Follow.twitter_excp_code = twterex.getErrorCode();

		    }catch(Exception ex) {
		    	try {
		    		this.response = this.TWITTER_INSTANCE.createFriendship(UserID);
		    		follow_Response = String.format("followed User '%s'",this.response.getName());
		    		Follow.IS_followed =true;
		    	} catch (TwitterException e) {
		    		Follow.IS_Exception = true;
		    		Follow.Exception_MSG = String.format("unable to follow user '%s' because  '%s'"+UserID ,e.getMessage());
		    		Follow.twitter_excp_code = e.getErrorCode();

		    	}
		    
		   }
		
	}
	public void reset() {
		Follow.IS_Exception = false;
		Follow.IS_followed = false;
		Follow.Exception_MSG = "";
		Follow.twitter_excp_code = -1;
		Follow.follow_Response = "";
	}

}
