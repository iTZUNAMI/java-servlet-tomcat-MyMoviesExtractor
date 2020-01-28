<%-- 
    Document   : index
    Created on : 20-mar-2012, 11.28.15
    Author     : supremo_zim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
     if(session.getAttribute("user") != null) {
           String redirectURL = application.getContextPath() + "/home.jsp";
            response.sendRedirect(redirectURL);
            return;
        }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index Page</title>
    </head>
    <body>
            <div id="login">
            <input value="Vai al login" type="button" onclick="location.href='login.jsp'">
            </div>
    </body>
</html>
