<%-- 
    Document   : brand-manage
    Created on : Jun 9, 2025, 7:13:40 AM
    Author     : namp0
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Brand" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <main class="content">
    <div class="container-fluid p-0">

        <h1 class="h3 mb-3">Quản lý Brand</h1>

        <div class="row">
            <div class="col-12 col-xl-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">
                            <%= request.getAttribute("editBrand") != null ? "Chỉnh sửa Brand" : "Thêm Brand mới" %>
                        </h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="brand">
                            <input type="hidden" name="action" value="<%= request.getAttribute("editBrand") != null ? "edit" : "add" %>"/>
                            <% Brand b = (Brand) request.getAttribute("editBrand"); %>
                            <% if (b != null) { %>
                                <input type="hidden" name="id" value="<%= b.getBrandId() %>"/>
                            <% } %>
                            <div class="mb-3">
                                <label class="form-label">Tên Brand</label>
                                <input type="text" class="form-control" name="name" value="<%= b != null ? b.getBrandName() : "" %>" required />
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <%= b != null ? "Cập nhật" : "Thêm mới" %>
                            </button>
                        </form>
                        <% String error = (String) request.getAttribute("error");
                           if (error != null) { %>
                            <div class="alert alert-danger mt-2"><%= error %></div>
                        <% } %>
                    </div>
                </div>
            </div>

            <div class="col-12 col-xl-12 mt-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Danh sách Brand</h5>
                    </div>
                    <div class="card-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên Brand</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                List<Brand> list = (List<Brand>) request.getAttribute("brands");
                                if (list != null) {
                                    for (Brand brand : list) {
                            %>
                            <tr>
                                <td><%= brand.getBrandId() %></td>
                                <td><%= brand.getBrandName() %></td>
                                <td>
                                    <a href="brand?action=edit&id=<%= brand.getBrandId() %>" class="btn btn-sm btn-warning">Sửa</a>
                                    <a href="brand?action=delete&id=<%= brand.getBrandId() %>"
                                       class="btn btn-sm btn-danger"
                                       onclick="return confirm('Bạn có chắc muốn xóa brand này?');">Xóa</a>
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
            </div>
        </div>

    </div>
</main>
    </body>
</html>
