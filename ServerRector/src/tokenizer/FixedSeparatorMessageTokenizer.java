package tokenizer;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharacterCodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import rector.ConnectionHandler;
import Client.*;
import Stomp.*;

public class FixedSeparatorMessageTokenizer implements MessageTokenizer<StringMessage> {

   private final String _messageSeparator;

   private final StringBuffer _stringBuf = new StringBuffer();
   /**
	 * the fifo queue, which holds data coming from the socket. Access to the
	 * queue is serialized, to ensure correct processing order.
	 */
	private final Vector<ByteBuffer> _buffers = new Vector<ByteBuffer>();

   private final CharsetDecoder _decoder;
   private final CharsetEncoder _encoder;
   private String lasMessageSend;
   private ArrayList<Client> clients;
   private Client client;
   private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
   private ArrayList<MessageFrame> messageFrameList;
   private Stats stats;
   private ConnectFrame connectFrame;
   private int count;
//   private String allMessages;
   
   
   
   
   
   public FixedSeparatorMessageTokenizer(String separator, Charset charset,ArrayList<Client> clients,Stats stats) {
      this._messageSeparator = separator;

      this._decoder = charset.newDecoder();
      this._encoder = charset.newEncoder();
      this.clients=clients;
      this.stats=stats;
      this.count=0;
      this.messageFrameList=new ArrayList<MessageFrame>();
//      this.allMessages="";
   }

   /**
    * Add some bytes to the message.  
    * Bytes are converted to chars, and appended to the internal StringBuffer.
    * Complete messages can be retrieved using the nextMessage() method.
    *
    * @param bytes an array of bytes to be appended to the message.
    */
   public synchronized void addBytes(ByteBuffer bytes) {
	   _buffers.add(bytes);
      
   }

   /**
    * Is there a complete message ready?.
    * @return true the next call to nextMessage() will not return null, false otherwise.
    */
   public synchronized boolean hasMessage() {
	   while(_buffers.size() > 0) {
           ByteBuffer bytes = _buffers.remove(0);
           CharBuffer chars = CharBuffer.allocate(bytes.remaining());
 	      this._decoder.decode(bytes, chars, false); // false: more bytes may follow. Any unused bytes are kept in the decoder.
 	      chars.flip();
 	      this._stringBuf.append(chars);
	   }
	   return this._stringBuf.indexOf(this._messageSeparator) > -1;
   }

   /**
    * Get the next complete message if it exists, advancing the tokenizer to the next message.
 * @param <T>
    * @return the next complete message, and null if no complete message exist.
    */
   public synchronized StringMessage nextMessage() {
	  StompTokenizer tokenizer=new StompTokenizer("\0",Charset.forName("UTF-8"),this.clients);
      String message = null;
      int messageEnd = this._stringBuf.indexOf(this._messageSeparator);
      if (messageEnd > -1) {
         message = this._stringBuf.substring(0, messageEnd);
         this._stringBuf.delete(0, messageEnd+this._messageSeparator.length());
      }
      this.lasMessageSend=message;
   // parsing raw data to StompFrame format
      StompFrame frame=tokenizer.getFrame(message);
      StompFrame resFrame=this.processMessage(frame);
      if (resFrame!=null){
    	  return new StringMessage(resFrame.getString());
      }
      else {
    	  return null;
      }

   }

