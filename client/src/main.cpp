    #include <stdlib.h>
    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
    #include "../encoder/utf8.h"
    #include "../encoder/encoder.h"
	#include <boost/thread.hpp>
    #include "../include/StompFrame.h"
    #include "../include/SendFrame.h"
    #include "../include/Client.h"
    #include "../include/Console.h"
#include <queue>



	using namespace std;
    boost::mutex * _mutex;
    std::queue<STOMP::StompFrame*> stompFramesIn;                                // empty vector of ints
    //std::queue<STOMP::StompFrame*> stompFramesOut;                                // empty vector of ints
    //ConnectionHandler& connectionHandler("123",123);


int main(int argc, char *argv[]){

    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    unsigned short  port = atoi(argv[2]);
	ConnectionHandler connectionHandler(host,port);
    //connectionHandler = connectionHandler(host,port);

    boost::mutex mutex;
    Network task1(&mutex,&stompFramesIn);
    Console task2(&mutex,&stompFramesIn);

    boost::thread th1(&Network::run, &task1, boost::ref(connectionHandler));
    boost::thread th2(&Console::run, &task2, boost::ref(connectionHandler));
    th1.join();
    th2.join();

    return 0;
}
