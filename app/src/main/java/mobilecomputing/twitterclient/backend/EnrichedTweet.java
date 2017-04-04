package backend; 

public class EnrichedTweet extends Tweet {
	private String senderScreenname; 
	private String senderImageUrl; 
	public EnrichedTweet(String sender_name, String sender_imageUrl, Tweet t) {
		super(t.getText());
		this.setDateCreated(t.getDateCreated());
		senderScreenname = sender_name; 
		senderImageUrl = sender_imageUrl; 
	}
	
	
	public String getSenderScreenname(){
		return senderScreenname; 
	}
	public String getSenderImageUrl(){
		return senderImageUrl; 
	}
	
	public String toString(){
		return senderScreenname + "\t" + super.toString() + "\t" + senderImageUrl; 
	}

}
