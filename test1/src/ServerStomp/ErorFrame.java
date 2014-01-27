package ServerStomp;
import java.util.ArrayList;

import ServerClient.Client;
import ServerClient.Topic;


public class ErorFrame extends StompFrame implements ErorFrameInterface{
	
	public ErorFrame(ArrayList<Client> clients,ArrayList<Topic> topics,String whatIsTheProblem,String msg,String descriptionOfTheProblem){
		super(clients,topics);
		this.command=StompCommand.valueOf("ERROR");
		this.header.put("message", whatIsTheProblem);
		this.header.put("The message", msg);
		this.body=descriptionOfTheProblem;
		
		
	}
	public ErorFrame(ArrayList<Client> clients,ArrayList<Topic> topics,String whatIsTheProblem,StompFrame frame,String descriptionOfTheProblem){
		super(clients,topics);
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
