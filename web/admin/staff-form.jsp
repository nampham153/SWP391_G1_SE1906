<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Staff" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String error = (String) request.getAttribute("error");
    List<String> staffIdOptions = (List<String>) request.getAttribute("staffIdOptions");
    Staff staff = (Staff) request.getAttribute("staff");
    boolean isEdit = staff != null;
    request.setAttribute("isEdit", isEdit); // ✅ Gán JSTL sử dụng được
%>

<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <h5><%= isEdit ? "Cập nhật nhân viên" : "Thêm nhân viên mới" %></h5>
        </div>
        <div class="card-body">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/admin/staff" method="post">
                <input type="hidden" name="action" value="<%= isEdit ? "edit" : "add" %>" />

                <!-- Mã nhân viên -->
                <div class="form-group mb-3">
                    <label for="staffId">Mã nhân viên:</label>
                    <c:choose>
                        <c:when test="${isEdit}">
                            <!-- Gửi ngầm -->
                            <input type="hidden" name="staffId" value="<%= staff.getStaffId() %>" />
                            <!-- Chỉ hiển thị -->
                            <input type="text" class="form-control" value="<%= staff.getStaffId() %>" readonly />
                        </c:when>
                        <c:otherwise>
                            <select name="staffId" class="form-control" required>
                                <option value="">-- Chọn mã nhân viên --</option>
                                <c:forEach var="id" items="${staffIdOptions}">
                                    <option value="${id}">${id}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Họ tên -->
                <div class="form-group mb-3">
                    <label for="staffName">Họ tên:</label>
                    <input type="text" name="staffName" class="form-control"
                           value="<%= isEdit ? staff.getStaffName() : "" %>" required />
                </div>

                <!-- Chức danh -->
                <div class="form-group mb-3">
                    <label for="staffTitle">Chức danh:</label>
                    <input type="text" name="staffTitle" class="form-control"
                           value="<%= isEdit ? staff.getStaffTitle() : "" %>" required />
                </div>

                <!-- Địa chỉ -->
                <div class="form-group mb-3">
                    <label for="staffAddress">Địa chỉ:</label>
                    <input type="text" name="staffAddress" class="form-control"
                           value="<%= isEdit ? staff.getStaffAddress() : "" %>" required />
                </div>

                <!-- Ngày sinh -->
                <div class="form-group mb-3">
                    <label for="staffBirthDate">Ngày sinh:</label>
                    <input type="date" name="staffBirthDate" class="form-control"
                           value="<%= isEdit ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(staff.getStaffBirthDate()) : "" %>" required />
                </div>

                <!-- Giới tính -->
                <div class="form-group mb-3">
                    <label>Giới tính:</label><br>
                    <label><input type="radio" name="staffGender" value="true" <%= isEdit && staff.isStaffGender() ? "checked" : "" %> /> Nam</label>
                    <label class="ms-3"><input type="radio" name="staffGender" value="false" <%= isEdit && !staff.isStaffGender() ? "checked" : "" %> /> Nữ</label>
                </div>

                <!-- Phòng ban -->
                <div class="form-group mb-3">
                    <label for="departmentId">Phòng ban (ID):</label>
                    <input type="number" name="departmentId" class="form-control"
                           value="<%= isEdit ? staff.getDepartmentId() : "" %>" required />
                </div>

                <!-- Trạng thái -->
                <div class="form-group mb-3">
                    <label for="status">Trạng thái:</label>
                    <select name="status" class="form-control" required>
                        <option value="true" <%= isEdit && staff.getStatus() ? "selected" : "" %>>Hoạt động</option>
                        <option value="false" <%= isEdit && !staff.getStatus() ? "selected" : "" %>>Ngưng</option>
                    </select>
                </div>

                <!-- Nút -->
                <div class="mt-3">
                    <button type="submit" class="btn btn-success">Lưu</button>
                    <a href="${pageContext.request.contextPath}/admin/staff" class="btn btn-secondary">Huỷ</a>
                </div>
            </form>
        </div>
    </div>
</div>
