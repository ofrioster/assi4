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


		int Network::run (std::string host, unsigned short  port ) {

        ConnectionHandler connectionHandler(host, port);
        if (!connectionHandler.connect()) {
            std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
            return 1;
        }

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




