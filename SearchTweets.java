package testing;

import java.util.ArrayList;

import database.DBClass;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter_source.Auth_Class;
import util.EnviornmentVariables;
/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class SearchTweets {
    private static final DBClass DBClass = null;
	/**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args search query
     */
	private static DBClass dbobj;
	private static Auth_Class authobj;
    public  static String UserID ="";

	public static void main(String[] args) {
		dbobj = new DBClass();
		dbobj.getAuthKeys();

		if (DBClass.AUTH_OK) {
			UserID = database.DBClass.twitter_user_id;
			dbobj.updateTimeStamp(UserID);
			authobj = new Auth_Class(DBClass,EnviornmentVariables.TWEET_Lookup_LIMIT);
			if (!Auth_Class.IS_OK) {
				System.out.println("auth problem");

			}
			Twitter twitter = Auth_Class.TWITTER_INSTANCE;
			int limits = Auth_Class.REMAINING_CALLS;
			System.out.println("search tweet limit" + limits);
			QueryResult result = null;
			Query query = new Query("#PP20KaptaanKa");
			// query.query("PML-N");
			long lastID = Long.MAX_VALUE;
			ArrayList<Status> tweets = new ArrayList<Status>();

			query.setCount(200);
			try {
				result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			;
			while (result.hasNext()) {

				tweets.addAll(result.getTweets());
				System.out.println(result.hasNext());
				System.out.println(result.nextQuery());
				System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();

				query.setMaxId(lastID - 1);
				System.out.println("Math" + Math.subtractExact(lastID, 1));
				query.setLang("en");
				try {
					result = twitter.search(query);
				} catch (TwitterException twex) {
					System.out.println("unable to search tweets because " + twex.getMessage());
				}
			}

		}
	}
}
