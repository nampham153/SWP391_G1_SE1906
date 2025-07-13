<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quên mật khẩu</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container" style="margin-top: 50px;">
    <h3>Quên mật khẩu</h3>
    <form action="forgot-password" method="post" style="max-width: 400px;">
        <div class="form-group">
            <label>Nhập số điện thoại đã đăng ký:</label>
            <input type="text" name="phone" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary">Gửi mật khẩu mới qua Email</button>

        <c:if test="${not empty message}">
            <div class="alert alert-success" style="margin-top: 10px;">
                ${message}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" style="margin-top: 10px;">
                ${error}
            </div>
        </c:if>
    </form>
    <br>
    <a href="login.jsp">Quay lại đăng nhập</a>
</div>
</body>
</html>
