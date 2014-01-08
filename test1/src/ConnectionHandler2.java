import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import stomp.StompException;



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
            this.tokenizer=new StompTokenizer("\n",Charset.forName("UTF-8"),this.clients,this.topics);
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


            while ((msg = in.readLine()) != null)
            {
  //              System.out.println("Received \"" + msg + "\" from client");
                logger.log(Level.INFO, "Received \"" + msg + "\" from client");
                // parsing raw data to StompFrame format
                StompFrame frame=this.tokenizer.getFrame(in);
                
                if (this.client.hasNewMessage()){
                	this.sendNewMessage();
                }
                // run handlers
                switch (frame.command) {
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
                }
                
                
                
                
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
        	String userName=frame.header.get("login:");
        	Boolean clientIsLogIn=false;
        	Boolean messageHasBeenSend=false;
        	for (int i=0; i<this.clients.size(); i++){
        		if (this.clients.get(i).isThisTheClient(userName)){
        			clientIsLogIn=this.clients.get(i).isClientOnLine();
        			if (!this.clients.get(i).isThisIsThePassword(frame.header.get("passcode:"))){
        				this.error("Wrong password", frame);
        				messageHasBeenSend=true;
        			}
        		}
        	}
        	if (clientIsLogIn && !messageHasBeenSend){
        		this.error("User is already logged in", frame);
        	}
        	if (!messageHasBeenSend){
        		this.connectFrame=new ConnectFrame(frame,frame.getCommend());
                this.client=this.connectFrame.getClient();
                StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, StompCommand.valueOf("CONNECTED"));
                this.send(receiptFramConnectFrameToSend);
        	}
        }
        public void DISCONNECT(StompFrame frame){
        	logger.log(Level.INFO, "DISCONNECT");
        	this.disconnectFrame=new DisconnectFrame(frame, frame.command);
            StompFrame receiptFramDisconnectFrameToSend=new ReceiptFram(frame, StompCommand.valueOf("DISCONNECT"));
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
        	logger.log(Level.INFO, "SUBSCRIBE user:"+frame.header.get("id:"));
        	Client newClient=null;
        	String clientName=frame.header.get("destination:");
        	Boolean found=false;
        	for (int i=0;i<this.clients.size();i++){
        		if (this.clients.get(i).getClientUserName().equals(clientName)){
        			newClient=this.clients.get(i);
        			found=true;
        			if (this.clients.get(i).isClientFollowingClient(frame.header.get("id:"))){
        				this.error("Already following username:", frame);
        			}
        			else{
        				this.client.addClientToFollow(frame.header.get("id:"), newClient);
        			}
        		}
        	}
        	if (!found){
        		this.error("Wrong username", frame);
        	}
        }
        public void UNSUBSCRIBE(StompFrame frame){
        	logger.log(Level.INFO, "UNSUBSCRIBE user:"+frame.header.get("id:"));
        	String res=this.client.removeFollowingClient(frame.header.get("id:"));
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
        	logger.log(Level.INFO, "EROR" + res.getString());
        	this.send(res);
        }
        /**sent to client an error frame
         * @param String whatIsTheProblam
         * @param String msg that receive
         */
        public void error(String whatIsTheProblam, String msg){
        	logger.log(Level.INFO, "EROR" + msg);
        	StompFrame res=new ErorFrame(clients, topics, whatIsTheProblam, msg);
        	this.send(res);
        }
        
}
