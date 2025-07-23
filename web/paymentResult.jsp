<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
        <title>Kết quả thanh toán</title>
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
    <body class="bg-light">
        <div class="container mt-5">
            <div class="card shadow-lg">
                <div class="card-body text-center">
                    <h2 class="card-title mb-4">Kết quả thanh toán VNPay</h2>

                    <c:choose>
                        <c:when test="${param.vnp_TransactionStatus == '00'}">
                            <div class="alert alert-success">
                                 Thanh toán thành công!
                            </div>
                            <p>Mã đơn hàng: <strong>${param.vnp_TxnRef}</strong></p>
                            <p>Mã giao dịch: <strong>${param.vnp_TransactionNo}</strong></p>
                            <p>Số tiền: <strong><fmt:formatNumber value="${param.vnp_Amount / 100}" type="number" groupingUsed="true" maxFractionDigits="0"/> VNĐ</strong></p>
                            <p>Thời gian: <strong>${param.vnp_PayDate}</strong></p>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-danger">
                                 Thanh toán thất bại!
                            </div>
                            <p>Lý do: <strong>Mã lỗi ${param.vnp_ResponseCode}</strong></p>
                        </c:otherwise>
                    </c:choose>

                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-4">Quay về trang chủ</a>
                </div>
            </div>
        </div>
    </body>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</html>
