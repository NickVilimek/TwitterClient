package mobilecomputing.twitterclient.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwitterInterface {
	private Twitter4JWrapper wrapper = new Twitter4JWrapper("rfZiyU6XkHv5Sm0oEFU6hgKX2", 
			"LYATfrM2s43snIAWRIvUx8oxmknwnzsG03isCuBZAvcstVsurN", 
			"818480492-FvbG8smtMnvsFi9hDDtNjEe8L8ZzqAWSX6hjAOzi", 
			"GvV4K5237zZxMsEaXVC0D6KrexeTLBUrfOwNzccLrTuc3"); 
	
	
//	public HashMap<String, TwitterUser> getTweetDataByScreennames(List<String> screennames, int numTweetsPerUser){
//		HashMap<String, TwitterUser> userMap = new HashMap<String, TwitterUser>(); 
//		for (String screenname : screennames){
//			TwitterUser user = wrapper.getUserInformation(screenname); 
//			wrapper.setTweetsForUser(user, numTweetsPerUser);
//			userMap.put(screenname, user); 
//		}
//		return new HashMap<String, TwitterUser>(userMap); 
//	}
	
	public List<EnrichedTweet> getTopTweetsByDateSent(List<String> screennames, int numTweetsPerUser){
		List<EnrichedTweet> tweets = new ArrayList<EnrichedTweet>(); 
		for (String screenname : screennames){
			TwitterUser user = wrapper.getUserInformation(screenname); 
			wrapper.setTweetsForUser(user, numTweetsPerUser);
			for (Tweet t : user.getTweets()){
				tweets.add(new EnrichedTweet(user.getScreenname(), user.getImageUrl(), t)); 
			}
		}
		Collections.sort(tweets);
		Collections.reverse(tweets);
		return tweets; 
	}
	
	

}
