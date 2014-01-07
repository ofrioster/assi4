import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.nio.channels.ServerSocketChannel;


public class mainTest {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	 public static void main(String[] args) throws IOException{
		 logger.log(Level.INFO, "main has start");
			 System.out.println("work");
			 ArrayList<Client> clients=new ArrayList<Client>();
			// Get port
		        int port = Integer.decode(args[0]).intValue();
		        
		        MultipleClientProtocolServer server = new MultipleClientProtocolServer(port, new EchoProtocolFactory(),clients);
		        Thread serverThread = new Thread(server);
		        serverThread.start();
		        try {
		            serverThread.join();
		        }
		        catch (InterruptedException e)
		        {
		            System.out.println("Server stopped");
		        }
			 
			 
			 /*

		        ServerSocket lpServerSocket = null;
		 
		        // Get port
		        int port = Integer.decode(args[0]).intValue();
		        
		        // Listen on port
		        try {
		            lpServerSocket = new ServerSocket(port);
		        } catch (IOException e) {
		            System.out.println("Couldn't listen on port " + port);
		            System.exit(1);
		        }
		        
		        System.out.println("Listening...");
		        
		        // Waiting for a client connection
		        Socket lpClientSocket = null;
		        try {
		            lpClientSocket = lpServerSocket.accept();
		        } catch (IOException e) {
		            System.out.println("Failed to accept...");
		            System.exit(1);
		        }
		        
		        System.out.println("Accepted connection from client!");
		        System.out.println("The client is from: " + lpClientSocket.getInetAddress() + ":" + lpClientSocket.getPort());
		        
		        // Read messages from client
		        BufferedReader in = new BufferedReader(new InputStreamReader(lpClientSocket.getInputStream()));
		        String msg;
		 
		        while ((msg = in.readLine()) != null)
		        {
		            System.out.println("Received from client: " + msg);
		            if (msg.equals("bye"))
		            {
		                System.out.println("Client sent a terminating message");
		                break;
		            }
		        }
		        
		        System.out.println("Client disconnected - bye bye...");
		        
		        lpServerSocket.close();
		        lpClientSocket.close();
		        in.close();
		     */   
		    }

}
