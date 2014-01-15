/*
 * HTMLwrite.h
 *
 *  Created on: Jan 12, 2014
 *      Author: ofri
 */

#ifndef HTMLWRITE_H_
#define HTMLWRITE_H_

#include <vector>
#include <string>
#include <iostream>
#include <fstream>
#include <iostream>
#include <sstream>

#include "Tweet.h"

using namespace std;

class HTMLwrite{

public:
	HTMLwrite();
	//HTMLwrite(string username, string TweetBody,string DateAndTimeRecievedRtTheServer);
	HTMLwrite(string username);
	void addTweet(string username,string TweetBody,string DateAndTimeRecievedRtTheServer);
	void printTweet(string username, string TweetBody,string DateAndTimeRecievedRtTheServer);
	void print();
	virtual ~HTMLwrite();

private:
	string username;
	string TweetBody;
	string DateAndTimeRecievedRtTheServer;
	vector<Tweet> TweetsVector;
	//vector<string> DateAndTimeRecievedRtTheServerVector;
//	vector <string> data;

};


#endif /* HTMLWRITE_H_ */
