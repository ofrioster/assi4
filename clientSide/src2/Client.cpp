/*
 * Client.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#include "../include2/Client.h"
#include <string>
#include <iostream>
#include <vector>


using namespace std;


Client::Client(){
	}
Client::Client(string usersName,string password,string hostIP,string hostPort){
	this->usersName=usersName;
	this->password=password;
	this->hostIP=hostIP;
	this->hostPort=hostPort;
}


string Client::getUserName(){
		return this->usersName;
	}
string Client::getPassword(){
	return this->password;
}
string Client::getHostIP(){
	return this->hostIP;
}
string Client::getHosrPort(){
	return this->hostPort;
}
/*
void Client::addClientToFollow(Client client){
	this->follow.push_back(client);
}
void Client::unfollowingClient(Client client){
	for (int i=0;  i<this->follow.size();i++){
		if (this->follow[i].getUserName()==client.getUserName()){
			this->follow.erase(follow.begin()+i);
		}
	}
}*/

void Client::newTweet(string tweet){
	this->tweet.push_back(tweet);
}
bool Client::getIsClientOnline(){
	return this->isClientOnline;
}
void Client::setClientIsOnline(bool isClientOnline){
	this->isClientOnline=isClientOnline;
}
//void Client::exitClient(); //TODO
Client:: ~Client(){

}
void Client::addClientToFollow(string clientName,int id){
	Follower follower(clientName,id);
	this->follow.push_back(follower);
}
void Client::unfollowingClient(string clientName){
	for (int i=0; i<follow.size();i++){
		if (follow[i].getUserName()==clientName){
			follow.erase(follow.begin()+i,follow.begin()+i);
		}
	}
}
int Client::getFollowerID(string clientName){
	int res=-1;
	for (int i=0; i<follow.size();i++){
			if (follow[i].getUserName()==clientName){
				return this->follow[i].getID();
			}
		}
	return res;
}
