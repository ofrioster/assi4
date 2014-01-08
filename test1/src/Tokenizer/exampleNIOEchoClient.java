package Tokenizer;


/**
 * This is like EchoClient.java, but uses Java's new IO classes (java.nio).
 * 
 * This is intended to demonstrate the use of these classes.
 * This may seem like just a more complicated way to do something we already know how
 * to do.
 * But this will make the next tirgul easier to follow.
 *
 * @param host port
 */
 
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
 
// our code
//import tokenizer.StringMessageTokenizer;
//import tokenizer.FixedSeparatorMessageTokenizer;
 
public class exampleNIOEchoClient {
 
    public static void main(String[] args) throws IOException
    {
        final int NUM_OF_BYTES = 1024;
 
        SocketChannel sChannel = null; // the connection socket
 
        // used for reading
        ByteBuffer inbuf = ByteBuffer.allocate(NUM_OF_BYTES); 
        // the message tokenizer accumulate bytes until it has a complete message.
        StringMessageTokenizer tokenizer = new FixedSeparatorMessageTokenizer("\n",Charset.forName("UTF-8"));
 
        // Get host and port
        String host = args[0];
        int port = Integer.decode(args[1]).intValue();
 
        System.out.println("Connecting to " + host + ":" + port);
 
        // connect to the server
        sChannel=SocketChannel.open();
        try {
            sChannel.connect(new InetSocketAddress(host, port));
            /*The cool thing about the nio package is the
             possibility of non-blocking io, we will be non -blocking for this Tirgul.*/
            sChannel.configureBlocking(false); 
            System.out.println("Connected to - "+host);
        } catch(IOException e){
            System.out.println("Failed to connected to - "+host);
            sChannel.close();
            System.exit(1);
        }
 
        System.out.println("Connected to server!");
 
        // initialize user input, and start main loop:
 
        String msg;
        boolean b=true;
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
 
        while (b && (msg = userIn.readLine())!= null){
            //make sure to add the end of line 
            msg += "\n";
 
            // write the line to the server
            ByteBuffer outbuf = ByteBuffer.wrap(msg.getBytes("UTF-8"));
            while (outbuf.remaining() > 0) {
                sChannel.write(outbuf);
            }

            // read a line from the server and print it
            while (!tokenizer.hasMessage()) {
                System.out.println("Trying to read until we have a massege");
                inbuf.clear();
                sChannel.read(inbuf);
                inbuf.flip();
                tokenizer.addBytes(inbuf);
                try {
                    //Sleep because we want to give a chance to read several bytes from the channel
                    System.out.println("Going to sleep , give a chance to read some bytes");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
            // write the line to the screen
            System.out.println("tokenizer.nextMessage()"+tokenizer.nextMessage());
 
            if (msg.equals("bye\n")){
                System.out.println("Client exit...");
                b = false;
            }
        }
 
        System.out.println("Exiting...");
 
        // Close all I/O
        sChannel.close();
        userIn.close();
    }
}
