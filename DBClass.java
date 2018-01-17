package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import util.EnviornmentVariables;

public class DBClass {
	
    private static Connection con=null;
	public static boolean IS_Exception = false;
	public static boolean AUTH_OK =false;
	public static String Exception_MSG = "";
	public static String CONSUMER_KEY = "";
	public static String CONSUMER_SECRET = "";
	public static String ACCESS_KEY = "";
	public static String ACCESS_SECRET = "";
	public static String twitter_user_id ="";
    public DBClass() {
		try {
				Class.forName("com.mysql.jdbc.Driver");
		
		} catch (ClassNotFoundException e1) {
			DBClass.IS_Exception = true;
			DBClass.Exception_MSG = "mysql driver class not found";
		}
        try {
            
        	con = (Connection) DriverManager.getConnection(EnviornmentVariables.DB_URL, EnviornmentVariables.DB_USER, EnviornmentVariables.DB_PASS);
           DatabaseMetaData mt = con.getMetaData();
           
           if(! mt.getDatabaseProductName().isEmpty()) {
        	  
        	   System.out.println("connected to db "+mt.getDatabaseProductName()+" successfully");
          }
          
        } catch (SQLException e) {
        	DBClass.IS_Exception = true;
        	DBClass.Exception_MSG = "Error in connection to db because  "+e.getMessage();
        }
		
	}
    
    public Connection getConnection() {
    	return DBClass.con;
    }
    
    public void getAuthKeys() {
    	
    	PreparedStatement st=null;
    	ResultSet rs=null;
    	String Query = "SELECT * from tt_twitter_app order by last_used limit 1";
    	try {
    		st = DBClass.con.prepareStatement(Query);
    		st.execute();
    		rs =  st.getResultSet();
    	if( rs.getMetaData().getColumnCount()>0) {
    		while(rs.next()) {
    		DBClass.CONSUMER_KEY = rs.getString("consumerKey");
    		DBClass.CONSUMER_SECRET =rs.getString("consumerSecret");
    		DBClass.ACCESS_KEY = rs.getString("accessToken");
    		DBClass.ACCESS_SECRET =rs.getString("accessTokenSecret");
    		DBClass.twitter_user_id =rs.getString("twitter_user_id");
    		DBClass.AUTH_OK = true;
    			//System.out.println(access+"   "+acsecret+"     "+acctok+"      "+acsectok+"     "+acsectok +"          " +id);
    		}
    	}
    	}catch(SQLException sqlex) {
    		DBClass.IS_Exception = true;
    		DBClass.Exception_MSG = String.format("Error in create PreparedStatem because  '%s'",sqlex.getMessage());
    		
    		try {
    			DBClass.con.rollback();
    			DBClass.con.close();
    		}catch(SQLException ex) {
    			
    			System.err.println("Error in closing db connection beacuse " +ex.getMessage());
    		}
    	}
    	
    
    }
    
    
    
    
    
 public void getAuthKeys(String userName) {
    	
    	PreparedStatement st=null;
    	ResultSet rs=null;
    	String Query = String.format("SELECT * from tt_twitter_app where twitter_user_id ='%s' limit 1",userName);
    	try {
    		st = DBClass.con.prepareStatement(Query);
    		st.execute();
    		rs =  st.getResultSet();
    	if( rs.getMetaData().getColumnCount()>0) {
    		while(rs.next()) {
    		DBClass.CONSUMER_KEY = rs.getString("consumerKey");
    		DBClass.CONSUMER_SECRET =rs.getString("consumerSecret");
    		DBClass.ACCESS_KEY = rs.getString("accessToken");
    		DBClass.ACCESS_SECRET =rs.getString("accessTokenSecret");
    		DBClass.twitter_user_id =rs.getString("twitter_user_id");
    		DBClass.AUTH_OK = true;
    			//System.out.println(access+"   "+acsecret+"     "+acctok+"      "+acsectok+"     "+acsectok +"          " +id);
    		}
    	}
    	}catch(SQLException sqlex) {
    		DBClass.IS_Exception = true;
    		DBClass.Exception_MSG = String.format("Error in create PreparedStatem because  '%s'",sqlex.getMessage());
    		
    		try {
    			DBClass.con.rollback();
    			DBClass.con.close();
    		}catch(SQLException ex) {
    			
    			System.err.println("Error in closing db connection beacuse " +ex.getMessage());
    		}
    	}
    	
    
    }
    
    
    
    public void updateTimeStamp(String userID) {
    	long seconds = System.currentTimeMillis()/1000;
    	PreparedStatement st =null;
    	System.out.println("Current Time"+LocalDateTime.now().toLocalTime());
    	String Query  = String.format("update tt_twitter_app set last_used ='%s' where twitter_user_id='%s' ",LocalDateTime.now(),userID);
    	try {
    		st = DBClass.con.prepareStatement(Query);
    		st.executeUpdate();
    	
    	}catch(SQLException sqlex) {
    		DBClass.IS_Exception = true;
    		DBClass.Exception_MSG = "Error in execute Update timestamp because" +sqlex.getMessage();
    		
    	}
    	
    }
    public void reset() {
    	DBClass.IS_Exception = false;
    	DBClass.Exception_MSG = "";
    	DBClass.ACCESS_KEY ="";
    	DBClass.ACCESS_SECRET = "";
    	DBClass.CONSUMER_KEY = "";
    	DBClass.CONSUMER_SECRET = "";
    	DBClass.twitter_user_id = "";
    	DBClass.AUTH_OK = false;
    }
}
