# Android-WebSocket-NodeJs
Communication Android-NodeJs (Server Web) , Server propose an URL (to communicate user web, and user Android )

Change with your adresses :

NodeJs :
Android-WebSocket-NodeJs/NodeJs-tchat-/Views/measure.html.twig
            Line 13 :   var socket = io.connect("http://192.168.1.14:9999");// with your Adress URL 

Android : 
Android-WebSocket-NodeJs/AndroidNodeJs/app/src/main/java/com/chakibtemal/fr/android_nodejs/MainActivity.java
            Line 20 : mSocket = IO.socket("http://192.168.1.14:9999");
