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
Network::Network(std::string host, unsigned short port){
	ConnectionHandler connection_Handler(host,port);
}
void Network::run(std::string host, unsigned short port){

	string test;
	while (!this->isClientOnline){
		/*  for (int i= 0; i < 100; i++){
			//  std::cout << i << ") Task " << "Network" << " is working" << std::endl;
		        }
		        boost::this_thread::yield(); //Gives up the remainder of the current thread's time slice, to allow other threads to run.
		        cout<<"net"<<endl;
		        cin>>test;*/
		  //      cout<<"net"<<endl;
		        /*
		        		if(test.size()>0){
		        			size_t pos = 0;
		        				string delimiter="\n";
		        					const short bufsize = 1024;
		        					char buf[bufsize];
		        					cin.getline(buf, bufsize);
		        					string line(buf);
		        					pos = line.find(delimiter);
		        					string res = line.substr(0, pos);
		        					line.erase(0, pos + delimiter.length());
		        			cout<<"network: "<<test<<endl;
		        			test.clear();
		        		}*/
	}
	cout<<"network loop"<<endl;
	ConnectionHandler connection_Handler(this->host,this->port);
	this->isClientOnline=true;
	connection_Handler.connect();//TODO what if there a problem?
	while (!this->exitClient){
	//	 boost::this_thread::sleep(boost::posix_time::milliseconds(10));
	//send the frame to server
		while (this->tweet.size()>0 && (this->isClientOnline)){
			cout<<"start send frame"<<endl;
			string toSend=tweet[tweet.size()-1].toString();
			vector<char> bytes(toSend.begin(),toSend.end());
			const char* c=&bytes[0];
			bytes.push_back('\n');
	//		this->connection_Handler.sendBytes(c,bytes.size());
			tweet.pop_back();
		}
		//TODO receive message
	}
}
void Network::addStompFrameToSend(StompFrame frame){
	this->tweet.push_back(frame);
	cout<<"frame has been add to network"<< endl;//TODO delete
}
Network::~Network(){
}
bool Network::getIsclientOnLine(){
	return this->isClientOnline;
}
void Network::setClineOnLine(bool status){
	this->isClientOnline=status;
}
bool Network::connect(const string host,const unsigned short port){
//	this->connection_Handler(host,port);
	this->isClientOnline=true;
	//return connection_Handler.connect();

}
void Network::setExitClient(bool status){
	this->exitClient=status;
}
void Network::setConnection(string host, unsigned short port){
	this->host=host;
	this->port=port;
}


