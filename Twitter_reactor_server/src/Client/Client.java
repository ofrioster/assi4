package Client;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rector.ConnectionHandler;
import Stomp.MessageFrame;
import Stomp.StompFrame;


public class Client<T> implements ClientInterfce{
	
	private String userName;
	private ArrayList<Follower> followers;
	private ArrayList<Follower> following; 
	private String hostIP;
	private String hostPort;
	private String password;
	private String acceptVersion;
	private Boolean clientIsOnline;
	private ArrayList<Tweet> tweets;
	private ArrayList<Tweet> friendsMessage;
	private ArrayList<Client> clients;
	private int messageCount;
	private int numberOfTimeClienMention;
	private int numberOfTimeClienMentionInHisTweets;
	private Stats stats;
	private String lastAction;
	private ConnectionHandler<T> connectionHandler;
	

	
	
	public Client(String userName,String hostIP, String hostPort,String password,ArrayList<Client> clients,Stats stats){
		this.userName=userName;
		this.tweets= new ArrayList<Tweet>();
		this.friendsMessage=new ArrayList<Tweet>();
		this.hostIP=hostIP;
		this.hostPort=hostPort;
		this.password=password;
		this.clientIsOnline=false;
		this.clients=clients;
		this.messageCount=0;
		this.clients.add(this);
		this.stats=stats;
		this.followers = new ArrayList<Follower>();
		this.following = new ArrayList<Follower>();
		
		
	}
	public Client(String userName,ArrayList<Client> clients, Stats stats) {
		this.userName = userName;
		this.tweets = new ArrayList<Tweet>();
		this.friendsMessage = new ArrayList<Tweet>();
		this.hostIP = "null";
		this.password = "password";
		this.clientIsOnline = false;
		this.clients = clients;
		this.messageCount = 0;
		this.clients.add(this);
		this.stats = stats;
		this.followers = new ArrayList<Follower>();
		this.following = new ArrayList<Follower>();
		
	}
	public Client(String userName,String hostIP,String password,ArrayList<Client> clients,Stats stats){
		this.userName=userName;
		this.tweets= new ArrayList<Tweet>();
		this.friendsMessage=new ArrayList<Tweet>();
		this.hostIP=hostIP;
		this.password=password;
		this.clientIsOnline=false;
		this.clients=clients;
		this.messageCount=0;
		this.clients.add(this);
		this.stats=stats;
		this.followers = new ArrayList<Follower>();
		this.following = new ArrayList<Follower>();
		
	}
	public Client(StompFrame frame,ArrayList<Client> clients,Stats stats){
		this.userName=frame.getHeader("login");
		this.tweets= new ArrayList<Tweet>();
		this.friendsMessage=new ArrayList<Tweet>();
		this.hostIP=frame.getHeader("host");
		this.password=frame.getHeader("passcode");
		this.acceptVersion=frame.getHeader("accept-version");
		this.clientIsOnline=false;
		this.clients=clients;
		this.messageCount=0;
		this.clients.add(this);
		this.stats=stats;
		this.followers = new ArrayList<Follower>();
		this.following = new ArrayList<Follower>();
		
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
		Follower res = new Follower(id, newfollower);
		this.followers.add(res);
		
	}



	/** (non-Javadoc)
	 * @param follower To Remove
	 */
	public void removeFollower(Client followerToRemove) {
		for (int i = 0; i < this.followers.size(); i++) {
			if (this.followers.get(i).getClient().equals(followerToRemove)) {
				this.followers.remove(i);
			}
		}
	}


