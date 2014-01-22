/*
 * Client.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CLIENT_H_
#define CLIENT_H_
#include <stdlib.h>
    #include <stdlib.h>
//    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
	#include <boost/thread.hpp>
    #include "../include/Client.h"
#include <queue>

    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/ConnectFrame.h"
    #include "../include/DisconnectFrame.h"
    #include "../include/SubscribeFrame.h"
    #include "../include/UnsubscribeFrame.h"
#include <fstream>
    #include "../include/HTMLwrite.h"



	class Network{
	    int _id;
//	    std::queue<STOMP::StompFrame*>* _stompFramesIn;                                // empty vector of ints
	    //std::queue<STOMP::StompFrame*>* _stompFramesOut;                                // empty vector of ints

	public:
	   // Network (boost::mutex* mutex,std::queue<STOMP::StompFrame*> stompFramesIn , std::queue<STOMP::StompFrame*> stompFramesOut) :_mutex(mutex) ,_stompFramesIn(stompFramesIn),_stompFramesOut(stompFramesOut) {}
	    Network (int number);
	    int run(ConnectionHandler& connectionHandler, std::map<string, int> folowing,HTMLwrite& htmlwrite,int& receiptId);
		virtual ~Network();


	};






#endif /* CLIENT_H_ */
