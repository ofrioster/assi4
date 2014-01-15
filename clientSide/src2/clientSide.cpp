//============================================================================
// Name        : clientSide.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

	#include <iostream>
    #include <stdlib.h>
 //   #include "../include2/ConnectionHandler.h"
	#include <boost/thread.hpp>
    #include "../include2/Network.h"
    #include "../include2/Console.h"
	#include <queue>
	#include <fstream>
	#include <string>

using namespace std;
	boost::mutex * _mutex;
 //   std::queue<STOMP::StompFrame*> stompFramesIn;

int main(int argc, char *argv[]) {
	cout << "!!!Hello World!!!" << endl; // prints !!!Hello World!!!
	Network network("a");
	Console console("'\n'");
	Console console2("1");
	string host= "127.0.0.1";
	string port=("50001");



    boost::thread th1(&Console::run, &console, boost::ref(network));
    boost::thread th2(&Network::run, &network, (host), (atoi(port.c_str())));
   // boost::thread th2(&Network::run, &network);
    th1.join();
    th2.join();
   //	th2.join();
cout<<"now"<<endl;

/*
string host= "127.0.0.1";
	unsigned short port=atoi("50001");
/*
	ConnectionHandler connection_Hendler(host,port);
	if (!connection_Hendler.connect()) {
		cout << "Cannot connect to " << host << ":" << "port"<<port << std::endl;
		        return 1;
		    }

//	string res1="will it work?";
//	char res='1';
//	connection_Hendler.sendBytes(&res,1);


	vector<string> sendVEC;
	string todo1="CONNECT";
	todo1.append("\n");
//	cout<<todo1<<endl;
	string todo2="accept-version:3.5";
	todo2.append("\n");
//	cout<<todo2<<endl;
	string todo3="host:GT3";
	todo3.append("\n");
//	cout<<todo3<<endl;
	sendVEC.push_back(todo1);
	sendVEC.push_back(todo2);
	sendVEC.push_back(todo3);
	string todo4="\n";
	todo4.append("\n");
	todo4.append("\0");
//	string todo4="login:ofri1"+'\n'+"passcode:123"+'\n'+'\n'+'\0';
	for (int i=0;i<sendVEC.size();i++){
		string sendString=sendVEC[i];
		cout<<"sending: "<<sendVEC[i]<<endl;
		vector<char> bytes(sendString.begin(),sendString.end());
			const char* c=&bytes[0];
		//	bytes.push_back('\n');
//				    	   	    	cout<<bytes<<endl;
				    	    	connection_Hendler.sendBytes(c,bytes.size());
	}

	vector<char> bytes;
		    	//   	   	bytes.push_back('\n');
		    	 //  	    	bytes.push_back('\n');
		    	   	    	bytes.push_back('\0');
	const char* c=&bytes[0];
		    	//   	    	cout<<todo->toString()<<endl;
		    	    	connection_Hendler.sendBytes(c,bytes.size());
////****///
	/*
	string answer;
	if (!connection_Hendler.getFrameAscii(answer,'\0')) {
	std::cout << "Disconnected. Exiting...\n" << std::endl;
	}
	std::cout << "ans: " <<answer << std::endl;
	/*cout<< "end main"<<endl;
	StompFrame frame=new StompFrame("command");
//	string res1="will it work?"
	connection_Hendler.sendFrameAscii(frame.toSend(),'\0');*/
//	while(1){
	/*	cout<< connection_Hendler.getBytes()<<endl;
		string answer;
			if (!connection_Hendler.getFrameAscii(answer,'\0')) {
			std::cout << "Disconnected. Exiting...\n" << std::endl;
			}
			std::cout << "ans: " <<answer << std::endl;
	}*/
	while (1){cout<< "end main"<<endl;}//TODO need to add exit
		cout<< "end main"<<endl;

	return 0;
}
