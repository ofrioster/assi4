/*
 * Console.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef CONSOLE_H_
#define CONSOLE_H_


//	#include <queue>
    #include <stdlib.h>
    #include "../include2/ConnectionHandler.h"
	#include <boost/thread.hpp>
    #include "../include2/Client.h"
   // #include "../include2/StompFrame.h"
   // #include "../include2/SendFrame.h"
   // #include "../include2/ConnectFrame.h"
  //  #include "../include2/DisconnectFrame.h"
 //   #include "../include2/SubscribeFrame.h"
//   #include "../include2/SubscribeFrame.h"
	#include "../include2/Network.h"
	#include <fstream>
	#include <string>
	#include <vector>

using namespace std;

class Console {

public:

	Console (string delimiter);
	virtual ~Console();
	void run(Network& network);
	void login();
		void follow();
		void unfollow();
		void tweet();
		void clients();
		void stats();
		void logout();
		void exitClient();
		void stop();
/*	void login(Network network);
	void follow(Network network);
	void unfollow(Network network);
	void tweet(Network network);
	void clients(Network network);
	void stats(Network network);
	void logout(Network network);
	void exitClient(Network network);
	void stop(Network network);*/
	void clientIsConnecting(string usersName,string password,string hostIP,string hostPort);


private:

    boost::mutex * _mutex;
    int counter;
    string delimiter;
    vector<Client> clientsVector;
    Client client;
    bool clientExist;
    bool logoutClinet;
    bool exitClientClinet;
    bool stopClinet;
    Network network;

};


#endif /* CONSOLE_H_ */
