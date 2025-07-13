<%-- 
    Document   : login
    Created on : Jun 3, 2025, 11:19:55 PM
    Author     : tuananh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home | E-Shopper</title>

        <!-- Bootstrap CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

        <!-- Font Awesome CDN (đảm bảo icon mắt hiển thị) -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">

        <style>
            #loginContainer {
                margin-top: 30px;
            }
            .password-wrapper {
                position: relative;
                display: block;
                width: 100%;
            }

            .password-wrapper input {
                width: 100%;
                padding-right: 40px;
                box-sizing: border-box;
            }

            .password-wrapper button {
                position: absolute;
                right: 8px;
                top: 8px;
                bottom: 8px;
                border: none;
                background: transparent;
                cursor: pointer;
                font-size: 16px;
                color: #666;
                width: 24px;
                outline: none;
            }
            #formlogin {
                margin-top: 0;
                margin-bottom: 50px;
            }

        </style>
    </head>
    <body>

        <section id="formlogin">
            <div class="container" id="loginContainer">
                <div class="row">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="login-form"><!--login form-->
                            <h2>Login to your account</h2>
                            <form id="loginForm" action="login" method="POST">
                                <input type="text" name="phone" placeholder="Phone Number" required />
                                <div class="password-wrapper">
                                    <input type="password" id="passwordInput" name="password" placeholder="Password" required style="padding-right: 35px;" />
                                    <button type="button" id="togglePassword" tabindex="-1" aria-label="Toggle password visibility" 
                                            style="position: absolute; right: 8px; top: 8px; bottom: 8px; border: none; background: none; cursor: pointer; font-size: 16px; color: #666; width: 24px; outline: none;">
                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                    </button>
                                </div>

                                <p style="margin-top: 10px;">
                                    <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                                </p>

                                <span>
                                    <input type="checkbox" class="checkbox" name="remember"> 
                                    Keep me signed in
                                </span>
                                <button type="submit" class="btn btn-default">Login</button>
                            </form>

                            <c:if test="${valid == false || not empty message}">
                                <div id="loginMessage" style="color: red; margin-top: 10px;">
                                    <c:choose>
                                        <c:when test="${not empty message}">
                                            ${message}
                                        </c:when>
                                        <c:otherwise>
                                            Invalid phone number or password.
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-sm-1">
                        <h2 class="or">OR</h2>
                    </div>
                    <div class="col-sm-4">
                        <div class="signup-form">
                            <h2>New User?</h2>
                            <p>Create an account to enjoy all our services</p>
                            <a href="${pageContext.request.contextPath}/register" class="btn btn-default">Register Now</a>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                const passwordInput = document.getElementById('passwordInput');
                const togglePassword = document.getElementById('togglePassword');
                const icon = togglePassword.querySelector('i');

                togglePassword.addEventListener('click', function () {
                    if (passwordInput.type === 'password') {
                        passwordInput.type = 'text';
                        icon.classList.remove('fa-eye');
                        icon.classList.add('fa-eye-slash');
                    } else {
                        passwordInput.type = 'password';
                        icon.classList.remove('fa-eye-slash');
                        icon.classList.add('fa-eye');
                    }
                });
            </script>
        </section>

    </body>
</html>
