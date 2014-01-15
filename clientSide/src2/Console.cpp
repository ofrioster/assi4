/*
 * Console.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */



	#include <boost/thread.hpp>
	#include <string>
	#include <iostream>
	#include <vector>
	#include "../include2/StompFrame.h"
    #include "../include2/SendFrame.h"
    #include "../include2/ConnectFrame.h"
    #include "../include2/DisconnectFrame.h"
    #include "../include2/SubscribeFrame.h"
    #include "../include2/UnSubscribeFrame.h"
	#include "../include2/Console.h"
	#include "../include2/Network.h"



	using namespace std;

Console::Console(string delimiter){
	this->counter=0;
	this->delimiter=delimiter;
	this->clientExist=false;
	this->logoutClinet=false;
	this->exitClientClinet=false;
	this->stopClinet=false;
	Network network;

	}
Console::Console(){

}

Console::~Console() {
		// TODO Auto-generated destructor stub
	}
void Console::run(Network& network){
	this->network=network;
	size_t pos = 0;
	const short bufsize = 1024;
/*	while(1){
		  for (int i= 0; i < 100; i++){
		           std::cout << i << ") Task " <<delimiter<<  " is working" << std::endl;
		        }
		        boost::this_thread::yield(); //Gives up the remainder of the current thread's time slice, to allow other threads to run.
		//boost::thread::yield();
		//boost::this_thread::sleep(boost::posix_time::milliseconds(5000));
	}*/
	while(!this->logoutClinet){

		///*****////
/*		string test;
		string fullTest;
		//const short bufsize = 1024;
		char buf1[bufsize];
		vector <string> testVector;
		string word;
		while (1){
	/*		for (int i= 0; i < 100; i++){
			//	std::cout << i << ") Task " << "console" << " is working" << std::endl;
			}
			boost::this_thread::yield(); //Gives up the remainder of the current thread's time slice, to allow other threads to run.
		cin>>test;*/
	//	cin>>buf1;
		//cin>>testVector;
		//fullTest+=test;
	//	if(fullTest.size()>0){
		//	size_t pos = 0;
	/*		pos = fullTest.find(delimiter);


			getline(cin,test);
			cout<<test<<endl;
			test.clear();

/*
			 if(test.size()>0){
							fullTest= fullTest+test;
							testVector.push_back(test);
							cout<<"vector size: "<<testVector.size()<<endl;
						//	test.clear();
						//	test>>buf1;

						}
			if (test.compare("'\n'")){
		//		cout<<"console pos: "<<pos<<endl;
				fullTest.append(test);
				cout<<"console: "<<fullTest<<" size: "<<fullTest.size()<<endl;
				fullTest.clear();
				test.clear();

				string line(buf1);
			//	cout<<"console line: "<<line<<" size: "<<line.size()<<endl;
				while (!testVector.empty()){
					string t=testVector[testVector.size()-1];
					testVector.pop_back();
					word.append(t);
				}

				cout<<"console word: "<<word<<endl;
				word.clear();

			}
			else if(test.compare("' '")){
			//	fullTest.append(" ");
				cout<<"console space: "<<endl;
				test.clear();
			}
			else if(test.size()>0){
				fullTest= fullTest+test;
				testVector.push_back(test);
				cout<<"vector size: "<<testVector.size()<<endl;
				test.clear();
			//	test>>buf1;

			}


	//	}

/*

				size_t pos = 0;
				const short bufsize = 1024;
				char buf[bufsize];
				cin.getline(buf, bufsize);
				string line(buf);
				pos = line.find(delimiter);
				string res = line.substr(0, pos);
				line.erase(0, pos + delimiter.length());
*/








/*
		}
		for (int i= 0; i < 100; i++){
			       }
		      boost::this_thread::yield(); //Gives up the remainder of the current thread's time slice, to allow other threads to run.



		      //***////
		/*      char buf[bufsize];
		cin.getline(buf, bufsize);
		string line(buf);
		if (line.size()>0){
			size_t pos = 0;
			pos = line.find(delimiter);
			string command = line.substr(0, pos);
			line.erase(0, pos + delimiter.length());
		//	cout<<command<<endl;//TODO delete
		 *
		 */
		string command= readLine();
			if (command=="login"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				login();
			}
			else if (command=="follow"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				follow();
			}
			else if (command=="unfollow"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				unfollow();
				}
			else if (command=="tweet"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				tweet();
				}
			else if (command=="clients"){
				cout<<"Receive"<<command<<endl;//TODO delete
				clients();
				}
			else if (command=="stats"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				stats();
				}
			else if (command=="logout"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				logout();
				}
			else if (command=="exit client"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				exitClient();
				}
			else if (command=="stop"){
				cout<<"Receive: "<<command<<endl;//TODO delete
				stop();
						}

		}
	}

