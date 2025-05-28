<%-- 
    Document   : review
    Created on : May 25, 2025, 4:03:30 PM
    Author     : tuananh
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Review</title>
    </head>
    <body>
        <form action="review" method="post">
            <h1>${requestScope.item.itemName}</h1>
            <h2>Your rating: </h2>
            <input type="text" name="reviewRating"/><br/>
            <label>Product image(optional): </label>
            <input type="file" name="reviewImage"/>
            <label>Your review: </label><br/>
            <input type="text" name="reviewContent"/>
            <input type="submit" name="Submit"/><br/>
        </form>
    </body>
</html>
