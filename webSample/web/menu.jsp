<%-- 
    Document   : menu
    Created on : Mar 22, 2019, 3:18:22 PM
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
        <a href="/webSample">Home</a>
        <a href="#">Subjects</a>
        <a href="#">Update subjects</a>
        <h1>Welcome <%=request.getParameter("userName")%></h1>
        <p>Select a semester </p>
        <form>
            <select name="semester">
                <option>--Select--</option>
                <option value="All">All</option>
                <option value="SP17">Spring 2017</option>
                <option value="SP16">Spring 2016</option>
                <option value="SU17">Summer 2017</option>
            </select>
            <input type="submit" value="Search"/>
        </form>
        
    </body>
</html>
