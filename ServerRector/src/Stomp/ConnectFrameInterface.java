package Stomp;

import MainServer.Stats;
import ServerClient.Client;

public interface ConnectFrameInterface {
	public Client serchForClient(String userName,String password,String serverIP);
}
