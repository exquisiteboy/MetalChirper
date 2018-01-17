package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FormatQuery {
	
	public enum SearchOperator {
		Operator_OR("OR"),
		Operator_AND(" ");

		private final String name;

		/**
		 * @param name
		 */
		private SearchOperator(final String name) {
		    this.name = name;
		}

		public String getName() {
		    return name;
		}

		}

    public String [] keywords;
    public String [] negtivekeywrods;
    public static String finalQuery = "";
    public ArrayList<Integer> randomInts ;
	public static boolean IS_Exception = false;
	public static String Exception_MSG = "";
	
	
    public FormatQuery(String [] keywords) {
		if(keywords.length>0)
    	this.keywords = Arrays.copyOf(keywords,keywords.length);
		else {
			this.IS_Exception = true;
			this.Exception_MSG  = "Size of keyword is zero";
		}
	}
	
    public FormatQuery(String [] keywords,String []negtiveKeywords) {
		if(keywords.length>0) {
    	this.keywords = Arrays.copyOf(keywords,keywords.length);
		this.negtivekeywrods = Arrays.copyOf(negtiveKeywords,negtiveKeywords.length);
		}else {
			this.IS_Exception = true;
			this.Exception_MSG  = "Size of keyword is zero";
		}
	}
	public void makeQuery() {
		
		if(this.keywords.length>15) {
			Random rand = new Random();
			randomInts = new ArrayList<Integer>(15);
			
			for (int i=0;i<this.keywords.length;i++) {       
				int random = rand.nextInt(this.keywords.length-1);
				//System.out.println(random);
				
				if(!(randomInts.contains(random))) {
				    	randomInts.add(random);
				}
				 if(randomInts.size()>=15) {
				    	break;
				 }
				    
			}
			for(Integer i:this.randomInts) {
				FormatQuery.finalQuery += String.format("\"%s\" OR ", this.keywords[i]);
			}
		}else {
			for(String keyword:this.keywords) {
				FormatQuery.finalQuery += String.format("\"%s\" OR ", keyword);
			}
		}
		
		boolean t = FormatQuery.finalQuery.endsWith("OR ");
		if(t) {
			int lastOR = FormatQuery.finalQuery.lastIndexOf("OR ");
			FormatQuery.finalQuery = FormatQuery.finalQuery.substring(0, lastOR);
			this.IS_Exception = false;
			this.Exception_MSG = "";
		}
	}

	
	
public void makeQuery(SearchOperator operator) {
		
		if(this.keywords.length>15) {
			Random rand = new Random();
			randomInts = new ArrayList<Integer>(15);
			
			for (int i=0;i<this.keywords.length;i++) {       
				int random = rand.nextInt(this.keywords.length-1);
				//System.out.println(random);
				
				if(!(randomInts.contains(random))) {
				    	randomInts.add(random);
				}
				 if(randomInts.size()>=15) {
				    	break;
				 }
				    
			}
			for(Integer i:this.randomInts) {
				FormatQuery.finalQuery += String.format("\"%s\" %s ", this.keywords[i],operator.getName());
			}
		}else {
			for(String keyword:this.keywords) {
				FormatQuery.finalQuery += String.format("\"%s\" %s ", keyword,operator.getName());
			}
		}
		String negtkeywords = "";
		for(String negkey:this.negtivekeywrods) {
			negtkeywords +=String.format("-\"%s\" ",negkey);
		}
		
		boolean t = FormatQuery.finalQuery.endsWith(operator.getName()+" ");
		if(t) {
			int lastOR = FormatQuery.finalQuery.lastIndexOf(operator.getName()+" ");
			FormatQuery.finalQuery = FormatQuery.finalQuery.substring(0, lastOR);
			FormatQuery.finalQuery = FormatQuery.finalQuery.concat(negtkeywords);
			this.IS_Exception = false;
			this.Exception_MSG = "";
		}
	}
	
	
	
}
