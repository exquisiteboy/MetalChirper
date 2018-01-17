package twitter_source;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
public class Follower_IDS {
	
    private Twitter TWITTER_INSTANCE ;
    public static boolean IS_Exception =false;
    public static String Exception_MSG ="";
    public static int Error_Code = -1;
    public static long[] followerids = {};
    public static long cursor = -1;
 
    
	public Follower_IDS(Twitter twitter) {
		 this.TWITTER_INSTANCE = twitter;
	}
	
	
	public void getFollowerIDs(String UserId,long cursor) {
		try {
			System.out.println("passed name "+UserId+" curor "+cursor);
			long id = Long.getLong(UserId);
			IDs ids = this.TWITTER_INSTANCE.getFollowersIDs(id,cursor);
			Follower_IDS.followerids = ids.getIDs();
			Follower_IDS.cursor = ids.getNextCursor();
		
		}catch(NullPointerException ex) {
			try {
				
			IDs ids	 = this.TWITTER_INSTANCE.getFollowersIDs(UserId, cursor);
				Follower_IDS.followerids =  ids.getIDs();
				Follower_IDS.cursor = ids.getNextCursor();
				//System.out.println("cursor"+Follower_IDS.cursor);
				
			}catch(TwitterException twex) {
				Follower_IDS.IS_Exception =true;
				Follower_IDS.Exception_MSG = String.format("unable to finds followerids of user '%s' because '%s'", UserId,twex.getMessage());
				Follower_IDS.Error_Code = twex.getErrorCode();
			}
		}catch(TwitterException twex) {
			Follower_IDS.IS_Exception =true;
			Follower_IDS.Exception_MSG = String.format("unable to finds followerids of user '%s' because '%s'", UserId,twex.getMessage());
			Follower_IDS.Error_Code = twex.getErrorCode();
		}
	}
	
	public void reset() {
		long [] a = {};
		Follower_IDS.followerids = a;
		Follower_IDS.Exception_MSG = "";
		Follower_IDS.IS_Exception = false;
		Follower_IDS.Error_Code = -1;
		Follower_IDS.cursor = -1;
	}

}
