/*
 * SendFrame.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/SendFrame.h"

namespace STOMP {

SendFrame::SendFrame() : StompFrame("SEND"){}
SendFrame::SendFrame(string destination, string body):StompFrame("SEND"){
addheader("destination",destination);
m_body=body;
}

SendFrame::~SendFrame() {
}

} /* namespace STOMP */
