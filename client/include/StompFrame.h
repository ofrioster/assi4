/*
 * StompFrame.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef STOMPFRAME_H_
#define STOMPFRAME_H_

#include <string>
#include <map>
#include <iostream>
#include <sstream>

using namespace std;


namespace STOMP {

/* STOMP Frame header map */
typedef std::map<string, string> hdrmap;




class StompFrame {

protected:

std::string m_command;
hdrmap m_headers;
std::string m_body;


public:
	StompFrame();
	StompFrame(string cmd);
	StompFrame(string cmd, hdrmap h, string b);
	virtual ~StompFrame();
	string toSend();
	void addheader(string headerName,string headerValue);
	string readheader(string headerName);
};

} /* namespace STOMP */

#endif /* STOMPFRAME_H_ */
