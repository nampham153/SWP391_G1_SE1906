<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            <h2 class="title text-center">Blog List</h2>
            <c:forEach var="b" items="${blogList}">
                <div class="single-blog-post">
                    <h3>${b.title}</h3>
                    <div class="post-meta">
                        <ul>
                            <li><i class="fa fa-user"></i> ${b.staffId}</li>
                            <li><i class="fa fa-clock-o"></i> 
                                <fmt:formatDate value="${b.createdAt}" pattern="HH:mm" />
                            </li>
                            <li><i class="fa fa-calendar"></i> 
                                <fmt:formatDate value="${b.createdAt}" pattern="MMM d, yyyy" />
                            </li>
                        </ul>
                    </div>
                    <a href="blog-detail?blogId=${b.blogId}">
                        <img src="${pageContext.request.contextPath}/images/blog/${b.image}" alt="" style="max-height: 500px;">
                    </a>
                    <p>
                        ${fn:substring(b.content, 0, 300)}...
                    </p>
                    <a class="btn btn-primary" href="blog-detail?blogId=${b.blogId}">Read More</a>
                </div>
            </c:forEach>
        </div>
                    </div>
        <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
    </body>
</html>
