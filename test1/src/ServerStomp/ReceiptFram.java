package ServerStomp;
import java.util.ArrayList;



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
			 /*
			 for (String key : this.header.keySet()) {
                 frame += "receipt-id:" + ":" + this.header.get(key) + '\n';
         }
*/
	         
	     //    frame += "\n";
	       //  frame += "\0";
	         frame += "\u0000";
	         return frame;
			}
			else{
				return super.getString();
			}
        
 }

}
