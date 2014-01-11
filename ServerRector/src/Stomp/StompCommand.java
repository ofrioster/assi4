package Stomp;


enum StompCommand {
    // client-commands
    CONNECT, SEND, SUBSCRIBE, UNSUBSCRIBE, DISCONNECT,

    // server-commands
    CONNECTED, MESSAGE, RECEIPT, ERROR, DISCONNECTED
}