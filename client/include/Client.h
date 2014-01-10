/*
 * Client.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CLIENT_H_
#define CLIENT_H_

#include <queue>

	class Network{

	    int _id;
	    boost::mutex * _mutex;
	    std::queue<STOMP::StompFrame*> _stompFramesIn;                                // empty vector of ints
	    std::queue<STOMP::StompFrame*> _stompFramesOut;                                // empty vector of ints

	public:
	    Network (boost::mutex* mutex,std::queue<STOMP::StompFrame*> stompFramesIn , std::queue<STOMP::StompFrame*> stompFramesOut) :_mutex(mutex) ,_stompFramesIn(stompFramesIn),_stompFramesOut(stompFramesOut) {}
		int run(std::string host, unsigned short  port);
		virtual ~Network();


	};






#endif /* CLIENT_H_ */
