<%-- 
    Document   : verify
    Created on : Jul 1, 2025, 11:12:23 PM
    Author     : namp0
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container" style="max-width: 500px; margin: 60px auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
    <h2 class="text-center mb-4">Xác thực tài khoản</h2>

    <form method="post" action="verify">
        <div class="form-group">
            <label for="code">Nhập mã xác thực đã gửi đến email của bạn:</label>
            <input type="text" id="code" name="code" class="form-control" placeholder="Mã OTP gồm 6 chữ số" required>
        </div>

        <button type="submit" class="btn btn-primary btn-block mt-3">Xác thực</button>
    </form>

    <c:if test="${not empty message}">
        <div class="alert alert-danger mt-3" role="alert">
            ${message}
        </div>
    </c:if>

    <div class="text-center mt-3">
        <a href="login">← Quay lại đăng nhập</a>
    </div>
</div>

