/*
 * console.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CONSOLE_H_
#define CONSOLE_H_

#include <queue>
#include <stdlib.h>
#include <boost/thread.hpp>
#include "../include/ConnectionHandler.h"
#include "../include/Client.h"
#include "../include/StompFrame.h"
#include "../include/SendFrame.h"
#include "../include/ConnectFrame.h"
#include "../include/DisconnectFrame.h"
#include "../include/SubscribeFrame.h"
#include "../include/UnsubscribeFrame.h"



class Console {
    int _id;

public:
	//Console  (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn , std::queue<STOMP::StompFrame*>* stompFramesOut) :_mutex(mutex) ,_stompFramesIn(stompFramesIn),_stompFramesOut(stompFramesOut) {}
	Console (int number);
	virtual ~Console();
	int run (ConnectionHandler& connectionHandler, std::map<string, int> folowing,bool& close,string username,int& receiptId);
};

#endif /* CONSOLE_H_ */
