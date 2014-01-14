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

Console::~Console() {
		// TODO Auto-generated destructor stub
	}
void Console::run(Network& network){
	this->network=network;
	size_t pos = 0;
	const short bufsize = 1024;
	while(!this->logoutClinet){
		char buf[bufsize];
		cin.getline(buf, bufsize);
		string line(buf);
		if (line.size()>0){
			size_t pos = 0;
			pos = line.find(delimiter);
			string command = line.substr(0, pos);
			line.erase(0, pos + delimiter.length());
			if (command=="login"){
				login();
			}
			else if (command=="follow"){
				follow();
			}
			else if (command=="unfollow"){
				unfollow();
				}
			else if (command=="tweet"){
				tweet();
				}
			else if (command=="clients"){
				clients();
				}
			else if (command=="stats"){
				stats();
				}
			else if (command=="logout"){
				logout();
				}
			else if (command=="exit client"){
				exitClient();
				}
			else if (command=="stop"){
				stop();
						}

		}
	}
}
void Console::login(){
	/*size_t pos = 0;
	const short bufsize = 1024;
	char buf[bufsize];
	cin.getline(buf, bufsize);
	string line(buf);
	pos = line.find(delimiter);
	string hostIP = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());

	cin.getline(buf, bufsize);
	pos = line.find(delimiter);
	string hostPort = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());

	cin.getline(buf, bufsize);
	pos = line.find(delimiter);
	string login1 = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());

	cin.getline(buf, bufsize);
	pos = line.find(delimiter);
	string passcode = line.substr(0, pos);
	line.erase(0, pos + delimiter.length());
	network.setClineOnLine(true);
	if(!network.connect(hostIP,atoi(hostPort.c_str())));//TODO what if there is a problem with the connection?

	network.setClineOnLine(true);
	ConnectFrame connectFrame(hostIP,login1,passcode);
	network.addStompFrameToSend(connectFrame);
*/
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
