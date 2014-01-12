package clientTEST;
import java.io.*;
import java.net.*;

public class EchoClient {

	  
    public static void main(String[] args) throws IOException
    {
        Socket clientSocket = null; // the connection socket
        PrintWriter out = null;
        BufferedReader in = null;
 
        // Get host and port
        String host = "localhost";//args[0];
        int port =50001;// Integer.decode(args[1]).intValue();
        
        System.out.println("Connecting to " + host + ":" + port);
        
        // Trying to connect to a socket and initialize an output stream
        try {
            clientSocket = new Socket(host, port); // host and port
              out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
              System.out.println("Unknown host: " + host);
              System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get output to " + host + " connection");
            System.exit(1);
        }
        
        // Initialize an input stream
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            System.out.println("Couldn't get input to " + host + " connection");
            System.exit(1);
        }
        
        System.out.println("Connected to server!");
 
        String msg;
        String inMsg;
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
        
        while ((msg = userIn.readLine())!= null)
        {
        	if(msg.equals("1")){
        		msg=connect();
        	}
        	else if(msg.equals("2")){
        		msg=SEND();
        	}
        	else if(msg.equals("3")){
        		msg=SUBSCRIBE();
        	}
        	else if(msg.equals("4")){
        		msg=UNSUBSCRIBE();
        	}
        	else if(msg.equals("5")){
        		msg=disconnect();
        	}
            out.println(msg);
//            System.out.println("this msg: "+msg);
            while(!(inMsg=in.readLine()).equals("\0")){
            	System.out.println(inMsg);
//            	System.out.println("get msg");
            }
            System.out.println("finish get msg");
            if(msg.equals("bye"))
                break;
        }
        
        System.out.println("Exiting...");
        
        // Close all I/O
        out.close();
        in.close();
        userIn.close();
        clientSocket.close();
    }
    /**1
     * @return
     */
    public static String connect(){
    	String res="CONNECT"+"\n"+"accept-version:1.2"+"\n"+"host:GT3"+"\n"+"login:ofri1"+"\n"+"passcode:123"+"\n"+"\n"+"\0";
    	return res;
    }
    /**2
     * @return
     */
    public static String SEND(){
    	String res="SEND"+"\n"+"destination:ofri2"+"\n"+"\n"+"whats my name?"+"\n"+"\0";
    	return res;
    }
    /**3
     * @return
     */
    public static String SUBSCRIBE(){
    	String res="SUBSCRIBE"+"\n"+"destination:ofri2"+"\n"+"id:102"+"\n"+"\n"+"\0";
    	return res;
    }
    /**4
     * @return
     */
    public static String UNSUBSCRIBE(){
    	String res="UNSUBSCRIBE"+"\n"+"id:102"+"\n"+"\n"+"\0";
    	return res;
    }
    /**5
     * @return
     */
    public static String disconnect(){
    	String res="DISCONNECT"+"\n"+"receipt:02"+"\n"+"\n"+"\0";
    	return res;
    }
}
