<%@page import="model.Customer"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Customer c = (Customer) request.getAttribute("editCustomer");
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
    String formAction = (String) request.getAttribute("formAction");
    if (formAction == null) {
        // Nếu không có formAction thì mặc định là add
        formAction = (c != null && c.getCustomerId() != null) ? "edit" : "add";
    }
%>

<div class="container mt-4">
    <h3><%= ("edit".equals(formAction) ? "Chỉnh sửa khách hàng" : "Thêm khách hàng") %></h3>
    <form action="${pageContext.request.contextPath}/admin/customer" method="post" novalidate>
        <input type="hidden" name="action" value="<%= formAction %>"/>

        <div class="mb-3">
            <label for="customerId" class="form-label">ID (Số điện thoại)</label>
            <input type="text" class="form-control <% if(errors != null && errors.get("id") != null) out.print("is-invalid"); %>" 
                   id="customerId" name="id" 
                   value="<%= c != null ? c.getCustomerId() : "" %>" 
                   <%= "edit".equals(formAction) ? "readonly" : "" %> 
                   placeholder="Nhập số điện thoại (9-12 số)">
            <div class="invalid-feedback"><%= errors != null ? errors.get("id") : "" %></div>
        </div>

        <div class="mb-3">
            <label for="customerName" class="form-label">Tên</label>
            <input type="text" class="form-control <% if(errors != null && errors.get("name") != null) out.print("is-invalid"); %>" 
                   id="customerName" name="name" 
                   value="<%= c != null ? c.getCustomerName() : "" %>" 
                   placeholder="Nhập tên khách hàng">
            <div class="invalid-feedback"><%= errors != null ? errors.get("name") : "" %></div>
        </div>

        <div class="mb-3">
            <label for="customerEmail" class="form-label">Email</label>
            <input type="email" class="form-control <% if(errors != null && errors.get("email") != null) out.print("is-invalid"); %>" 
                   id="customerEmail" name="email" 
                   value="<%= c != null ? c.getCustomerEmail() : "" %>" 
                   placeholder="Nhập email khách hàng">
            <div class="invalid-feedback"><%= errors != null ? errors.get("email") : "" %></div>
        </div>

        <div class="mb-3">
            <label for="customerBirthDate" class="form-label">Ngày sinh</label>
            <input type="date" class="form-control <% if(errors != null && errors.get("birthDate") != null) out.print("is-invalid"); %>" 
                   id="customerBirthDate" name="birthDate" 
                   value="<%= c != null && c.getCustomerBirthDate() != null ? c.getCustomerBirthDate().toString() : "" %>">
            <div class="invalid-feedback"><%= errors != null ? errors.get("birthDate") : "" %></div>
        </div>

        <div class="mb-3">
            <label class="form-label">Giới tính</label><br/>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" id="genderMale" value="1"
                    <%= (c == null || c.isCustomerGender()) ? "checked" : "" %>>
                <label class="form-check-label" for="genderMale">Nam</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" id="genderFemale" value="0"
                    <%= (c != null && !c.isCustomerGender()) ? "checked" : "" %>>
                <label class="form-check-label" for="genderFemale">Nữ</label>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">Trạng thái</label><br/>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="status" id="statusActive" value="1"
                    <%= (c == null || c.isStatus()) ? "checked" : "" %>>
                <label class="form-check-label" for="statusActive">Kích hoạt</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="status" id="statusInactive" value="0"
                    <%= (c != null && !c.isStatus()) ? "checked" : "" %>>
                <label class="form-check-label" for="statusInactive">Vô hiệu</label>
            </div>
        </div>

        <% if (errors != null && errors.get("general") != null) { %>
            <div class="alert alert-danger"><%= errors.get("general") %></div>
        <% } %>

        <button type="submit" class="btn btn-primary"><%= ("edit".equals(formAction) ? "Cập nhật" : "Thêm mới") %></button>
        <a href="<%= request.getContextPath() %>/admin/customer" class="btn btn-secondary">Quay lại</a>
    </form>
</div>
