<%-- 
    Document   : customer-manage1
    Created on : Jun 9, 2025, 8:59:47 AM
    Author     : namp0
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Customer" %>
<%
    String error = (String) request.getAttribute("error");
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Director | General UI</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Ionicons -->
        <link href="${pageContext.request.contextPath}/css/ionicons.min.css" rel="stylesheet" type="text/css" />

        <link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
        <!-- Theme style -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />


        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
    <h2 class="h4 mb-3">Quản lý khách hàng</h2>

    <!-- Form tìm kiếm -->
    <form action="${pageContext.request.contextPath}/admin/customer" method="get" class="row g-3 mb-3 align-items-center">
        <input type="hidden" name="action" value="search" />

        <!-- Tên -->
        <div class="col-md-4">
            <input type="text" class="form-control" name="name" placeholder="Tìm theo tên..." />
        </div>

        <!-- Trạng thái + Tìm kiếm -->
        <div class="col-md-5 d-flex align-items-center gap-2">
            <select class="form-select" name="status" style="max-width: 200px;">
                <option value="">-- Trạng thái --</option>
                <option value="1">Hoạt động</option>
                <option value="0">Ngưng hoạt động</option>
            </select>
            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
        </div>

        <!-- Thêm khách hàng -->
        <div class="col-md-3 text-end">
            <button type="button" class="btn btn-success" onclick="openAddModal()">+ Thêm khách hàng</button>
        </div>
    </form>

    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <!-- Bảng danh sách khách hàng -->
    <div class="card">
        <div class="card-body">
            <table class="table table-bordered align-middle">
                <thead>
                    <tr>
                        <th>ID</th><th>Tên</th><th>Email</th><th>Ngày sinh</th><th>Giới tính</th><th>Trạng thái</th><th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (customers != null) {
                        for (Customer c : customers) { %>
                    <tr>
                        <td><%= c.getCustomerId() %></td>
                        <td><%= c.getCustomerName() %></td>
                        <td><%= c.getCustomerEmail() %></td>
                        <td><%= c.getCustomerBirthDate() %></td>
                        <td><%= c.isCustomerGender() ? "Nam" : "Nữ" %></td>
                        <td><%= c.getStatus() ? "Hoạt động" : "Ngưng" %></td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick='openEditModal("<%= c.getCustomerId() %>", "<%= c.getCustomerName() %>", "<%= c.getCustomerEmail() %>", "<%= c.getCustomerBirthDate() %>", <%= c.isCustomerGender() %>, <%= c.getStatus() %>)'>Sửa</button>
                            <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/admin/customer?action=delete&id=<%= c.getCustomerId() %>" 
                               onclick="return confirm('Bạn có chắc muốn vô hiệu hóa khách hàng này? Nếu khách hàng đã ngưng hoạt động, sẽ không có thay đổi nào xảy ra.')">
                                Xoá
                            </a>
                        </td>
                    </tr>
                    <% } } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Modal Thêm/Sửa -->
    <div id="customerModal" class="modal" tabindex="-1" style="display:none;">
        <div class="modal-dialog">
            <div class="modal-content p-3">
                <div class="modal-header">
                    <h5 id="modalTitle" class="modal-title">Thêm khách hàng</h5>
                    <button type="button" class="btn-close" onclick="closeModal()"></button>
                </div>
                <form id="customerForm" action="${pageContext.request.contextPath}/admin/customer" method="post">
                    <input type="hidden" name="action" id="actionInput" value="">
                    <div class="modal-body">
                        ID: <input type="text" class="form-control mb-2" name="id" id="id" required>
                        Tên: <input type="text" class="form-control mb-2" name="name" id="name" required>
                        Email: <input type="email" class="form-control mb-2" name="email" id="email">
                        Ngày sinh: <input type="date" class="form-control mb-2" name="birthDate" id="birthDate">
                        Giới tính:
                        <select name="gender" id="gender" class="form-select mb-2">
                            <option value="1">Nam</option>
                            <option value="0">Nữ</option>
                        </select>
                        Trạng thái:
                        <select name="status" id="status" class="form-select mb-2">
                            <option value="1">Hoạt động</option>
                            <option value="0">Ngưng hoạt động</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Lưu</button>
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Đóng</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </body>
</html>
