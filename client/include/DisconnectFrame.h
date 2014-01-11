/*
 * DisconnectFrame.h
 *
 *  Created on: Jan 11, 2014
 *      Author: arnon
 */

#ifndef DISCONNECTFRAME_H_
#define DISCONNECTFRAME_H_

#include "StompFrame.h"

namespace STOMP {

class DisconnectFrame: public STOMP::StompFrame {
public:
	DisconnectFrame(int receipt);
	DisconnectFrame();
	virtual ~DisconnectFrame();
};

} /* namespace STOMP */

#endif /* DISCONNECTFRAME_H_ */
