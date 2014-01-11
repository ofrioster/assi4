package Stomp;
import java.util.ArrayList;
import Client.*;

//TODO can be delete
public class SendFrame extends StompFrame implements SendFrameInterface{
	
	public SendFrame(ArrayList<Client> clients){
		super(clients);
	}

}
