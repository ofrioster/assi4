/*
 * DisconnectFrame.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef DISCONNECTFRAME_H_
#define DISCONNECTFRAME_H_

#include <string>
#include "StompFrame.h"
using namespace std;


class DisconnectFrame :public StompFrame{

public:

	DisconnectFrame(string id);
	virtual ~DisconnectFrame();

};


#endif /* DISCONNECTFRAME_H_ */
