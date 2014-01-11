/*
 * ConnectFrame.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef CONNECTFRAME_H_
#define CONNECTFRAME_H_

#include "../include/StompFrame.h"
namespace STOMP {

class ConnectFrame: public STOMP::StompFrame {
public:
	ConnectFrame();
	ConnectFrame(hdrmap h, string b);
	//ConnectFrame(std::string& host, std::string& login,std::string& passcode);
	virtual ~ConnectFrame();
};

} /* namespace STOMP */

#endif /* CONNECTFRAME_H_ */
