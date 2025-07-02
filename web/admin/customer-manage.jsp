<%@page import="model.Customer"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>

<div class="card m-4">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Quản lý khách hàng</h5>
        <!-- Nút chuyển sang trang thêm -->
        <a href="${pageContext.request.contextPath}/staff/customer?action=add" class="btn btn-primary">+ Thêm mới</a>
    </div>
    <div class="card-body">
        <table id="customerTable" class="table table-bordered align-middle">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên</th>
                    <th>Email</th>
                    <th>Ngày sinh</th>
                    <th>Giới tính</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <% if (customers != null) {
                       for (Customer c : customers) { %>
                    <tr>
                        <td><%= c.getCustomerId() %></td>
                        <td><%= c.getCustomerName() %></td>
                        <td><%= c.getCustomerEmail() %></td>
                        <td><%= c.getCustomerBirthDate() != null ? c.getCustomerBirthDate().toString() : "" %></td>
                        <td><%= c.isCustomerGender() ? "Nam" : "Nữ" %></td>
                        <td><%= c.getStatus() ? "Hoạt động" : "Ngưng" %></td>
                        <td>
                            <a class="btn btn-warning btn-sm" 
                               href="${pageContext.request.contextPath}/staff/customer?action=edit&id=<%= c.getCustomerId() %>">
                               Sửa
                            </a>
                            <a class="btn btn-danger btn-sm"
                               href="${pageContext.request.contextPath}/staff/customer?action=delete&id=<%= c.getCustomerId() %>"
                               onclick="return confirm('Bạn có chắc muốn vô hiệu hóa khách hàng này?')">
                                Xoá
                            </a>
                        </td>
                    </tr>
                <% }
                   } %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="//cdn.datatables.net/2.3.2/js/dataTables.min.js"></script>
<script>
    $(document).ready(function () {
        new DataTable('#customerTable');
    });
</script>
