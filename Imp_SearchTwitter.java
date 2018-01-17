package implementations;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import database.DBClass;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterObjectFactory;
import twitter_source.Auth_Class;
import twitter_source.SearchTwitter;
import util.EnviornmentVariables;
import util.FormatQuery;

public class Imp_SearchTwitter {
	private static final DBClass DBClass = null;
	private static int remainig_calls = 0;
	public static boolean IS_Exception;
	public static boolean DONE= false;
	private static DBClass dbobj;
	private static FormatQuery frmq;
	 public  static String UserID ="";
	private static Auth_Class authobj;

    public  static SearchTwitter srchtwter;
	public Imp_SearchTwitter(Twitter twitter) {
	}
	public  static void search(Query query,String FileOutput) {
		Path path = FileSystems.getDefault().getPath(FileOutput);
		if(Files.isWritable(path)) {
		long MaxID = -1;
		while(Imp_SearchTwitter.remainig_calls>2) {
		srchtwter.searchTweets(query);
		if (!SearchTwitter.IS_Exception) {
				ArrayList<Status> tweetInfo = SearchTwitter.tweetInfo;
				for (Status tweet : tweetInfo) {
					TwitterObjectFactory.getRawJSON(tweet);
					System.out.println("user" + tweet.getUser().getName());
					System.out.println("tweet text" + tweet.getText());
				}
				// System.out.println("tweet Size Returned"+tweetInfo.size());
				if (SearchTwitter.hasNextPage) {
					MaxID = SearchTwitter.MAX_ID;
					query.setMaxId(MaxID - 1);
					query.setLang("en");
					
					//srchtwter.reset();
				} else if (!(SearchTwitter.hasNextPage)) {
					System.out.println("Done Searching Tweets");
					srchtwter.reset();
					Imp_SearchTwitter.DONE =true;
					
				}

			} else if (SearchTwitter.IS_Exception) {
				System.err.println(SearchTwitter.Exception_MSG);
			}
		} 
		}else {
			 System.out.println("Can write to the specified path "+FileOutput);
		 }
		
		} // looping for remainig calls

	
	public static void Main(String[]args) {
		Twitter twitter;
		int remlimits = -1;
		String FileOutput="/home/orbit/tweets";

		String[] keywords = { "Terms You Need to Know in order to talk",
				"We are super excited to announce our first winner",
				"We are thinking of putting together the first leg of our research paper" };
		frmq = new FormatQuery(keywords);

		if (!FormatQuery.IS_Exception)
			frmq.makeQuery();
		Query query = new Query(FormatQuery.finalQuery);
		query.setCount(100);
		query.setLang("en");
		while(true) {
		dbobj = new DBClass();
		dbobj.getAuthKeys();

		if (database.DBClass.AUTH_OK) {
			UserID = database.DBClass.twitter_user_id;
			dbobj.updateTimeStamp(UserID);
			authobj = new Auth_Class(DBClass,EnviornmentVariables.TWEET_Lookup_LIMIT);
			if (twitter_source.Auth_Class.IS_OK) {
				twitter = Auth_Class.TWITTER_INSTANCE;
				System.out.println("remaning Search calls " + Auth_Class.REMAINING_CALLS);
				if (remlimits <= 2) {
					System.out.println("going to sleep");
					try {
						Thread.sleep(900000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				srchtwter = new SearchTwitter(twitter);
				
					Imp_SearchTwitter.search(query,FileOutput);
					Imp_SearchTwitter.remainig_calls = Auth_Class.REMAINING_CALLS;
	
			}
			}
		}//endwhile
	}
}// end of class


