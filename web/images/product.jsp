<%-- 
    Document   : product
    Created on : 8 Jun 2025, 23:48:55
    Author     : DELL
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Management</title>
    <style>
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .delete-link { color: red; text-decoration: underline; cursor: pointer; }
        .edit-link { color: blue; text-decoration: underline; cursor: pointer; }
    </style>
</head>
<body>
    <h1>Product Management</h1>

    <!-- Form thêm sản phẩm -->
    <h3>Add Product</h3>
    <form action="ProductController" method="post">
        <input type="hidden" name="action" value="add">
        ID: <input type="text" name="id" required><br>
        Category ID: <input type="number" name="categoryId" required><br>
        Item Name: <input type="text" name="itemName" required><br>
        Stock: <input type="number" name="stock" required><br>
        Price: <input type="number" step="0.01" name="price" required><br>
        Views: <input type="number" name="views" required><br>
        <input type="submit" value="Add Product">
    </form>

    <!-- Form sửa sản phẩm -->
    <h3>Edit Product</h3>
    <form action="ProductController" method="post">
        <input type="hidden" name="action" value="edit">
        ID: <input type="text" name="id" value="${product.productId}" readonly><br>
        Category ID: <input type="number" name="categoryId" value="${product.categoryId}" required><br>
        Item Name: <input type="text" name="itemName" value="${product.item.itemName}" required><br>
        Stock: <input type="number" name="stock" value="${product.item.stock}" required><br>
        Price: <input type="number" step="0.01" name="price" value="${product.item.price}" required><br>
        Views: <input type="number" name="views" value="${product.item.views}" required><br>
        <input type="submit" value="Update Product">
    </form>

    <!-- Bảng hiển thị danh sách -->
    <table>
        <tr>
            <th>ID</th>
            <th>Category ID</th>
            <th>Item Name</th>
            <th>Stock</th>
            <th>Price</th>
            <th>Views</th>
            <th>Action</th>
        </tr>
        <% ArrayList<Product> list = (ArrayList<Product>) request.getAttribute("list");
           if (list != null) {
               for (Product p : list) { %>
        <tr>
            <td><%= p.getProductId() %></td>
            <td><%= p.getCategoryId() %></td>
            <td><%= p.getItem() != null ? p.getItem().getItemName() : "N/A" %></td>
            <td><%= p.getItem() != null ? p.getItem().getStock() : "N/A" %></td>
            <td><%= p.getItem() != null ? p.getItem().getPrice() : "N/A" %></td>
            <td><%= p.getItem() != null ? p.getItem().getViews() : "N/A" %></td>
            <td>
                <a class="edit-link" href="ProductController?action=edit&id=<%= p.getProductId() %>">Edit</a> |
                <a class="delete-link" href="ProductController?action=delete&id=<%= p.getProductId() %>" 
                   onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?')">Delete</a>
            </td>
        </tr>
        <% } } else { %>
        <tr>
            <td colspan="7">No products found.</td>
        </tr>
        <% } %>
    </table>
</body>
</html>
