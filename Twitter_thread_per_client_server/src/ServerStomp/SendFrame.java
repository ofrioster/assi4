package ServerStomp;
import java.util.ArrayList;

import ServerClient.Client;
import ServerClient.Topic;


public class SendFrame extends StompFrame implements SendFrameInterface{
	
	public SendFrame(ArrayList<Client> clients,ArrayList<Topic> topics){
		super(clients,topics);
	}

}
