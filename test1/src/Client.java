import java.util.ArrayList;


public class Client implements ClientInterfce{
	
	private String userName;
	private ArrayList<Client> followers;
	private ArrayList<Client> following; 
	private String hostIP;
	private String hostPort;
	private String password;
	
	
	public Client(String userName,String hostIP, String hostPort,String password){
		this.userName=userName;
		this.followers= new ArrayList<Client>();
		this.following= new ArrayList<Client>();
		this.hostIP=hostIP;
		this.hostPort=hostPort;
		this.password=password;
		
	}


	/** (non-Javadoc)
	 * @return ClientUserName
	 */
	public String getClientUserName() {
		return this.userName;
	}


	/** (non-Javadoc)
	 * @return ClientPassword()
	 */
	public String getClientPassword() {
		return this.password;
	}



	/** (non-Javadoc)
	 * @param Client newfollower
	 */
	public void addFollower(Client newfollower) {
		this.followers.add(newfollower);
		
	}



	/** (non-Javadoc)
	 * @param follower To Remove
	 */
	public void removeFollower(Client followerToRemove) {
		Boolean found=false;
		for (int i=0; i<this.followers.size() && !found;i++){
			if (this.followers.get(i).equals(followerToRemove)){
				this.followers.remove(i);
				found=true;
			}
		}
		
	}


	@Override
	public void addClientToFollow(Client clienTofollow) {
		this.following.add(clienTofollow);
		
	}


	@Override
	public void removeFollowingClient(Client followingClient) {
		Boolean found=false;
		for (int i=0; i<this.following.size() && !found;i++){
			if (this.following.get(i).equals(followingClient)){
				this.following.remove(i);
				found=true;
			}
		}
		
	}
	/** (non-Javadoc)
	 * @param newHostIP
	 */
	public void setHostIP(String hostIP) {
		this.hostIP=hostIP;
		
	}


	/** (non-Javadoc)
	 * @param newHostPort
	 * @param hostPort 
	 */
	public void setHostPort(String hostPort) {
		this.hostPort=hostPort;
		
	}



	/** (non-Javadoc)
	 * @param new Password
	 */
	public void setPassword(String password) {
		this.password=password;
		
	}


	/** (non-Javadoc)
	 * @return  Boolean confirm Password
	 */
	public Boolean confirmPassword(String Password) {
		Boolean res= this.password.equals(Password);
		return res;
	}


	/** (non-Javadoc)
	 * @return  Boolean confirm hostIP
	 */
	public Boolean confirmHostIP(String hostIP) {
		Boolean res= this.hostIP.equals(hostIP);
		return res;
	}


	/** (non-Javadoc)
	 * @return  Boolean confirm hostPort
	 */
	public Boolean confirmHostPort(String hostPort) {
		Boolean res= this.hostPort.equals(hostPort);
		return res;
	}



	

}
