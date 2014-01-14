/*
 * Network.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef NETWORK_H_
#define NETWORK_H_



    #include <stdlib.h>
	#include <boost/thread.hpp>
	#include <queue>
	#include <fstream>
	#include <string>
	#include "../include2/Client.h"
	#include "../include2/ConnectionHandler.h"
	#include "../include2/StompFrame.h"

using namespace std;




class Network{

public:
	//	Network(ConnectionHandler Connection_Handler);
		Network();
		Network(string delimiter);
		void run();
		void addStompFrameToSend(StompFrame frame);
		bool getIsclientOnLine();
		void setClineOnLine(bool status);
		virtual ~Network();
		bool connect(string host,unsigned short port);
		void setExitClient(bool status);

private:

		vector <StompFrame> tweet;
		ConnectionHandler connection_Handler;
		bool isClientOnline;
		bool exitClient;


	};




#endif /* NETWORK_H_ */
