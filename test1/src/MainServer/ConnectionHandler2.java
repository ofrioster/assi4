package MainServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import ServerClient.*;
import ServerStomp.*;





public class ConnectionHandler2 implements Runnable{
        
        private BufferedReader in;
        private PrintWriter out;
        private Socket clientSocket;
        private ServerProtocol protocol;
        private ConnectFrame connectFrame;
        private DisconnectFrame disconnectFrame;
        private ArrayList<MessageFrame> messageFrameList;
        private ArrayList<Client> clients;
        private Client client;
        private StompTokenizer tokenizer;
        private ArrayList<Topic> topics;
        private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        private ByteBuffer inbuf;
        private Stats stats;
        private boolean keepGoing;
        private int count;
        
        
        
        
        public ConnectionHandler2(Socket acceptedSocket, ServerProtocol p,ArrayList<Client> clients,ArrayList<Topic> topics,Stats stats)
        {
            in = null;
            out = null;
            clientSocket = acceptedSocket;
            protocol = p;
            this.clients=clients;
            this.topics=topics;
            this.messageFrameList=new ArrayList<MessageFrame>();
            this.stats=stats;
            this.keepGoing=true;
            this.count=0;
            logger.log(Level.INFO, "Accepted connection from client!");
            logger.log(Level.INFO, "The client is from: " + acceptedSocket.getInetAddress() + ":" + acceptedSocket.getPort());
            this.tokenizer=new StompTokenizer("\0",Charset.forName("UTF-8"),this.clients,this.topics);
            final int NUM_OF_BYTES = 1024;
            this.inbuf = ByteBuffer.allocate(NUM_OF_BYTES); 
        }
        
        public void run()
        {
     
            String msg;
            
            try {
                initialize();
            }
            catch (IOException e) {
                logger.log(Level.INFO, "Error in initializing I/O");
            }
     
            try {
                process();
            } 
            catch (IOException e) {
                logger.log(Level.INFO, "Error in I/O");
            } 
            
            logger.log(Level.INFO, "Connection closed - bye bye...");
            close();
     
        }
        
        public void process() throws IOException
        {
            String msg;


            while (this.keepGoing)
            {
            	if(this.clientSocket.isClosed()){
            		this.keepGoing=false;
            	}
            	if (in!=null && this.keepGoing){
                   // parsing raw data to StompFrame format
                StompFrame frame=this.tokenizer.getFrame(in);
                if (frame==null){
                	this.error("problam with the message", frame,"malformed STOMP frame received");
                }
                if (this.client!=null && this.client.hasNewMessage()){
                	this.sendNewMessage();
                }
                // run handlers
                try{
                	 if(frame.getStringCommend().equals("CONNECT")){
                     	this.CONNECT(frame);
                     }
                     else if(frame.getStringCommend().equals("DISCONNECT")){
                     	this.DISCONNECT(frame);
                     }
                     else if(frame.getStringCommend().equals("SEND")){
                     	this.SEND(frame);
                     }
                     else if(frame.getStringCommend().equals("SUBSCRIBE")){
                     	this.SUBSCRIBE(frame);
                     }
                     else if(frame.getStringCommend().equals("UNSUBSCRIBE")){
                     	 this.UNSUBSCRIBE(frame);
                     }
                     else{
                     	this.error("malformed STOMP frame received", frame,"Did not contain a destination header, which is required for message propagation.");
                     	
                     }
                }
                catch (Exception e){
                	if (!this.clientSocket.isBound()){
                		this.keepGoing=false;
                		this.close();
                	}
                	else if (this.clientSocket.isClosed()){
                		this.keepGoing=false;
                	}            
                	else{
                		this.error("malformed STOMP frame received", frame,"worng command");
                	}
                }
                            
            }
        }
        }
        // Starts listening
        public void initialize() throws IOException
        {
            // Initialize I/O
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"), true);
            logger.log(Level.INFO, "I/O initialized");
        }
        
        // Closes the connection
        public void close()
        {
            try {
                if (in != null)
                {
                    in.close();
                }
                if (out != null)
                {
                    out.close();
                }
                
                clientSocket.close();
            }
            catch (IOException e)
            {
                logger.log(Level.INFO, "Exception in closing I/O");
            }
        }
       
        /**
         * @param frame to send
         */
        public void send(StompFrame frame) {
        	try{
        		String msg=frame.getString();
                out.println(msg);
                System.out.print("msg send:"+msg);
                System.out.println("---msg end");
        	}
        	 catch ( Exception e){
        	 }
   
        }
        public void CONNECT(StompFrame frame){
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
        				this.error("Wrong password", frame,"Wrong password");
        				errorMessageHasBeenSend=true;
        				newClient=false;
        				this.keepGoing=false;
        				for (int k=0; k<1000;k++){}
         				this.close();
        			}
        			else{
        				this.client=this.clients.get(i);
        			}
        		}
        	}
        	if(newClient){
        		try{
        			this.client=new Client(frame, clients,this.stats,this);
        			StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, "CONNECTED");
                    this.send(receiptFramConnectFrameToSend);
                    this.client.setClienLastAction("connected");
        		}
        		catch (Exception e){
        			this.error("cant add new client", frame,"cant add new client");
        		}
        	}
        	else if (clientIsLogIn && !errorMessageHasBeenSend){
        		this.error("User is already logged in", frame,"User is already logged in");
        	}
        	else if (!errorMessageHasBeenSend){
                StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, "CONNECTED");
                this.send(receiptFramConnectFrameToSend);
        	}
        	this.client.setClientIsOnline(true);
        }
        /** disconnect the connection and send message to the client
         * @param frame
         */
        public void DISCONNECT(StompFrame frame){
        	logger.log(Level.INFO, "DISCONNECT");
            StompFrame receiptFramDisconnectFrameToSend=new ReceiptFram(frame, "DISCONNECT");
            this.send(receiptFramDisconnectFrameToSend);
            this.client.setClienLastAction("disconnected");
            this.client.setClientIsOnline(false);
            this.keepGoing=false;
            this.close();
        }
        /**take care of SEND message from the client
         * @param frame
         */
        public void SEND(StompFrame frame){
        	logger.log(Level.INFO, "send message");
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
            		StompFrame res=new MessageFrame(clients, topics, this.stats.toStringForFrame(), stats,"server");
                	this.send(res);
            	}
            }
            else{
            	
            	this.client.setClienLastAction("tweet");
            	this.client.addNewMessageThatSendByThis(messageFrame);
            	this.client.setClienLastAction("tweet");
            	this.send(messageFrame);
            }
            
            
        }
        /** SUBSCRIBE the new client
         * @param frame
         */
        public void SUBSCRIBE(StompFrame frame){
        	logger.log(Level.INFO, "SUBSCRIBE user:"+frame.getHeader("destination"));
        	Client newClient=null;
        	String tempUserName=frame.getHeader("destination");
    		tempUserName=tempUserName.substring(7, tempUserName.length());
        	String clientName=tempUserName;
        	System.out.println("looking for: "+tempUserName);
        	Boolean found=false;
        	for (int i=0;i<this.clients.size();i++){
        		if (this.clients.get(i).getClientUserName().equals(clientName)){
        			newClient=this.clients.get(i);
        			found=true;
        			if (this.clients.get(i).isClientFollowingClient(frame.getHeader("id"))){
        				this.error("Already following username:", frame,"Already following username");
        			}
        			else{
        				this.client.addClientToFollow(frame.getHeader("id"), newClient);
        				this.client.setClienLastAction("follow "+clientName);
        				String messageId=""+this.count;
        				this.count++;
        	        	String subscription=frame.getHeader("id");
        				StompFrame resMessage=new MessageFrame(frame,clients, topics, "following " +clientName, this.client.getClientUserName(),messageId,"00"+subscription);
        				this.send(resMessage);
        				return;
        			}
        		}
        	}
        	if (!found){
        		this.error("Wrong username", frame,"Wrong username");
        	}
        }
        /**UNSUBSCRIBE
         * @param frame
         */
        public void UNSUBSCRIBE(StompFrame frame){
        	logger.log(Level.INFO, "UNSUBSCRIBE user:"+frame.getHeader("id"));
        	String res=this.client.removeFollowingClient(frame.getHeader("id"));
        	if (!res.substring(0, 1).equals("1")){
        		this.error(res, frame,res);
        	}
        	this.client.setClienLastAction("unfollow "+res.substring(1, res.length()));
        	String messageId=""+this.count;
        	this.count++;
        	String subscription=""+this.count;
        	this.count++;
        	StompFrame resMessage=new MessageFrame(frame,clients, topics, "unfollowing " +res.substring(1, res.length()),this.client.getClientUserName(),messageId,"00"+subscription);
			this.send(resMessage);
        }
        /**
         * send the message that receive for this client
         */
        public synchronized void sendNewMessage(){
        	logger.log(Level.INFO, "sending message");
        	while (this.client.hasNewMessage()){
        		this.send(this.client.getNextMessage());
        		
        	}
        	
        }
        /**sent to client an error frame
         * @param String whatIsTheProblam
         * @param StompFrame frame
         */
        public void error(String whatIsTheProblam, StompFrame frame,String descriptionOfTheProblem){
        	StompFrame res=new ErorFrame(clients, topics, whatIsTheProblam, frame,descriptionOfTheProblem);
        	logger.log(Level.INFO, "EROR1 " + res.getString());
        	this.send(res);
        }
        /**sent to client an error frame
         * @param String whatIsTheProblam
         * @param String msg that receive
         */
        public void error(String whatIsTheProblam, String msg,String descriptionOfTheProblem){
        	logger.log(Level.INFO, "EROR " + msg+" has been recieva");
        	StompFrame res=new ErorFrame(clients, topics, whatIsTheProblam, msg,descriptionOfTheProblem);
        	this.send(res);
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
            	MessageFrame res=new MessageFrame(clients, topics, msg2, stats,"server");
            	for (int i=0;i<this.clients.size();i++){
            		if (this.clients.get(i).isThisTheClient("server")){
            			this.clients.get(i).sendClientsMessage(res);
            		}
            	}
        	}
        	catch (Exception e){
        		System.out.println("something worg::: "+e.getMessage());
        	
        	}
        }
        /**
         * Disconnect all client
         * after STOP message arrive
         */
        public void stop(){
        	StompFrame frame=new ReceiptFram( "DISCONNECT","1.2",this.topics,this.clients,"001");
        	try{
        		for (int i=0; i<this.clients.size();i++){
            		if (!this.clients.get(i).isThisTheClient("server")){
            			if (this.clients.get(i).isClientOnLine()){
            				this.clients.get(i).stop(frame);
            			}
            		}
            	}
        	}
        	catch (Exception e){}
        	
        }
        /** disconnect the connection and send message to the client
         * after STOP message arrive
         * @param frame
         */
        public void stopDisconnect(StompFrame frame){
        	try{
            	logger.log(Level.INFO, "DISCONNECT");
                this.send(frame);
                this.client.setClienLastAction("disconnected");
                this.client.setClientIsOnline(false);
                this.keepGoing=false;
                this.close();
        	}
        	catch (Exception e){
        		
        	}

        }
}