   /**
    * Convert the String message into bytes representation, taking care of encoding and framing.
    *
    * @return a ByteBuffer with the message content converted to bytes, after framing information has been added.
    */
   public ByteBuffer getBytesForMessage(StringMessage msg)  throws CharacterCodingException {
      StringBuilder sb = new StringBuilder(msg.getMessage());
      sb.append(this._messageSeparator);
      ByteBuffer bb = this._encoder.encode(CharBuffer.wrap(sb));
      return bb;
   }
   /** (non-Javadoc)
 * @return the message as string
 */
   public String getLastMessageSend(){
	   return this.lasMessageSend;
   }
   public <T> StompFrame processMessage(StompFrame frame){
	   StompFrame resFrame=null;
	   if (frame==null){
		   resFrame=this.error("problam with the message", frame);
       }
       if (this.client!=null && this.client.hasNewMessage()){
    	   resFrame=this.sendNewMessage();
       }
       if(frame.getStringCommend().equals("CONNECT")){
    	   resFrame=this.CONNECT(frame);
       }
       else if(frame.getStringCommend().equals("DISCONNECT")){
    	   resFrame=this.DISCONNECT(frame);
       }
       else if(frame.getStringCommend().equals("SEND")){
    	   resFrame=this.SEND(frame);
       }
       else if(frame.getStringCommend().equals("SUBSCRIBE")){
    	   resFrame=this.SUBSCRIBE(frame);
       }
       else if(frame.getStringCommend().equals("UNSUBSCRIBE")){
    	   resFrame=this.UNSUBSCRIBE(frame);
       }
       else{
    	   resFrame=this.error("Did not contain a destination header, which is required for message propagation.", frame);
       	
       }
       return resFrame;
   }
   public StompFrame CONNECT(StompFrame frame){
	StompFrame resFrame=null;
   	logger.log(Level.INFO, "CONNECT");
   	String userName=frame.getHeader("login");
   	Boolean clientIsLogIn=false;
   	Boolean errorMessageHasBeenSend=false;
   	Boolean newClient=true;
   	for (int i=0; i<this.clients.size(); i++){
   		if (this.clients.get(i).isThisTheClient(userName)){
   			newClient=false;
   			clientIsLogIn=this.clients.get(i).isClientOnLine();
   			if (!this.clients.get(i).isThisIsThePassword(frame.getHeader("passcode"))){
   				resFrame=this.error("Wrong password", frame);
   				errorMessageHasBeenSend=true;
   			}
   			else{
   				this.client=this.clients.get(i);
   			}
   		}
   	}
   	if(newClient){
   		try{
   			this.client=new Client(frame, clients,this.stats);
   		//	this.client=this.connectFrame.getClient();
   			StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, "CONNECTED");
   			resFrame=receiptFramConnectFrameToSend;
   			this.client.setClienLastAction("connected");
   		}
   		catch (Exception e){
   			resFrame=this.error("cant add new client", frame);
   		}
   	}
   	else if (clientIsLogIn && !errorMessageHasBeenSend){
   		resFrame=this.error("User is already logged in", frame);
   	}
   	else if (!errorMessageHasBeenSend){
   		this.connectFrame=new ConnectFrame(frame,frame.getCommend());
        this.client=this.connectFrame.getClient();
        StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, "CONNECTED");
        resFrame=receiptFramConnectFrameToSend;
        
        
   	}
   	this.client.setClientIsOnline(true);
   	return resFrame;
   }
   /** disconnect the connection and send message to the client
    * @param frame
    */
   public StompFrame DISCONNECT(StompFrame frame){
   	logger.log(Level.INFO, "DISCONNECT");
   	StompFrame resFrame=null;
 //  	this.disconnectFrame=new DisconnectFrame(frame, frame.command);
    StompFrame receiptFramDisconnectFrameToSend=new ReceiptFram(frame, "DISCONNECT");
    resFrame=receiptFramDisconnectFrameToSend;
    this.client.setClienLastAction("disconnected");
    this.client.setClientIsOnline(false);
    return resFrame;
    
   }
   /**take care of SEND message from the client
    * @param frame
    */
   public StompFrame SEND(StompFrame frame){
   	logger.log(Level.INFO, "send message");
   	StompFrame resFrame=null;
  /* 	MessageFrame messageFrame=new MessageFrame(frame,this.stats);
    this.messageFrameList.add(messageFrame);
    this.client.addNewMessage(messageFrame);
    return resFrame;
    */   
    /////******/////
      
//	logger.log(Level.INFO, "send message");
	String messageId=""+this.count;
	this.count++;
	String subscription=""+this.count;
	this.count++;
	MessageFrame messageFrame=new MessageFrame(frame,this.stats,messageId,"00"+subscription );
    this.messageFrameList.add(messageFrame);
    if (messageFrame.getHeader("destination").equals("/topic/server")){
    	if (messageFrame.getBody().contains("clients")){
    		this.printClients();
    	}
    	
    	else if( messageFrame.getBody().contains("stats")){
    		this.stats.updateStats(this.client);
    		StompFrame res=new MessageFrame(clients, this.stats.toStringForFrame(), stats,"server");
    		resFrame=res;
    	}
//    	String tempRes=messageFrame.getBody();
//    	System.out.println("body:"+tempRes+"-end");
    }
    else{
    	
    	this.client.setClienLastAction("tweet");
    	this.client.addNewMessageThatSendByThis(messageFrame);
    	this.client.setClienLastAction("tweet");
    	resFrame=messageFrame;
    }

     ////******////
    return resFrame;
   }
   /** SUBSCRIBE the new client
    * @param frame
    */
   public StompFrame SUBSCRIBE(StompFrame frame){
   	logger.log(Level.INFO, "SUBSCRIBE user:"+frame.getHeader("id"));
   	StompFrame resFrame=null;
   	Client newClient=null;
   	String tempUserName=frame.getHeader("destination");
   	tempUserName=tempUserName.substring(7, tempUserName.length());
   	String clientName=tempUserName;
//	System.out.println("looking for: "+tempUserName);
   	Boolean found=false;
   	for (int i=0;i<this.clients.size();i++){
   		if (this.clients.get(i).getClientUserName().equals(clientName)){
   			newClient=this.clients.get(i);
   			found=true;
   			if (this.clients.get(i).isClientFollowingClient(frame.getHeader("id"))){
   				resFrame=this.error("Already following username:", frame);
   			}
   			else{
   				this.client.addClientToFollow(frame.getHeader("id"), newClient);
				this.client.setClienLastAction("follow "+clientName);
				String messageId=""+this.count;
				this.count++;
	        	String subscription=frame.getHeader("id");
	    //    	this.count++;
				StompFrame resMessage=new MessageFrame(frame,clients, "following " +clientName, this.client.getClientUserName(),messageId,"00"+subscription);
				resFrame=(resMessage);
				return resFrame;
   			}
   		}
   	}
   	if (!found){
   		resFrame=this.error("Wrong username", frame,"Wrong username");
   	}
   	return resFrame;
   }
   

