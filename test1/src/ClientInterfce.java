import java.util.ArrayList;


public interface ClientInterfce {
	
	public String getClientUserName();
	public String getClientPassword();
	public void addFollower(String id,Client newfollower);
	public void removeFollower(Client followerToRemove);
	public void addClientToFollow(String id,Client clienTofollow);
	public void removeFollowingClient(Client followingClient);
	public void setHostIP(String hostIP);
	public void setHostPort(String hostPort);
	public void setPassword(String password);
	public Boolean confirmPassword(String Password);
	public Boolean confirmHostIP(String hostIP);
	public Boolean confirmHostPort(String hostPort);
	public void setClientIsOnline(Boolean clientIsOnline);
	public void addTweet(String tweet);
	public String getTweet(int index);
	public ArrayList<String[]> getFriendsMessage();
	public void addFriendsMessage(String[] newMessage);
	public void removeFollowingClient(String followingClientName);
	public void addMessageToFollowers(String message);
	public void addTopic(Topic newTopic);
	public void removeTopic(Topic topicToRemove);
	public Boolean hasNewMessage();
	public void thisClientIsOffLine();
	public void thisClientIsOnLine();
	public void removeFollowingClientByID(String clientID);
	public MessageFrame getNextMessage();
	
	

}
