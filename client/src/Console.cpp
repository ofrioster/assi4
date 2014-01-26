/*
 * console.cpp


 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/Console.h"



	using namespace std;

	Console::Console (int number)
	{
		_id = number;
	}
    //Task (int number) :
	Console::~Console() {
		// TODO Auto-generated destructor stub
	}


int Console::run (ConnectionHandler& connectionHandler , std::map<string, int> folowing,bool& close,string username,int& receiptId) {
    while (1) {
    	if (close){
    		return 0;
    	}

        int _counter;
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


        	STOMP::SubscribeFrame tmpFrame("/topic/"+arg, _counter);
        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');

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
	        	STOMP::UnsubscribeFrame tmpFrame(folowing.find(arg)->second);
	        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');

	        	folowing.erase(folowing.find(arg));

			}else{
	    		cout << "Not following this user:" << arg << endl;
			}

        }
        else if (command == "tweet")
        {

        	STOMP::SendFrame tmpFrame("/topic/"+ username,line);
        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');

        }
        else if (command == "clients")
        {
        	STOMP::SendFrame tmpFrame("/topic/server","clients");
        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');

        }
        else if (command == "stats")
        {
        	STOMP::SendFrame tmpFrame("/topic/server","stats");
        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');

        }
        else if (command == "logout")
        {
        		receiptId=2345678;
	        	STOMP::DisconnectFrame tmpFrame(receiptId);//TODO Change to dynamic
	        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');


	        	return 0;
        }
        else if (command == "exit_client")
        {
    		receiptId=98765;
        	STOMP::DisconnectFrame tmpFrame(receiptId);//TODO Change to dynamic
        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');

        	close = true;
        	return 1;
        }
        else if (command == "stop")
        {
        	STOMP::SendFrame tmpFrame("/topic/server","stop");
        	connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');
        }
        else
        {
           // ...
            std::cout << "Wrong Command" << std::endl;

        }





    }
}



