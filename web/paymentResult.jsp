<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Kết quả thanh toán</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
    </head>
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
</html>