/**UNSUBSCRIBE
* @param frame
*/
   public StompFrame UNSUBSCRIBE(StompFrame frame){
   	logger.log(Level.INFO, "UNSUBSCRIBE user:"+frame.getHeader("id"));
   	StompFrame resFrame=null;
   	String res=this.client.removeFollowingClient(frame.getHeader("id"));
   	if (!res.substring(0, 1).equals("1")){
   		resFrame=this.error(res, frame);
   	}
   	this.client.setClienLastAction("unfollow "+res.substring(1, res.length()));
	String messageId=""+this.count;
	this.count++;
	String subscription=""+this.count;
	this.count++;
	StompFrame resMessage=new MessageFrame(frame,clients, "unfollowing " +res.substring(1, res.length()),this.client.getClientUserName(),messageId,"00"+subscription);
   	return resMessage;
   }

/**
 * send the message that receive for this client
 */
   public StompFrame sendNewMessage(){
   	logger.log(Level.INFO, "sending message");
   	StompFrame resFrame=null;
   	if (this.client.hasNewMessage()){
   		resFrame=(this.client.getNextMessage());
   		
   	}
   	return resFrame;
   }
   /**sent to client an error frame
    * @param String whatIsTheProblam
    * @param StompFrame frame
    */
   public StompFrame error(String whatIsTheProblam, StompFrame frame){
   	StompFrame res=new ErorFrame(clients, whatIsTheProblam, frame);
   	logger.log(Level.INFO, "EROR1 " + res.getString());
   	return(res);
   }
   /**sent to client an error frame
    * @param String whatIsTheProblam
    * @param StompFrame frame
    */
   public StompFrame error(String whatIsTheProblam, StompFrame frame,String descriptionOfTheProblem){
   	StompFrame res=new ErorFrame(clients, whatIsTheProblam, frame,descriptionOfTheProblem);
   	logger.log(Level.INFO, "EROR1 " + res.getString());
   	return(res);
   }
   /**sent to client an error frame
    * @param String whatIsTheProblam
    * @param String msg that receive
    */
   public StompFrame error(String whatIsTheProblam, String msg){
   	logger.log(Level.INFO, "EROR " + msg+" has been recieva");
   	StompFrame res=new ErorFrame(clients, whatIsTheProblam, msg);
   	return(res);
   }
   /**send a message to the client of the all clients status
    * 
    */
   public void printClients(){
   	try{
   		String msg="";
       	String msg2="";
       	for (int i=0;i<this.clients.size();i++){
       		if (this.clients.get(i).isClientOnLine()){
       			msg=this.clients.get(i).getClientUserName()+" last action was: "+this.clients.get(i).getClienLastAction()+"\n";
           		System.out.println(msg);
           		msg2+=msg;
       		}
       	}
       	MessageFrame res=new MessageFrame(clients, msg2, stats,"server");
       	for (int i=0;i<this.clients.size();i++){
       		if (this.clients.get(i).isThisTheClient("server")){
       			this.clients.get(i).sendClientsMessage(res);
       		}
       	}
   	}
   	catch (Exception e){
   		System.out.println("something worg::: "+e.getMessage());
   	
   	}
 //  	this.send(res);
   }

@Override
public <T> void setConnectionHandler(ConnectionHandler<T> _handler) {
	this.client.setConnectionHandler(_handler);
	
}

}