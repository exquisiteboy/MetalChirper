package twitter_source;


import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
public class Friends_IDS {
		
	    private Twitter TWITTER_INSTANCE ;
	    public static boolean IS_Exception =false;
	    public static String Exception_MSG ="";
	    public static int Error_Code = -1;
	    public static long[] friendsids = {};
	    public static long cursor = -1;
	 
	    
		public Friends_IDS(Twitter twitter) {
			 this.TWITTER_INSTANCE = twitter;
		}
		
		
		public void getFriendsIDs(String UserId,long cursor) {
			try {
				
				long id = Long.getLong(UserId);
				IDs ids = this.TWITTER_INSTANCE.getFriendsIDs(id,cursor);
				Friends_IDS.friendsids = ids.getIDs();
				Friends_IDS.cursor = ids.getNextCursor();
			
			}catch(NullPointerException ex) {
				try {
					
				IDs ids	 = this.TWITTER_INSTANCE.getFriendsIDs(UserId, cursor);
					Friends_IDS.friendsids =  ids.getIDs();
					Friends_IDS.cursor = ids.getNextCursor();
					
				}catch(TwitterException twex) {
					Friends_IDS.IS_Exception =true;
					Friends_IDS.Exception_MSG = String.format("unable to finds friendsids of user '%s' because '%s'", UserId,twex.getMessage());
					Friends_IDS.Error_Code = twex.getErrorCode();
				}
			}catch(TwitterException twex) {
				Friends_IDS.IS_Exception =true;
				Friends_IDS.Exception_MSG = String.format("unable to finds friendsids of user '%s' because '%s'", UserId,twex.getMessage());
				Friends_IDS.Error_Code = twex.getErrorCode();
			}
		}
		
		public void reset() {
			long [] a = {};
			Friends_IDS.friendsids = a;
			Friends_IDS.Exception_MSG = "";
			Friends_IDS.IS_Exception = false;
			Friends_IDS.Error_Code = -1;
			Friends_IDS.cursor = -1;
		}

	}



