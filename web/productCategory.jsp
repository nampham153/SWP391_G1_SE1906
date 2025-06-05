<%-- 
    Document   : productCategory.jsp
    Created on : 5 Jun 2025, 21:46:12
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Category Management</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-container { width: 50%; margin: 20px auto; }
    </style>
</head>
<body>
    <h2 style="text-align: center;">Product Category Management</h2>

    <!-- Form to create or update -->
    <div class="form-container">
        <form action="productCategory" method="post">
            <input type="hidden" name="action" value="${productCategory != null ? 'update' : 'create'}">
            <input type="hidden" name="id" value="${productCategory != null ? productCategory.categoryId : ''}">
            <label>Category Name:</label><br>
            <input type="text" name="categoryName" value="${productCategory != null ? productCategory.categoryName : ''}" required><br><br>
            <input type="submit" value="${productCategory != null ? 'Update' : 'Create'}">
        </form>
    </div>

    <!-- List of categories -->
    <table>
        <tr>
            <th>ID</th>
            <th>Category Name</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="pc" items="${productCategories}">
            <tr>
                <td>${pc.categoryId}</td>
                <td>${pc.categoryName}</td>
                <td>
                    <a href="productCategory?action=edit&id=${pc.categoryId}">Edit</a> |
                    <a href="productCategory?action=delete&id=${pc.categoryId}" 
                       onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>