/*
 * SubscribeFrame.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */


#include "../include2/SubscribeFrame.h"
#include <string>
#include "../include2/StompFrame.h"
using namespace std;

SubscribeFrame::SubscribeFrame( string& id, string& username):StompFrame("SUBSCRIBE") {
	string a="destination";
	string b="id";
	addHeader(a,username);
	addHeader(b,id);


}

SubscribeFrame::~SubscribeFrame() {

}

