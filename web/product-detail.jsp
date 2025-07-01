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
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            border: 1px solid #ccc;
            border-radius: 6px;
            padding: 10px 14px;
            cursor: pointer;
            transition: all 0.2s;
            min-width: 120px;
            text-align: center;
            position: relative;
        }

        .swatch-element input[type="radio"] {
            display: none;
        }

        .swatch-element label.sd {
            cursor: pointer;
            margin: 0;
        }

        .swatch-element input[type="radio"]:checked + label.sd {
            background-color: #007bff;
            color: #fff;
            border-radius: 4px;
            font-weight: bold;
        }

        .swatch-element span {
            display: block;
            font-size: 14px;
        }

        .swatch-element small {
            font-size: 12px;
            color: #666;
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

            <form id="buildForm" action="cart" method="post">
    <input type="hidden" name="itemId" value="${pcItem.serialNumber}" />
    <input type="hidden" name="action" value="add" />

    <c:forEach var="entry" items="${specMap}">
        <div class="form-group">
            <label><strong>${entry.key.specName}</strong></label>
            <div class="row" style="display: flex; flex-wrap: wrap; gap: 12px;">
                <c:forEach var="item" items="${entry.value}">
                    <div class="mb-3">
                        <div class="swatch-element">
                            <input type="radio"
                                   class="spec-radio"
                                   name="${entry.key.specName}"
                                   id="${entry.key.specName}-${item.serialNumber}"
                                   value="${item.serialNumber}"
                                   data-price="${item.price}"
                                   <c:if test="${item.serialNumber == defaultMap[entry.key.specName].serialNumber}">checked</c:if>>
                            <label for="${entry.key.specName}-${item.serialNumber}" class="sd">
                                <span>${item.itemName}</span>
                                <small><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VNĐ</small>
                            </label>
                        </div>
                    </div>
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
            let total = parseFloat(document.getElementById("fixedPrice").value || "0");

            document.querySelectorAll(".spec-radio:checked").forEach(input => {
                const price = parseFloat(input.dataset.price || "0");
                total += price;
            });

            document.getElementById("totalPrice").innerText = formatter.format(total) + " VNĐ";
        }

        document.querySelectorAll(".spec-radio").forEach(input => {
            input.addEventListener("change", updateTotalPrice);
        });

        updateTotalPrice();
    });
</script>
</body>
</html>
