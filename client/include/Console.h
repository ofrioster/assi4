/*
 * console.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CONSOLE_H_
#define CONSOLE_H_


class Console {

    int _id;
    boost::mutex * _mutex;


public:
	Console (boost::mutex* mutex) :_mutex(mutex) {}
	virtual ~Console();
	int run ();


};

#endif /* CONSOLE_H_ */
