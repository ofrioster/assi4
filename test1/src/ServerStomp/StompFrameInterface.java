package ServerStomp;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import ServerClient.Client;
import ServerClient.Topic;


public interface StompFrameInterface {
	public byte[] getBytes();
	public String toString();
	public StompCommand getCommend();
	public String getBody();
	public Map<String, String> getHeader();
	public Client getClient();
	public ArrayList<Client> getClients();
	public String getString();
	public ArrayList<Topic> getTopics();
	public void addHeaderAndBody(String raw);
	public String getHeader(String msg);
	
}
