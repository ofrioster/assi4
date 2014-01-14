/*
 * UnSubscribeFrame.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef UNSUBSCRIBEFRAME_H_
#define UNSUBSCRIBEFRAME_H_


#include <string>
#include "StompFrame.h"
using namespace std;

class UnSubscribeFrame:public StompFrame {

public:

	UnSubscribeFrame(string& id,string& username);
	virtual ~UnSubscribeFrame();

};

#endif /* UNSUBSCRIBEFRAME_H_ */
