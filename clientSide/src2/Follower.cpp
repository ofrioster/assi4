/*
 * Follower.cpp
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#include "../include2/Follower.h"
#include <string>
#include <iostream>
#include <vector>


using namespace std;


Follower::Follower(string userName,int id){
	this->usersName=userName;
	this->id=id;
}
int Follower::getID(){
	return this->id;
}
string Follower::getUserName(){
	return this->usersName;
}
Follower::~Follower(){

}
