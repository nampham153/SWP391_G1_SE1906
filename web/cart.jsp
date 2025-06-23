<%-- 
    Document   : Cart
    Created on : Jun 18, 2025, 9:54:23 AM
    Author     : namp0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
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
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">
    </head>
    <body>
        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                        <li class="active">Shopping Cart</li>
                    </ol>
                </div>
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <th class="image">Item</th>
                                <th class="description"></th>
                                <th class="price">Price</th>
                                <th class="quantity">Quantity</th>
                                <th class="total">Total</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ci" items="${cartItems}">
                                <c:set var="item" value="${ci.itemDetail}" />
                                <tr id="row-${item.serialNumber}">
                                    <td class="cart_product">
                                        <c:choose>
                                            <c:when test="${not empty item.image and not empty item.image.imageContent}">
                                                <img src="${pageContext.request.contextPath}/images/home/${item.image.imageContent}"
                                                     alt="${item.itemName}"
                                                     style="width: 100px; height: 100px; object-fit: cover;"
                                                     onerror="this.src='${pageContext.request.contextPath}/images/default-product.jpg'" />
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${pageContext.request.contextPath}/images/default-product.jpg"
                                                     alt="No image available"
                                                     style="width: 100px; height: 100px; object-fit: cover;" />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="cart_description">
                                        <h4>${item.itemName}</h4>
                                        <p>Web ID: ${item.serialNumber}</p>
                                    </td>
                                    <td class="cart_price">
                                        <p id="price-${item.serialNumber}">
                                            <fmt:formatNumber value="${item.price}" type="number"/> $
                                        </p>
                                    </td>
                                    <td class="cart_quantity">
                                        <div class="cart_quantity_button">
                                            <a class="cart_quantity_up" href="javascript:void(0)" onclick="updateQuantity('${item.serialNumber}', 1)"> + </a>
                                            <input id="qty-${item.serialNumber}" class="cart_quantity_input" type="text" value="${ci.quantity}" size="2" readonly>
                                            <a class="cart_quantity_down" href="javascript:void(0)" onclick="updateQuantity('${item.serialNumber}', -1)"> - </a>
                                        </div>
                                    </td>
                                    <td class="cart_total">
                                        <p id="total-${item.serialNumber}" class="cart_total_price">
                                            <fmt:formatNumber value="${item.price * ci.quantity}" type="number"/> $
                                        </p>
                                    </td>
                                    <td class="cart_delete">
                                        <a class="cart_quantity_delete" href="javascript:void(0)" onclick="removeItem('${item.serialNumber}')">
                                            <i class="fa fa-times"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty cartItems}">
                                <tr><td colspan="6" class="text-center">Giỏ hàng trống.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>

        <section id="do_action">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6"></div>
                    <div class="col-sm-6">
                        <div class="total_area">
                            <ul>
                                <li>Cart Sub Total 
                                    <span id="grand-total">
                                        <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true" minFractionDigits="0"/> $
                                    </span>
                                </li>
                                <li>Shipping Cost <span>Free</span></li>
                                <li>Total 
                                    <span id="grand-total-2">
                                        <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true" minFractionDigits="0"/> $
                                    </span>
                                </li>
                            </ul>

                            <c:choose>
                                <c:when test="${not empty cartItems}">
                                    <form action="${pageContext.request.contextPath}/checkout" method="get">
                                        <button type="submit" class="btn btn-default check_out">Check Out</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn btn-default check_out" onclick="alert('Giỏ hàng trống! Vui lòng thêm sản phẩm trước khi thanh toán.')" disabled>
                                        Check Out
                                    </button>
                                </c:otherwise>
                            </c:choose>

                        </div>

                    </div>
                </div>
            </div>
        </section>
    </body>
    <c:if test="${not empty sessionAttributes}">
        <h3>Session Debug</h3>
        <table border="1" cellpadding="5" cellspacing="0">
            <thead>
                <tr><th>Tên thuộc tính</th><th>Giá trị</th></tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${sessionAttributes}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <script>
        document.addEventListener("DOMContentLoaded", function () {

            window.updateQuantity = function (itemId, delta) {
                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    body: new URLSearchParams({
                        action: delta > 0 ? 'add' : 'removeOne',
                        itemId: itemId
                    })
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'ok') {
                                const qtyInput = document.getElementById('qty-' + itemId);
                                let qty = parseInt(qtyInput.value) + delta;
                                if (qty < 1)
                                    qty = 1;
                                qtyInput.value = qty;

                                updateCartTotal(); // Chỉ cần gọi hàm cập nhật lại toàn bộ
                            }
                        })
                        .catch(err => console.error("Lỗi:", err));
            };

            window.removeItem = function (itemId) {
                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    body: new URLSearchParams({
                        action: 'remove',
                        itemId: itemId
                    })
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'ok') {
                                const row = document.getElementById('row-' + itemId);
                                if (row)
                                    row.remove();
                                updateCartTotal();
                            }
                        });
            };

            function formatCurrency(amount) {
                return new Intl.NumberFormat('vi-VN', {
                    style: 'decimal',
                    minimumFractionDigits: 0,
                    maximumFractionDigits: 0
                }).format(amount) + " $";
            }

            function updateCartTotal() {
                let total = 0;

                document.querySelectorAll('[id^="qty-"]').forEach(qtyInput => {
                    const itemId = qtyInput.id.replace('qty-', '');
                    const qty = parseInt(qtyInput.value);
                    const priceText = document.getElementById('price-' + itemId).innerText;
                    const price = parseFloat(priceText.replace(/[^0-9]/g, ''));

                    const itemTotal = price * qty;

                    document.getElementById('total-' + itemId).innerText = formatCurrency(itemTotal);
                    total += itemTotal;
                });

                document.getElementById('grand-total').innerText = formatCurrency(total);
                document.getElementById('grand-total-2').innerText = formatCurrency(total);
            }

            // Khởi tạo tổng ngay từ khi load
            updateCartTotal();
        });
    </script>
</body>
</html>
