/*
 * DisconnectFrame.cpp
 *
 *  Created on: Jan 11, 2014
 *      Author: arnon
 */

#include "../include/DisconnectFrame.h"
#include <stdio.h>


namespace STOMP {

DisconnectFrame::DisconnectFrame(int receipt): StompFrame("DISCONNECT") {
	  char buffer [10];
	  sprintf(buffer, "%d",receipt);
	  addheader("receipt",buffer);
}

DisconnectFrame::DisconnectFrame(): StompFrame("DISCONNECT") {
	// TODO Auto-generated constructor stub

}

DisconnectFrame::~DisconnectFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */
