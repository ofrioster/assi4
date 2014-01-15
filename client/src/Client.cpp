    #include <stdlib.h>
    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
    #include "../encoder/utf8.h"
    #include "../encoder/encoder.h"
	#include <boost/thread.hpp>
    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/Client.h"
    #include "../include/HTMLwrite.h"
#include <time.h>       /* time_t, time, ctime */

	using namespace std;


	Network::~Network() {
		// TODO Auto-generated destructor stub
	}
	Network::Network(boost::mutex* mutex,std::queue<STOMP::StompFrame*>* stompFramesIn)
	{
		_mutex=mutex;
		_stompFramesIn=stompFramesIn;
		//_connectionHandler=connectionHandler.get();
	}


		int Network::run (ConnectionHandler& connectionHandler, std::map<string, int> folowing,HTMLwrite& htmlwrite) {
//	        try
//	        {
	            // Sleep and check for interrupt.
	            // To check for interrupt without sleep,
	            // use boost::this_thread::interruption_point()
	            // which also throws boost::thread_interrupted


//			ConnectionHandler connectionHandler(host, port);

        //From here we will see the rest of the ehco client implementation:
        while (1) {

        	//_stompFramesOut.

//        	if (!_stompFramesOut->empty()){
//        		string tmp = _stompFramesOut->front()->toSend();
//        		_stompFramesOut->pop();
//                if (!connectionHandler.sendFrameAscii(tmp,'\0')) {
//                    std::cout << "Disconnected. Exiting...\n" << std::endl;
//                    break;
//                }
//        	}











//            const short bufsize = 1024;
//            char buf[bufsize];
//            std::string line(buf);
//            int len=line.length();
//            if (!connectionHandler.sendLine(line)) {
//                std::cout << "Disconnected. Exiting...\n" << std::endl;
//                break;
//            }
//            // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
//            std::cout << "Sent " << len+1 << " bytes to server" << std::endl;


            // We can use one of two options to read data from the server:
            // 1. Read a fixed number of characters.
            // 2. Read a line (up to the newline character using getLine).
            //if (connectionHandler.recived()){

            std::string answer;
            if (!connectionHandler.getFrameAscii(answer,'\0')) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                return 2;
            }

            //cout<< "answer:"  <<answer<<endl;
            //std::string line;
            size_t pos = 0;
            std::string delimiter = "\n";

            //while (pos == 0){
            //answer = answer.substr(pos+1,(answer.length() - pos+1));
            pos = answer.find(delimiter);
            //}

            if (pos == 0){
            	answer = answer.substr(1,(answer.length() - 1));
                pos = answer.find(delimiter);
            }

            string command = answer.substr(0, pos);
            cout<< "command:"  <<command<<endl;
            answer.erase(0, pos + delimiter.length());
    		if (command.compare("MESSAGE")== 0){
    			  time_t rawtime;
    			  time (&rawtime);
    			STOMP::hdrmap headers;
    			for (int var = 0; var < 3; ++var) {
                    pos = answer.find(":");
                    string headerName = answer.substr(0, pos);
                    answer.erase(0, pos + 1);

                    pos = answer.find("\n");
                    string headerValue = answer.substr(0, pos);
            		answer.erase(0, pos + 1);
            		headers.insert(std::make_pair(headerName , headerValue));
				}

    			pos = answer.find(" ");
    			if (answer.substr(0, pos).compare("following")== 0){
            		answer.erase(0, pos + 1);
    				cout << "Now following " << answer << endl;
    			}else if (answer.substr(0, pos).compare("unfollowing")== 0){
            		answer.erase(0, pos + 1);
    				cout << "No longer following" << answer << endl;
    			}else
    			{
				 cout << headers.find("destination")->second << " Sent a Messege" << endl;
				 cout << answer << endl;
				 htmlwrite.addTweet(headers.find("destination")->second,answer,ctime (&rawtime));
    		}

    		}else if (command.compare("RECEIPT")== 0){
    			STOMP::hdrmap headers;

                pos = answer.find(":");
                string headerName = answer.substr(0, pos);
        		answer.erase(0, pos + 1);
        		if (headerName.compare("receipt-id")== 0){
                pos = answer.find("\n");
                string headerValue = answer.substr(0, pos);
        		answer.erase(0, pos + 1);
        		char a_char[10];
  	    	  //strcpy(array, s.c_str());
        		strcpy (a_char,headerValue.c_str());
  	    	  //cout<<"As an integer: "<<atoi(a_char);
        		int id = atoi(a_char);

        		if (id == 10){//TODO match with send messege
        			return 0;
        		}
        		if (id == 11){//TODO match with send messege
        			return 1;
        		}
        		}
        		cout<<"problem with RECEIPT"<<endl;
        		//headers.insert(std::make_pair(headerName , headerValue));

    		}else if (command.compare("ERROR")== 0){
    			STOMP::hdrmap headers;

                pos = answer.find(":");
                string headerName = answer.substr(0, pos);
        		answer.erase(0, pos + 1);

                pos = answer.find("\n");
                string headerValue = answer.substr(0, pos);
        		answer.erase(0, pos + 1);
        		headers.insert(std::make_pair(headerName , headerValue));

    		}else if (command.compare("CONNECTED")== 0){

                pos = answer.find(":");
                string headerName = answer.substr(0, pos);
        		answer.erase(0, pos + 1);

                pos = answer.find("\n");
                string headerValue = answer.substr(0, pos);
        		answer.erase(0, pos + 1);

        		cout<<"Login successfully."<<endl;

    		}else if (command == "123456782345678"){

    		}else if (command == "234567890-=4567"){

    		}else{
                std::cout << "Reply: " << command  << std::endl;
    		}

            if (answer == "bye") {
                std::cout << "Exiting...\n" << std::endl;
                break;
            }
            //}
        }


//        }
//        catch(boost::thread_interrupted&)
//        {
//            cout << "Thread is stopped" << endl;
//            return 1;
//        }
        return 0;
    }




