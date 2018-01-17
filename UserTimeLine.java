package twitter_source;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
public class UserTimeLine {
	
	public static Twitter TWITTER_INSTANCE;
	public static boolean IS_Exception;
	public static String Exception_MSG;
	public static int    ERROR_CODE = 0;
    public static ResponseList<Status> userTimeLine = null;
    public static boolean IS_Resonse_OK = false;
	
    public UserTimeLine(Twitter twitter) {
		UserTimeLine.TWITTER_INSTANCE = twitter;
	}
	
	public void getUserTimeLine(String UserName,Paging page) {
		
	            try {
	            ResponseList<Status> timeline = UserTimeLine.TWITTER_INSTANCE.getUserTimeline(UserName, page);
	            	if(timeline.isEmpty()) {
	            		UserTimeLine.IS_Exception = true;
	            		UserTimeLine.Exception_MSG = "no more tweets found for page"+page.getPage();
	            		UserTimeLine.IS_Resonse_OK = false;
	            		return;
	            	}
	            	UserTimeLine.userTimeLine = timeline;
	            	IS_Resonse_OK = true;
	            	
	            }catch(TwitterException twex) {
	            	UserTimeLine.IS_Exception = true;
	            	UserTimeLine.Exception_MSG = String.format("Unable to fetch UserTimline of user '%s' because '%s'", UserName,twex.getMessage());
	            	UserTimeLine.ERROR_CODE = twex.getErrorCode();
	            }
	            
		
	} // end getUserTimeLine
	
	public void getUserHomeTimeLine() {
		
        try {
        ResponseList<Status> timeline = UserTimeLine.TWITTER_INSTANCE.getHomeTimeline();
        	if(timeline.isEmpty()) {
        		UserTimeLine.IS_Exception = true;
        		UserTimeLine.Exception_MSG = "no status found of "+this.TWITTER_INSTANCE.getScreenName();
        		UserTimeLine.IS_Resonse_OK = false;
        		return;
        	}
        	UserTimeLine.userTimeLine = timeline;
        	IS_Resonse_OK = true;
        	
        }catch(TwitterException twex) {
        	UserTimeLine.IS_Exception = true;
        	UserTimeLine.Exception_MSG = String.format("Unable to fetch UserTimline of  because '%s'",twex.getMessage());
        	UserTimeLine.ERROR_CODE = twex.getErrorCode();
        }
        

} // end getUserTimeLine
	
	public void reset() {
		UserTimeLine.IS_Exception = false;
		UserTimeLine.Exception_MSG = "";
		UserTimeLine.userTimeLine = null;
		UserTimeLine.ERROR_CODE = 0;
		UserTimeLine.IS_Resonse_OK = false;
	}

}
