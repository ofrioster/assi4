/*
 * SendFrame.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef SENDFRAME_H_
#define SENDFRAME_H_

#include <string>
#include "StompFrame.h"
using namespace std;

class SendFrame :public StompFrame{

public:
	SendFrame(string username,string msg);
	virtual ~SendFrame();

};



#endif /* SENDFRAME_H_ */
