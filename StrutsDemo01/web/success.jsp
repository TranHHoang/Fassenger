<%-- 
    Document   : welcome
    Created on : Oct 23, 2017, 3:34:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ taglib uri="/struts-tags" prefix="s" %>  
        
        <h1>Success</h1>
  
        Product Id:<s:property value="id"/><br/>  
        Product Name:<s:property value="name"/><br/>  
        Product Price:<s:property value="price"/><br/>
    </body>
</html>
