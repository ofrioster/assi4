    #include <stdlib.h>
    #include <boost/locale.hpp>
    #include "../include/ConnectionHandler.h"
    #include "../encoder/utf8.h"
    #include "../encoder/encoder.h"
#include <boost/thread.hpp>


	using namespace std;

    /**
    * This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
    */
	class Network{

	    int _id;
	    boost::mutex * _mutex;


	public:
	    Network (boost::mutex* mutex) :_mutex(mutex) {}


		int run (std::string host, unsigned short  port ) {

        ConnectionHandler connectionHandler(host, port);
        if (!connectionHandler.connect()) {
            std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
            return 1;
        }

        Encoder encoder;

        //From here we will see the rest of the ehco client implementation:
        while (1) {
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            int len=line.length();
            if (!connectionHandler.sendLine(line)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
            std::cout << "Sent " << len+1 << " bytes to server" << std::endl;


            // We can use one of two options to read data from the server:
            // 1. Read a fixed number of characters.
            // 2. Read a line (up to the newline character using getLine).
            std::string answer;
            if (!connectionHandler.getLine(answer)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }

            len=answer.length();
            // When we filled the answer buffer from the socket we filled up to the \n char (including), so we truncate last character.
            answer.resize(len-1);
            std::cout << "Reply: " << answer << " " << len << " bytes " << std::endl << std::endl;
            if (answer == "bye") {
                std::cout << "Exiting...\n" << std::endl;
                break;
            }
        }
        return 0;
    }

	};

int main(int argc, char *argv[]){


    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    unsigned short  port = atoi(argv[2]);


    boost::mutex mutex;
    Network task1(&mutex);
    //Task task2(2, &mutex);

    boost::thread th1(&Network::run, &task1, host, port);
    //boost::thread th2(&Task::run, &task2);
    th1.join();
    //th2.join();
    return 0;
}


