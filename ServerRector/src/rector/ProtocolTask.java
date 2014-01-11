package rector;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;


import Stomp.*;
import Client.*;
import protocol.*;
import tokenizer.*;

/**
 * This class supplies some data to the protocol, which then processes the data,
 * possibly returning a reply. This class is implemented as an executor task.
 * 
 */
public class ProtocolTask<T> implements Runnable {

	private final ServerProtocol<T> _protocol;
	private final MessageTokenizer<T> _tokenizer;
	private final ConnectionHandler<T> _handler;
	private ArrayList<Client> clients;

	public ProtocolTask(final ServerProtocol<T> protocol, final MessageTokenizer<T> tokenizer, final ConnectionHandler<T> h,ArrayList<Client> clients) {
		this._protocol = protocol;
		this._tokenizer = tokenizer;
		this._handler = h;
		this.clients=clients;
	}

	// we synchronize on ourselves, in case we are executed by several threads
	// from the thread pool.
	public synchronized void run() {
      // go over all complete messages and process them.
      while (_tokenizer.hasMessage()) {
         T msg = _tokenizer.nextMessage();
         T response = this._protocol.processMessage(msg);
         if (response != null) {
            try {
               ByteBuffer bytes = _tokenizer.getBytesForMessage(response);
               this._handler.addOutData(bytes);
            } catch (CharacterCodingException e) { e.printStackTrace(); }
         }
      }
	}

	public void addBytes(ByteBuffer b) {
		_tokenizer.addBytes(b);
	}
	/** (non-Javadoc)
	 * @param BufferedReader
	 * @return the stomp frame that receive
	 */
	public StompFrame getFrame(BufferedReader br) {
		StompFrame frame = null;
		String msg="";
		String message="";
		do{
			try {
				msg=br.readLine();
//				System.out.println("read "+msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message+=msg+"\n";
//			System.out.println("message: "+message);
		}
		while(!msg.equals("\0"));

        try{
//        	System.out.println("try");
        	frame = new StompFrame(this.clients);
        	String commandheaderSections = message.split("\n\n")[0];
            String[] headerLines = commandheaderSections.split("\n");
//System.out.println(headerLines[0]);//TODO delete
//System.out.println(headerLines[1]);//TODO delete
            frame.commandAdd(headerLines[0]);

            for (int i = 1; i < headerLines.length; i++) {
                    String key = headerLines[i].split(":")[0];
                    frame.headerPut(key, headerLines[i].substring(key.length() + 1));
            }

            frame.bodyAdd(message.substring(commandheaderSections.length() + 2));
        }
        catch(Exception e){
        	frame=null;
        }
        

        return frame;
	}
}