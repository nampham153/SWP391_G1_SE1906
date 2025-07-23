<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header id="header">
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
    <!-- header_top -->
    <div class="header_top">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="contactinfo">
                        <ul class="nav nav-pills">
                            <li><a href="#"><i class="fa fa-phone"></i> +84 0392 96 8548</a></li>
                            <li><a href="#"><i class="fa fa-envelope"></i> namp04464@gmail.com</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="social-icons pull-right">
                        <ul class="nav navbar-nav">
                            <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                            <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- header-middle -->
    <div class="header-middle">
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    <div class="logo pull-left">
                        <a href="${pageContext.request.contextPath}/home">
                            <img src="${pageContext.request.contextPath}/images/home/logo.png" alt="Logo" />
                        </a>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">
                            <c:choose>
                                <c:when test="${not empty sessionScope.account}">
                                    <li><a href="#"><i class="fa fa-user"></i> Xin chào, ${sessionScope.account.phone}</a></li>
                                        <c:if test="${sessionScope.account.roleId == 3}">
                                        <li><a href="${pageContext.request.contextPath}/admin/staff"><i class="fa fa-user-secret"></i> Trang quản lý (Admin)</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.roleId == 2}">
                                        <li><a href="${pageContext.request.contextPath}/staff/customer"><i class="fa fa-cogs"></i> Trang quản lý (Staff)</a></li>
                                        </c:if>
                                        <c:if test="${sessionScope.account.roleId == 1}">
                                        <li><a href="${pageContext.request.contextPath}/customer/home"><i class="fa fa-home"></i> Trang cá nhân</a></li>
                                        </c:if>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/cart">
                                            <i class="fa fa-shopping-cart"></i> Giỏ hàng
                                            (<span id="cart-size" style="color: orange;">${cartSize}</span>)
                                        </a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-unlock"></i> Thoát</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li><a href="#"><i class="fa fa-user"></i> Xin chào, Khách</a></li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/cart">
                                            <i class="fa fa-shopping-cart"></i> Giỏ hàng
                                            (<span id="cart-size" style="color: orange;">${cartSize}</span>)
                                        </a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/login"><i class="fa fa-lock"></i> Đăng nhập</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- header-bottom -->
    <div class="header-bottom">
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="mainmenu pull-left">
                        <ul class="nav navbar-nav collapse navbar-collapse">
                            <li><a href="${pageContext.request.contextPath}/home" class="active">Trang chủ</a></li>
                            <li><a href="${pageContext.request.contextPath}/blog">Tin Tức</a></li>
                            <li><a href="404.jsp">404</a></li>
                            <li><a href="${pageContext.request.contextPath}/contact">Liên hệ</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        updateCartSize();
    });
    function updateCartSize() {
        fetch('${pageContext.request.contextPath}/cart-size')
                .then(res => res.text())
                .then(size => {
                    const el = document.getElementById("cart-size");
                    if (el)
                        el.textContent = size;
                });
    }
</script>
