package twitter_source;

import twitter4j.Twitter;
import twitter4j.TwitterException;
public class Tweet {
	public static Twitter TWITTER_INSTANCE;
	public static boolean IS_Exception;
	public static String Exception_MSG;
	public static int    ERROR_CODE = 0;
	public static String TweetInfo;

	public Tweet(Twitter twitter) {
	     Tweet.TWITTER_INSTANCE = twitter;	
	}

	public void do_tweet(String Text) {
		try {
			
			String status = Tweet.TWITTER_INSTANCE.updateStatus(Text).getText();
			Tweet.TweetInfo =String.format("Tweet successful with text  '%s'", status);
		
		}catch(TwitterException twex) {
			Tweet.IS_Exception = true;
			Tweet.Exception_MSG = String.format("Unable to update Status '%s' because  '%s'",Text,twex.getMessage());
			Tweet.ERROR_CODE = twex.getErrorCode();
			
		}
	}
	public void reset() {
		
		Tweet.IS_Exception  =false;
		Tweet.Exception_MSG = "";
		Tweet.TweetInfo = "";
		Tweet.ERROR_CODE = 0;
	} // reset
}
