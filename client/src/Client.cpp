    #include "../include/Client.h"

	using namespace std;


	Network::~Network() {
	}
	Network::Network(int number)
	{
		_id = number;
	}


		int Network::run (ConnectionHandler& connectionHandler, std::map<string, int> folowing,HTMLwrite& htmlwrite,int& receiptId) {

        while (1) {

        	std::string answer;
            if (!connectionHandler.getFrameAscii(answer,'\0')) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                return 2;
            }
            try{

				size_t pos = 0;
				std::string delimiter = "\n";
				pos = answer.find(delimiter);
				if (pos == 0){
					answer = answer.substr(1,(answer.length() - 1));
					pos = answer.find(delimiter);
				}

				string command = answer.substr(0, pos);
				//cout<< "command:"  <<command<<endl;
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
						cout << "No longer following " << answer << endl;
					}else
					{
					if (headers.find("destination") != headers.end()){
					 cout << headers.find("destination")->second << " Sent a Messege" << endl;
					 cout << answer << endl;
					 htmlwrite.addTweet(headers.find("destination")->second,answer,ctime (&rawtime));
					}else{
						 cout << "missing header" << endl;
					}
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
					strcpy (a_char,headerValue.c_str());
					int id = atoi(a_char);

					if (id == receiptId){
						return 0;
					}else{
						cout<<"problem with RECEIPT"<<endl;
					}

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

				}else if (command.compare("CONNECTED")== 0){

					pos = answer.find(":");
					string headerName = answer.substr(0, pos);
					answer.erase(0, pos + 1);

					pos = answer.find("\n");
					string headerValue = answer.substr(0, pos);
					answer.erase(0, pos + 1);

					cout<<"Login successfully."<<endl;

				}else{
					//std::cout << "Reply: " << command  << std::endl;
				}

				if (answer == "bye") {
					std::cout << "Exiting...\n" << std::endl;
					break;
				}
            }catch(int e){
                cout << "Standard exception: " << e << endl;
            }
        }

        return 0;
    }




