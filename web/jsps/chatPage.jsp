<%-- 
    Document   : chatPage
    Created on : Mar 5, 2019, 8:53:37 AM
    Author     : Kiruu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat page</title>

        <script src="./scripts/chatroom.js"></script>
        <script>
            var webSocket = null;

            function openConnection() {
                if (webSocket === null) {
                    webSocket = new WebSocket("ws://localhost:8080/Fassenger/chatroom");

                    webSocket.onmessage = event => {
                        textAreaMessage.value += event.data + " \n";
                    };

                    webSocket.onclose = () => webSocket = null;
                }
            }

            function sendMessage() {
                if (webSocket !== null && webSocket.readyState == WebSocket.OPEN) {
                    webSocket.send(textMessage.value);
                    textMessage.value = "";
                }
            }
            openConnection();
        </script>
        <!--fix this change to dynamic-->
    </head>
    <body>
        <input id="textMessage" type="text" />
        <input onclick="sendMessage()" value="Send Message" type="button" /> <br/><br/>
        <textarea id="textAreaMessage" rows="10" cols="50"></textarea>
        <a href="logout">Sign out</a>
    </body>
</html>
