package ServerStomp;

import ServerClient.Client;

public interface ConnectFrameInterface {
	public Client serchForClient(String userName,String password,String serverIP);
}
