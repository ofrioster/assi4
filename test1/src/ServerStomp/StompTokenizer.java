package ServerStomp;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharacterCodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.StringTokenizer;

import ServerClient.Client;
import ServerClient.Topic;
import ServerStomp.StompCommand;
import ServerStomp.StompFrame;


public class StompTokenizer implements StompTokenizerInterface{
	private final String _messageSeparator;
    private final StringBuffer _stringBuf = new StringBuffer();
    private final CharsetDecoder _decoder;
    private final CharsetEncoder _encoder;
    private ArrayList<Client> clients;
    private ArrayList<Topic> topics;
    
    public StompTokenizer(String separator, Charset charset,ArrayList<Client> clients,ArrayList<Topic> topics) {
        this._messageSeparator = separator;
        this._decoder = charset.newDecoder();
        this._encoder = charset.newEncoder();
        this.clients=clients;
        this.topics=topics;
    }
    
    /**
     * Add some bytes to the message.  
     * Bytes are converted to chars, and appended to the internal StringBuffer.
     * Complete messages can be retrieved using the nextMessage() method.
     *
     * @param bytes an array of bytes to be appended to the message.
     */
    public synchronized void addBytes(ByteBuffer bytes) {
        CharBuffer chars = CharBuffer.allocate(bytes.remaining());
        this._decoder.decode(bytes, chars, false); // false: more bytes may follow. Any unused bytes are kept in the decoder.
        chars.flip();
        this._stringBuf.append(chars);
    }

    /**
     * Is there a complete message ready?.
     *
     * @return true the next call to nextMessage() will not return null, false otherwise.
     */
    public synchronized boolean hasMessage() {
        return this._stringBuf.indexOf(this._messageSeparator) > -1;
    }
 
    /**
     * Get the next complete message if it exists, advancing the tokenizer to the next message.
     * @return the next complete message, and null if no complete message exist.
     */
    public synchronized String nextMessage() {
        String message = null;
        int messageEnd = this._stringBuf.indexOf(this._messageSeparator);
        if (messageEnd > -1) {
            message = this._stringBuf.substring(0, messageEnd);
            this._stringBuf.delete(0, messageEnd+this._messageSeparator.length());
        }
        return message;
    }
 
    /**
     * Convert the String message into bytes representation, taking care of encoding and framing.
     *
     * IGNORE THIS ONE IN TIRGUL 11
     *
     * @return a ByteBuffer with the message content converted to bytes, after framing information has been added.
     */
    public ByteBuffer getBytesForMessage(String msg)  throws CharacterCodingException {
        StringBuilder sb = new StringBuilder(msg);
        sb.append(this._messageSeparator);
        ByteBuffer bb = this._encoder.encode(CharBuffer.wrap(sb));
        return bb;
    }
 
    

	
	/** (non-Javadoc)
	 * @param BufferedReader
	 * @return the stomp frame that receive
	 */
	public StompFrame getFrame(BufferedReader br) {
		System.out.println("getFrame");
		StompFrame frame = null;
		String msg="";
		String message="";
		boolean doneReading=false;
//		do{
			try {
//				while (!br.ready()){}
//				System.out.println("br ready");
			//	while (((msg = br.readLine()) != null) && !msg.equals("\u0000") && !doneReading){
				///********************///
				int msg2=br.read();
				char msg3=(char) msg2;
				while (msg3!= '\u0000'){
					msg="";
					while (msg3!='\n'){
					msg+=msg3;	
					msg2=br.read();
					msg3=(char) msg2;
					}
						
				
				
				//while ((msg2 = br.read()){
					
					//*******************//
				try {

//				System.out.println("read line"+msg);
//				if(br.ready()){
//					msg=br.readLine();
//						System.out.println("read "+msg);
//				}
//				msg=br.readLine();
					System.out.println("read "+msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("some problem");
//				e.printStackTrace();
				}
				message+=msg+"\n";
				if (msg.contains("\u0000")){
//					System.out.println("msg: "+msg);
					doneReading=true;
				}
//			System.out.println("message: "+message);
				
				/**///
				msg2=br.read();
				msg3=(char) msg2;
				//**//
}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
//				e1.printStackTrace();
			}
//		while(!msg.equals("\0"));

        try{
//        	System.out.println("try");
        	frame = new StompFrame(this.clients,this.topics);
        	String commandheaderSections = message.split("\n\n")[0];
            String[] headerLines = commandheaderSections.split("\n");
//System.out.println(headerLines[0]);//TODO delete
//System.out.println(headerLines[1]);//TODO delete
            frame.command = StompCommand.valueOf(headerLines[0]);

            for (int i = 1; i < headerLines.length; i++) {
                    String key = headerLines[i].split(":")[0];
//                    System.out.println("key: "+key);
                    frame.header.put(key, headerLines[i].substring(key.length() + 1));
            }
//            System.out.println("add msg to body");
            frame.body = message.substring(commandheaderSections.length() + 2);
//            System.out.println("finish add msg to body");
        }
        catch(Exception e){
        	frame=null;
        }
			
        System.out.println("finish getFrame");
        return frame;
		/*
		// used for reading
        System.out.println("bufferReader"+ br);
		String raw;
		try {
			System.out.println("here0");
			raw = br.readLine();
			System.out.println("here");
			return this.parse(raw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		*/
		
	}
	//TODO add "^@" in the end of the message
	/** parse string to frame object
     * @param raw frame as string
     * @return frame object
     */
    public StompFrame parse(String raw) {
            StompFrame frame = null;

            try{
            	frame = new StompFrame(this.clients,this.topics);
            	String commandheaderSections = raw.split("\n\n")[0];
                String[] headerLines = commandheaderSections.split("\n");
//System.out.println(headerLines[0]);//TODO delete
//System.out.println(headerLines[1]);//TODO delete
                frame.command = StompCommand.valueOf(headerLines[0]);

                for (int i = 1; i < headerLines.length; i++) {
                        String key = headerLines[i].split(":")[0];
                        frame.header.put(key, headerLines[i].substring(key.length() + 1));
                }

                frame.body = raw.substring(commandheaderSections.length() + 2);
            }
            catch(Exception e){
            	frame=null;
            }
            

            return frame;
    }


}
