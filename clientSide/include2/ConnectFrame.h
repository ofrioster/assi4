/*
 * ConnectFrame.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef CONNECTFRAME_H_
#define CONNECTFRAME_H_

	#include <stdlib.h>
    #include <stdlib.h>
	#include <boost/thread.hpp>
	#include <queue>
	#include <fstream>
	#include <string>
	#include "StompFrame.h"


using namespace std;

class ConnectFrame: public StompFrame{
public:
ConnectFrame(string host, string login, string pass);

};
#endif
