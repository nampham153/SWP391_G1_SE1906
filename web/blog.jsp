<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <div class="blog-post-area">
        <h2 class="title text-center">Latest From our Blog</h2>
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
