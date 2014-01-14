/*
 * DisconnectFrame.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */



#include "../include2/DisconnectFrame.h"
#include <string>
#include "../include2/StompFrame.h"
using namespace std;


DisconnectFrame::DisconnectFrame(string id) :StompFrame("DISCONNECT"){
	string a="receipt";
	string id1="1.2";
	addHeader(a,id1);

}

DisconnectFrame::~DisconnectFrame() {

}
