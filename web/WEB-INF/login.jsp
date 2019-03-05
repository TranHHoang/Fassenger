<%-- 
    Document   : login
    Created on : Mar 5, 2019, 8:40:48 AM
    Author     : Kiruu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="login" method="POST" enctype="multipart/form-data">
            Username:<input name="userName" type="text"/> 
            Password:<input name="password" type="text"/>
            File: <input type="file" name="avatar" accept="image/*"/>
            <input type="submit" name="action" value="login" />
            <input type="submit" name="action" value="create" />
        </form>
    </body>
</html>
