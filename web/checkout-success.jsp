<%-- 
    Document   : checkout-success
    Created on : Jun 30, 2025, 6:30:51 AM
    Author     : namp0
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Success</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
    <div class="container text-center mt-5">
        <h2 class="text-success">✅ Đặt hàng thành công!</h2>
        <p>🧾 Mã đơn hàng của bạn là: <strong>#${order.orderId}</strong></p>
        <p>📦 Đơn hàng của bạn đã được gửi lên hệ thống.<br>
        ✅ Chúng tôi sẽ xét duyệt và phản hồi trong thời gian sớm nhất.</p>

        <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-4">Quay về trang chủ</a>
    </div>
</body>
</html>

