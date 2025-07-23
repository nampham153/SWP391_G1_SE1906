<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside class="left-side sidebar-offcanvas">
    <section class="sidebar">

        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${pageContext.request.contextPath}/img/avatar3.png" class="img-circle" alt="User Image" />
            </div>
            <div class="pull-left info">
                <p>
                    Hello, 
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            ${sessionScope.account.phone}
                        </c:when>
                        <c:otherwise>Guest</c:otherwise>
                    </c:choose>
                </p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>

        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search..."/>
                <span class="input-group-btn">
                    <button type='submit' name='seach' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
                </span>
            </div>
        </form>

        <!-- sidebar menu -->
        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/admin/admin-index.jsp">
                    <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                </a>
            </li>

            <c:if test="${sessionScope.account.roleId == 2}">
                <li>
                    <a href="${pageContext.request.contextPath}/staff/customer">
                        <i class="fa fa-users"></i> <span>Quản lý khách hàng</span>
                    </a>
                </li>
            </c:if>

            <c:if test="${sessionScope.account.roleId == 2}">
                <li>
                    <a href="${pageContext.request.contextPath}/staff/order">
                        <i class="fa fa-truck"></i> <span>Quản lý đơn hàng</span>
                    </a>
                </li>
            </c:if>

            <c:if test="${sessionScope.account.roleId == 3}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/staff">
                        <i class="fa fa-user-secret"></i> <span>Quản lý nhân viên</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.account.roleId == 3}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/product">
                        <i class="fa fa-desktop"></i> <span>Quản lý sản phẩm</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/blog">
                        <i class="fa fa-newspaper-o"></i> <span>Quản lý blog</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/report">
                        <i class="fa fa-bar-chart"></i> <span>Báo cáo & thống kê</span>
                    </a>
                </li>
            </c:if>
            <li>
                <a href="${pageContext.request.contextPath}/basic_form.html">
                    <i class="fa fa-globe"></i> <span>Basic Elements</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/simple.html">
                    <i class="fa fa-glass"></i> <span>Simple tables</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/home">
                    <i class="fa fa-globe"></i> <span>Quay về trang home</span>
                </a>
            </li>
        </ul>
    </section>
</aside>
