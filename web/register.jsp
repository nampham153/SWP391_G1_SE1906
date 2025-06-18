<%-- 
    Document   : register
    Created on : Jun 14, 2025, 3:39:02 PM
    Author     : namp0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home | E-Shopper</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
</head>
<body>
<section id="form">
    <div class="container">
        <div class="row">
            <div class="col-sm-4 col-sm-offset-1">
                <div class="login-form">
                    <h2>Already have an account?</h2>
                    <a href="login" class="btn btn-default">Login Now</a>
                </div>
            </div>
            <div class="col-sm-1">
                <h2 class="or">OR</h2>
            </div>
            <div class="col-sm-4">
                <div class="signup-form">
                    <h2>New User Signup!</h2>

                    <c:if test="${not empty message}">
                        <div style="color:red; margin-bottom: 10px;">${message}</div>
                    </c:if>

                    <form action="register" method="POST">
                        <input type="text" name="phone" placeholder="Phone Number" pattern="0\d{10}" title="Phone number must start with 0 and be 10 digits" required />
                        <input type="password" name="password" placeholder="Password (min 6 characters)" minlength="6" required />

                        <!-- Optional fields you might remove later -->
                        <input type="text" name="name" placeholder="Full Name" />
                        <input type="email" name="email" placeholder="Email Address" />
                        <input type="date" name="birthdate" />
                        <div class="form-group">
                            <label>Gender:</label><br/>
                            <label class="radio-inline"><input type="radio" name="gender" value="1" checked> Male</label>
                            <label class="radio-inline"><input type="radio" name="gender" value="0"> Female</label>
                        </div>
                        <!-- End optional -->

                        <button type="submit" class="btn btn-default">Signup</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
