/*
 * SubscribeFrame.h
 *
 *  Created on: Jan 11, 2014
 *      Author: arnon
 */

#ifndef UNSUBSCRIBEFRAME_H_
#define UNSUBSCRIBEFRAME_H_
#include "../include/StompFrame.h"

namespace STOMP {

class UnsubscribeFrame: public STOMP::StompFrame {
public:
	UnsubscribeFrame();
	UnsubscribeFrame(int id);
	virtual ~UnsubscribeFrame();
};

} /* namespace STOMP */

#endif /* UNSUBSCRIBEFRAME_H_ */
