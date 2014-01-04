import java.util.ArrayList;


public interface ClientInterfce {
	
	public String getClientUserName();
	public String getClientPassword();
	public void addFollower(Client newfollower);
	public void removeFollower(Client followerToRemove);
	public void addClientToFollow(Client clienTofollow);
	public void removeFollowingClient(Client followingClient);
	public void setHostIP(String hostIP);
	public void setHostPort(String hostPort);
	public void setPassword(String password);
	public Boolean confirmPassword(String Password);
	public Boolean confirmHostIP(String hostIP);
	public Boolean confirmHostPort(String hostPort);
	

}
