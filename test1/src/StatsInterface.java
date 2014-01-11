
public interface StatsInterface {

	public void addTweet();
	public void updateAvgNumberOfTweetsPer5Seconds();
	public void updateMaxNumberOfTweetsPer5Seconds();
	public void updateAvgTimeToPassATweetToAllUsersFollowingAnAccount();
	public void updateNameOfTheUserWithTheMaximumNumberOfFollowersAndTheNumberOfTheFollowers();
	public void updateNameOfTheUserWithTheMaximumNumberOfTweetsAndTheNumberOfTweets();
	public void updateNameOfTheUserWithTheMaximumMentionsInOtherFollowersTweets();
	public void updateNameOfTheUserWithTheMaximumNumberOfMentionsInHerOwnTweets();
	public String toString();
}
