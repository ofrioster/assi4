assi4
=====
add git to eclipse:
http://www.youtube.com/watch?v=U1kXlahkwd4

or use the program (like the on on windows): cola git GUI

good example:
https://github.com/YaroslavGaponov/StompLib/blob/master/stomp/src/stomp/client/StompClient.java
https://github.com/jjeffery/stomp

server example:
http://code.google.com/p/fourm/source/browse/trunk/StompServer/Domain/?r=105#Domain


to run server:
* ant compile
* ant run -Darg0=50001
ServerRector:
* ant run -Darg0=50001 -Darg0=6



commands:

login 172.20.10.3 50002 ofri1 passofri1
login 172.20.10.3 50001 ofri1 passofri1

socat -v -x TCP-LISTEN:50002 TCP:127.0.0.1:50001

can be add:
to check if connection is terminated in server, then close the connection.
