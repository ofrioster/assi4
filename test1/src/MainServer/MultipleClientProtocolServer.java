package MainServer;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;

import ServerClient.Client;
import ServerClient.Topic;



public class MultipleClientProtocolServer implements Runnable{
        private ServerSocket serverSocket;
        private int listenPort;
        private ServerProtocolFactory factory;
        private static ArrayList<Client> clients;
        private static ArrayList<Topic> topics;
        private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        private static Stats stats;
        private Boolean stop;
        
        
        public MultipleClientProtocolServer(int port, ServerProtocolFactory p,ArrayList<Client> clients,ArrayList<Topic> topics,Stats stats)
        {
            serverSocket = null;
            listenPort = port;
            factory = p;
            this.clients=clients;
            this.topics=topics;
            this.stats=stats;
            this.stop=false;
        }
        
        public void run()
        {
            try {
                serverSocket = new ServerSocket(listenPort);
                logger.log(Level.INFO, "Listening...");
            }
            catch (IOException e) {
                System.out.println("Cannot listen on port " + listenPort);
            }
            
            while (!this.stop)
            {
                try {
                    ConnectionHandler2 newConnection = new ConnectionHandler2(serverSocket.accept(), factory.create(),this.clients,this.topics,this.stats,this.stop,this);
                new Thread(newConnection).start();
                }
                catch (IOException e)
                {
                   if(!this.stop){
                	   logger.log(Level.INFO, "Failed to accept on port " + listenPort);
                   }
                }
            }
        }
        
     
        // Closes the connection
        public void close() throws IOException
        {
            serverSocket.close();
        }
        public void stop(){
        	this.stop=true;
        	try {
				this.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        public static void main(String[] args) throws IOException
        {
            // Get port
            int port = Integer.decode(args[0]).intValue();
            
            MultipleClientProtocolServer server = new MultipleClientProtocolServer(port, new EchoProtocolFactory(), clients, topics,stats);
            Thread serverThread = new Thread(server);
          serverThread.start();
            try {
                serverThread.join();
            }
            catch (InterruptedException e)
            {
                System.out.println("Server stopped");
                logger.log(Level.INFO, "Server stopped");
            }
            
            
                    
        }

}
