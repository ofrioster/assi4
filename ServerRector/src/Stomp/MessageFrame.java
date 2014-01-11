package Stomp;
import java.util.ArrayList;
import Client.*;


public class MessageFrame extends StompFrame implements MessageFrameInterface{
	
	private String destination;
	private String subscription;
	private String messageId;
	private String tweet;
	private Stats stats;
	
	/** constructor, set client is offline
    *
    */
	public MessageFrame(StompFrame frame,Stats stats){
		super(frame.getClients());
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
		this.stats=stats;
		if (this.header.get("destination").equals("server")){
			this.stats.updateStats(this.client);
		}
		else{
			this.serchForMentionsClients();
		}
	}
	public MessageFrame(ArrayList<Client> clients, String msg,Stats stats){
		super(clients);
		this.command=StompCommand.valueOf("MESSAGE");
		this.addHeaderAndBody(msg);
		this.messageId=this.header.get("message-id");
		this.destination=this.header.get("destination");
		this.subscription=this.header.get("subscription");
	//	this.client.addTweet(this.body);
		this.tweet=this.body;
		this.stats=stats;
		if (this.header.get("destination").equals("server")){
			this.stats.updateStats(this.client);
		}
		else{
			this.serchForMentionsClients();
		}
	}
	public String getTweet(){
		return this.tweet;
	}
	public String getMessageId(){
		return this.messageId;
	}
	/**serch for mention clients and send them this message
	 * @param frame
	 */
	public void serchForMentionsClients(){
		String[] msg=this.tweet.split(" ");
		for (int i=0;i<msg.length;i++){
			if(msg[i].startsWith("@")){
				String userName=msg[i].substring(1, msg[i].length()-1);
				for (int k=0;k<this.clients.size();k++){
					if (this.clients.get(k).isThisTheClient(userName)){
						this.clients.get(k).addNewMessage(this);
						if (this.clients.get(k).isThisTheClient(this.destination)){
							this.clients.get(k).updateClientMentionInHisTweets();
						}
						else{
							this.clients.get(k).updateClientMention();
						}
						
					}
				}
			}
		}
	}

	

}
