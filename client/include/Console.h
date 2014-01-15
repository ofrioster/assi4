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
    #include <stdlib.h>
    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
    #include "../encoder/utf8.h"
    #include "../encoder/encoder.h"
	#include <boost/thread.hpp>
    #include "../include/Client.h"

    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/ConnectFrame.h"
    #include "../include/DisconnectFrame.h"
    #include "../include/SubscribeFrame.h"
    #include "../include/UnsubscribeFrame.h"
#include <fstream>



class Console {

    int _id;
    boost::mutex * _mutex;
    std::queue<STOMP::StompFrame*>* _stompFramesIn;                                // empty vector of ints
    std::queue<STOMP::StompFrame*>* _stompFramesOut;                                // empty vector of ints
    int _counter;

public:
	//Console  (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn , std::queue<STOMP::StompFrame*>* stompFramesOut) :_mutex(mutex) ,_stompFramesIn(stompFramesIn),_stompFramesOut(stompFramesOut) {}
	Console (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn );
	virtual ~Console();
	int run (ConnectionHandler& connectionHandler, std::map<string, int> folowing,bool close,string username);
};

#endif /* CONSOLE_H_ */
