<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Chi tiết sản phẩm</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .swatch-element {
                display: inline-block;
                border: 1px solid #ccc;
                border-radius: 6px;
                padding: 10px 14px;
                cursor: pointer;
                transition: all 0.2s;
                min-width: 120px;
                text-align: center;
                margin: 5px;
            }

            .swatch-element input[type="radio"] {
                display: none;
            }

            .swatch-element input[type="radio"]:checked + span {
                background-color: #007bff;
                color: #fff;
                font-weight: bold;
                border-radius: 4px;
            }

            .product-image {
                max-width: 100%;
                max-height: 350px;
                object-fit: contain;
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 4px;
                background-color: #f9f9f9;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-5">
                    <img src="${pageContext.request.contextPath}/images/home/${pcItem.image.imageContent}"
                         class="img-fluid product-image" alt="${pcItem.itemName}">
                </div>
                <div class="col-md-7">
                    <h2>${pcItem.itemName}</h2>
                    <h4>
                        Tổng giá:
                        <c:choose>
                            <c:when test="${not empty specMap}">
                                <span id="totalPrice"></span>
                                <input type="hidden" id="fixedPrice" value="${fixedComponentPrice}" />
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="VNĐ" />
                            </c:otherwise>
                        </c:choose>
                    </h4>
                    <h4>Số lượng tồn kho: ${pcItem.stock}</h4>
                    <h4>Mô tả : ${pcItem.description}</h4>

                    <form id="buildForm" action="cart" method="post">
                        <input type="hidden" name="itemId" value="${pcItem.serialNumber}" />
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="variantSignature" id="variantSignature" />

                        <c:forEach var="entry" items="${specMap}">
                            <div class="form-group">
                                <label><strong>${entry.key.specName}</strong></label>
                                <div class="row" style="display: flex; flex-wrap: wrap; gap: 12px;">
                                    <c:forEach var="item" items="${entry.value}">
                                        <label class="swatch-element">
                                            <input type="radio"
                                                   id="${entry.key.specName}-${item.serialNumber}"
                                                   class="spec-radio"
                                                   name="${entry.key.specName}"
                                                   value="${item.serialNumber}"
                                                   data-spec-name="${entry.key.specName}"
                                                   data-price="${item.price}"
                                                   <c:if test="${item.serialNumber == defaultMap[entry.key.specName].serialNumber}">checked</c:if> />
                                            <span>${item.itemName}</span>
                                            <small><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VNĐ</small>
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>

                        <button type="submit" class="btn btn-primary mt-3">Thêm vào giỏ hàng</button>
                    </form>
                </div>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const formatter = new Intl.NumberFormat('vi-VN');

                function updateTotalPrice() {
                    let total = parseFloat(document.getElementById("fixedPrice")?.value || "0");

                    document.querySelectorAll(".spec-radio:checked").forEach(input => {
                        const price = parseFloat(input.dataset.price || "0");
                        total += price;
                    });

                    const totalPriceSpan = document.getElementById("totalPrice");
                    if (totalPriceSpan) {
                        totalPriceSpan.innerText = formatter.format(total) + " VNĐ";
                    }
                }

                function updateCartSize() {
                    fetch('cart-size')
                            .then(res => res.text())
                            .then(size => {
                                const el = document.getElementById("cart-size");
                                if (el) {
                                    el.textContent = size;
                                }
                            });
                }

                document.querySelectorAll(".spec-radio").forEach(input => {
                    input.addEventListener("change", updateTotalPrice);
                });

                updateTotalPrice();

                document.getElementById("buildForm").addEventListener("submit", function (e) {
                    e.preventDefault();

                    const form = e.target;
                    const specRadios = document.querySelectorAll(".spec-radio:checked");
                    const parts = [];

                    specRadios.forEach(radio => {
                        parts.push(radio.value); // chỉ lấy itemId
                    });

                    const variantSignature = parts.join("|");
                    document.getElementById("variantSignature").value = variantSignature;

                    console.log("✅ variantSignature:", variantSignature);

                    const formData = new FormData(form);

                    fetch("cart", {
                        method: "POST",
                        body: new URLSearchParams(formData),
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                    })
                            .then(res => res.json())
                            .then(data => {
                                if (data.status === "ok") {
                                    alert("Đã thêm vào giỏ hàng");
                                    updateCartSize();
                                    location.href = "cart";
                                } else {
                                    alert(data.error || "Lỗi khi thêm vào giỏ hàng");
                                }
                            })
                            .catch(error => {
                                console.error("Error during fetch:", error);
                                alert("Đã xảy ra lỗi mạng hoặc máy chủ.");
                            });
                });
            });
        </script>
    </body>
</html>