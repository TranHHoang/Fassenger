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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
    </head>
        <body>
            <h1>Login</h1>
            <form action="login" method="POST">
                Username:<input name="userName" type="text"/> 
                Password:<input name="password" type="text"/>
                <input type="submit" name="action" value="login"/>
            </form>
    
            <h1>Register</h1>
            <form action="register" method="POST" enctype="multipart/form-data">
                Username:<input name="userName" type="text"/> 
                Password:<input name="password" type="text"/>
                File: <input type="file" name="avatar" accept="image/*"/>
                <input type="submit" name="action" value="create" />
            </form>
        </body>

    

</html>
