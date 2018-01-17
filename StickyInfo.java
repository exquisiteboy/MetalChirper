package twitter_source;

import java.util.HashMap;
import java.util.Map;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class StickyInfo {
	public static Twitter TWITTER_INSTANCE ;
	public static boolean IS_Exception =false;
	public static String Exception_MSG ="";
	public static int    ERROR_CODE = 0;
	public static Map<String, Object> userInfo;
	
	public StickyInfo(Twitter twitter) {
		StickyInfo.TWITTER_INSTANCE = twitter;
	}
	
	public void getStickyInfo(String screenName) {
		
		 try {          
			 
		      Map<String, Object> user = null;      
		      User thisUser = StickyInfo.TWITTER_INSTANCE.showUser(screenName);
		      user = new HashMap<String, Object>();
		      user.put("screenName", thisUser.getScreenName());
		      user.put("friendsCount",thisUser.getFriendsCount());
		      user.put("followersCount",thisUser.getFollowersCount());
		      double friendsCount = thisUser.getFriendsCount();
		      double followersCounts = thisUser.getFollowersCount();
		      double ratio = 0;
				if (friendsCount!=0) {
					 ratio = (followersCounts/friendsCount);
				}
				
			  user.put("ratio", ratio);
		      user.put("id", thisUser.getId());
		      user.put("user_image", thisUser.getProfileImageURL());
		      user.put("description", thisUser.getDescription());
		      user.put("tweetsCount", thisUser.getStatusesCount());
		      user.put("user_location", thisUser.getLocation());
		      user.put("timeZone",thisUser.getUtcOffset());
		      user.put("time",thisUser.getCreatedAt().getTime());
		      StickyInfo.userInfo = user;
	      }catch(TwitterException twex) {
	    	  StickyInfo.IS_Exception = true;
	    	  StickyInfo.Exception_MSG = String.format("unable to get stickyinfo about user '%s' because ", screenName,twex.getMessage());
	    	  StickyInfo.ERROR_CODE = twex.getErrorCode();
	}

 }// end get stiky info 
	
	public void reset() {
		StickyInfo.IS_Exception =false;
		StickyInfo.Exception_MSG = "";
		StickyInfo.ERROR_CODE = 0;
		Map<String,Object>map = null;
		StickyInfo.userInfo = map;
	}
}
