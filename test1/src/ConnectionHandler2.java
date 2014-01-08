import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;


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
        
        
        
        public ConnectionHandler2(Socket acceptedSocket, ServerProtocol p,ArrayList<Client> clients,ArrayList<Topic> topics)
        {
            in = null;
            out = null;
            clientSocket = acceptedSocket;
            protocol = p;
            this.clients=clients;
            this.topics=topics;
            this.messageFrameList=new ArrayList<MessageFrame>();
            System.out.println("Accepted connection from client!");
            System.out.println("The client is from: " + acceptedSocket.getInetAddress() + ":" + acceptedSocket.getPort());
            this.tokenizer=new StompTokenizer("\n",Charset.forName("UTF-8"),this.clients,this.topics);
        }
        
        public void run()
        {
     
            String msg;
            
            try {
                initialize();
            }
            catch (IOException e) {
                System.out.println("Error in initializing I/O");
            }
     
            try {
                process();
            } 
            catch (IOException e) {
                System.out.println("Error in I/O");
            } 
            
            System.out.println("Connection closed - bye bye...");
            close();
     
        }
        
        public void process() throws IOException
        {
            String msg;


            while ((msg = in.readLine()) != null)
            {
                System.out.println("Received \"" + msg + "\" from client");
//TODO check for new messages
                // parsing raw data to StompFrame format
                StompFrame frame=this.tokenizer.getFrame(in);
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
                                break;
                }
                
                
                ///***old***//
                
                String response = protocol.processMessage(msg);
                if (response != null)
                {
                    out.println(response);
                }
                
                if (protocol.isEnd(msg))//TODO what about protocol?
                {
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
            System.out.println("I/O initialized");
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
                System.out.println("Exception in closing I/O");
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
        	out.println(frame.getString());
        }
        public void CONNECT(StompFrame frame){
        	String sessionId = frame.header.get("session");
            this.connectFrame=new ConnectFrame(frame,frame.getCommend(),sessionId);
            this.client=this.connectFrame.getClient();
            StompFrame receiptFramConnectFrameToSend=new ReceiptFram(frame, StompCommand.valueOf("CONNECTED"));
            this.send(receiptFramConnectFrameToSend);
        }
        public void DISCONNECT(StompFrame frame){
        	this.disconnectFrame=new DisconnectFrame(frame, frame.command,this.clientSocket);
            StompFrame receiptFramDisconnectFrameToSend=new ReceiptFram(frame, StompCommand.valueOf("DISCONNECT"));
            this.send(receiptFramDisconnectFrameToSend);
        }
        public void SEND(StompFrame frame){
        	MessageFrame messageFrame=new MessageFrame(frame);
            this.messageFrameList.add(messageFrame);
            this.client.addNewMessage(messageFrame);
            
        }
        public void SUBSCRIBE(StompFrame frame){
        	Client newClient=null;
        	String clientName=frame.header.get("destination:");
        	Boolean found=false;
        	for (int i=0;i<this.clients.size();i++){
        		if (this.clients.get(i).getClientUserName().equals(clientName)){
        			newClient=this.clients.get(i);
        			found=true;
        		}
        	}
        	if (found){
        		this.client.addClientToFollow(frame.header.get("id:"), newClient);
        	}
        	else if (!found){
        		//TODO return eror frame
        	}
        }
        public void UNSUBSCRIBE(StompFrame frame){
        	this.client.removeFollowingClient(frame.header.get("id:"));
        }
        public void sendNewMessage(){
        	
        }
        
}
