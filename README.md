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


commands:

login 10.0.0.11 50002 ofri1 passofri1

socat -v -x TCP-LISTEN:50002 TCP:127.0.0.1:50001


