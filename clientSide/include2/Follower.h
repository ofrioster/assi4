/*
 * Follower.h
 *
 *  Created on: Jan 13, 2014
 *      Author: ofri
 */

#ifndef FOLLOWER_H_
#define FOLLOWER_H_



    #include <stdlib.h>
	#include <boost/thread.hpp>
	#include <fstream>
	#include <string>


using namespace std;



class Follower{

	public:
		Follower(string userName,int id);
		string getUserName();
		int getID();
		virtual ~Follower();
private:
		string usersName;
		int id;



	};

#endif /* FOLLOWER_H_ */
