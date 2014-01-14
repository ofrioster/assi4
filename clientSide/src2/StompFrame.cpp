/*
 * StompFrame.cpp
 *
 *  Created on: Jan 9, 2014
 *      Author: sagibu
 */
#include <string>
#include <vector>
#include <iostream>
#include "../include2/StompFrame.h"
using namespace std;

StompFrame::StompFrame(){
	}
StompFrame::StompFrame(string command){
		_command=command;
	}

void StompFrame::addHeader(string& name,string& value){
		Header* h=new Header(name,value);
		headers.push_back(h);


	}
void StompFrame::addBody(string& body){
		_body=body;
	}
string StompFrame::toString(){
		string ans=_command+'\n';
		for(int i=0 ;i<headers.size();i++){
			ans+=headers.at(i)->toString()+'\n';
		}

		ans+="\n";
		if(!_body.empty()){
		ans+=_body;
		ans+="\n";
		}
		ans+="\n";
		ans+='\0';

		return ans;
	}
void StompFrame::print(){
		for(unsigned i=0;i<headers.size();i++){
			cout<<headers.at(i)->toString()<<endl;
		}
	}
StompFrame::~StompFrame(){
	{
			for(int i=0;i<headers.size();i++)
				delete headers.at(i);
		}
}



