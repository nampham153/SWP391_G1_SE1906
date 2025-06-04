<%-- 
    Document   : login
    Created on : Jun 3, 2025, 11:19:55 PM
    Author     : tuananh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <form action="login" method="post">
            Phone number: <input type="text" name="phone"/><br/>
            Password: <input type="password" name="password"/><br/>
            <input type="submit" value="Login"/>
        </form>
    </body>
</html>
