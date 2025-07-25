<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    model.Account account = (model.Account) session.getAttribute("account");
    boolean isLoggedIn = account != null;
    pageContext.setAttribute("isLoggedIn", isLoggedIn);

    model.Customer customer = (model.Customer) session.getAttribute("customer");
    model.CustomerAddress customeraddress = (model.CustomerAddress) request.getAttribute("customerAddress");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Checkout | E-Shopper</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/ico/favicon.ico">
        <style>
            .cart_product {
                width: 140px;
                padding: 10px;
            }
            .cart_description {
                padding-left: 10px;
                vertical-align: top;
            }
            .cart_description h4, .cart_description p {
                margin: 0;
                line-height: 1.4;
            }
            .product-image-wrapper img {
                width: 120px;
                height: 120px;
                object-fit: contain;
                display: block;
            }
            .variant-debug {
                font-size: 13px;
                color: #990000;
                margin-top: 4px;
            }

            .cart_info table {
                table-layout: fixed;
                width: 100%;
            }

            .cart_info th.image {
                width: 160px;
            }

            .cart_info td, .cart_info th {
                word-wrap: break-word;
                vertical-align: top;
            }
        </style>
    </head>
    <body>
        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <li class="active">Check out</li>
                    </ol>
                </div>

                <div class="register-req">
                    <p>Hãy nhập đúng các thông tin vào các trường dưới để chúng tôi có thể mang đơn hàng đến nhà của bạn nhé!</p>
                </div>

                <form id="checkoutForm" action="checkout" method="post">
                    <div class="shopper-informations">
                        <div class="row">
                            <div class="col-sm-8 clearfix">
                                <div class="bill-to">
                                    <p>Bill To</p>
                                    <div class="form-one">
                                        <c:choose>
                                            <c:when test="${isLoggedIn}">
                                                <input type="email" name="email" placeholder="Email*" value="${customer.customerEmail}" required class="form-control">
                                                <input type="text" name="Tên đơn" placeholder="Title" class="form-control">
                                                <input type="text" name="customerName" value="${customer.customerName}" readonly class="form-control">
                                            </c:when>
                                            <c:otherwise>
                                                <input type="email" name="email" placeholder="Email *" required class="form-control">
                                                <input type="text" name="title" placeholder="Tên đơn" class="form-control">
                                                <input type="text" name="firstName" placeholder="Họ *" required class="form-control">
                                                <input type="text" name="middleName" placeholder="Tên đệm" class="form-control">
                                                <input type="text" name="lastName" placeholder="Tên *" required class="form-control">
                                            </c:otherwise>
                                        </c:choose>
                                        <input type="text" name="address" id="address"
                                               value="${isLoggedIn ? customeraddress.customerAddress : ''}"
                                               placeholder="Địa chỉ giao hàng *" class="form-control" required>
                                        <input type="text" name="zipcode" placeholder="Zip / Postal Code *" class="form-control">
                                        <select name="country" class="form-control">
                                            <option>-- Đất nước --</option>
                                            <option>Vietnam</option>
                                        </select>
                                        <select name="province" class="form-control">
                                            <option>Thành Phố</option>
                                            <option>Hanoi</option>
                                            <option>HaiPhong</option>
                                            <option>HaiDuong</option>
                                        </select>
                                        <input type="text" name="phone" id="phone"
                                               placeholder="Số điện thoại *"
                                               value="${isLoggedIn ? customer.customerId : ''}" 
                                               class="form-control" required>

                                        <input type="text" name="fax" placeholder="Fax" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="order-message">
                                    <p>Shipping Order</p>
                                    <textarea name="note" placeholder="Ghi chú cho đơn hàng của bạn" rows="16" class="form-control"></textarea>
                                    <label><input type="checkbox" name="sameAddress"> Shipping to bill address</label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="review-payment">
                        <h2>Review & Payment</h2>
                    </div>

                    <div class="table-responsive cart_info">
                        <table class="table table-condensed">
                            <thead>
                                <tr class="cart_menu">
                                    <td class="image">Item</td>
                                    <td class="description">Description</td>
                                    <td class="price">Price</td>
                                    <td class="quantity">Quantity</td>
                                    <td class="total">Total</td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="ci" items="${cartItems}">
                                    <c:set var="item" value="${ci.itemDetail}" />
                                    <tr>
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
                                                         alt="No image"
                                                         style="width: 100px; height: 100px; object-fit: cover;" />
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="cart_description">
                                            <h4>${item.itemName}</h4>
                                            <p>Web ID: ${item.serialNumber}</p>
                                        </td>
                                        <td class="cart_price">
                                            <p><fmt:formatNumber value="${item.price}" type="number" /> VNĐ</p>
                                        </td>
                                        <td class="cart_quantity">
                                            <p>${ci.quantity}</p>
                                        </td>
                                        <td class="cart_total">
                                            <p class="cart_total_price">
                                                <fmt:formatNumber value="${item.price * ci.quantity}" type="number" /> VNĐ
                                            </p>
                                        </td>
                                        <td class="cart_delete"></td>
                                    </tr>
                                </c:forEach>

                                <tr>
                                    <td colspan="4" class="text-right"><strong>Total</strong></td>
                                    <td><strong><fmt:formatNumber value="${cartTotal}" type="number" /> VNĐ</strong></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="payment-options">
                        <span><label><input type="radio" name="payment" value="cod" checked> Pay on Delivery</label></span>
                        <span><label><input type="radio" name="payment" value="vnpay"> Bank Online</label></span>
                    </div>

                    <div class="text-right mb-5">
                        <input type="hidden" name="totalBill" value="${cartTotal}">
                        <button type="submit" class="btn btn-success">Confirm Order</button>
                    </div>
                </form>
            </div>
        </section>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.getElementById("checkoutForm");
                const radios = document.querySelectorAll("input[name='payment']");

                form.addEventListener("submit", function (e) {
                    e.preventDefault();

                    const address = document.getElementById('address').value.trim();
                    const phone = document.getElementById('phone').value.trim();

                    if (address.length === 0) {
                        alert("Vui lòng nhập địa chỉ giao hàng.");
                        return;
                    }

                    if (address.length < 5) {
                        alert("Địa chỉ giao hàng quá ngắn.");
                        return;
                    }

                    const phonePattern = /^[0-9]{9,12}$/;
                    if (!phonePattern.test(phone)) {
                        alert("Vui lòng nhập số điện thoại hợp lệ (9-12 chữ số).");
                        return;
                    }

                    let paymentMethod = "cod";
                    radios.forEach(r => {
                        if (r.checked)
                            paymentMethod = r.value;
                    });

                    const formData = new URLSearchParams(new FormData(form));

                    fetch("checkout", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: formData.toString()
                    })
                            .then(res => res.json())
                            .then(data => {
                                if (data.error) {
                                    alert(data.error);
                                } else if (paymentMethod === "vnpay") {
                                    const params = new URLSearchParams();
                                    params.append('amount', data.amount);
                                    params.append('orderId', data.orderId);
                                    params.append('language', 'vn');

                                    fetch("ajaxServlet", {
                                        method: "POST",
                                        headers: {
                                            "Content-Type": "application/x-www-form-urlencoded"
                                        },
                                        body: params.toString()
                                    })
                                            .then(res => res.json())
                                            .then(vnpRes => {
                                                if (vnpRes.code === "00") {
                                                    window.location.href = vnpRes.data;
                                                } else {
                                                    alert("VNPay Error: " + (vnpRes.message || "Không tạo được link thanh toán"));
                                                }
                                            });
                                } else {
                                    window.location.href = "checkout-success?orderId=" + data.orderId;
                                }
                            })
                            .catch(err => {
                                alert("Lỗi hệ thống, vui lòng thử lại sau.");
                                console.error(err);
                            });
                });
            });
        </script>
    </body>
</html>
