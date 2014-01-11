/*
 * ConnectFrame.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/ConnectFrame.h"

namespace STOMP {


ConnectFrame::ConnectFrame() : StompFrame("CONNECT"){}
ConnectFrame::ConnectFrame(hdrmap h, string b) : StompFrame("CONNECT",  h,  b){}
//ConnectFrame::ConnectFrame(std::string& host, std::string& login,std::string& passcode){
//	StompFrame("CONNECT");
//	addheader("host",host);
//	addheader("login",login);
//	addheader("passcode",passcode);
//}


ConnectFrame::~ConnectFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */
