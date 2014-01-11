/*
 * StompFrame.cpp
 *
 *  Created on: Jan 8, 2014
 *      Author: arnon
 */

#include "../include/StompFrame.h"

namespace STOMP {
// constructors

StompFrame::StompFrame(string cmd){
	  m_command="";
//    m_headers= *(new hdrmap);
//    m_body="";
    };

StompFrame::StompFrame(string cmd, hdrmap h, string b){
    m_command=cmd;
    m_headers=h;
    m_body=b;
    };

StompFrame::~StompFrame() {
	// TODO Auto-generated destructor stub
}
string StompFrame::toSend() {
	string ret = m_command;
		   ret.append("\n");
	   for( std::map<string, string>::iterator ii=m_headers.begin(); ii!=m_headers.end(); ++ii)
	   {
		   ret.append(ii->first);
		   ret.append(":");
		   ret.append(ii->second);
		   ret.append("\n");
	       //cout << ii->first << ": " << ii->second << endl;
	       //cout << (*ii).first << ": " << (*ii).second << endl;
	   }
	   ret.append("\n");
	   ret.append(m_body);
	   ret.append("\n");
	   ret.append("\0");
	return ret;
}

void StompFrame::addheader(string headerName,string headerValue){
	m_headers.insert(std::make_pair(headerName,headerValue));
}

string StompFrame::readheader(string headerName){
	if (m_headers.find(headerName)!= m_headers.end()){
		return m_headers.find(headerName)->second;
	}
	return "/0";
}

} /* namespace STOMP */

