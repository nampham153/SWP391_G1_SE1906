<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .component-filter {
                margin: 30px 0;
                text-align: right;
            }
            .search_box {
                margin: 20px 0;
                text-align: right;
            }
            .search_box input[type="text"] {
                padding: 5px 10px;
                width: 250px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="search_box">
                <form action="home" method="get">
                    <input type="text" name="search" placeholder="Tìm sản phẩm..." value="${searchKeyword != null ? searchKeyword : ''}"/>
                    <button type="submit">Tìm kiếm</button>
                </form>
            </div>
        </div>

        <section id="slider">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
                                <li data-target="#slider-carousel" data-slide-to="1"></li>
                                <li data-target="#slider-carousel" data-slide-to="2"></li>
                            </ol>

                            <div class="carousel-inner">
                                <div class="item active">
                                    <div class="col-sm-6">
                                        <h1><span>PC</span>-SHOPPER</h1>
                                        <h2>PC thả ga, không lo giá cả</h2>
                                        <p> Đủ mọi loại máy tính, đáp ứng đủ như cầu cho anh em từ mọi cấu hình. giúp anh em có trải nghiệm tốt nhất</p>
                                        <button type="button" class="btn btn-default get">Get it now</button>
                                    </div>
                                    <div class="col-sm-6">
                                        <img src="images/home/banner2.png" class="girl img-responsive" alt="" />
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="col-sm-6">
                                        <h1><span>PC</span>-SHOPPER</h1>
                                        <h2>Máy tính siêu tốt</h2>
                                        <p>Máy tính văn phòng, đủ mọi kiểu dáng, giá siêu ưu đãi, đảm bảo chất lượng, bảo hành tận nơi</p>
                                        <button type="button" class="btn btn-default get">Get it now</button>
                                    </div>
                                    <div class="col-sm-6">
                                        <img src="images/home/banner1.png" class="girl img-responsive" alt="" />
                                    </div>
                                </div>

                                <div class="item">
                                    <div class="col-sm-6">
                                        <h1><span>PC</span>-SHOPPER</h1>
                                        <h2>Build PC theo mọi yêu cầu</h2>
                                        <p>Tự mình build PC phong cách, phù hợp theo yêu cầu, nhận sự hỗ trợ của nhân viên</p>
                                        <button type="button" class="btn btn-default get">Get it now</button>
                                    </div>
                                    <div class="col-sm-6">
                                        <img src="images/home/banner3.png" class="girl img-responsive" alt="" />
                                    </div>
                                </div>

                            </div>

                            <a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                                <i class="fa fa-angle-left"></i>
                            </a>
                            <a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </div>

                    </div>
                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <div class="shipping text-center"><!--shipping-->
                                <img src="images/home/home_tabs_1_banner.jpg" alt="" />
                            </div><!--/shipping-->
                        </div>
                    </div>

                    <div class="col-sm-9 padding-right">
                        <c:choose>
                            <c:when test="${not empty searchResults}">
                                <h2 class="title text-center">Kết quả tìm kiếm cho: "${searchKeyword}"</h2>
                                <div class="row">
                                    <c:forEach var="item" items="${searchResults}">
                                        <div class="col-sm-4">
                                            <div class="product-image-wrapper">
                                                <div class="single-products">
                                                    <div class="productinfo text-center">
                                                        <c:choose>
                                                            <c:when test="${not empty item.image and not empty item.image.imageContent}">
                                                                <img src="${pageContext.request.contextPath}/images/home/${item.image.imageContent}" alt="${item.itemName}" style="width:100%; height:200px; object-fit:cover;"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="${pageContext.request.contextPath}/images/default-product.jpg" alt="No image" style="width:100%; height:200px; object-fit:cover;"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <h2><fmt:formatNumber value="${item.price}" type="number"/> VNĐ</h2>
                                                        <p>${item.itemName}</p>
                                                    </div>
                                                    <div class="product-overlay">
                                                        <div class="overlay-content">
                                                            <h2><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" minFractionDigits="0" maxFractionDigits="0"/> VNĐ</h2>
                                                            <p>${item.itemName}</p>
                                                            <button class="btn btn-default add-to-cart" onclick="addToCart('${item.serialNumber}')">
                                                                <i class="fa fa-shopping-cart"></i> Add to cart
                                                            </button>
                                                            <a href="${pageContext.request.contextPath}/product-detail?pid=${item.serialNumber}" class="btn btn-default add-to-cart">
                                                                <i class="fa fa-info-circle"></i> Detail
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>

                            <c:when test="${searchKeyword != null && (searchResults == null || searchResults.isEmpty())}">
                                <h2 class="title text-center">Không tìm thấy sản phẩm phù hợp với từ khóa: "${searchKeyword}"</h2>
                            </c:when>

                            <c:otherwise>
                                <h2 class="title text-center">Máy tính nổi bật</h2>
                                <c:forEach var="item" items="${pcItems}">
                                    <div class="col-sm-4">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <c:choose>
                                                        <c:when test="${not empty item.image and not empty item.image.imageContent}">
                                                            <img src="${pageContext.request.contextPath}/images/home/${item.image.imageContent}" 
                                                                 alt="${item.itemName}" 
                                                                 style="width: 100%; height: 250px; object-fit: cover;"
                                                                 onerror="this.src='${pageContext.request.contextPath}/images/default-product.jpg'" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/images/default-product.jpg" 
                                                                 alt="No image available" 
                                                                 style="width: 100%; height: 250px; object-fit: cover;" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <h2><fmt:formatNumber value="${item.price}" type="number"/> VNĐ</h2>
                                                    <p>${item.itemName}</p>
                                                </div>
                                                <div class="product-overlay">
                                                    <div class="overlay-content">
                                                        <h2><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" minFractionDigits="0" maxFractionDigits="0"/> VNĐ</h2>
                                                        <p>${item.itemName}</p>
                                                        <button class="btn btn-default add-to-cart" onclick="addToCart('${item.serialNumber}')">
                                                            <i class="fa fa-shopping-cart"></i> Add to cart
                                                        </button>
                                                        <a href="${pageContext.request.contextPath}/product-detail?pid=${item.serialNumber}"
                                                           class="btn btn-default add-to-cart" style="margin-left: 5px;">
                                                            <i class="fa fa-info-circle"></i> Detail
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                                <!-- Bộ lọc linh kiện -->
                                <div class="component-filter">

                                    <select id="categorySelect" onchange="filterComponentCategory()">
                                        <option value="0" selected>-- Tất cả --</option>
                                        <c:forEach var="cat" items="${componentCategories}">
                                            <option value="${cat.categoryId}">${cat.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Linh kiện theo danh mục được lọc -->
                                <c:forEach var="entry" items="${componentItemsByCategory}">
                                    <c:set var="categoryId" value="${entry.key}"/>
                                    <c:set var="items" value="${entry.value}"/>
                                    <div class="component-section" data-category-id="${categoryId}" style="display: block;">
                                        <c:forEach var="cat" items="${componentCategories}">
                                            <c:if test="${cat.categoryId == categoryId}">
                                                <h2 class="title text-center">${cat.categoryName}</h2>
                                            </c:if>
                                        </c:forEach>
                                        <div class="row">
                                            <c:forEach var="item" items="${items}">
                                                <div class="col-sm-4">
                                                    <div class="product-image-wrapper">
                                                        <div class="single-products">
                                                            <div class="productinfo text-center">
                                                                <c:choose>
                                                                    <c:when test="${not empty item.image and not empty item.image.imageContent}">
                                                                        <img src="${pageContext.request.contextPath}/images/home/${item.image.imageContent}"
                                                                             alt="${item.itemName}" style="width:100%; height:200px; object-fit:cover;"/>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img src="${pageContext.request.contextPath}/images/default-product.jpg" 
                                                                             alt="No image" style="width:100%; height:200px; object-fit:cover;"/>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <h2><fmt:formatNumber value="${item.price}" type="number"/> VNĐ</h2>
                                                                <p>${item.itemName}</p>
                                                            </div>
                                                            <div class="product-overlay">
                                                                <div class="overlay-content">
                                                                    <h2><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" minFractionDigits="0" maxFractionDigits="0"/> VNĐ</h2>
                                                                    <p>${item.itemName}</p>
                                                                    <button class="btn btn-default add-to-cart" onclick="addToCart('${item.serialNumber}')">
                                                                        <i class="fa fa-shopping-cart"></i> Add to cart
                                                                    </button>
                                                                    <a href="${pageContext.request.contextPath}/product-detail?pid=${item.serialNumber}" class="btn btn-default add-to-cart">
                                                                        <i class="fa fa-info-circle"></i> Detail
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </section>
    </body>
    <script>
        function addToCart(itemId) {
            fetch('${pageContext.request.contextPath}/cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                body: new URLSearchParams({
                    action: 'add',
                    itemId: itemId,
                    quantity: 1
                })
            })
                    .then(response => {
                        if (!response.ok)
                            throw new Error("Lỗi mạng");
                        return response.json();
                    })
                    .then(data => {
                        if (data.status === 'ok') {
                            updateCartSize(); 
                            alert("Đã thêm vào giỏ hàng!");
                        } else {
                            alert("Không thể thêm vào giỏ hàng.");
                        }
                    })
                    .catch(error => console.error("Lỗi khi thêm vào giỏ hàng:", error));
        }

        function filterComponentCategory() {
            const selectedId = document.getElementById("categorySelect").value;
            document.querySelectorAll(".component-section").forEach(div => {
                div.style.display = (selectedId === "0" || div.dataset.categoryId === selectedId) ? "block" : "none";
            });
        }
    </script>
</html>
