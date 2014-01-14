/*
 * SubscribeFrame.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef SUBSCRIBEFRAME_H_
#define SUBSCRIBEFRAME_H_


#include <string>
#include "StompFrame.h"
using namespace std;

class SubscribeFrame:public StompFrame {

public:

	SubscribeFrame(string& id, string& username);
	virtual ~SubscribeFrame();

};


#endif /* SUBSCRIBEFRAME_H_ */
