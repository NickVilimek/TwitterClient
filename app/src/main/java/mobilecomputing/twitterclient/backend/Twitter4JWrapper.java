package mobilecomputing.twitterclient.backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import twitter4j.GeoLocation;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterResponse;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter4JWrapper {
	private Twitter twitter; 
	
	public static final int SCREENNAME = 0;
	public static final int ID = 1;
	public static final int LOCATION = 2;
	public static final int NUMBER_OF_FOLLOWERS = 3;
	public static final int NUMBER_OF_FLAGS = 4; 
	
	
	public Twitter4JWrapper(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret){
		if (consumerKey == null) throw new IllegalArgumentException("consumerKey can't be null"); 
		if (consumerSecret == null) throw new IllegalArgumentException("consumerSecret can't be null"); 
		if (accessToken == null) throw new IllegalArgumentException("accessToken can't be null"); 
		if (accessTokenSecret == null) throw new IllegalArgumentException("accessTokenSecret can't be null"); 
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setOAuthConsumerKey(consumerKey);
	    cb.setOAuthConsumerSecret(consumerSecret);
	    cb.setOAuthAccessToken(accessToken);
	    cb.setOAuthAccessTokenSecret(accessTokenSecret);
	    cb.setDebugEnabled(false); 
	    

	    twitter = new TwitterFactory(cb.build()).getInstance();
	    
	}
	
	
	public TwitterUser getUserInformation(String screenname){
		if (screenname == null) throw new IllegalArgumentException("screenname can't be null"); 
		try{
			User twitterUser = twitter.showUser(screenname); 
			checkRateLimitRemaining(twitterUser); 
			
			String handle = twitterUser.getScreenName(); 
			long id = twitterUser.getId(); 
			String imageUrl = twitterUser.getMiniProfileImageURL(); 
			
			return new TwitterUser(handle, id, imageUrl); 
			
		}catch (TwitterException e){

		}catch (NullPointerException e){

		}
		return new TwitterUser(screenname, 0L, ""); 
	}
	
	public void setTweetsForUser(TwitterUser u, int numTweets){
		List<Tweet> tweets = getUserTweets(u.getId(), numTweets); 
		u.setTweets(tweets);
	}
	
	
	public List<Tweet> getUserTweets(long ID, int numTweets){
		List<Tweet> userTweets = new ArrayList<Tweet>(); 
		try{
			int count = 0; 
			for (int i = 1; i < (numTweets/100)+2; i++){
				int pageno = i;
				Paging page = new Paging(i, 100);
				ResponseList<Status> s = twitter.getUserTimeline(ID, page); 
				checkRateLimitRemaining(s); 
				for (Status stat: s){
					count++; 
					if (count>numTweets) break; 
					//text
					Tweet userTweet = new Tweet(stat.getText());
					//date
					Date d = stat.getCreatedAt(); 
					userTweet.setDateCreated(d); 
					userTweets.add(userTweet); 	
				}
			}
		}catch (TwitterException e){

		}catch (NullPointerException e){

		}
		
		return new ArrayList<Tweet>(userTweets); 
		
	}

	
	private void checkRateLimitRemaining(TwitterResponse response){
		if (response == null) throw new IllegalArgumentException("response can't be null"); 
		try{
			if (response.getRateLimitStatus().getRemaining() == 0){
				try{
					System.out.println("rate limit reached...sleeping..."); 
					TimeUnit.SECONDS.sleep((60*15)+1); 
				}catch (InterruptedException e){
					System.out.println("interupted exception occured");
				}
			}
		}catch(NullPointerException e){
		}

	}
	
}
