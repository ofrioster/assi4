/*
 * Header.cpp
 *
 *  Created on: Jan 9, 2014
 *      Author: sagibu
 */

#include "../include2/Header.h"
#include <string>
#include <iostream>
using namespace std;

	Header::Header(string& name,string& value){
		_name=name;
		_value=value;
	}
	Header::~Header(){

	}
	string Header::toString(){

		return _name+":"+_value;
	}
