package ServerStomp;
import java.util.ArrayList;

import MainServer.Stats;
import ServerClient.Client;
import ServerClient.Topic;


public class ConnectFrame extends StompFrame implements ConnectFrameInterface{
	private String sessionId;
	private Stats stats;
	/** constructor, set client is offline
    *
    */
	public ConnectFrame(ArrayList<Client> clients,StompCommand command,String body,String sessionId, String userName,String password, String serverIP,ArrayList<Topic> topics,Stats stats){
		super(clients,topics);
		this.command= command;
		this.header.put("session", sessionId);
		this.body=body;
		this.stats=stats;
		this.client=this.serchForClient(userName, password, serverIP);
		this.sessionId=sessionId;
		this.client.setClientIsOnline(true);
	}
	/** constructor, set client is offline
    *
    */
	public ConnectFrame(StompFrame frame, StompCommand command){
		super(frame.getClients(),frame.getTopics());
		this.command= command;
		this.header=frame.getHeader();
		this.body=frame.getBody();
		this.client=frame.getClient();
		this.sessionId=frame.header.get("message-id");
		this.client.setClientIsOnline(true);
	}
	/** 
	 * @param client data
	 * @return the client if exist if not return new client
	 */
	public Client serchForClient(String userName, String password,String serverIP) {
		Client res=new Client(userName, serverIP, password,this.clients,this.stats);
		for (int i=0; i<clients.size();i++){
			if (clients.get(i).getClientUserName().equals(userName)){
				if(clients.get(i).getClientPassword().equals(password)){
					res=clients.get(i);
				}
			}
		}
		return res;
	}

	

}
