/*
 * console.cpp


 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/Console.h"



	using namespace std;

	Console::Console (boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn )
	{
		_mutex=mutex;
		_stompFramesIn=stompFramesIn;

	}

	Console::~Console() {
		// TODO Auto-generated destructor stub
	}


int Console::run (ConnectionHandler& connectionHandler , std::map<string, int> folowing ,std::ofstream& outfile) {
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
        	h.insert(std::make_pair("accept-version","1.2"));

            pos = line.find(delimiter);
            string arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());

        	h.insert(std::make_pair("host",arg));


            pos = line.find(delimiter);
            arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	h.insert(std::make_pair("login",arg));


            pos = line.find(delimiter);
            arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	h.insert(std::make_pair("passcode",arg));


        	STOMP::ConnectFrame *tmpFrame =  new STOMP::ConnectFrame(h,"");
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
//
//        	"<html> \
//        	<head>	\
//        	<title>KarlMarxs tweeter site</title>	\
//        	<style type=\"text/css\"> \
//        	.username{	\
//        		font-weight:bold; \
//        	}\
//        	.tweet{\
//        		font-weight:normal;\
//        		margin-left: 50px;\
//        	}\
//        	.time{\
//        		color: gray;\
//        		margin-left: 35px;\
//        	}\
//        	.entry{\
//        		margin-top: 10px;\
//        		background-color: #AACCFF;
//        	}
//        	</style>
//        	</head>
//        	<body>
//        	<h1>KarlMarx</h1>
//




        }
        else if (command == "follow")
        {
            pos = line.find(delimiter);
            string arg = arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	folowing.insert(std::make_pair(arg,_counter));


        	STOMP::SubscribeFrame *tmpFrame =  new STOMP::SubscribeFrame("/topic/"+arg, _counter);
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');

    		_counter++;

        }
        else if (command == "unfollow")
        {

            pos = line.find(delimiter);
            string arg = arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());

			if (folowing.find(arg)!= folowing.end()){
	        	STOMP::UnsubscribeFrame *tmpFrame =  new STOMP::UnsubscribeFrame(folowing.find(arg)->second);
	        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
			}

        }
        else if (command == "tweet")
        {



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



