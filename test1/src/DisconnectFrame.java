import java.awt.Frame;
import java.net.Socket;
import java.util.ArrayList;




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
	//	this.socket=socket;
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
	


    /**
     * disconnect() - finalize work with STOMP server
     */
	/*
    public void disconnect() {
            if (socket.isConnected()) {
                    try {
                            // sending DISCONNECT command
                            StompFrame frame = new StompFrame(StompCommand.DISCONNECTED);
                            frame.header.put("session", sessionId);
                            send(frame);

                            // stopping reader thread
                            running = false;
                            
                            // close socket
                            socket.close();
                    } catch (Exception e) {
                    }
            }
    }*/
}
