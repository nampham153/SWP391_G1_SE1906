<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container mt-4">
    <h3>${formAction == 'edit' ? 'Chỉnh sửa khách hàng' : 'Thêm khách hàng'}</h3>
    <form action="${pageContext.request.contextPath}/staff/customer" method="post" novalidate>
        <input type="hidden" name="action" value="${formAction}" />

        <!-- ID -->
        <div class="mb-3">
            <label for="customerId" class="form-label">ID (Số điện thoại)</label>
            <input type="text" class="form-control ${errors.id != null ? 'is-invalid' : ''}"
                   id="customerId" name="id"
                   value="${editCustomer.customerId}" 
                   ${formAction == 'edit' ? 'readonly' : ''}
                   placeholder="Nhập số điện thoại (9-12 số)">
            <c:if test="${errors.id != null}">
                <div class="invalid-feedback">${errors.id}</div>
            </c:if>
        </div>

        <!-- Tên -->
        <div class="mb-3">
            <label for="customerName" class="form-label">Tên</label>
            <input type="text" class="form-control ${errors.name != null ? 'is-invalid' : ''}" 
                   id="customerName" name="name" 
                   value="${editCustomer.customerName}" 
                   placeholder="Nhập tên khách hàng">
            <c:if test="${errors.name != null}">
                <div class="invalid-feedback">${errors.name}</div>
            </c:if>
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label for="customerEmail" class="form-label">Email</label>
            <input type="email" class="form-control ${errors.email != null ? 'is-invalid' : ''}" 
                   id="customerEmail" name="email" 
                   value="${editCustomer.customerEmail}" 
                   placeholder="Nhập email khách hàng">
            <c:if test="${errors.email != null}">
                <div class="invalid-feedback">${errors.email}</div>
            </c:if>
        </div>

        <!-- Ngày sinh -->
        <div class="mb-3">
            <label for="customerBirthDate" class="form-label">Ngày sinh</label>
            <input type="date" class="form-control ${errors.birthDate != null ? 'is-invalid' : ''}" 
                   id="customerBirthDate" name="birthDate" 
                   value="${editCustomer.customerBirthDate}">
            <c:if test="${errors.birthDate != null}">
                <div class="invalid-feedback">${errors.birthDate}</div>
            </c:if>
        </div>

        <!-- Giới tính -->
        <div class="mb-3">
            <label class="form-label">Giới tính</label><br/>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" id="genderMale" value="1"
                       ${editCustomer.customerGender ? 'checked' : ''}>
                <label class="form-check-label" for="genderMale">Nam</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" id="genderFemale" value="0"
                       ${!editCustomer.customerGender ? 'checked' : ''}>
                <label class="form-check-label" for="genderFemale">Nữ</label>
            </div>
        </div>

        <!-- Trạng thái -->
        <div class="mb-3">
            <label class="form-label">Trạng thái</label><br/>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="status" id="statusActive" value="1"
                       ${editCustomer.status ? 'checked' : ''}>
                <label class="form-check-label" for="statusActive">Kích hoạt</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="status" id="statusInactive" value="0"
                       ${!editCustomer.status ? 'checked' : ''}>
                <label class="form-check-label" for="statusInactive">Vô hiệu</label>
            </div>
        </div>

        <!-- Lỗi tổng quát -->
        <c:if test="${errors.general != null}">
            <div class="alert alert-danger">${errors.general}</div>
        </c:if>

        <button type="submit" class="btn btn-primary">${formAction == 'edit' ? 'Cập nhật' : 'Thêm mới'}</button>
        <a href="${pageContext.request.contextPath}/staff/customer" class="btn btn-secondary">Quay lại</a>
    </form>
</div>
