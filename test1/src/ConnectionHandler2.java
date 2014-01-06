import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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
        
        
        
        public ConnectionHandler2(Socket acceptedSocket, ServerProtocol p,ArrayList<Client> clients)
        {
            in = null;
            out = null;
            clientSocket = acceptedSocket;
            protocol = p;
            this.clients=clients;
            this.messageFrameList=new ArrayList<MessageFrame>();
            System.out.println("Accepted connection from client!");
            System.out.println("The client is from: " + acceptedSocket.getInetAddress() + ":" + acceptedSocket.getPort());
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
                
             // parsing raw data to StompFrame format
                StompFrame frame = new StompFrame(msg.toString(),this.clients,this.clientSocket); 
             // run handlers
                switch (frame.command) {
                        case CONNECT:
                                // unblock connect()
                        	/*
                                synchronized(this) {
                                        notify();
                                }*/
                                String sessionId = frame.header.get("session");
                                this.connectFrame=new ConnectFrame(frame,frame.getCommend(),sessionId);
                                this.client=this.connectFrame.getClient();
                                break;
                        case DISCONNECT:
                                this.disconnectFrame=new DisconnectFrame(frame, frame.command,this.clientSocket);
                                break;
                        case SEND:
                            String messageId = frame.header.get("message-id");
                            MessageFrame messageFrame=new MessageFrame(frame);
                            this.messageFrameList.add(messageFrame);
                            break;
                        case SUBSCRIBE:
                            this.client.addClientToFollow( frame.header.get("id"));
                            break;
                        case UNSUBSCRIBE:
                                this.client.removeFollowingClient(frame.header.get("id"));
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
                
                if (protocol.isEnd(msg))
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
        private synchronized void send(StompFrame frame) throws StompException {
                try {
                        clientSocket.getOutputStream().write(frame.getBytes());
                } catch (IOException e) {
                        StompException ex = new StompException("Problem with sending frame");
                        ex.initCause(e);
                        throw ex;
                }
        }
}
