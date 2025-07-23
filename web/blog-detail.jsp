<%-- 
    Document   : blog-detail
    Created on : Jul 20, 2025, 10:21:08 AM
    Author     : namp0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Home | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">
    </head><!--/head-->
    <body>
        <div id="contact-page" class="container">
            <div class="blog-post-area">
                <h2 class="title text-center">Blog Detail</h2>
                <div class="single-blog-post">
                    <h3>${blog.title}</h3>
                    <div class="post-meta">
                        <ul>
                            <li><i class="fa fa-user"></i> ${blog.staffId}</li>
                            <li><i class="fa fa-clock-o"></i>
                                <fmt:formatDate value="${blog.createdAt}" pattern="HH:mm" />
                            </li>
                            <li><i class="fa fa-calendar"></i>
                                <fmt:formatDate value="${blog.createdAt}" pattern="MMM dd, yyyy" />
                            </li>
                        </ul>
                    </div>

                    <c:if test="${not empty blog.image}">
                        <div style="text-align: center; margin: 20px 0;">
                            <img src="${pageContext.request.contextPath}/images/blog/${blog.image}"
                                 alt=""
                                 style="max-height:500px; width:auto; border-radius:8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);"/>
                        </div>
                    </c:if>

                    <div style="white-space: pre-line; line-height: 1.7em; font-size: 16px;">
                        <c:out value="${blog.content}" escapeXml="false"/>
                    </div>

                    <div class="pager-area">
                        <ul class="pager pull-right">
                            <li><a href="blog">← Quay lại</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>