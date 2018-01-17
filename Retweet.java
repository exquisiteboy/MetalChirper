package twitter_source;

import twitter4j.Twitter;
import twitter4j.TwitterException;
public class Retweet {
	
		public static Twitter TWITTER_INSTANCE;
		public static boolean IS_Exception;
		public static String Exception_MSG;
		public static int    ERROR_CODE = 0;
		public static String ReTweetInfo;

		public Retweet(Twitter twitter) {
		     Retweet.TWITTER_INSTANCE = twitter;	
		}

		public void do_Retweet(long tweetid) {
			try {
				
				String status = Retweet.TWITTER_INSTANCE.retweetStatus(tweetid).getText();
				Retweet.ReTweetInfo = String.format("retweet successful with text  '%s' for tweet id '%d'",status,tweetid);			
			
			}catch(TwitterException twex) {
				
				Retweet.IS_Exception = true;
				Retweet.Exception_MSG = String.format("Unable to retweet Status id  '%d'  because  '%s'",tweetid,twex.getMessage());
				Retweet.ERROR_CODE = twex.getErrorCode();
				
			}
		}
		public void reset() {
			
			Retweet.IS_Exception  =false;
			Retweet.Exception_MSG = "";
			Retweet.ReTweetInfo = "";
			Retweet.ERROR_CODE = 0;
		} // reset
	}


