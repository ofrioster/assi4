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


int Console::run (ConnectionHandler& connectionHandler , std::map<string, int> folowing,bool close,string username) {
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

		if (command == "follow")
        {
            pos = line.find(delimiter);
            string arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
        	folowing.insert(std::make_pair(arg,_counter));


        	STOMP::SubscribeFrame *tmpFrame =  new STOMP::SubscribeFrame("/topic/"+arg, _counter);
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
//        	connectionHandler.sendBytes("\n",1);

    		_counter++;

        }
        else if (command == "unfollow")
        {

            pos = line.find(delimiter);
            string arg = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
    		cout << "unfollow:" << arg << endl;
   			if (folowing.find(arg)!= folowing.end()){
	        	STOMP::UnsubscribeFrame *tmpFrame =  new STOMP::UnsubscribeFrame(folowing.find(arg)->second);
	        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
	        	folowing.erase(folowing.find(arg));
			}else{
	    		cout << "Not following this user:" << arg << endl;

			}

        }
        else if (command == "tweet")
        {

        	STOMP::SendFrame *tmpFrame =  new STOMP::SendFrame("/topic/"+ username,line);
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');


        }
        else if (command == "clients")
        {
        	STOMP::SendFrame *tmpFrame =  new STOMP::SendFrame("/topic/server","clients");
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
        }
        else if (command == "stats")
        {
        	STOMP::SendFrame *tmpFrame =  new STOMP::SendFrame("/topic/server","stats");
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
        }
        else if (command == "logout")
        {

	        	STOMP::DisconnectFrame *tmpFrame =  new STOMP::DisconnectFrame(10);//TODO Change to dynamic
	        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
	        	return 0;
        }
        else if (command == "exit_client")
        {
        	STOMP::DisconnectFrame *tmpFrame =  new STOMP::DisconnectFrame(11);//TODO Change to dynamic
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
        	close = true;
        	return 1;
        }
        else if (command == "stop")
        {
        	STOMP::SendFrame *tmpFrame =  new STOMP::SendFrame("/topic/server","stop");
        	connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
        }
        else
        {
           // ...
            std::cout << "Something went wrong" << std::endl;

        }





    }
}



