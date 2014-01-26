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
    //boost::mutex * _mutex;
    //std::queue<STOMP::StompFrame*> stompFramesIn;                                // empty vector of ints
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

		//STOMP::ConnectFrame *tmpFrame1 =  new STOMP::ConnectFrame();
		STOMP::ConnectFrame tmpFrame(h,"");
		connectionHandler.sendFrameAscii(tmpFrame.toSend(),'\0');
		//tmpFrame->~StompFrame();
		h.clear();
		HTMLwrite htmlwrite(user);
//    	connectionHandler.sendBytes("\n",1);


        std::string answer;
        if (!connectionHandler.getFrameAscii(answer,'\0')) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            return 2;
        }

		size_t pos = 0;
		std::string delimiter = "\n";
		pos = answer.find(delimiter);
		if (pos == 0){
			answer = answer.substr(1,(answer.length() - 1));
			pos = answer.find(delimiter);
		}

		string command = answer.substr(0, pos);
		answer.erase(0, pos + delimiter.length());

        if (command.compare("CONNECTED")== 0){

        					pos = answer.find(":");
        					string headerName = answer.substr(0, pos);
        					answer.erase(0, pos + 1);

        					pos = answer.find("\n");
        					string headerValue = answer.substr(0, pos);
        					answer.erase(0, pos + 1);

        					cout<<"Login successfully. (main)"<<endl;




    //connectionHandler = connectionHandler(host,port);
	std::map<string, int> folowing;
    //boost::mutex mutex;
    int receiptId = -1;
    Console task1(1);
    Network task2(2);

    boost::thread th1(&Console::run, &task1, boost::ref(connectionHandler),boost::ref(folowing),boost::ref(close),boost::ref(user),boost::ref(receiptId));
    boost::thread th2(&Network::run, &task2, boost::ref(connectionHandler),boost::ref(folowing),boost::ref(htmlwrite),boost::ref(receiptId));

    th1.join();
//    cout<< "th1 quit"<<endl;
    th2.join();
//    cout<< "th2 quit"<<endl;
    connectionHandler.close();
    //task1.~Console();
    //task2.~Network();
//    cout<< "    connectionHandler.close();"<<endl;
    htmlwrite.print();
    if (close == true){
        //connectionHandler();
        return 0;
    }
        }else if (command.compare("ERROR")== 0){
			STOMP::hdrmap headers;

			pos = answer.find(":");
			string headerName = answer.substr(0, pos);
			answer.erase(0, pos + 1);

			pos = answer.find("\n");
			string headerValue = answer.substr(0, pos);
			answer.erase(0, pos + 1);
			headers.insert(std::make_pair(headerName , headerValue));

			if (headers.find("message") != headers.end()){
			 cout << headers.find("message")->second << endl;
			}
		    connectionHandler.close();

        }
	    }else{
	        std::cerr << "Cannot connect to " << "host" << ":" << "port" << std::endl;


	    }

	    }
	    else{
	        cout<< "Wrong Command \n You did not loging yet"<<endl;
	    }
	}

    return 0;
}
