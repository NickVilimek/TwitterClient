package mobilecomputing.twitterclient.backend;

import java.util.ArrayList;
import java.util.List;


public class Test{
	public static void main(String[] args){
		TwitterInterface t = new TwitterInterface(); 
		List<String> screennames = new ArrayList<String>(); 
		screennames.add("barackobama"); 
		screennames.add("realdonaldtrump"); 
		List<EnrichedTweet> tweets = t.getTopTweetsByDateSent(screennames, 50); 
		for (EnrichedTweet tweet: tweets){
			System.out.println(tweet);
		}
	}
}
