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


* to make sure that there is no 2 ID subscribe the same person
* put the clients in hashMap?


כאשר שולחים הודעת STOMP:

SEND

צריך לכלול בdestination את הקידומת topic מהסיבה שקיימות קידומות נוספות כגון queue

בכל מקרה אפילו שאין לנו אפשרות לקידומות נוספות אלו, צריך להשתמש בפרוטוקול הסטנדרטי.
