/*
 * console.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CONSOLE_H_
#define CONSOLE_H_

#include <queue>


class Console {

    int _id;
    boost::mutex * _mutex;
    std::queue<STOMP::StompFrame*>* _stompFramesIn;                                // empty vector of ints
    std::queue<STOMP::StompFrame*>* _stompFramesOut;                                // empty vector of ints

public:
	//Console  (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn , std::queue<STOMP::StompFrame*>* stompFramesOut) :_mutex(mutex) ,_stompFramesIn(stompFramesIn),_stompFramesOut(stompFramesOut) {}
	Console (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn );
	virtual ~Console();
	int run (ConnectionHandler& connectionHandler);


};

#endif /* CONSOLE_H_ */
