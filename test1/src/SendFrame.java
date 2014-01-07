import java.util.ArrayList;


public class SendFrame extends StompFrame implements SendFrameInterface{
	
	public SendFrame(ArrayList<Client> clients){
		super(clients);
	}

}
