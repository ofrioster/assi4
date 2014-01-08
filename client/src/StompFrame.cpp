/*
 * StompFrame.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/StompFrame.h"

namespace STOMP {
// constructors


StompFrame::StompFrame(string cmd, hdrmap h, string b){
    m_command=cmd;
    m_headers=h;
    m_body=b;
    };

StompFrame::~StompFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */

