<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header id="header">
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
                                            <i class="fa fa-shopping-cart"></i> Cart
                                            (<span id="cart-size" style="color: orange;">${cartSize}</span>)
                                        </a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-unlock"></i> Logout</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="#"><i class="fa fa-user"></i> Xin chào, Khách</a></li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/cart">
                                            <i class="fa fa-shopping-cart"></i> Cart
                                            (<span id="cart-size" style="color: orange;">${cartSize}</span>)
                                        </a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/login"><i class="fa fa-lock"></i> Login</a></li>
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
                            <li><a href="${pageContext.request.contextPath}/home" class="active">Home</a></li>
                            <li><a href="${pageContext.request.contextPath}/blog">Blog</a></li>
                            <li><a href="404.jsp">404</a></li>
                            <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

<script>
        window.addEventListener("DOMContentLoaded", function () {
        updateCartSize();
    });
function updateCartSize() {
    fetch('${pageContext.request.contextPath}/cart-size')
        .then(res => res.text())
        .then(size => {
            const el = document.getElementById("cart-size");
            if (el) el.textContent = size;
        });
}
</script>
