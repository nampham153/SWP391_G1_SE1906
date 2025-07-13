<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Giỏ hàng</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .cart_product {
                width: 120px;
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
                width: 100px;
                height: 100px;
                object-fit: cover;
            }
            .variant-debug {
                font-size: 13px;
                color: #990000;
                margin-top: 4px;
            }
        </style>
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
                                <th class="description">Description</th>
                                <th class="price">Price</th>
                                <th class="quantity">Quantity</th>
                                <th class="total">Total</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ci" items="${cartItems}">
                                <c:set var="item" value="${ci.itemDetail}" />
                                <c:set var="rowId" value="${item.serialNumber}-${fn:replace(ci.variantSignature, ' ', '')}" />
                                <tr id="row-${rowId}" data-variant="${ci.variantSignature}">
                                    <td class="cart_product">
                                        <div class="product-image-wrapper">
                                            <c:choose>
                                                <c:when test="${not empty item.image and not empty item.image.imageContent}">
                                                    <img src="${pageContext.request.contextPath}/images/home/${item.image.imageContent}"
                                                         alt="${item.itemName}"
                                                         onerror="this.src='${pageContext.request.contextPath}/images/default-product.jpg'" />
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/images/default-product.jpg"
                                                         alt="No image available" />
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </td>

                                    <td class="cart_description">
                                        <h4>${item.itemName}</h4>
                                        <p>Web ID: ${item.serialNumber}</p>
                                        <p>
                                            <c:if test="${not empty ci.variantSignature}">
                                                <span style="font-style: italic; color: #555;">Biến thể:</span>
                                                <br/>
                                                <span class="variant-debug">${ci.variantSignature}</span>
                                                <br/>
                                            </c:if>
                                            <c:if test="${empty ci.variantSignature}">
                                                <span class="variant-debug">(Không có biến thể)</span>
                                            </c:if>
                                        </p>
                                        <p>Stock: ${item.stock}</p>
                                    </td>

                                    <td class="cart_price">
                                        <p id="price-${rowId}">
                                            <fmt:formatNumber value="${item.price}" type="number"/> VNĐ
                                        </p>
                                    </td>

                                    <td class="cart_quantity">
                                        <div class="cart_quantity_button">
                                            <a class="cart_quantity_up" href="javascript:void(0)" onclick="updateQuantity('${item.serialNumber}', '${ci.variantSignature}', 1)"> + </a>
                                            <input id="qty-${rowId}" data-variant="${ci.variantSignature}" class="cart_quantity_input" type="text" value="${ci.quantity}" size="2" readonly>
                                            <a class="cart_quantity_down" href="javascript:void(0)" onclick="updateQuantity('${item.serialNumber}', '${ci.variantSignature}', -1)"> - </a>
                                        </div>
                                    </td>

                                    <td class="cart_total">
                                        <p id="total-${rowId}" class="cart_total_price">
                                            <fmt:formatNumber value="${item.price * ci.quantity}" type="number"/> VNĐ
                                        </p>
                                    </td>

                                    <td class="cart_delete">
                                        <a class="cart_quantity_delete" href="javascript:void(0)" onclick="removeItem('${item.serialNumber}', '${ci.variantSignature}')">
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
                <div class="total_area">
                    <ul>
                        <li>Cart Sub Total 
                            <span id="grand-total">
                                <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/> VNĐ
                            </span>
                        </li>
                        <li>Shipping Cost <span>Free</span></li>
                        <li>Total 
                            <span id="grand-total-2">
                                <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/> VNĐ
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
                            <button type="button" class="btn btn-default check_out" onclick="alert('Giỏ hàng trống!')" disabled>
                                Check Out
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>

        <script>
            function formatCurrency(amount) {
                return new Intl.NumberFormat('vi-VN').format(amount) + " VNĐ";
            }

            function updateCartTotal() {
                let total = 0;
                document.querySelectorAll('[id^="qty-"]').forEach(input => {
                    const id = input.id.replace('qty-', '');
                    const qty = parseInt(input.value);
                    const priceText = document.getElementById('price-' + id).innerText;
                    const price = parseFloat(priceText.replace(/[^0-9]/g, ''));

                    const itemTotal = qty * price;
                    document.getElementById('total-' + id).innerText = formatCurrency(itemTotal);
                    total += itemTotal;
                });
                document.getElementById('grand-total').innerText = formatCurrency(total);
                document.getElementById('grand-total-2').innerText = formatCurrency(total);
            }

            function updateCartSize() {
                fetch('${pageContext.request.contextPath}/cart-size')
                        .then(res => res.text())
                        .then(size => {
                            const el = document.getElementById("cart-size");
                            if (el)
                                el.textContent = size;
                        });
            }

            function updateQuantity(itemId, variantSignature, delta) {
                const rowId = itemId + '-' + variantSignature.replace(/\s/g, '');
                const qtyInput = document.getElementById('qty-' + rowId);
                let currentQty = parseInt(qtyInput.value);
                let newQty = currentQty + delta;

                if (newQty < 1)
                    return;

                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    body: new URLSearchParams({
                        action: delta > 0 ? 'add' : 'removeOne',
                        itemId: itemId,
                        variantSignature: variantSignature,
                        currentQty: newQty
                    })
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'ok') {
                                qtyInput.value = newQty;
                                updateCartTotal();
                                updateCartSize();
                            } else {
                                alert(data.error || "Lỗi cập nhật số lượng.");
                            }
                        });
            }

            function removeItem(itemId, variantSignature) {
                const rowId = itemId + '-' + variantSignature.replace(/\s/g, '');

                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    body: new URLSearchParams({
                        action: 'remove',
                        itemId: itemId,
                        variantSignature: variantSignature
                    })
                })
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === 'ok') {
                                const row = document.getElementById('row-' + rowId);
                                if (row)
                                    row.remove();

                                updateCartTotal();
                                updateCartSize();

                                if (document.querySelectorAll('[id^="row-"]').length === 0) {
                                    const tbody = document.querySelector('tbody');
                                    const emptyRow = document.createElement('tr');
                                    emptyRow.innerHTML = '<td colspan="6" class="text-center">Giỏ hàng trống.</td>';
                                    tbody.appendChild(emptyRow);

                                    const btn = document.querySelector('.check_out');
                                    btn.disabled = true;
                                    btn.onclick = () => alert("Giỏ hàng trống!");
                                }
                            }
                        });
            }

            document.addEventListener("DOMContentLoaded", () => {
                updateCartTotal();
                updateCartSize();
            });
        </script>

    </body>
</html>
