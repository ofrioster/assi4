import java.util.ArrayList;


public class ConnectFrame extends StompFrame implements ConnectFrameInterface{
	
	/** constructor, set client is offline
    *
    */
	public ConnectFrame(ArrayList<Client> clients,StompCommand command,String body,String sessionId, String userName,String password, String serverIP){
		this.command= command;
		this.header.put("session", sessionId);
		this.body=body;
		this.client=this.serchForClient(userName, password, serverIP);
		this.sessionId=sessionId;
		this.client.setClientIsOnline(true);
		this.clients=clients;
	}
	/** constructor, set client is offline
    *
    */
	public ConnectFrame(StompFrame frame, StompCommand command,String sessionId){
		this.command= command;
		this.header=frame.getHeader();
		this.body=frame.getBody();
		this.client=frame.getClient();
		this.sessionId=sessionId;
		this.client.setClientIsOnline(true);
		this.clients=frame.getClients();
	}
	/** 
	 * @param client data
	 * @return the client if exist if not return new client
	 */
	public Client serchForClient(String userName, String password,String serverIP) {
		Client res=new Client(userName, serverIP, password,this.clients);
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
