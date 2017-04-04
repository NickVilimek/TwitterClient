package backend;

import java.util.ArrayList;
import java.util.List;

public class TwitterUser {
	
	private String screenname; 
	private long id; 
	private String imageUrl; 
	private List<Tweet> tweets = new ArrayList<Tweet>(); 
	
	public TwitterUser(String user_screenname, long user_id, String user_imageUrl){
		screenname = user_screenname; 
		id = user_id; 
		imageUrl = user_imageUrl; 
	}
	
	public String getScreenname(){
		return screenname; 
	}
	public long getId(){
		return id; 
	}
	public String getImageUrl(){
		return imageUrl; 
	}
	public void setTweets(List<Tweet> user_tweets){
		tweets.addAll(user_tweets); 
	}
	public List<Tweet> getTweets(){
		return new ArrayList<Tweet>(tweets); 
	}

}
