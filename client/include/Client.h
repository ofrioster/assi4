/*
 * Client.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CLIENT_H_
#define CLIENT_H_


	class Network{

	    int _id;
	    boost::mutex * _mutex;


	public:
	    Network (boost::mutex* mutex) :_mutex(mutex) {}
		int run(std::string host, unsigned short  port);
		virtual ~Network();


	};






#endif /* CLIENT_H_ */
