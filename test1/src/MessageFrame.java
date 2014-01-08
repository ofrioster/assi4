
public class MessageFrame extends StompFrame implements MessageFrameInterface{
	
	private String messageId;
	private String tweet;
	
	/** constructor, set client is offline
    *
    */
	public MessageFrame(StompFrame frame){
		super(frame.getClients(),frame.getTopics());
		this.command= frame.command;
		this.header=frame.getHeader();
		this.body=frame.getBody();
		this.client=frame.getClient();
		this.sessionId=frame.getSessionId();
		this.client.setClientIsOnline(true);
		this.messageId=frame.header.get("message-id");
		this.client.addTweet(frame.body);
		this.tweet=frame.body;
		this.client.addmessageToFollowers(this);
	}
	public String getTweet(){
		return this.tweet;
	}
	public String getMessageId(){
		return this.messageId;
	}
	

}
