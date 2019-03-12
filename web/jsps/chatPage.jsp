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
        <script>openConnection()</script>
        <!--fix this change to dynamic-->
    </head>
    <body>
        <input id="textMessage" type="text" />
        <input onclick="sendMessage()" value="Send Message" type="button" /> <br/><br/>
        <textarea id="textAreaMessage" rows="10" cols="50"></textarea>
        <a href="logout">Sign out</a>
    </body>
</html>
