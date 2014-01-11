/*
 * SubscribeFrame.cpp
 *
 *  Created on: Jan 11, 2014
 *      Author: arnon
 */
#include <string>
#include "../include/SubscribeFrame.h"
#include <stdlib.h>
#include <stdio.h>

namespace STOMP {

SubscribeFrame::SubscribeFrame() : StompFrame("SUBSCRIBE") {
	// TODO Auto-generated constructor stub

}

SubscribeFrame::SubscribeFrame(string destination, int id) : StompFrame("SUBSCRIBE"){
	  addheader("destination",destination);
	  char buffer [10];
	  sprintf(buffer, "%d",id);
	  addheader("id",buffer);


}

SubscribeFrame::~SubscribeFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */