/*
 * ConnectFrame.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */


#include "../include2/StompFrame.h"
#include <string>
#include "../include2/ConnectFrame.h"
#include <iostream>
using namespace std;


ConnectFrame::ConnectFrame(string host, string login, string pass):StompFrame("CONNECT"){

		string name="accept-version";
		string v="1.2";
		addHeader(name,v);
		name="host";
		addHeader(name,host);
		name="login";
		addHeader(name,login);
		name="passcode";
		addHeader(name,pass);

	}

