package util;

public class EnviornmentVariables {
	public static final String DB_URL  = "jdbc:mysql://localhost:3306/metalchirper";
	public static final String DB_USER = "mchirper_owner";
	public static final String DB_NAME = "metalchirper";
	public static final String DB_PASS = "11223344";
	
	public static final String FOLLOWER_LIMIT = "/followers/ids";
    public static final String FAVORITES_LIMIT= "/favorites/list";
    public static final String FRIENDS_IDS_LIMIT= "/friends/ids";
    public static final String FRIENDSHIP_LIMIT = "/friendships/show";
    public static final String Search_TWeet_LIMIT = "/search/tweets";
    public static final String TWEET_Lookup_LIMIT = "/search/tweets";
    public static final String Mention_TImeline_LIMIT = "/statuses/mentions_timeline";
    public static final String TIMELINE_LIMIT = "/statuses/user_timeline";
    public static final String USER_LOOKUP_LIMIT = "/users/lookup";
    public static final String USER_SEARCH_LIMIT = "/users/search";
    public static final String USER_SHOW_LIMIT = "/users/show";
}