void Console::login(){


	string hostIP = readLine();
	cout<<"hostIP: "<<hostIP<<endl;
	string hostPort =readLine();
	cout<<"hostPort: "<<hostPort<<endl;
	string login1 = readLine();
	cout<<"login1: "<<login1<<endl;
	string passcode = readLine();
	cout<<"passcode: "<<passcode<<endl;
//	Network network("a");
	this->network=network;
	//boost::thread th2(&Network::run, &network, (hostIP), (atoi(hostPort.c_str())));
	//th2.join();
	network.setConnection(hostPort,atoi(hostPort.c_str()));
	network.setClineOnLine(true);
	if(!network.connect(hostIP,atoi(hostPort.c_str())));//TODO what if there is a problem with the connection?

	network.setClineOnLine(true);
	ConnectFrame connectFrame(hostIP,login1,passcode);
	network.addStompFrameToSend(connectFrame);
	cout<<"console send login frame"<<endl;

}
void Console::follow(){
	size_t pos = 0;
	const short bufsize = 1024;
	char buf[bufsize];
	cin.getline(buf, bufsize);
	string line(buf);
	pos = line.find(delimiter);
	string clientToFollow = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());

	this->client.addClientToFollow(clientToFollow,this->counter);
	string id=""+this->counter;
	SubscribeFrame subscribeFrame(id,clientToFollow);
	network.addStompFrameToSend(subscribeFrame);
	this->counter++;
}
void Console::unfollow(){
	size_t pos = 0;
	const short bufsize = 1024;
	char buf[bufsize];
	cin.getline(buf, bufsize);
	string line(buf);
	pos = line.find(delimiter);
	string clientToFollow = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());

	this->client.unfollowingClient(clientToFollow);
	string id="";
	UnSubscribeFrame unSubscribeFrame(id,clientToFollow);
	network.addStompFrameToSend(unSubscribeFrame);
}
void Console::tweet(){
	size_t pos = 0;
	const short bufsize = 1024;
	char buf[bufsize];
	cin.getline(buf, bufsize);
	string line(buf);
	pos = line.find(delimiter);
	string message = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());

	SendFrame sendFrame(this->client.getUserName(),message);
	network.addStompFrameToSend(sendFrame);
}
void Console::clients(){
	//TODO also in the server
}
void Console::stats(){
	SendFrame sendFrame(this->client.getUserName(),"stats");
	network.addStompFrameToSend(sendFrame);
}
void Console::logout(){
	//TODO check what they mean
	this->logoutClinet=true;
	DisconnectFrame disconnectFrame("1.2");
	this->clientExist=false;
	network.addStompFrameToSend(disconnectFrame);
	network.setClineOnLine(false);
}
void Console::exitClient(){
	//TODO check what they mean
	this->exitClientClinet=true;
	if (this->clientExist){
		logout();
	}
	network.setExitClient(true);
}
void Console::stop(){
	//TODO also in the server
}
void Console::clientIsConnecting(string usersName,string password,string hostIP,string hostPort){
	/*bool found=false;
	for (int i=0;i<clientsVector.size();i++){
		if(clientsVector[i]->getUserName()==usersName){
			found=true;
		}
	}*/

	if(!this->clientExist){
		Client client(usersName,password,hostIP, hostPort);
		this->client=client;
		clientsVector.push_back(client);
		this->clientExist=true;
	}
}
string Console::readLine(){
	size_t pos = 0;
		const short bufsize = 1024;
		char buf[bufsize];
		cin.getline(buf, bufsize);
		string line(buf);
		pos = line.find(delimiter);
		string res = line.substr(0, pos);
		line.erase(0, pos + delimiter.length());
		return res;
}

