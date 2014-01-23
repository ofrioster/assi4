package Stomp;
import java.util.ArrayList;
import Client.*;


public class ErorFrame extends StompFrame implements ErorFrameInterface{
	
	public ErorFrame(ArrayList<Client> clients,String whatIsTheProblem,String msg){
		super(clients);
		this.command=StompCommand.valueOf("ERROR");
		this.header.put("message", "malformed STOMP frame received");
		this.header.put("The message", msg);
		this.body=whatIsTheProblem;
		
		
	}
	public ErorFrame(ArrayList<Client> clients,String whatIsTheProblem,String msg,String descriptionOfTheProblem){
		super(clients);
		this.command=StompCommand.valueOf("ERROR");
		this.header.put("message", whatIsTheProblem);
		this.header.put("The message", msg);
		this.body=descriptionOfTheProblem;
		
		
	}
	public ErorFrame(ArrayList<Client> clients,String whatIsTheProblem,StompFrame frame,String descriptionOfTheProblem){
		super(clients);
		try{
			this.command=StompCommand.valueOf("ERROR");
		}
		catch (Exception e){
			
		}
		try{
			this.header.put("message", whatIsTheProblem);
		}
		catch (Exception e){
			
		}
		try{
			this.header.put("The message", frame.getString());
		}
		catch (Exception e){
			
		}

		this.body=descriptionOfTheProblem;
		
		
	}
	public ErorFrame(ArrayList<Client> clients,String whatIsTheProblem,StompFrame frame){
		super(clients);
		this.command=StompCommand.valueOf("ERROR");
		this.header.put("message", "malformed STOMP frame received");
		this.header.put("The message", frame.getString());
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
