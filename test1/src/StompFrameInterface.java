import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;


public interface StompFrameInterface {
//	public StompFrame parse(String raw);
	public byte[] getBytes();
	public String toString();
	public StompCommand getCommend();
	public String getBody();
	public String getSessionId();
	public Map<String, String> getHeader();
	public Client getClient();
	public ArrayList<Client> getClients();
	public Socket getSocket();
	public String getString();
	public ArrayList<Topic> getTopics();
	public void addHeaderAndBody(String raw);
	
}
