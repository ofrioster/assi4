package ServerStomp;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import MainServer.Stats;
import ServerClient.Client;
import ServerClient.Topic;





public class  StompFrame implements StompFrameInterface{
	
	public StompCommand command;
    protected Map<String, String> header = new HashMap<String, String>();
    protected String body;
    protected Client client;
    protected ArrayList<Client> clients;
    protected ArrayList<Topic> topics;
  //  protected Stats stats;

    /** constructor
     *
     */
    public StompFrame(ArrayList<Client> clients,ArrayList<Topic> topics) {
    	this.clients=clients;
    	this.topics=topics;
//    	this.stats=stats;
    }

    /** constructor
     * @param command type of frame
     */
    public StompFrame(StompCommand command) {
            this.command = command;
    }
    /** constructor
    *
    */
   public StompFrame(ArrayList<Client> clients,StompCommand command,String body,String sessionId, Client client) {
	   this.command=command;
	   this.body=body;
	   this.client=client;
	   this.clients=clients;
   }

    public String toString() {
            return String.format("command: %s, header: %s, body: %s", this.command,
                            this.header.toString(), this.body);
    }
//TODO check the end of message if \0
    /** getBytes convert frame object to array of bytes
     * @return array of bytes
     */
    public byte[] getBytes() {
            String frame = this.command.toString() + '\n';
            for (String key : this.header.keySet()) {
                    frame += key + ":" + this.header.get(key) + '\n';
            }
            frame += '\n';

            if (this.body != null) {
                    frame += this.body;
            }
            frame += "\0";
            return frame.getBytes();
    }
  //TODO check the end of message if \0
    /** getBytes convert frame object to array of bytes
     * @return array of bytes
     */
    public String getString() {
            String frame = this.command.toString() + '\n';
            for (String key : this.header.keySet()) {
                    frame += key + ":" + this.header.get(key) + '\n';
            }
            frame += '\n';

            if (this.body != null) {
                    frame += this.body;
            }
            frame += "\n";
          //  frame += "\0";
            frame += "\u0000";
            return frame;
    }

    /** parse string to frame object
     * @param raw frame as string
     * @return frame object
     */
    public StompFrame(String raw, ArrayList<Client> clients) {
    	this.clients=clients;
            String commandheaderSections = raw.split("\n\n")[0];
            String[] headerLines = commandheaderSections.split("\n");

            this.command = StompCommand.valueOf(headerLines[0]);

            for (int i = 1; i < headerLines.length; i++) {
                    String key = headerLines[i].split(":")[0];
                    this.header.put(key, headerLines[i].substring(key.length() + 1));
            }

            this.body = raw.substring(commandheaderSections.length() + 2);

    }
    
    /** 
     * @return the commend
     */
    public StompCommand getCommend(){
    	return this.command;
    }
    /** 
     * @return the commend as String
     */
    public String getStringCommend(){
    	return this.command.toString();
    }
	/** (non-Javadoc)
	 * @return body
	 */
	public String getBody(){
		return this.body;
	}

	public Map<String, String> getHeader(){
		return this.header;
	}
	public Client getClient(){
		return this.client;
	}
	public ArrayList<Client> getClients(){
		return this.clients;
	}
	public ArrayList<Topic> getTopics(){
		return this.topics;
	}
	/**add headar and body to frame
	 * @param String
	 */
	public void addHeaderAndBody(String raw) {

		String commandheaderSections = raw.split("\n\n")[0];
        String[] headerLines = commandheaderSections.split("\n");

        for (int i = 0; i < headerLines.length; i++) {
                String key = headerLines[i].split(":")[0];
                
                this.header.put(key, headerLines[i].substring(key.length() + 1));
        }

        this.body = raw.substring(commandheaderSections.length() + 2);

	}
	public String getHeader(String msg){
		return this.header.get(msg);
	}

}
