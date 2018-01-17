package twitter_source;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
public class MuteUser {
    public static Twitter TWITTER_INSTANCE;
    public static  boolean IS_Exception = false;
    public static  String Exception_MSG = "";
    public static int ERROR_CODE = 0;
    public static String response;
	public MuteUser(Twitter twitter) {
		MuteUser.TWITTER_INSTANCE = twitter;
	}
	
	public void muteUser(String UserId) {
		try {
		long id = Long.getLong(UserId);
		User user = MuteUser.TWITTER_INSTANCE.createMute(id);
		     
		     MuteUser.response = String.format("Create mute sucessfull for user '%s'" ,user.getName());
		     
		}catch(NumberFormatException nex) {
			try {
				User user = MuteUser.TWITTER_INSTANCE.createMute(UserId);
				MuteUser.response = String.format("Create mute sucessfull for user '%s'" ,user.getName());
			}catch(TwitterException twex) {
				MuteUser.IS_Exception = true;
				MuteUser.Exception_MSG = String.format("Unable to create mute user '%s' because '%s'", UserId,twex.getMessage());
				MuteUser.ERROR_CODE = twex.getErrorCode();
			}
		}catch(TwitterException twex) {
			MuteUser.IS_Exception = true;
			MuteUser.Exception_MSG = String.format("Unable to create mute user '%s' because '%s'", UserId,twex.getMessage());
			MuteUser.ERROR_CODE = twex.getErrorCode();
		}
		
	}
	public void reset() {
		MuteUser.IS_Exception =false;
		MuteUser.Exception_MSG = "";
		MuteUser.ERROR_CODE = 0;
		MuteUser.response = "";
	}

}
