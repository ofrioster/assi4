    #include <stdlib.h>
    //#include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
	#include <boost/thread.hpp>
    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/Client.h"
    #include "../include/Console.h"
	#include <queue>
	#include <fstream>
    #include "../include/HTMLwrite.h"
	using namespace std;
    boost::mutex * _mutex;
    std::queue<STOMP::StompFrame*> stompFramesIn;                                // empty vector of ints
    //std::queue<STOMP::StompFrame*> stompFramesOut;                                // empty vector of ints
    //ConnectionHandler& connectionHandler("123",123);


int main(int argc, char *argv[]){


    std::string host;
    unsigned short  port;
    std::string user;

    bool close = false;
	while(!close){

        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);

		STOMP::hdrmap h;
	    //std::string delimiter = " ";
	    size_t pos = 0;
	    pos = line.find(' ');
	    string command = line.substr(0, pos);
		line.erase(0, pos + 1);


	    if (command == "login")
	    {
	        // ...
	    	h.insert(std::make_pair("accept-version","1.2"));

	        pos = line.find(' ');
	        string arg = line.substr(0, pos);
			line.erase(0, pos + 1);

	    	h.insert(std::make_pair("host",arg));
	    	host = arg;

	        pos = line.find(' ');
	        arg = line.substr(0, pos);
	        line.erase(0, pos + 1);
	    	  char a_char[10];
	    	  //strcpy(array, s.c_str());
	    	  strcpy (a_char,arg.c_str());
	    	  //cout<<"As an integer: "<<atoi(a_char);
	    	  port = atoi(a_char);

		        pos = line.find(' ');
		        user = line.substr(0, pos);
		        line.erase(0, pos + 1);
		    	h.insert(std::make_pair("login",user));

	        pos = line.find(' ');
	        arg = line.substr(0, pos);
			line.erase(0, pos + 1);
	    	h.insert(std::make_pair("passcode",arg));



	    ConnectionHandler connectionHandler(host,port);
		std::ofstream outfile;
	    if (connectionHandler.connect()) {
	        //std::cerr << "Cannot connect to " << "host" << ":" << "port" << std::endl;


		STOMP::ConnectFrame *tmpFrame =  new STOMP::ConnectFrame(h,"");
		connectionHandler.sendFrameAscii(tmpFrame->toSend(),'\0');
		tmpFrame->~StompFrame();

		HTMLwrite htmlwrite(user);
//    	connectionHandler.sendBytes("\n",1);


    //connectionHandler = connectionHandler(host,port);
	std::map<string, int> folowing;
    boost::mutex mutex;
    int receiptId = -1;
    Console task1(&mutex);
    Network task2(&mutex);

    boost::thread th1(&Console::run, &task1, boost::ref(connectionHandler),boost::ref(folowing),boost::ref(close),boost::ref(user),boost::ref(receiptId));
    boost::thread th2(&Network::run, &task2, boost::ref(connectionHandler),boost::ref(folowing),boost::ref(htmlwrite),boost::ref(receiptId));


    th1.join();
    cout<< "th1 quit"<<endl;
    th2.join();
    cout<< "th2 quit"<<endl;
    connectionHandler.close();
    cout<< "    connectionHandler.close();"<<endl;
    htmlwrite.print();
    if (close == true){
        //connectionHandler();
        return 0;
    }
	    }else{
	        std::cerr << "Cannot connect to " << "host" << ":" << "port" << std::endl;


	    }
	    }
	    else{
	        cout<< "wrong command"<<endl;



	    }
	}
    return 0;
}
