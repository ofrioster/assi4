/*
 * console.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */
#include <stdlib.h>
    #include <stdlib.h>
    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
    #include "../encoder/utf8.h"
    #include "../encoder/encoder.h"
	#include <boost/thread.hpp>
    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/ConnectFrame.h"
    #include "../include/Client.h"
#include "../include/Console.h"
#include <queue>



	using namespace std;

	Console::Console (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn )
	{
		_mutex=mutex;
		_stompFramesIn=stompFramesIn;
	}

	Console::~Console() {
		// TODO Auto-generated destructor stub
	}


int Console::run (ConnectionHandler& connectionHandler) {
    while (1) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
//        int len=line.length();


        std::string delimiter = " ";
        size_t pos = 0;
        pos = line.find(delimiter);
        string command = line.substr(0, pos);
		line.erase(0, pos + delimiter.length());
        if (command == "login")
        {
            // ...
        	STOMP::hdrmap h;
        	string b;

        	STOMP::ConnectFrame *tmpFrame =  new STOMP::ConnectFrame(h, b);
        	tmpFrame->addheader("accept-version","1.2");

            pos = line.find(delimiter);
            string arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	tmpFrame->addheader("host",arg);

            pos = line.find(delimiter);
            arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	tmpFrame->addheader("login",arg);

            pos = line.find(delimiter);
            arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	tmpFrame->addheader("passcode",arg);
        	_stompFramesOut->push(tmpFrame);


        }
        else if (command == "follow")
        {
           // ...
        }
        else if (command == "unfollow")
        {
           // ...
        }
        else if (command == "tweet")
        {
           // ...
        }
        else if (command == "clients")
        {
           // ...
        }
        else if (command == "stats")
        {
           // ...
        }
        else if (command == "logout")
        {
           // ...
        }
        else if (command == "exit_client")
        {
           // ...
        }
        else if (command == "stop")
        {
           // ...
        }
        else
        {
           // ...
            std::cout << "Something went wrong" << std::endl;

        }





    }
}



