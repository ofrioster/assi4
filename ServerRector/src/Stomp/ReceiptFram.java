package Stomp;
import java.util.ArrayList;

import ServerStomp.StompCommand;
import ServerStomp.StompFrame;
import Client.*;



public class ReceiptFram extends StompFrame implements ReceiptFramInterface{
	
	public ReceiptFram (StompFrame frame,StompCommand command){
		super(frame.getClients());
		this.command=command;
		this.header.put("version", frame.header.get("accept-version"));
	}
	public ReceiptFram (StompFrame frame,String command){
		super(frame.getClients(),frame.getTopics());
		this.command=StompCommand.valueOf(command);
		this.header.put("version", frame.header.get("accept-version"));
	}

}
