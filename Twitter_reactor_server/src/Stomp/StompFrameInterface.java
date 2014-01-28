package Stomp;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import Client.*;


public interface StompFrameInterface {
	public byte[] getBytes();
	public String toString();
	public StompCommand getCommend();
	public String getBody();
	public Map<String, String> getHeader();
	public Client getClient();
	public ArrayList<Client> getClients();
	public String getString();
	public void addHeaderAndBody(String raw);
	public String getHeader(String msg);
	public void headerPut(String subject,String msg);
	public void bodyAdd(String msg);
	public String getStringCommend();
	
	
}
