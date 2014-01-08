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
	ConnectFrame(hdrmap h, string b);
	virtual ~ConnectFrame();
};

} /* namespace STOMP */

#endif /* CONNECTFRAME_H_ */
