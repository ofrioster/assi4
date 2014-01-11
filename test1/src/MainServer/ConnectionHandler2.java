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
        ByteBuffer inbuf;
        
        
        
        public ConnectionHandler2(Socket acceptedSocket, ServerProtocol p,ArrayList<Client> clients,ArrayList<Topic> topics)
        {
            in = null;
            out = null;
            clientSocket = acceptedSocket;
            protocol = p;
            this.clients=clients;
            this.topics=topics;
            this.messageFrameList=new ArrayList<MessageFrame>();
            logger.log(Level.INFO, "Accepted connection from client!");
            logger.log(Level.INFO, "The client is from: " + acceptedSocket.getInetAddress() + ":" + acceptedSocket.getPort());
//            System.out.println("Accepted connection from client!");
//            System.out.println("The client is from: " + acceptedSocket.getInetAddress() + ":" + acceptedSocket.getPort());
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
  //             System.out.println("Error in initializing I/O");
                logger.log(Level.INFO, "Error in initializing I/O");
            }
     
            try {
                process();
            } 
            catch (IOException e) {
 //               System.out.println("Error in I/O");
                logger.log(Level.INFO, "Error in I/O");
            } 
            
    //        System.out.println("Connection closed - bye bye...");
            logger.log(Level.INFO, "Connection closed - bye bye...");
            close();
     
        }
        
        public void process() throws IOException
        {
            String msg;


            while (true)
            {
            	/*while (!tokenizer.hasMessage()) {
            		System.out.println("Trying to read until we have a massege");
            		 inbuf.clear();
                     inbuf.put(in.readLine().getBytes());
                     inbuf.flip();
            		String temp=in.readLine();
            		msg+=temp;
            		inbuf.clear();
                    inbuf.put(temp.getBytes());
                    inbuf.flip();
                    tokenizer.addBytes(inbuf);
                    try {
                        //Sleep because we want to give a chance to read several bytes from the channel
                        System.out.println("Going to sleep , give a chance to read some bytes");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
            	}*/
           //     System.out.println("Received " + msg + " tokenzer: "+this.tokenizer.nextMessage());
//                logger.log(Level.INFO, "Received \"" + msg + "\" from client");
                // parsing raw data to StompFrame format
                StompFrame frame=this.tokenizer.getFrame(in);
//                StompFrame frame=new StompFrame(tokenizer.nextMessage(), clients);
//                System.out.println("here");
                if (frame==null){
                	this.error("problam with the message", frame);
                }
                if (this.client!=null && this.client.hasNewMessage()){
                	this.sendNewMessage();
                }
                // run handlers
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
                	this.error("Did not contain a destination header, which is required for message propagation.", frame);
                	
                }
            /*    switch (frame.command) {
                        case CONNECT:

                                this.CONNECT(frame);
                                break;
                        case DISCONNECT:
                                this.DISCONNECT(frame);
                                break;
                        case SEND:
                            this.SEND(frame);
                            break;
                        case SUBSCRIBE:
                            this.SUBSCRIBE(frame);
                            break;
                        case UNSUBSCRIBE:
                                this.UNSUBSCRIBE(frame);
                                break;
                        default:   
                        	this.error("Did not contain a destination header, which is required for message propagation.", frame);
                                break;
                }*/
                
                
                
                
            }
        }
        
        // Starts listening
        public void initialize() throws IOException
        {
            // Initialize I/O
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"), true);
  //          System.out.println("I/O initialized");
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
 //               System.out.println("Exception in closing I/O");
                logger.log(Level.INFO, "Exception in closing I/O");
            }
        }
        /**
         * send - help function for sending any frame to STOMP server
         * @param frame
         * @throws StompException
         */
        /*
        private synchronized void send(StompFrame frame) throws StompExceptionn {
        	out.println(frame.getString());
                try {
                        clientSocket.getOutputStream().write(frame.getBytes());
                } catch (IOException e) {
                        StompException ex = new StompException("Problem with sending frame");
                        ex.initCause(e);
                        throw ex;
                }
        }*/
        /**
         * @param frame to send
         */
        public void send(StompFrame frame) {
        	///***old***//
            String msg=frame.getString();
            String response = protocol.processMessage(msg);
            if (response != null)
            {
                out.println(response);
            }
            //TODO is needed?
            /*
            if (protocol.isEnd(msg))
            {
                break;
            }
*/
        }
        public void CONNECT(StompFrame frame){
        	logger.log(Level.INFO, "CONNECT");
        	String userName=frame.getHeader("login:");
        	Boolean clientIsLogIn=false;
        	Boolean errorMessageHasBeenSend=false;
        	Boolean newClient=true;
        	for (int i=0; i<this.clients.size(); i++){
        		if (this.clients.get(i).isThisTheClient(userName)){
        			newClient=false;
        			clientIsLogIn=this.clients.get(i).isClientOnLine();
        			if (!this.clients.get(i).isThisIsThePassword(frame.getHeader("passcode:"))){
        				this.error("Wrong password", frame);
        				errorMessageHasBeenSend=true;
        			}
        			else{
        				this.client=this.clients.get(i);
        			}
        		}
        	}
        	if(newClient){
        		try{
        			this.client=new Client(frame, clients);
        		//	this.client=this.connectFrame.getClient();
        			StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, "CONNECTED");
                    this.send(receiptFramConnectFrameToSend);
        		}
        		catch (Exception e){
        			this.error("cant add new client", frame);
        		}
        	}
        	else if (clientIsLogIn && !errorMessageHasBeenSend){
        		this.error("User is already logged in", frame);
        	}
        	else if (!errorMessageHasBeenSend){
        	//	this.connectFrame=new ConnectFrame(frame,frame.getCommend());
                this.client=this.connectFrame.getClient();
                StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, "CONNECTED");
                this.send(receiptFramConnectFrameToSend);
        	}
        }
        public void DISCONNECT(StompFrame frame){
        	logger.log(Level.INFO, "DISCONNECT");
        	this.disconnectFrame=new DisconnectFrame(frame, frame.command);
            StompFrame receiptFramDisconnectFrameToSend=new ReceiptFram(frame, "DISCONNECT");
            this.send(receiptFramDisconnectFrameToSend);
            this.close();
        }
        public void SEND(StompFrame frame){
        	logger.log(Level.INFO, "send message");
        	MessageFrame messageFrame=new MessageFrame(frame);
            this.messageFrameList.add(messageFrame);
            this.client.addNewMessage(messageFrame);
            
        }
        public void SUBSCRIBE(StompFrame frame){
        	logger.log(Level.INFO, "SUBSCRIBE user:"+frame.getHeader("id:"));
        	Client newClient=null;
        	String clientName=frame.getHeader("destination:");
        	Boolean found=false;
        	for (int i=0;i<this.clients.size();i++){
        		if (this.clients.get(i).getClientUserName().equals(clientName)){
        			newClient=this.clients.get(i);
        			found=true;
        			if (this.clients.get(i).isClientFollowingClient(frame.getHeader("id:"))){
        				this.error("Already following username:", frame);
        			}
        			else{
        				this.client.addClientToFollow(frame.getHeader("id:"), newClient);
        			}
        		}
        	}
        	if (!found){
        		this.error("Wrong username", frame);
        	}
        }
        public void UNSUBSCRIBE(StompFrame frame){
        	logger.log(Level.INFO, "UNSUBSCRIBE user:"+frame.getHeader("id:"));
        	String res=this.client.removeFollowingClient(frame.getHeader("id:"));
        	if (res!=null){
        		this.error(res, frame);
        	}
        }
        public void sendNewMessage(){
        	logger.log(Level.INFO, "sending message");
        	if (this.client.hasNewMessage()){
        		this.send(this.client.getNextMessage());
        		
        	}
        }
        /**sent to client an error frame
         * @param String whatIsTheProblam
         * @param StompFrame frame
         */
        public void error(String whatIsTheProblam, StompFrame frame){
        	StompFrame res=new ErorFrame(clients, topics, whatIsTheProblam, frame);
        	logger.log(Level.INFO, "EROR1 " + res.getString());
        	this.send(res);
        }
        /**sent to client an error frame
         * @param String whatIsTheProblam
         * @param String msg that receive
         */
        public void error(String whatIsTheProblam, String msg){
        	logger.log(Level.INFO, "EROR " + msg+" has been recieva");
        	StompFrame res=new ErorFrame(clients, topics, whatIsTheProblam, msg);
        	this.send(res);
        }
        
}
