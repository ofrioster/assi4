import java.util.ArrayList;


public class MessageFrame extends StompFrame implements MessageFrameInterface{
	
	private String destination;
	private String subscription;
	private String messageId;
	private String tweet;
	
	/** constructor, set client is offline
    *
    */
	public MessageFrame(StompFrame frame){
		super(frame.getClients(),frame.getTopics());
		this.command= StompCommand.valueOf("MESSAGE");
		this.header=frame.getHeader();
		this.body=frame.getBody();
//		this.client=frame.getClient();
		//this.client.setClientIsOnline(true);
		this.messageId=frame.header.get("message-id");
		this.destination=frame.header.get("destination");
		this.subscription=frame.header.get("subscription");
	//	this.client.addTweet(frame.body);
		this.tweet=frame.body;
	}
	public MessageFrame(ArrayList<Client> clients,ArrayList<Topic> topics, String msg){
		super(clients,topics);
		this.command=StompCommand.valueOf("MESSAGE");
		this.addHeaderAndBody(msg);
		this.messageId=this.header.get("message-id");
		this.destination=this.header.get("destination");
		this.subscription=this.header.get("subscription");
	//	this.client.addTweet(this.body);
		this.tweet=this.body;
	}
	public String getTweet(){
		return this.tweet;
	}
	public String getMessageId(){
		return this.messageId;
	}
	

}
