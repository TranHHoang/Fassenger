<%-- 
    Document   : home
    Created on : Mar 22, 2019, 2:59:24 PM
    Author     : Kiruu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Student name: Dam Son Tung</h1>
        <h1>Student id: HE130134</h1>
        <h1>
            <form action="MenuServlet" method="POST">
                Enter your name 
                <input type="text" name="userName"/>
                <input type="submit" value="Start"/>
            </form>
            
        </h1>
    </body>
</html>
