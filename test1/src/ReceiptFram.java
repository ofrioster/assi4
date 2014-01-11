import java.util.ArrayList;



public class ReceiptFram extends StompFrame implements ReceiptFramInterface{
	
	public ReceiptFram (StompFrame frame,StompCommand command){
		super(frame.getClients(),frame.getTopics());
		this.command=command;
		if (this.command.equals(StompCommand.valueOf("CONNECT"))){
			this.header.put("version", frame.header.get("accept-version"));
		}
		else if (this.command.equals(StompCommand.valueOf("DISCONNECT"))){
			this.header.put("receipt-id", frame.header.get("receipt"));
		}
	}


}
