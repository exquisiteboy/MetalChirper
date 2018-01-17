package twitter_source;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchTwitter {
	public static Twitter TWITTER_INSTANCE ;
	public static boolean IS_Exception = false;
	public static String Exception_MSG = "";
	public static int    ERROR_CODE = 0;
	public static ArrayList<Status> tweetInfo = new ArrayList<>();
	public static long MAX_ID = 0;
	public static boolean hasNextPage = false;
	
	public SearchTwitter(Twitter twitter) {
		SearchTwitter.TWITTER_INSTANCE = twitter;
	}
	
	public void searchTweets(Query query) {
		
		ArrayList<Status> tweets = new ArrayList<Status>();
		
		QueryResult result =null;
		long lastID = Long.MAX_VALUE;
		try {
            result = SearchTwitter.TWITTER_INSTANCE.search(query);
            tweets.addAll(result.getTweets());
            SearchTwitter.tweetInfo = tweets;
            System.out.println("Gathered " + tweets.size() + " tweets"+"\n");
            if(tweets.size()==0) {
            	SearchTwitter.IS_Exception = true;
            	SearchTwitter.Exception_MSG = "Query return 0 result for query "+query.getQuery();
            	SearchTwitter.hasNextPage =false;
            	
            }
           }catch (TwitterException twex) {
        	   SearchTwitter.IS_Exception = true;
               SearchTwitter.Exception_MSG = "unable to search tweets because " + twex.getMessage();
               SearchTwitter.ERROR_CODE = twex.getErrorCode();
             };
             
           for (Status t: tweets) 
             if(t.getId() < lastID) 
                 lastID = t.getId();

           if(result.hasNext()) {
        	   SearchTwitter.MAX_ID = lastID;
        	   SearchTwitter.hasNextPage = true;
        	   System.out.println(result.hasNext());
               System.out.println(result.nextQuery());
           }
           

	}
	public void reset() {
		SearchTwitter.IS_Exception = false;
		SearchTwitter.Exception_MSG = "";
		ArrayList<Status> arr = new ArrayList<>();
		SearchTwitter.tweetInfo =  arr;
		SearchTwitter.MAX_ID = 0;
		SearchTwitter.hasNextPage = false;
	}

}
