/*
 * Client.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef CLIENT_H_
#define CLIENT_H_

	#include <stdlib.h>
    #include <stdlib.h>
 //   #include "../include/ConnectionHandler.h"
	#include <boost/thread.hpp>
    #include "../include2/Follower.h"
	#include <queue>
	#include <fstream>
	#include <string>


using namespace std;



class Client{

	public:
		Client();
		Client(string usersName,string password,string hostIP,string hostPort);
		string getUserName();
		string getPassword();
		string getHostIP();
		string getHosrPort();
		void addClientToFollow(string clientName,int id);
		void unfollowingClient(string clientName);
		int getFollowerID(string clientName);
		void newTweet(string tweet);
		bool getIsClientOnline();
		void setClientIsOnline(bool isClientOnline);
		void exitClient();
		virtual ~Client();
private:
		string usersName;
		string password;
		string hostIP;
		string hostPort;
		vector<Follower> follow;
		vector <string> tweet;
//		vector<Client::Client> allClients;
		bool isClientOnline;


	};




#endif /* CLIENT_H_ */
