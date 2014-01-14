/*
 * StompFrame.h
 *
 *  Created on: Jan 9, 2014
 *      Author: sagibu
 */

#ifndef STOMPFRAME_H_
#define STOMPFRAME_H_
#include <string>
#include "Header.h"
#include <vector>
using namespace std;


class StompFrame{

public:
	StompFrame(string command);
	StompFrame();
	void addHeader(string& name,string& value);
	void addBody(string& body);
	string toString();
	void print();
	string toSend();
	virtual ~StompFrame();
private:
	string _command;
	vector<Header*> headers;
	string _body;

};



#endif /* STOMPFRAME_H_ */
