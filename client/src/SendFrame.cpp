/*
 * SendFrame.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/SendFrame.h"

namespace STOMP {



SendFrame::SendFrame(hdrmap h, string b) : StompFrame("SEND",  h,  b) {
	// TODO Auto-generated constructor stub

}

SendFrame::~SendFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */
