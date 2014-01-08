/*
 * SendFrame.h
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#ifndef SENDFRAME_H_
#define SENDFRAME_H_
#include "../include/StompFrame.h"

namespace STOMP {

class SendFrame : public STOMP::StompFrame {
public:
	SendFrame(hdrmap h, string b);
	virtual ~SendFrame();
};

} /* namespace STOMP */

#endif /* SENDFRAME_H_ */
