/*
 * Tweet.cpp
 *
 *  Created on: Jan 15, 2014
 *      Author: ofri
 */

#include "../include/Tweet.h"


using namespace std;



Tweet::Tweet() {
	// TODO Auto-generated constructor stub

}
Tweet::Tweet(string username,string TweetBody,string DateAndTimeRecievedRtTheServer){
	this->username=username;
	this->TweetBody=TweetBody;
	this->DateAndTimeRecievedRtTheServer=DateAndTimeRecievedRtTheServer;
}

Tweet::~Tweet() {
	// TODO Auto-generated destructor stub
}

string Tweet::getUsername(){
	return this->username;
}
string Tweet::getTweetBody(){
return this->TweetBody;
}
string Tweet::getDateAndTimeRecievedRtTheServer(){
	return this->DateAndTimeRecievedRtTheServer;
}
string Tweet::toString(){
	string res=this->username+", "+ this->TweetBody+", "+this->DateAndTimeRecievedRtTheServer+"<BR/>";
	return res;
}
