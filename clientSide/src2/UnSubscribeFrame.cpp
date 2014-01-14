/*
 * UnSubscribeFrame.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */


#include "../include2/UnSubscribeFrame.h"
#include <string>
#include "../include2/StompFrame.h"
using namespace std;


UnSubscribeFrame::UnSubscribeFrame(string& id,string& username):StompFrame("UNSUBSCRIBE") {
	string a="id";
	addHeader(a,id);
}

UnSubscribeFrame::~UnSubscribeFrame() {
}

