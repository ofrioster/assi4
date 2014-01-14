/*
 * Network.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#include "../include2/Client.h"
#include "../include2/Network.h"
#include "../include2/ConnectionHandler.h"
#include <string>
#include <iostream>
#include <vector>
#include <boost/thread.hpp>


using namespace std;

/*
Network::Network(ConnectionHandler Connection_Handler){
	this.Connection_Handler=Connection_Handler;
}
*/
Network::Network(){

}
Network::Network(string delimiter){
 this->exitClient=false;
}
void Network::run(){
	while (!this->exitClient){
	//send the frame to server
		while (this->tweet.size()>0 && (this->isClientOnline)){
			string toSend=tweet[tweet.size()-1].toString();
			vector<char> bytes(toSend.begin(),toSend.end());
			const char* c=&bytes[0];
			bytes.push_back('\n');
			this->connection_Handler.sendBytes(c,bytes.size());
			tweet.pop_back();
		}
		//TODO receive message
	}
}
void Network::addStompFrameToSend(StompFrame frame){
	this->tweet.push_back(frame);
}
Network::~Network(){
}
bool Network::getIsclientOnLine(){
	return this->isClientOnline;
}
void Network::setClineOnLine(bool status){
	this->isClientOnline=status;
}
bool Network::connect(string host,unsigned short port){
	this->connection_Handler(host,port);
	this->isClientOnline=true;
	return connection_Handler.connect();

}
void Network::setExitClient(bool status){
	this->exitClient=status;
}
