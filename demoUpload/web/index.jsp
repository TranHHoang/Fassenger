<%-- 
    Document   : index
    Created on : Mar 14, 2019, 4:10:05 PM
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
        <form action="NewServlet" method="POST" enctype="multipart/form-data">
            <input type="file" name="avatar"/>
            <input type="submit"/>
        </form>
    </body>
</html>
