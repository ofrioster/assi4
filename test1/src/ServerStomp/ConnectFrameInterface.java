package ServerStomp;

import MainServer.ConnectionHandler2;
import MainServer.Stats;
import ServerClient.Client;

public interface ConnectFrameInterface {
	public Client serchForClient(String userName,String password,String serverIP,ConnectionHandler2 connectionHandler2);
}
