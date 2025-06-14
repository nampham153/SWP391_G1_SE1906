<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Staff" %>
<%
    String error = (String) request.getAttribute("error");
    List<Staff> staffList = (List<Staff>) request.getAttribute("staffList");
%>
<div class="card m-4">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Quản lý Nhân viên</h5>
        <!-- Nút chuyển sang trang thêm -->
        <a href="${pageContext.request.contextPath}/admin/staff?action=add" class="btn btn-primary">+ Thêm mới</a>
    </div>
  
    <div class="card-body">
        <table id="staffTable" class="table table-bordered align-middle">
            <thead>
                <tr>
                    <th>ID</th><th>Tên</th><th>Chức danh</th><th>Địa chỉ</th><th>Ngày sinh</th><th>Giới tính</th><th>Phòng ban</th><th>Trạng thái</th><th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Staff> list = (List<Staff>) request.getAttribute("staffList");
                    if (list != null) {
                        for (Staff s : list) {
                %>
                <tr>
                    <td><%= s.getStaffId()%></td>
                    <td><%= s.getStaffName()%></td>
                    <td><%= s.getStaffTitle()%></td>
                    <td><%= s.getStaffAddress()%></td>
                    <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(s.getStaffBirthDate())%></td>
                    <td><%= s.isStaffGender() ? "Nam" : "Nữ"%></td>
                    <td><%= s.getDepartmentId()%></td>
                    <td><%= s.isStatus() ? "Hoạt động" : "Ngưng"%></td>
                    <td>
                        <a class="btn btn-warning btn-sm" 
                           href="${pageContext.request.contextPath}/admin/staff?action=edit&id=<%= s.getStaffId()%>">
                            Sửa
                        </a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/admin/staff?action=delete&id=<%= s.getStaffId()%>"
                           onclick="return confirm('Bạn có chắc muốn vô hiệu hóa nhân viên này?')">
                            Xoá
                        </a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="//cdn.datatables.net/2.3.2/js/dataTables.min.js"></script>
<script>
                                   $(document).ready(function () {
                                       new DataTable('#staffTable');
                                   });
</script>


