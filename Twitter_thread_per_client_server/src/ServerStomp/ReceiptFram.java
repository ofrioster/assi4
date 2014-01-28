package ServerStomp;
import java.util.ArrayList;

import ServerClient.Client;
import ServerClient.Topic;



public class ReceiptFram extends StompFrame implements ReceiptFramInterface{
	
	public ReceiptFram (StompFrame frame,StompCommand command){
		super(frame.getClients(),frame.getTopics());
		this.command=command;
		if (this.command.equals(StompCommand.valueOf("DISCONNECT"))){
			this.header.put("version", frame.header.get("receipt"));
		}
		else{
			this.header.put("version", frame.header.get("accept-version"));
		}
	}
	public ReceiptFram (String command, String receipt,ArrayList<Topic> topic,ArrayList<Client> clients,String version){
		super(clients,topic);
		this.command=StompCommand.valueOf(command);
		if (this.command.equals(StompCommand.valueOf("DISCONNECT"))){
			this.header.put("version", receipt);
			this.header.put("version", receipt);
		}
		else{
			this.header.put("version", version);
		}
	}
	public ReceiptFram (StompFrame frame,String command){
		super(frame.getClients(),frame.getTopics());
		this.command=StompCommand.valueOf(command);
		if (this.command.equals(StompCommand.valueOf("DISCONNECT"))){
			this.header.put("version", frame.header.get("receipt"));
		}
		else{
			this.header.put("version", frame.header.get("accept-version"));
		}
	}
	 public String getString() {
		 if (this.command.equals(StompCommand.valueOf("DISCONNECT"))){
			 String frame ="RECEIPT"+ '\n';
			 frame+="receipt-id:"+this.getHeader("version")+ '\n';
	         frame += "\u0000";
	         return frame;
			}
			else{
				return super.getString();
			}
        
 }

}
