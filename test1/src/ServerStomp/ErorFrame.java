package ServerStomp;
import java.util.ArrayList;

import ServerClient.Client;
import ServerClient.Topic;


public class ErorFrame extends StompFrame implements ErorFrameInterface{
	
	public ErorFrame(ArrayList<Client> clients,ArrayList<Topic> topics,String whatIsTheProblem,String msg){
		super(clients,topics);
		this.command=StompCommand.valueOf("ERROR");
		this.header.put("message", "malformed STOMP frame received");
		this.header.put("The message", msg);
		this.body=whatIsTheProblem;
		
		
	}
	public ErorFrame(ArrayList<Client> clients,ArrayList<Topic> topics,String whatIsTheProblem,StompFrame frame){
		super(clients,topics);
		try{
			this.command=StompCommand.valueOf("ERROR");
		}
		catch (Exception e){
			
		}
		try{
			this.header.put("message", "malformed STOMP frame received");
		}
		catch (Exception e){
			
		}
		try{
			this.header.put("The message", frame.getString());
		}
		catch (Exception e){
			
		}

		this.body=whatIsTheProblem;
		
		
	}
	//TODO check the end of message if \0
    /** getBytes convert frame object to array of bytes
     * @return array of bytes
     */
    public String getString() {
            String frame = this.command.toString() + '\n';
            frame+= "message"+ ":"+this.header.get("message")+ '\n';
            frame+= "The message"+":"+'\n';
            frame+="-----"+'\n';
            frame+= this.header.get("The message")+ '\n';
            frame+="-----"+'\n';
            if (this.body != null) {
                    frame += this.body;
            }
            frame += "\0";
            return frame;
    }

}
