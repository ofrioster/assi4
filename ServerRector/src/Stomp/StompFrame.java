package Stomp;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tokenizer.StringMessage;
import Client.*;





public class  StompFrame implements StompFrameInterface{
	
	protected StompCommand command;
    protected Map<String, String> header = new HashMap<String, String>();
    protected String body;
    protected Client client;
    protected ArrayList<Client> clients;
    private Charset charset;

    /** constructor
     *
     */
    public StompFrame(ArrayList<Client> clients) {
    	this.clients=clients;
    	this.charset = Charset.forName("UTF-8");
    }

    /** constructor
     * @param command type of frame
     */
    public StompFrame(StompCommand command) {
            this.command = command;
            this.charset = Charset.forName("UTF-8");
    }
    /** constructor
    *
    */
   public StompFrame(ArrayList<Client> clients,StompCommand command,String body,String sessionId, Client client) {
	   this.command=command;
	   this.body=body;
	   this.client=client;
	   this.clients=clients;
	   this.charset = Charset.forName("UTF-8");
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

	/**add headar and body to frame
	 * @param String
	 */
	public void addHeaderAndBody(String raw) {

        String[] headerLines = raw.split("\n");

        for (int i = 1; i < headerLines.length; i++) {
                String key = headerLines[i].split(":")[0];
                this.header.put(key, headerLines[i].substring(key.length() + 1));
        }

        this.body = raw.substring(raw.length() + 2);

	}
	public String getHeader(String msg){
		return this.header.get(msg);
	}
	/** add to this client header
	 * @param String the subject
	 * @param the String for this subject 
	 */
	public synchronized void headerPut(String subject,String msg){
		this.header.put(subject, msg);
	}
	/**
	 * @param msg
	 */
	public synchronized void bodyAdd(String msg){
		this.body=msg;
	}
	/**add command as String
	 * @param String command
	 */
	public void commandAdd(String command){
		this.command=StompCommand.valueOf(command);
	}
	/** 
     * @return the commend as String
     */
    public String getStringCommend(){
    	return this.command.toString();
    }
    /**
     * Convert the stomp message into bytes representation, taking care of encoding and framing.
     *
     * @return a ByteBuffer with the message content converted to bytes, after framing information has been added.
     */
    public ByteBuffer getBytesForMessage()  throws CharacterCodingException {
    	String msg=this.getString();
    	CharsetEncoder _encoder= this.charset.newEncoder();
       StringBuilder sb = new StringBuilder(msg);
       ByteBuffer bb = _encoder.encode(CharBuffer.wrap(msg));
       return bb;
    }

}
