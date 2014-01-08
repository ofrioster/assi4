/*
 * ConnectFrame.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/ConnectFrame.h"

namespace STOMP {

ConnectFrame::ConnectFrame(hdrmap h, string b) :
StompFrame("CONNECT",  h,  b)
{
	// TODO Auto-generated constructor stub

}

ConnectFrame::~ConnectFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */
