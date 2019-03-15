<%-- 
    Document   : index
    Created on : Oct 23, 2017, 3:24:07 PM
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
            
            <s:if test="hasError">
                Error Message: <s:property value="errorMessage"/>
            </s:if>
                
            <s:if test="hasActionErrors()">
                <div class="errors">
                   <s:actionerror/>
                </div>
            </s:if>
            
            
             <s:form action="product">  
              <s:textfield name="idInput" label="Product Id"></s:textfield>  
              <s:textfield name="name" label="Product Name"></s:textfield>  
              <s:textfield name="price" label="Product Price"></s:textfield>  
              <s:submit value="save"></s:submit>  
             </s:form>  
    </body>
</html>
