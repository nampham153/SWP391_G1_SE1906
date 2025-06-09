<%@page import="java.util.ArrayList"%>
<%@page import="model.ProductCategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Categories</title>
    <style>
        table { border-collapse: collapse; width: 50%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .delete-link { color: red; text-decoration: underline; cursor: pointer; }
    </style>
</head>
<body>
    <h1>Product Categories Management</h1>
    
    <!-- Form tìm kiếm -->
    <form action="ProductCategoryController" method="get">
        <label for="keyword">Search by Category Name:</label>
        <input type="text" id="keyword" name="keyword" value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
        <input type="submit" value="Search">
    </form>
    
    <!-- Form thêm danh mục -->
    <form action="ProductCategoryController" method="post">
        <label for="categoryName">Category Name:</label>
        <input type="text" id="categoryName" name="categoryName" required>
        <input type="submit" value="Add Category">
    </form>
    
    <!-- Bảng hiển thị danh sách -->
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Action</th>
        </tr>
        <% ArrayList<ProductCategory> list = (ArrayList<ProductCategory>) request.getAttribute("list");
           if (list != null && !list.isEmpty()) {
               for (ProductCategory pc : list) { %>
        <tr>
            <td><%= pc.getCategoryId() %></td>
            <td><%= pc.getCategoryName() %></td>
            <td><a class="delete-link" href="ProductCategoryController?action=delete&categoryId=<%= pc.getCategoryId() %>" 
                   onclick="return confirm('Bạn có chắc muốn xóa danh mục này?')">Xóa</a></td>
        </tr>
        <% } } else { %>
        <tr>
            <td colspan="3">No categories found.</td>
        </tr>
        <% } %>
    </table>
</body>
</html>