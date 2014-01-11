package MainServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ServerClient.Client;



public class Stats {
	private ArrayList<Client> clients; 
	private Long startTime;
	private Vector<Long> tweets;
	private int MaxNumberOfTweetsPer5Seconds;
	private double AvgNumberOfTweetsPer5Seconds;
	private double AvgTimeToPassATweetToAllUsersFollowingAnAccount; //in seconds
	private String NameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers;
	private String nameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets;
	private String nameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets;
	private String nameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets;// (used more mentions than anyone else)


	public Stats(ArrayList<Client> clients){
		this.tweets=new Vector<Long>();
		this.startTime=System.currentTimeMillis();
		this.clients=clients;
	}
	public synchronized void addTweet(){
		Long res=System.currentTimeMillis();
		this.tweets.add(res);
		this.updateMaxNumberOfTweetsPer5Seconds();
	}
	public void updateAvgNumberOfTweetsPer5Seconds(){
		Long currecTime=System.currentTimeMillis();
		this.AvgNumberOfTweetsPer5Seconds=(double) (this.tweets.size()/((currecTime-this.startTime)*5));
	}
	/**update max number every new message that send 
	 * 
	 */
	public synchronized void updateMaxNumberOfTweetsPer5Seconds(){
		Long currecTime=System.currentTimeMillis();
		boolean done=false;
		int numberOfTweetsPast5Sec=1;
		for (int i=0; i<this.tweets.size() && !done;i++){
			if (currecTime-this.tweets.get(i)<5000){
				numberOfTweetsPast5Sec++;
			}
			else{
				done=true;
			}
		}
		if(this.MaxNumberOfTweetsPer5Seconds<numberOfTweetsPast5Sec){
			this.MaxNumberOfTweetsPer5Seconds=numberOfTweetsPast5Sec;
		}
	}
	public void updateAvgTimeToPassATweetToAllUsersFollowingAnAccount(){
		int totalTweets=0;
		long totalTime=0;
		for (int i=0;i<this.clients.size();i++){
			totalTweets+=this.clients.get(i).totalNumberOfTweets();
			totalTime+=this.clients.get(i).totalSendTime();
		}
		this.AvgTimeToPassATweetToAllUsersFollowingAnAccount=totalTime/totalTweets;
	}
	public void updateNameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers(){
		this.NameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers=null;
		int maxFollowers=0;
		for (int i=0;i<this.clients.size();i++){
			if(maxFollowers<this.clients.get(i).getNumberOfFollowers()){
				this.NameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers=this.clients.get(i).getClientUserName();
			}
		}
	}
	public void updateNameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets(){
		int maxFollowers=0;
		for (int i=0;i<this.clients.size();i++){
			if(maxFollowers<this.clients.get(i).getNumberOfTweet()){
				this.nameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets=this.clients.get(i).getClientUserName();
			}
		}
	}
	public void updateNameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets(){
		int maxMentions=0;
		for (int i=0;i<this.clients.size();i++){
			if(maxMentions<this.clients.get(i).getHowMenyTimesThisClientMention()){
				this.nameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets=this.clients.get(i).getClientUserName();
			}
		}
	}
	public void updateNameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets(){
		int maxMentions=0;
		for (int i=0;i<this.clients.size();i++){
			if(maxMentions<this.clients.get(i).getHowMenyTimesThisClientMentionInHisTweets()){
				this.nameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets=this.clients.get(i).getClientUserName();
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stats [MaxNumberOfTweetsPer5Seconds=");
		builder.append(MaxNumberOfTweetsPer5Seconds);
		builder.append(", AvgNumberOfTweetsPer5Seconds=");
		builder.append(AvgNumberOfTweetsPer5Seconds);
		builder.append(", AvgTimeToPassATweetToAllUsersFollowingAnAccount=");
		builder.append(AvgTimeToPassATweetToAllUsersFollowingAnAccount);
		builder.append(", NameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers=");
		builder.append(NameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers);
		builder.append(", nameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets=");
		builder.append(nameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets);
		builder.append(", nameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets=");
		builder.append(nameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets);
		builder.append(", nameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets=");
		builder.append(nameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets);
		builder.append("]");
		return builder.toString();
	}
	public void updateStats(Client client){
		this.updateAvgNumberOfTweetsPer5Seconds();
		this.updateAvgTimeToPassATweetToAllUsersFollowingAnAccount();
		this.updateMaxNumberOfTweetsPer5Seconds();
		this.updateNameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets();
		this.updateNameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets();
		this.updateNameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets();
		this.updateNameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers();
		client.statsSend(this.toString());
	}
	

}
