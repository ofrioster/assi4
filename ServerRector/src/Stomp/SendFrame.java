package Stomp;
import java.util.ArrayList;
import Client.*;


public class SendFrame extends StompFrame implements SendFrameInterface{
	
	public SendFrame(ArrayList<Client> clients){
		super(clients);
	}

}
