package MainServer;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.nio.channels.ServerSocketChannel;

import ServerClient.Client;
import ServerClient.Topic;


public class main {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	 public static void main(String[] args) throws IOException{
		 logger.log(Level.INFO, "main has start");
			 ArrayList<Client> clients=new ArrayList<Client>();
			 ArrayList<Topic> topics=new ArrayList<Topic>();
			 Stats stats=new Stats(clients);
			// Get port
		        int port = Integer.decode(args[0]).intValue();
		        
		        Client serverClient=new Client("server", clients, stats);
		        
		        MultipleClientProtocolServer server = new MultipleClientProtocolServer(port, new EchoProtocolFactory(),clients,topics,stats);
		        Thread serverThread = new Thread(server);
		        serverThread.start();
		        try {
		            serverThread.join();
		        }
		        catch (InterruptedException e)
		        {
		            logger.log(Level.INFO, "Server stopped");
		        }
			 
			  
		    }

}
