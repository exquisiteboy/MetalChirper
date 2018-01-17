/**
 * 
 */
package main;

import java.util.ArrayList;

import database.DBClass;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter_source.Auth_Class;
import twitter_source.Follow;
import twitter_source.SearchTwitter;
import twitter_source.UnFollow;
import util.EnviornmentVariables;
import util.FormatQuery;
/**
 * @author orbit
 *
 */
public class Main {

	private static final DBClass DBClass = null;
	/**
	 * 
	 */
	private static DBClass dbobj;
	private static FormatQuery frmq;
	private static Auth_Class authobj;
    public  static String UserID ="";
    public  static Follow followobj ;
    public  static UnFollow unfollowObj ;
    public  static SearchTwitter srchtwter;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Twitter twitter;
		int remlimits = -1;
		long MaxID = -1;

		String[] keywords = { "Terms You Need to Know in order to talk",
				"We are super excited to announce our first winner",
				"We are thinking of putting together the first leg of our research paper" };
		frmq = new FormatQuery(keywords);

		if (!FormatQuery.IS_Exception)
			frmq.makeQuery();
		Query query = new Query(FormatQuery.finalQuery);
		query.setCount(100);
		query.setLang("en");
		dbobj = new DBClass();
		dbobj.getAuthKeys();

		if (database.DBClass.AUTH_OK) {
			UserID = DBClass.twitter_user_id;
			dbobj.updateTimeStamp(UserID);
			authobj = new Auth_Class(DBClass,EnviornmentVariables.TWEET_Lookup_LIMIT);
			if (twitter_source.Auth_Class.IS_OK) {
				twitter = Auth_Class.TWITTER_INSTANCE;
				System.out.println("remaning Search calls " + Auth_Class.REMAINING_CALLS);
				if (remlimits <= 2) {
					System.out.println("going to sleep");
					Thread.sleep(900000);
				}
				srchtwter = new SearchTwitter(twitter);
				for (int lp = 0; lp < remlimits - 2; lp++) {
					srchtwter.searchTweets(query);
					if (!SearchTwitter.IS_Exception) {
						ArrayList<Status> tweetInfo = SearchTwitter.tweetInfo;
						for (Status tweet : tweetInfo) {
							System.out.println("user" + tweet.getUser().getName());
							System.out.println("tweet text" + tweet.getText());
						}
						// System.out.println("tweet Size Returned"+tweetInfo.size());
						if (SearchTwitter.hasNextPage) {
							MaxID = SearchTwitter.MAX_ID;
							query.setMaxId(MaxID - 1);
							query.setLang("en");
							srchtwter.reset();
						} else if (!(SearchTwitter.hasNextPage)) {
							System.out.println("Done Searching Tweets");
							srchtwter.reset();
							break;
						}

					} else if (SearchTwitter.IS_Exception) {
						System.err.println(SearchTwitter.Exception_MSG);
					}

				} // looping for remainig calls

			}

		}

		/*
		 * String []followerNames = {"CoreCoder","sidrasheikh_","armaninspace"};
		 * 
		 * HashMap<String,Object> authKeys = dbobj.getAuthKeys(); if(authKeys.isEmpty())
		 * { System.err.println("Unable to find any authkeys"); System.exit(1); } UserID
		 * = authKeys.get("twitter_user_id").toString(); dbobj.updateTimeStamp(UserID);
		 * authobj = new Auth_Class(authKeys); if (Auth_Class.IS_OK) { int limit =0; int
		 * arrsize = followerNames.length; Twitter twitter =
		 * authobj.get_twitter_instance(); Main.unfollowObj = new UnFollow(twitter); int
		 * remainig_calls =
		 * authobj.get_RemainigCalls(EnviornmentVariables.FOLLOWER_LIMIT.toString());
		 * for (int call=0;call<remainig_calls;call++) { limit++; if(limit >arrsize) {
		 * break; } String User = followerNames[call]; followobj.follow_User(User);
		 * if(!Follow.IS_Exception) { System.out.println("Followed "+ User);
		 * 
		 * }else if(Follow.IS_Exception) { System.out.println(Follow.Exception_MSG); }
		 * 
		 * followobj.reset(); }
		 * 
		 * //System.out.println(authobj.get_RemainigCalls(EnviornmentVariables.
		 * FOLLOWER_LIMIT.toString())); } // auth IS_OK
		 */

	}
}
