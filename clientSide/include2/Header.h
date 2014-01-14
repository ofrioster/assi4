/*
 * Header.h
 *
 *  Created on: Jan 9, 2014
 *      Author: sagibu
 */

#ifndef HEADER_H_
#define HEADER_H_
#include <string>
using namespace std;
 class Header{

 public:

	Header(string& name,string& value);
	string toString();
	virtual ~Header();


 private:
		string _name;
		string _value;
};



#endif /* HEADER_H_ */
