/*
 * SubscribeFrame.cpp
 *
 *  Created on: Jan 11, 2014
 *      Author: arnon
 */
#include <string>
#include "../include/UnsubscribeFrame.h"
#include <stdlib.h>
#include <stdio.h>

namespace STOMP {

UnsubscribeFrame::UnsubscribeFrame() : StompFrame("UNSUBSCRIBE") {
	// TODO Auto-generated constructor stub

}

UnsubscribeFrame::UnsubscribeFrame(int id) : StompFrame("UNSUBSCRIBE"){
	  char buffer [10];
	  sprintf(buffer, "%d",id);
	  addheader("id",buffer);


}

UnsubscribeFrame::~UnsubscribeFrame() {
	// TODO Auto-generated destructor stub
}

} /* namespace STOMP */
