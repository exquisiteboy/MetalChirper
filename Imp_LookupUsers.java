package implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import database.DBClass;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;
import twitter_source.Auth_Class;
import twitter_source.LookupUsers;
import util.EnviornmentVariables;
public class Imp_LookupUsers {
    public static Twitter TWITTER_INSTANCE ;
    public static int remaning_Calls ;
    private static LookupUsers lkuser;
    
    private static final DBClass DBClass = null;
	private static DBClass dbobj;
	private static Auth_Class authobj;
    public Imp_LookupUsers(Twitter twitter) {
    	Imp_LookupUsers.TWITTER_INSTANCE = twitter;
    }
    public static void lokeup(String FileName,String Outfilename) {
    	System.out.println(FileName);
    	FileReader fr = null;
    	FileWriter fw = null;
    	BufferedReader br = null;
    	BufferedWriter bwr = null;
    	PrintWriter out = null;
    	int limits = Auth_Class.REMAINING_CALLS;
    	System.out.println("limits:"+limits);
    	lkuser = new LookupUsers(Imp_LookupUsers.TWITTER_INSTANCE);
    	try {
    		fr = new FileReader(FileName);
    		fw = new FileWriter(Outfilename,true);
    		bwr = new BufferedWriter(fw);
    		br = new BufferedReader(fr);
    		out = new PrintWriter(bwr);
    		String Line =null;
    		while((Line = br.readLine()) != null) {
    			System.out.println("inside while line");
    			 String[] ids = Line.replace("[", "").replace("]", "").split(","); 
    			 long [] ids_hundrd = new long[ids.length]; 
    			 for (int i = 0; i < ids.length; i++) { 
    				 ids_hundrd[i] = Long.parseLong(ids[i].trim());
    			  }
    			 lkuser.lookupUsers(ids_hundrd);
    			 if(!LookupUsers.IS_Exception) {
    				for(User u:LookupUsers.UserInfo) {
    					
    				String json = TwitterObjectFactory.getRawJSON(u).toString();
    				
    				JSONParser obj = new JSONParser();
    				JSONObject obj1 = (JSONObject)obj.parse(json);
    				//System.out.println();
    				//System.out.println(json);
    				
    					System.out.println(u.getName());
    					obj1.writeJSONString(fw);
    					fw.write("\n\n\n\n\n");
    					
    				}
    			 }else if(LookupUsers.IS_Exception) {
    				 System.out.println(LookupUsers.Exception_MSG);
    			 }
    			 System.out.println(ids_hundrd.length); 
    			 }
    	}catch(IOException | ParseException ex) {
    		
    	}
    }
    
    
    
	public static void lookupUsers(String FileIN,String FileOut) {
	     Path path = Paths.get(FileIN);
	     Path outpath = Paths.get(FileOut);
	     try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
	    	    for (Path file: stream) {
	    	    	
	    	    	String followerPf = file.getFileName().toString().split("_")[0];
	    	    	lokeup((file.toAbsolutePath().toString()),outpath.toAbsolutePath()+"/"+followerPf+"_profiles");
	    	        
	    	    }
	    	} catch (IOException | DirectoryIteratorException x) {
	    	    // IOException can never be thrown by the iteration.
	    	    // In this snippet, it can only be thrown by newDirectoryStream.
	    	    System.err.println(x);
	    	}
		
	}
	
	
	public static void CreateJSON(String FileName,String Outfilename,String []fields) {
		System.out.println(FileName);
    	FileReader fr = null;
    	FileWriter fw = null;
    	BufferedReader br = null;
    	BufferedWriter bwr = null;
    	PrintWriter out = null;
    	String str = "";
    	int limits = Auth_Class.REMAINING_CALLS;
    	System.out.println("limits:"+limits);
    	lkuser = new LookupUsers(Imp_LookupUsers.TWITTER_INSTANCE);
    	try {
    		fr = new FileReader(FileName);
    		fw = new FileWriter("/home/orbit/arif.json",true);
    		bwr = new BufferedWriter(fw);
    		br = new BufferedReader(fr);
    		out = new PrintWriter(bwr);
    		String Line =null;
    		while((Line = br.readLine()) != null) {
    			System.out.println("inside while line");
    			 String[] ids = Line.replace("[", "").replace("]", "").split(","); 
    			 long [] ids_hundrd = new long[ids.length]; 
    			 for (int i = 0; i < ids.length; i++) { 
    				 ids_hundrd[i] = Long.parseLong(ids[i].trim());
    				 //System.out.println((ids[i].trim()));
    			  }
    			 
    			 lkuser.lookupUsers(ids_hundrd);
    			 if(!LookupUsers.IS_Exception) {
    				
    				 for(User u:LookupUsers.UserInfo) {
    					JSONObject cre = new JSONObject();
    					 String obj = TwitterObjectFactory.getRawJSON(u);
    					JSONParser parse = new JSONParser();
    					Object parsed =	parse.parse(obj);
    				    JSONObject jjson = (JSONObject)parsed;
    				    for(int i=0;i<fields.length;i++) {
    				    	Object fieldval = jjson.get(fields[i]).toString();
    				    	String str2 = String.valueOf(fieldval);
    				    	Pattern ptrn =Pattern.compile("\\[\\]?",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
   	    				    //Pattern p2 = Pattern.compile("\\[\\]?",Pattern.MULTILINE);
   	    				 	Matcher match = ptrn.matcher(str2);
   	    				 	str2 = match.replaceAll("");
   	    				 	System.out.println(str2);
   	    				 	//match = p2.matcher(str2);
   	    				 	//str2 = match.replaceAll("");
   	    				    //str2 = str2.replace(",", "");
    				    	cre.put(fields[i], str2);
    				    }
    				    out.write(cre.toJSONString());
    				    //fw.flush();
    				
    					
    				}
    			 }else if(LookupUsers.IS_Exception) {
    				 System.out.println(LookupUsers.Exception_MSG);
    			 }
    			 System.out.println(ids_hundrd.length); 
    			 }
    		out.close();
    	}catch(IOException | ParseException   ex) {
    		System.out.println(ex.getMessage());
    	}
	}
	
	
	
	
	
	
	
	
	 public static void CreateCSV(String FileName,String Outfilename,String []fields) {
	    	System.out.println(FileName);
	    	FileReader fr = null;
	    	FileWriter fw = null;
	    	BufferedReader br = null;
	    	BufferedWriter bwr = null;
	    	PrintWriter out = null;
	    	String str = "";
	    	StringBuilder stblder = null;
	    	int limits = Auth_Class.REMAINING_CALLS;
	    	System.out.println("limits:"+limits);
	    	lkuser = new LookupUsers(Imp_LookupUsers.TWITTER_INSTANCE);
	    	try {
	    		fr = new FileReader(FileName);
	    		fw = new FileWriter("/home/orbit/JavaCSV.csv",true);
	    		bwr = new BufferedWriter(fw);
	    		br = new BufferedReader(fr);
	    		out = new PrintWriter(bwr);
	    		String Line =null;
	    		while((Line = br.readLine()) != null) {
	    			System.out.println("inside while line");
	    			 String[] ids = Line.replace("[", "").replace("]", "").split(","); 
	    			 long [] ids_hundrd = new long[ids.length]; 
	    			 for (int i = 0; i < ids.length; i++) { 
	    				 ids_hundrd[i] = Long.parseLong(ids[i].trim());
	    				 //System.out.println((ids[i].trim()));
	    			  }
	    			 
	    			 lkuser.lookupUsers(ids_hundrd);
	    			 StringBuilder headers = new StringBuilder();
	    			 			headers.append("Screen_name");
	    			 			headers.append(",");
	    			 			headers.append("description");
	    			 			headers.append(",");
	    			 			headers.append("id");
	    			 			headers.append(",");
	    			 			headers.append("followers_count");
	    			 			headers.append(",");
	    			 			headers.append("friends_count");
	    			 			headers.append(",");
	    			 			headers.append("statuses_count");
	    			 			out.write(headers.toString());
	    			 			out.write("\n");
	    			 if(!LookupUsers.IS_Exception) {
	    				
	    				 for(User u:LookupUsers.UserInfo) {
	    					 stblder = new StringBuilder();
	    					String obj = TwitterObjectFactory.getRawJSON(u);
	    					JSONParser parse = new JSONParser();
	    					Object parsed =	parse.parse(obj);
	    				    JSONObject jjson = (JSONObject)parsed;
	    				    for(int i=0;i<fields.length;i++) {
	    				    	String field = fields[i];
	    				    	Object fieldval =  jjson.get(field);
	    				    	 str = String.valueOf(fieldval);
	    				    	if(fieldval instanceof Long) {
	    				    		str = str+"";
	    				    	}
	    				    	
	    				    	 String f = str.replace(",", "");
	    				    	Pattern ptrn =Pattern.compile("\\w+?",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
	    				 		Pattern p2 = Pattern.compile("\\[\\]?",Pattern.MULTILINE);
	    				 		Matcher match = ptrn.matcher(f);
	    				 		f = match.replaceAll("");
	    				 		//System.out.println(text);
	    				 		match = p2.matcher(f);
	    				 		f =match.replaceAll("");
	    				 		//System.out.println(f);
	 	    	    	                f = String.format("%s", f);
	 	    	    	                
	    				    	System.out.println("field:"+field +":" +f);
	    				    	 //stblder.append(jjson.get(fields[i]));
	    				    	      stblder.append(f);
	    				    	        stblder.append(",");
	    				    	        
	    				    }
	    				    
	    				   int index = stblder.toString().lastIndexOf(",");
	    				        stblder = new StringBuilder(stblder.toString().substring(0, index));
	    				    stblder.append("\n");
	    				    //System.out.println(stblder.toString());
	    				    out.write(stblder.toString());
	    				    //fw.flush();
	    				
	    					
	    				}
	    			 }else if(LookupUsers.IS_Exception) {
	    				 System.out.println(LookupUsers.Exception_MSG);
	    			 }
	    			 System.out.println(ids_hundrd.length); 
	    			 }
	    		out.close();
	    	}catch(IOException | ParseException   ex) {
	    		System.out.println(ex.getMessage());
	    	}
	    }
	public static void main(String []args) {
		Twitter twitter; int remlimits = 0; dbobj = new DBClass();
		 dbobj.getAuthKeys();
		 if (database.DBClass.AUTH_OK) { 
			 dbobj.updateTimeStamp(database.DBClass.twitter_user_id); 
			 authobj = new Auth_Class(DBClass, EnviornmentVariables.USER_LOOKUP_LIMIT);
			 if(twitter_source.Auth_Class.IS_OK) { 
				 twitter = authobj.get_twitter_instance();
				 Imp_LookupUsers a  = new Imp_LookupUsers(twitter);
				  //lookupUsers("/home/orbit/eclipse-workspace/IDS","/home/orbit/eclipse-workspace/Profiles");
		    
			 }else if(!twitter_source.Auth_Class.IS_OK) {
				 System.out.println(twitter_source.Auth_Class.Exception_MSG);
			 }
		 
		 }else if(database.DBClass.IS_Exception) {
			 System.out.println(database.DBClass.Exception_MSG);
		 }
		String [] g = {"screen_name","description","id","followers_count","friends_count","statuses_count"};
		
		//CreateCSV("/home/orbit/eclipse-workspace/IDS/@shooby367_IDS","/home/orbit/eclipse-workspace/Profiles/",g);		
		
		CreateJSON("/home/orbit/eclipse-workspace/IDS/@arif_seit1_IDS","/home/orbit/eclipse-workspace/Profiles/",g);
		/*long myLong = 1234567890123545454L;
		String myString = Long.toString(myLong);
		System.out.println("mystrinf"+myString);
		*/
		/*String text ="\u200Fکشمیر کی آزادی سے ہماری مراد ایک کی غلامی سے نکل کر دوسرے کی غلامی اختیار کرنا نہیں ہے بلکہ ہم ایک ایسی ریاست چاہتے ہیں جو مکمل خودمختار ہو  \n\nمقبول بٹ شہید \"/\"/"; 
		Pattern ptrn =Pattern.compile("\\w+?",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
		Pattern p2 = Pattern.compile("\\[\\]?",Pattern.MULTILINE);
		Matcher match = ptrn.matcher(text);
		text = match.replaceAll("");
		System.out.println(text);
		match = p2.matcher(text);
		text =match.replaceAll("");
		System.out.println(text);*/
		
		
	}

}
