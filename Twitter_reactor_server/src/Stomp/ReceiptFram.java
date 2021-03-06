package Stomp;
import java.util.ArrayList;


import Client.*;



public class ReceiptFram extends StompFrame implements ReceiptFramInterface{
	
	public ReceiptFram (StompFrame frame,StompCommand command){
		super(frame.getClients());
		this.command=command;
		this.header.put("version", frame.header.get("accept-version"));
	}
	public ReceiptFram (StompFrame frame,String command){
		super(frame.getClients());
		this.command=StompCommand.valueOf(command);
		this.header.put("version", frame.header.get("accept-version"));
	}

}
