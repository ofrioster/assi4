/*
 * HTMLwrite.cpp
 *
 *  Created on: Jan 12, 2014
 *      Author: ofri
 */
#include "../include/HTMLwrite.h"


using namespace std;


HTMLwrite::HTMLwrite(){

}
/*
HTMLwrite::HTMLwrite(string username, string TweetBody,string DateAndTimeRecievedRtTheServer){
	this->username=username;
	this->TweetBodyVector.push_back(TweetBody);
	this->DateAndTimeRecievedRtTheServerVector.push_back(DateAndTimeRecievedRtTheServer);
}*/
HTMLwrite::HTMLwrite(string username){
	this->username=username;
}
void HTMLwrite::addTweet(string username,string TweetBody,string DateAndTimeRecievedRtTheServer){
	Tweet tweet(username,TweetBody,DateAndTimeRecievedRtTheServer);
	this->TweetsVector.push_back(tweet);
}

HTMLwrite::~HTMLwrite(){

}

void HTMLwrite::printTweet(string username, string TweetBody,string DateAndTimeRecievedRtTheServer){
string res=username+", "+ TweetBody+", "+DateAndTimeRecievedRtTheServer+"<BR/>";
}
void HTMLwrite::print(){
	ofstream listfile;
	string title=this->username.append(".html");
		listfile.open (title.c_str());
		listfile <<"<HTML>"<<endl;
		listfile <<"<HEAD>"<<endl;
		listfile <<"<TITLE>Arnon & Ofri</TITLE>"<<endl;
		listfile <<"</HEAD>"<<endl;
		listfile <<"<BODY >"<<endl;


/*
		for(vector<string>::reverse_iterator print=TweetsVector.rbegin(); print !=TweetsVector.rend(); print++){
		        listfile << *print<< endl;
		      }
*/
		for (int i=0;i<this->TweetsVector.size();i++){
			listfile <<this->TweetsVector[i].toString()<<endl;
		}
		listfile <<"</body>"<<endl;
		listfile <<"</HTML>"<<endl;
		listfile.close();

}





/*

<html>
<head>
<title>KarlMarx's tweeter site</title>
<style type="text/css">
.username{
	font-weight:bold;
}
.tweet{
	font-weight:normal;
	margin-left: 50px;
}
.time{
	color: gray;
	margin-left: 35px;
}
.entry{
	margin-top: 10px;
	background-color: #AACCFF;
}
</style>
</head>
<body>
<h1>KarlMarx</h1>
<div class="entry"><div class="username">KarlMarx <span class="time">10:15:20 28/2/1986</span></div><span class="tweet">tweet text 1</span></div>
<div class="entry"><div class="username">user2 <span class="time">10:16:25 28/2/1986</span></div><span class="tweet">tweet text 2</span></div>
<div class="entry"><div class="username">KarlMarx <span class="time">10:18:10 28/2/1986</span></div><span class="tweet">tweet text 3 - This tweet is very very very very very very very very very very very very very very very very long.</span></div>
<div class="entry"><div class="username">KarlMarx <span class="time">12:35:55 28/2/1986</span></div><span class="tweet">tweet text 4</span></div>
<br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
<font color="red">Note: The layout of this page is for your convience, and not mandatory. You can use only a black and white and format it in another way. Just keep it clear, and ordered.</font>
</body>
</html>
*/
