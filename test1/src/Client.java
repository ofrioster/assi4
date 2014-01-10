import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Client implements ClientInterfce{
	
	private String userName;
//	private ArrayList<Client> followers;
	private Map<String, Client> following = new HashMap<String, Client>();
	private Map<String, Client> followers = new HashMap<String, Client>();
//	private ArrayList<Client> following; 
	private String hostIP;
	private String hostPort;
	private String password;
	private String acceptVersion;
	private Boolean clientIsOnline;
	private ArrayList<String> tweets;
	private ArrayList<String[]> friendsMessage;
	private ArrayList<Client> clients;
	private ArrayList<Topic> topics;
	private int messageCount;
	private int numberOfTimeClienMention;
	private int numberOfTimeClienMentionInHisTweets;

	
	
	public Client(String userName,String hostIP, String hostPort,String password,ArrayList<Client> clients){
		this.userName=userName;
//		this.followers= new ArrayList<Client>();
	//	this.following= new ArrayList<Client>();
		this.tweets= new ArrayList<String>();
		this.friendsMessage=new ArrayList<String[]>();
		this.topics=new ArrayList<Topic>();
		this.hostIP=hostIP;
		this.hostPort=hostPort;
		this.password=password;
		this.clientIsOnline=false;
		this.clients=clients;
		this.messageCount=0;
		this.clients.add(this);
		
	}
	public Client(String userName,String hostIP,String password,ArrayList<Client> clients){
		this.userName=userName;
//		this.followers= new ArrayList<Client>();
//		this.following= new ArrayList<Client>();
		this.tweets= new ArrayList<String>();
		this.friendsMessage=new ArrayList<String[]>();
		this.hostIP=hostIP;
//		this.hostPort=hostPort;
		this.password=password;
		this.clientIsOnline=false;
		this.clients=clients;
		this.messageCount=0;
		this.clients.add(this);
	}
	public Client(StompFrame frame,ArrayList<Client> clients){
		this.userName=frame.header.get("login");
		this.tweets= new ArrayList<String>();
		this.friendsMessage=new ArrayList<String[]>();
		this.hostIP=frame.header.get("host");
		this.password=frame.header.get("passcode");
		this.acceptVersion=frame.header.get("accept-version");
		this.clientIsOnline=false;
		this.clients=clients;
		this.messageCount=0;
		this.clients.add(this);
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
	public void addFollower(String id,Client newfollower) {
		this.followers.put(id,newfollower);
		
	}



	/** (non-Javadoc)
	 * @param follower To Remove
	 */
	public void removeFollower(Client followerToRemove) {
		for (String key : this.followers.keySet()) {
			if (this.followers.get(key).equals(followerToRemove)){
				
			}
		}	
	}


	/** (non-Javadoc)
	 * @param Client to follow
	 */
	public void addClientToFollow(String id,Client clienTofollow) {
		this.following.put(id,clienTofollow);
		clienTofollow.addFollower(id,this);
		
	}
	/** (non-Javadoc)
	 * @param Client name to follow
	 */
	public void addClientToFollow(String id,String clienNameTofollow) {
		Boolean found=false;
		for (int i=0; i<this.clients.size() && !found;i++){
			if (this.clients.get(i).getClientUserName().equals(clienNameTofollow)){
				this.following.put(id,this.clients.get(i));
				found=true;
				this.clients.get(i).addFollower(id,this);
			}
		}
		
	}


	/**
	 * @param following Client
	 */
	public void removeFollowingClient(Client followingClient) {
		Boolean found=false;
		for (int i=0; i<this.following.size() && !found;i++){
			if (this.following.get(i).equals(followingClient)){
				this.following.remove(i);
				found=true;
				followingClient.removeFollower(this);
			}
		}
		
	}
	/**
	 * @param following Client by his ID
	 */
	public void removeFollowingClientByID(String clientID) {
		Client followinClient=this.following.get(clientID);
		followinClient.removeFollower(this);
		this.clients.remove(clientID);
	/*	for (int i=0; i<this.following.size() && !found;i++){
			if (this.following.get(i).equals(followingClient)){
				this.following.remove(i);
				found=true;
				followingClient.removeFollower(this);
			}
		}*/
		
	}
	/**
	 * @param following Client Name
	 */
	public String removeFollowingClient(String followingClientName) {
		String res=null;
		Boolean found=false;
		if (this.userName.equals(followingClientName)){
			return "Trying to unfollow itself";
		}
		for (int i=0; i<this.following.size() && !found;i++){
			if (this.following.get(i).getClientUserName().equals(followingClientName)){
				this.following.get(i).removeFollower(this);
				this.following.remove(i);
				found=true;
			}
		}
		if (!found){
			for (int i=0;i<this.clients.size();i++){
				if (this.clients.get(i).isThisTheClient(followingClientName)){
					return "Not following this user";
				}
			}
			return "Wrong username";
		}
		
		return res;
		
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
		/*
		public Socket getSocket(){
			return this.socket;
		}*/
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
	/** (non-Javadoc)
	 * @param boolean if the client is on line
	 */
	public void setClientIsOnline(Boolean clientIsOnline){
		this.clientIsOnline=clientIsOnline;
	}
	/** add new message that arrive from client and send it to all followers
	 * @param frame
	 */
	public void addNewMessage(StompFrame frame){
		this.tweets.add(frame.getBody());
		this.addMessageToFollowers(frame.getBody());
	}
	/** (non-Javadoc)
	 * @param tweet to add
	 */
	public void addTweet(String tweet){
		this.tweets.add(tweet);
	}
	/** (non-Javadoc)
	 * @return the tweet in the index
	 */
	public String getTweet(int index){
		return this.tweets.get(index);
	}
	public ArrayList<String[]> getFriendsMessage(){
		return this.friendsMessage;
	}
	/** index 0=subscription, index 1=message-id
	 * @param string array newMessage
	 */
	public synchronized void addFriendsMessage(String[] newMessage){
		this.friendsMessage.add(newMessage);
	}
	/**index 0=subscription, index 1=message-id
	 * @param message
	 */
	public void addMessageToFollowers(String message){
		for (int i=0; i<this.followers.size();i++){
			String[] addToFriend=new String[2];
			addToFriend[0]=""+this.followers.keySet();
			addToFriend[1]=message;
			this.followers.get(i).addFriendsMessage(addToFriend);
		}
	}
	/** (non-Javadoc)
	 * @param topic to add
	 */
	public void addTopic(Topic newTopic) {
		this.topics.add(newTopic);
		
	}

	/** (non-Javadoc)
	 * @param topic to remove
	 */
	public void removeTopic(Topic topicToRemove) {
		for (int i=0;i<this.topics.size();i++){
			if (this.topics.get(i).equals(topicToRemove)){
				this.topics.remove(i);
			}
		}
		
	}
	/**
	 * @return Boolean if this client have a new message that has not been send
	 */
	public synchronized Boolean hasNewMessage(){
		return (this.messageCount<(this.friendsMessage.size()-1));
	}
	/**
	 * @return the new message as String
	 */
	public synchronized String getNewMessage(){
		if(this.messageCount<(this.friendsMessage.size()-1)){
			StringBuilder builder = new StringBuilder();
			builder.append("destination:");
			builder.append(this.following.get(this.friendsMessage.get(this.messageCount-1)[0]).getClientUserName());
			builder.append('\n');
			builder.append("subscription:");
			builder.append(this.friendsMessage.get(this.messageCount-1)[0]);
			builder.append('\n');
			builder.append("message-id:");
			builder.append(this.messageCount);
			builder.append('\n');
			builder.append('\n');
			builder.append(this.friendsMessage.get(messageCount)[1]);
			this.messageCount++;
			return builder.toString();
		}
		
		return null;
	}
	public synchronized MessageFrame getNextMessage(){
		String msg=this.getNewMessage();
		MessageFrame res=new MessageFrame(this.clients,this.topics,msg);
		return res;
	}


	/** change variable that this client is off line
	 * 
	 */
	public void thisClientIsOffLine() {
		this.clientIsOnline=false;
		
	}
	/** change variable that this client is on line
	 * 
	 */
	public void thisClientIsOnLine() {
		this.clientIsOnline=true;
		
	}
	/** check if this is the client user name
	 * @param user name
	 */
	public Boolean isThisTheClient(String userName){
		return this.userName.equals(userName);
	}
	public Boolean isThisIsThePassword(String password){
		return this.password.equals(password);
	}
	/** is this client is already connect?
	 * @return Boolean of is this client is already connect
	 */
	public Boolean isClientOnLine(){
		return this.clientIsOnline;
	}
	public Boolean isClientFollowingClient(String id){
		return this.following.containsKey(id);
	}
	public int getNumberOfFollowers(){
		return this.followers.size();
	}
	public int getNumberOfTweet(){
		return this.tweets.size();
	}
	/**  mentions in other followers tweets
	 * 
	 */
	public int getHowMenyTimesThisClientMention(){
		return this.numberOfTimeClienMention;
	}
	/** add 1 to mentions in other followers tweets
	 * 
	 */
	public void updateClientMention(){
		this.numberOfTimeClienMention++;
	}
	/**  mentions in other his tweets
	 * 
	 */
	public int getHowMenyTimesThisClientMentionInHisTweets(){
		return this.numberOfTimeClienMentionInHisTweets;
	}
	/** add 1 to mentions in other his tweets
	 * 
	 */
	public void updateClientMentionInHisTweets(){
		this.numberOfTimeClienMentionInHisTweets++;
	}
}
