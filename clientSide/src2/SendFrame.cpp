/*
 * SendFrame.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */


#include "../include2/SendFrame.h"
#include "../include2/StompFrame.h"
#include <string>


SendFrame::SendFrame(string username,string msg): StompFrame("SEND") {
	string a="destination";
	addHeader(a,username);
	addBody(msg);

}

SendFrame::~SendFrame() {

}

