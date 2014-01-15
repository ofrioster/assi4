/*
 * Tweet.h
 *
 *  Created on: Jan 15, 2014
 *      Author: ofri
 */

#ifndef TWEET_H_
#define TWEET_H_

#include <string>


using namespace std;
class Tweet{

public:

	Tweet();
	Tweet(string username,string TweetBody,string DateAndTimeRecievedRtTheServer);
	virtual ~Tweet();
	string getUsername();
	string getTweetBody();
	string getDateAndTimeRecievedRtTheServer();
	string toString();

private:
	string username;
	string TweetBody;
	string DateAndTimeRecievedRtTheServer;

};


#endif /* TWEET_H_ */
