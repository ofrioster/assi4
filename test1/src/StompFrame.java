import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class  StompFrame implements StompFrameInterface{
	
	protected StompCommand command;
    protected Map<String, String> header = new HashMap<String, String>();
    protected String body;
    protected String sessionId;
    protected Client client;
    protected Socket socket;
    protected ArrayList<Client> clients;

    /** constructor
     *
     */
    public StompFrame() {
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
	   this.sessionId=sessionId;
	   this.clients=clients;
   }

    public String toString() {
            return String.format("command: %s, header: %s, body: %s", this.command,
                            this.header.toString(), this.body);
    }

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

    /** parse string to frame object
     * @param raw frame as string
     * @return frame object
     */
    public StompFrame(String raw, ArrayList<Client> clients,Socket socket) {
    	this.clients=clients;
    	this.socket=socket;
     //       StompFrame frame = new StompFrame();

            String commandheaderSections = raw.split("\n\n")[0];
            String[] headerLines = commandheaderSections.split("\n");

            this.command = StompCommand.valueOf(headerLines[0]);

            for (int i = 1; i < headerLines.length; i++) {
                    String key = headerLines[i].split(":")[0];
                    this.header.put(key, headerLines[i].substring(key.length() + 1));
            }

            this.body = raw.substring(commandheaderSections.length() + 2);

   //         return frame;
    }
    
    /** 
     * @return the commend
     */
    public StompCommand getCommend(){
    	return this.command;
    }
	/** (non-Javadoc)
	 * @return body
	 */
	public String getBody(){
		return this.body;
	}
	/** (non-Javadoc)
	 * @return SessionId
	 */
	public String getSessionId(){
		return this.sessionId;
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
	public Socket getSocket(){
		return this.socket;
	}

}
