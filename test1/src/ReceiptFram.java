import java.util.ArrayList;



public class ReceiptFram extends StompFrame implements ReceiptFramInterface{
	
	public ReceiptFram (StompFrame frame,StompCommand command){
		super(frame.getClients());
		this.command=command;
		this.header.put("receipt-id", frame.header.get("receipt-id"));
	}


}
