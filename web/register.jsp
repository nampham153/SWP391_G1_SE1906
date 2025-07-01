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
    <title>Register | E-Shopper</title>
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

                    <form action="register" method="POST" onsubmit="return validateForm();">
                        <input type="text" name="phone" placeholder="Phone Number" value="${param.phone}" required />

                        <input type="password" name="password" placeholder="Password (min 6 characters)" minlength="6" required />

                        <input type="text" name="name" placeholder="Full Name" value="${param.name}" required />

                        <input type="email" name="email" placeholder="Email Address" value="${param.email}" required />

                        <input type="date" name="birthdate" value="${param.birthdate}" required />

                        <div class="form-group">
                            <label>Gender:</label><br/>
                            <label class="radio-inline">
                                <input type="radio" name="gender" value="1" ${param.gender == '1' ? 'checked' : ''}> Male
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" value="0" ${param.gender == '0' ? 'checked' : ''}> Female
                            </label>
                        </div>

                        <button type="submit" class="btn btn-default">Signup</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<script>
function validateForm() {
    const phone = document.forms[0]["phone"].value.trim();
    const password = document.forms[0]["password"].value;
    const name = document.forms[0]["name"].value.trim();
    const email = document.forms[0]["email"].value.trim();
    const birthdate = document.forms[0]["birthdate"].value;
    const today = new Date().toISOString().split("T")[0];

    // Phone: bắt đầu bằng 0, đúng 11 số
    if (!/^0\d{10}$/.test(phone)) {
        alert("Số điện thoại phải bắt đầu bằng 0 và có 11 chữ số.");
        return false;
    }

    // Password: ít nhất 6 ký tự, không có khoảng trắng
    if (password.length < 6 || password.includes(" ")) {
        alert("Mật khẩu phải có ít nhất 6 ký tự và không chứa khoảng trắng.");
        return false;
    }

    // Name: tối thiểu 3 ký tự, không toàn khoảng trắng, không quá 5 khoảng trắng, không ký tự đặc biệt
    if (name.length < 3) {
        alert("Tên phải có ít nhất 3 ký tự.");
        return false;
    }

    const spaceCount = (name.match(/ /g) || []).length;
    if (spaceCount > 5) {
        alert("Tên không được có quá 5 khoảng trắng.");
        return false;
    }

    if (!/^[\p{L} ]+$/u.test(name)) {
        alert("Tên chỉ được chứa chữ cái và khoảng trắng.");
        return false;
    }

    // Email
    if (!/^\S+@\S+\.\S+$/.test(email)) {
        alert("Email không hợp lệ.");
        return false;
    }

    // Birthdate
    if (!birthdate) {
        alert("Vui lòng nhập ngày sinh.");
        return false;
    }

    if (birthdate > today) {
        alert("Ngày sinh không được lớn hơn ngày hiện tại.");
        return false;
    }

    return true;
}
</script>
</body>
</html>
