    #include <stdlib.h>
    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
    #include "../encoder/utf8.h"
    #include "../encoder/encoder.h"
	#include <boost/thread.hpp>
    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/Client.h"


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


		int Network::run (ConnectionHandler& connectionHandler, std::map<string, int> folowing,std::ofstream& outfile) {
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
            std::string line;
            std::string delimiter = "\n";
            size_t pos = 0;
            pos = line.find(delimiter);
            string command = line.substr(0, pos);
    		line.erase(0, pos + delimiter.length());
    		if (command == "MESSAGE"){
    			STOMP::hdrmap headers;
    			for (int var = 0; var < 3; ++var) {
                    pos = line.find(":");
                    string headerName = line.substr(0, pos);
            		line.erase(0, pos + 1);

                    pos = line.find("\n");
                    string headerValue = line.substr(0, pos);
            		line.erase(0, pos + 1);
            		headers.insert(std::make_pair(headerName , headerValue));
				}


       		 cout << headers.find("destination")->second << " Sent a Messege" << endl;
    		 cout << line << endl;

    		}else if (command == "RECEIPT"){
    			STOMP::hdrmap headers;

                pos = line.find(":");
                string headerName = line.substr(0, pos);
        		line.erase(0, pos + 1);
        		if (headerName == "receipt-id"){
                pos = line.find("\n");
                string headerValue = line.substr(0, pos);
        		line.erase(0, pos + 1);
        		char a_char[10];
  	    	  //strcpy(array, s.c_str());
        		strcpy (a_char,headerValue.c_str());
  	    	  //cout<<"As an integer: "<<atoi(a_char);
        		int id = atoi(a_char);

        		if (id == 10){
        			return 0;
        		}
        		}
        		cout<<"problem with recipt"<<endl;
        		//headers.insert(std::make_pair(headerName , headerValue));




    		}else if (command == "ERROR"){
    			STOMP::hdrmap headers;

                pos = line.find(":");
                string headerName = line.substr(0, pos);
        		line.erase(0, pos + 1);

                pos = line.find("\n");
                string headerValue = line.substr(0, pos);
        		line.erase(0, pos + 1);
        		headers.insert(std::make_pair(headerName , headerValue));



    		}else if (command == ""){

    		}else if (command == ""){

    		}else if (command == ""){

    		}



            std::cout << "Reply: " << answer << " " << std::endl << std::endl;
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




