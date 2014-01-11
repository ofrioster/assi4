
import java.util.concurrent.Semaphore;


public class Tweet implements TweetInterface{

	String tweetID;
	String message;
	long totalSendTime;
	Semaphore messagesThatSend;
	String userNameTweet;
	
	
	public Tweet(String tweetId,String message,int HowManyFollowers,String userName){
		this.tweetID=tweetId;
		this.message=message;
		this.userNameTweet=userName;
		this.messagesThatSend=new Semaphore(HowManyFollowers);
		this.totalSendTime=System.currentTimeMillis();
	}
	/** Constructor for stats tweet
	 * @param message
	 * @param HowManyFollowers
	 * @param userName
	 */
	public Tweet(String message,int HowManyFollowers,String userName){
		this.tweetID="stats";
		this.message=message;
		this.userNameTweet=userName;
		this.messagesThatSend=new Semaphore(HowManyFollowers);
		this.totalSendTime=System.currentTimeMillis();
	}
	/** tell that one message has been send
	 * need to know when all messages has been send
	 * @see TweetInterface#oneMessageHasBennSend()
	 * @exception InterruptedException
	 */
	public void oneMessageHasBennSend(){
		try {
			this.messagesThatSend.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(this.messagesThatSend.availablePermits()==0){
			this.totalSendTime=this.totalSendTime-System.currentTimeMillis();
		}
	}
	/** 
	 * @return boolean if all the messages has been send
	 */
	public boolean isAllMessagesHasBeenSend(){
		return this.messagesThatSend.availablePermits()==0;
	}
	/**
	 * @return -1 if not all messages has been send
	 */
	public long getTotalSendTime(){
		long res=-1;
		if (this.isAllMessagesHasBeenSend()){
			res=this.totalSendTime;
		}
		return res;
	}
	/**
	 * @return tweet message
	 */
	public String getTweet(){
		return this.message;
	}
	/**
	 * @return tweet ID
	 */
	public String getTweetID(){
		return this.tweetID;
	}
	public String getTweetUserName(){
		return this.userNameTweet;
	}
}
