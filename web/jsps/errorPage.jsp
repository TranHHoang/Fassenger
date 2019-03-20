<%-- 
    Document   : errorPage
    Created on : Mar 20, 2019, 1:41:11 PM
    Author     : TranHoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head> 
        <title>Error</title> 
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <link href="//fonts.googleapis.com/css?family=Courgette" rel="stylesheet" type="text/css" /> 
        <link href="../styles/errorStyle.css" rel="stylesheet" type="text/css" media="all" /> 
    </head> 
    <body style="background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), 
          url('https://source.unsplash.com/featured/1280x720/?nature,travel,wallpapers,backgrounds,weather,technology') no-repeat fixed center; background-size: cover"> 
        <div class="wrap"> 
            <div class="logo"> 
                <h1>${errorCode}</h1> 
                <p>${errorMessage}</p> 
                <div class="sub"> 
                    <p><a href="../">Back</a></p> 
                </div> 
            </div> 
        </div> 
    </body>
</html>