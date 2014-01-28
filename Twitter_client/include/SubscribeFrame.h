/*
 * SubscribeFrame.h
 *
 *  Created on: Jan 11, 2014
 *      Author: arnon
 */

#ifndef SUBSCRIBEFRAME_H_
#define SUBSCRIBEFRAME_H_
#include "../include/StompFrame.h"

namespace STOMP {

class SubscribeFrame: public STOMP::StompFrame {
public:
	SubscribeFrame();
	SubscribeFrame(string destination, int id);
	virtual ~SubscribeFrame();
};

} /* namespace STOMP */

#endif /* SUBSCRIBEFRAME_H_ */
