package ServerStomp;
import java.awt.Frame;
import java.net.Socket;
import java.util.ArrayList;

import ServerClient.Client;
import ServerClient.Topic;




public class DisconnectFrame extends StompFrame implements DisconnectFrameInterface{

	String sessionId;

	/** constructor, set client is offline
    *
    */
	public DisconnectFrame(ArrayList<Client> clients,StompCommand disconnected,String body,String sessionId, Client client,ArrayList<Topic> topics){
		super(clients,topics);
		this.command= disconnected;
		this.header.put("session", sessionId);
		this.body=body;
		this.client=client;
		this.sessionId=sessionId;
		this.client.setClientIsOnline(false);
	}
	/** constructor, set client is offline
    *
    */
	public DisconnectFrame(StompFrame frame, StompCommand command){
		super(frame.getClients(),frame.getTopics());
		this.command= command;
		this.header=frame.getHeader();
		this.body=frame.getBody();
		this.client=frame.getClient();
		this.sessionId=frame.header.get("message-id");
		this.client.setClientIsOnline(false);
		this.clients=frame.getClients();

	}
	
}
