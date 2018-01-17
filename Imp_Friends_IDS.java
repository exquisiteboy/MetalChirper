package implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import database.DBClass;
import twitter4j.Twitter;
import twitter_source.Auth_Class;
import twitter_source.Friends_IDS;
import util.EnviornmentVariables;

public class Imp_Friends_IDS {
	public static Twitter Twitter_instance;
	public static String Screen_name = "";
	public String FileInputPath = "";
	public String FileOutputPath = "";
	public static boolean IS_Exception = false;
	public static String Exception_MSG = "";
	public static Friends_IDS fids;

	private static final DBClass DBClass = null;
	private static DBClass dbobj;
	private static Auth_Class authobj;

	public Imp_Friends_IDS(Twitter twitter) {
		try {
			Imp_Friends_IDS.Twitter_instance = twitter;
		} catch (Exception ex) {
			Imp_Friends_IDS.IS_Exception = true;
			Imp_Friends_IDS.Exception_MSG = String.format("Following Error Occured", ex.getMessage());
		}
	}

	public static void getFriendsIDS(String Screen_name, String Fileoutpt, int max) {
		int maxx = max;
		int countids = 0;
		long cursor = -1;
		boolean braked = true;

		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		int remaining_calls = Auth_Class.REMAINING_CALLS;
		System.out.println("Calls remaing"+remaining_calls);
		try {
            if(Fileoutpt.isEmpty())Fileoutpt = Screen_name+"_IDS";
			fw = new FileWriter(Fileoutpt, true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
		} catch (IOException e) {

			Imp_Friends_IDS.IS_Exception = true;
			Imp_Friends_IDS.Exception_MSG = String.format("unable to write file because '%s'", e.getMessage());
		    System.out.println(Exception_MSG);
		}

		while (braked) {
			if (remaining_calls <= 2) {
				dbobj = new DBClass();
				dbobj.getAuthKeys();
				authobj = new Auth_Class(dbobj, EnviornmentVariables.FOLLOWER_LIMIT);
				remaining_calls = Auth_Class.REMAINING_CALLS;
			}
             
			fids = new Friends_IDS(Imp_Friends_IDS.Twitter_instance);
			fids.getFriendsIDs(Screen_name, cursor);
			do {
				if (!Friends_IDS.IS_Exception) {
					long[] ids = Friends_IDS.friendsids;
					System.out.println("ids len ret" + ids.length);
					for (int i = 0; i < ids.length; i += 100) {
						long[] bulk = Arrays.copyOfRange(ids, i, Math.min(i + 100, ids.length));
						countids += bulk.length;
						out.write(Arrays.toString(bulk));
						out.write("\n");

					}
					cursor = Friends_IDS.cursor;
					if (cursor == 0) {
						braked = false;
						break;
					}
					if (countids == max) {
						braked = false;
						break;
					}
					fids.getFriendsIDs(Screen_name, cursor);
					if (maxx == -1)
						continue;
					// fids.reset();
				} else if (Friends_IDS.IS_Exception) {
					System.out.println(Exception_MSG);
				}

			} while (cursor!=0);
			out.close();
			try {
				bw.close();
				fw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

		}
	}

	public static void getFriendDir(String filepath, String dirpath, int max) {
		Path file = FileSystems.getDefault().getPath(dirpath);
		if (Files.isReadable(file) && Files.isWritable(file)) {
			try {
				FileReader fr = new FileReader(filepath);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
				String userName = null;
				while ((userName = br.readLine()) != null) {
					String fileOutpath = file.toAbsolutePath() + "/" + userName + "_Friends_IDS";
					getFriendsIDS(userName, fileOutpath, -1);
				}

			} catch (IOException ex) {
				System.out.println("Error in reading file :" + ex.getMessage());
			}
		}

	}

	public static void main(String[] args) {
		/*
		 * Twitter twitter; int remlimits = 0; dbobj = new DBClass();
		 * dbobj.getAuthKeys();
		 * 
		 * if (database.DBClass.AUTH_OK) { UserID = DBClass.twitter_user_id;
		 * dbobj.updateTimeStamp(UserID); authobj = new Auth_Class(DBClass); if
		 * (twitter_source.Auth_Class.IS_OK) { twitter = authobj.get_twitter_instance();
		 * remlimits = authobj.get_RemainigCalls(EnviornmentVariables.FOLLOWER_LIMIT);
		 * System.out.println("remaning Search calls " + remlimits);
		 * imp_Follower_IDS.get_foll_ids(twitter, "NASA", "./NASAIDS","",remlimits);
		 * if(imp_Follower_IDS.IS_Exception) { System.out.println(Exception_MSG); } } }
		 */

		/*
		 * try { FileReader fr = new FileReader("./NASAIDS"); BufferedReader br = new
		 * BufferedReader(fr); while(br.readLine()!=null) { String Line = br.readLine();
		 * String[] ids = Line.replace("[", "").replace("]", "").split(","); long[] data
		 * = new long[ids.length]; for (int i = 0; i < ids.length; i++) { data[i] =
		 * Long.parseLong(ids[i].trim()); } System.out.println(data.length); break; } }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		Twitter twitter; dbobj = new DBClass();
		 dbobj.getAuthKeys();
		 if (database.DBClass.AUTH_OK) { 
			 dbobj.updateTimeStamp(database.DBClass.twitter_user_id); 
			 authobj = new Auth_Class(DBClass, EnviornmentVariables.FRIENDS_IDS_LIMIT);
			 if(twitter_source.Auth_Class.IS_OK) { 
				 twitter = authobj.get_twitter_instance();
				 new Imp_Friends_IDS(twitter); 
				 Imp_Friends_IDS.getFriendDir("./names","/home/orbit/eclipse-workspace/IDS", -1);
		    
			 }else if(!twitter_source.Auth_Class.IS_OK) {
				 System.out.println(twitter_source.Auth_Class.Exception_MSG);
			 }
		 
		 }else if(database.DBClass.IS_Exception) {
			 System.out.println(database.DBClass.Exception_MSG);
		 }

}
}