	/** (non-Javadoc)
	 * @param Client to follow
	 */
	public void addClientToFollow(String id,Client clienTofollow) {
		Follower follower = new Follower(id, clienTofollow);
		this.following.add(follower);
		clienTofollow.addFollower(id, this);
		
	}
	/** (non-Javadoc)
	 * @param Client name to follow
	 */
	public void addClientToFollow(String id,String clienNameTofollow) {
		Boolean found = false;
		for (int i = 0; i < this.clients.size() && !found; i++) {
			if (this.clients.get(i).getClientUserName()
					.equals(clienNameTofollow)) {
				Follower follower = new Follower(id, this.clients.get(i));
				this.following.add(follower);
				// this.following.put(id,this.clients.get(i));
				found = true;
				this.clients.get(i).addFollower(id, this);
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
		for (int i = 0; i < this.following.size(); i++) {
			if (this.following.get(i).getID().equals(clientID)) {
				this.following.remove(i);
			}
		}
		
	}
	/**
	 * @param following Client Name
	 */
	public String removeFollowingClient(String followingClientName) {
		String res = "1";
		Boolean found = false;
		if (this.userName.equals(followingClientName)) {
			return "Trying to unfollow itself";
		}
		for (int i = 0; i < this.following.size() && !found; i++) {
			if (this.following.get(i).getID().equals(followingClientName)) {
				res=this.following.get(i).getClient().getClientUserName();
				this.following.remove(i);
				found = true;
				return ("1"+res);
			}
		}
		if (!found) {
			for (int i = 0; i < this.clients.size(); i++) {
				if (this.clients.get(i).isThisTheClient(followingClientName)) {
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
	 * @param frameframe.getBody()
	 */
	public void addNewMessage(MessageFrame frame){
		Tweet tweet=new Tweet(frame.getMessageId(), frame.getBody(), this.followers.size(),this.userName);
		this.tweets.add(tweet);
		this.addMessageToFollowers(tweet);
	}
	/** (non-Javadoc)
	 * @param tweet to add
	 */
	public void addTweet(Tweet tweet){
		this.tweets.add(tweet);
		this.addMessageToFollowers(tweet);
		this.stats.addTweet();
	}
	/** (non-Javadoc)
	 * @return the tweet in the index
	 */
	public String getTweet(int index){
		return this.tweets.get(index).getTweet();
	}
	public ArrayList<Tweet> getFriendsMessage(){
		return this.friendsMessage;
	}
	/** index 0=subscription, index 1=message-id
	 * @param string array newMessage
	 */
	public synchronized void addFriendsMessage(Tweet tweet){
		this.friendsMessage.add(tweet);
	}
	/**index 0=subscription, index 1=message-id
	 * @param message
	 */
	public void addMessageToFollowers(Tweet tweet){
		for (int i = 0; i < this.followers.size(); i++) {
			this.followers.get(i).getClient().addFriendsMessage(tweet);
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
		if (this.messageCount < this.friendsMessage.size()) {
			StringBuilder builder = new StringBuilder();
			builder.append("destination:");
			builder.append(this.following.get(messageCount).getClient().getClientUserName());
			builder.append('\n');
			builder.append("subscription:");
			builder.append(this.friendsMessage.get(this.messageCount).getTweetUserName());
			builder.append('\n');
			builder.append("message-id:");
			builder.append(this.messageCount);
			builder.append('\n');
			builder.append('\n');
			builder.append(this.friendsMessage.get(messageCount).getTweet());
			this.friendsMessage.get(messageCount).oneMessageHasBennSend();
			this.messageCount++;
			return builder.toString();
		}
		
		return null;
	}
	public synchronized MessageFrame getNextMessage(){
		String msg=this.getNewMessage();
		MessageFrame res=new MessageFrame(this.clients,msg,this.stats);
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
	/** send a frame to all followers
	 * @param frame
	 */
	public void sendClientsMessage(StompFrame frame){
		for (int i=0; i<this.followers.size();i++){
			if (this.followers.get(i).getClient().isClientOnLine()){
				this.followers.get(i).getClient().sendSingleMessage(frame);
			}
			
		}
		
	}
	/** send this frame to this client
	 * @param frame
	 */
	public void sendSingleMessage(StompFrame frame){
		
		ByteBuffer bytes;
		try {
			bytes = frame.getBytesForMessage();
			this.connectionHandler.addOutData(bytes);
		} catch (CharacterCodingException e) {
		}
		catch (Exception e){
		}
		
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
		for (int i=0;i<this.following.size();i++){
			if (this.following.get(i).getID().equals(id)){
				return true;
			}
		}
		return false;
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
	/**
	 * @return number of tweets that has been send
	 */
	public int totalNumberOfTweets(){
		int res=0;
		for (int i=0;i<this.tweets.size();i++){
			if(this.tweets.get(i).isAllMessagesHasBeenSend()){
				res++;
			}
			
		}
		return res;
	}
	/**
	 * @return the total time of delivered tweets
	 */
	public long totalSendTime(){
		long res=0;
		for (int i=0;i<tweets.size();i++){
			if(this.tweets.get(i).isAllMessagesHasBeenSend()){
				res+=this.tweets.get(i).getTotalSendTime();
			}
		}
		return res;
	}
	/** sent this message to this client and all his followers
	 * @see ClientInterfce#statsSend(java.lang.String)
	 */
	public void statsSend(String msg){
		Tweet tweet=new Tweet(msg, this.followers.size(), this.userName);
		this.friendsMessage.add(tweet);
		this.addTweet(tweet);
		
	}
	public void setClienLastAction(String lasAction){
		this.lastAction=lasAction;
	}
	public String getClienLastAction(){
		return this.lastAction;
	}
	/**
	 * add new message that send by this client
	 * 
	 * @param frameframe
	 *            .getBody()
	 */
	public void addNewMessageThatSendByThis(MessageFrame frame) {
		Tweet tweet = new Tweet(frame.getMessageId(), frame.getBody(),this.followers.size(), this.userName,frame);
		this.addMessageToFollowers(tweet);
	}
	public void setConnectionHandler(ConnectionHandler<T> _handler){
		this.connectionHandler=_handler;
	}



}
