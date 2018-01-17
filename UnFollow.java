package twitter_source;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class UnFollow {
	   public static boolean IS_Exception =false;
	   public static boolean IS_Unfollowed =false;
	   public static String Exception_MSG = "";
	   public static int twitter_excp_code = -1 ;
	   private static User response;
	   private static String Unfollow_Response = "";
	 
	   private Twitter TWITTER_INSTANCE;

		
		public UnFollow(Twitter twitter) {
		   this.TWITTER_INSTANCE = twitter;	
			
		}
		
		public void Unfollow_User(String UserID) {
			    try {
			    	long userid = Long.getLong(UserID);
			    	UnFollow.response =this.TWITTER_INSTANCE.destroyFriendship(userid);
			    	UnFollow.Unfollow_Response =  String.format("followed User '%s'",UnFollow.response.getName());
			    	UnFollow.IS_Unfollowed =true;
			    }catch(TwitterException twterex) {
			    	UnFollow.Exception_MSG = "unable to Unfollow user "+UserID +" because "+twterex.getMessage();
			    	 UnFollow.IS_Exception = true;
			    	 UnFollow.twitter_excp_code = twterex.getErrorCode();

			    }catch(Exception ex) {
			    	try {
			    		UnFollow.response = this.TWITTER_INSTANCE.destroyFriendship(UserID);
			    		UnFollow.Unfollow_Response =  String.format("followed User '%s'",UnFollow.response.getName());
			    		UnFollow.IS_Unfollowed =true;
			    	} catch (TwitterException e) {
			    		UnFollow.IS_Exception = true;
			    		UnFollow.Exception_MSG = "unable to Unfollow user "+UserID +" because "+e.getMessage();
			    		UnFollow.twitter_excp_code = e.getErrorCode();

			    	}
			    
			   }
			
		}
		public void reset() {
			UnFollow.IS_Exception = false;
			UnFollow.IS_Unfollowed = false;
			UnFollow.Exception_MSG = "";
			UnFollow.twitter_excp_code = -1;
			UnFollow.response = null;
			UnFollow.Unfollow_Response = "";
		}

	}