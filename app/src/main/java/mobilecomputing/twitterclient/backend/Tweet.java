package mobilecomputing.twitterclient.backend;

import java.util.Date;

public class Tweet implements Comparable<Tweet>{
	private String text; 
	private Date dateCreated; 
	
	public Tweet(String t){
		text = t; 
	}
	public String getText(){
		return text; 
	}


	public void setDateCreated(Date d){
		dateCreated = d; 
	}
	public Date getDateCreated(){
		return dateCreated; 
	}

	
	public String toString(){
		return dateCreated.toString() + "\t" + text; 
	}
	@Override
	public int compareTo(Tweet other) {
		return dateCreated.compareTo(other.getDateCreated());
	}

}
