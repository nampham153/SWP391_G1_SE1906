<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <title>Build PC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { background-color: #1c1c1c; color: #fff; }
        .component-card {
            background-color: #2b2b2b;
            border-radius: 8px;
            padding: 16px;
            margin-bottom: 20px;
        }
        .component-card img {
            width: 100px;
            height: 100px;
            object-fit: contain;
        }
        .summary-table {
            background-color: #2b2b2b;
            border-radius: 8px;
            padding: 16px;
        }
        .btn-select {
            background-color: #555;
            color: white;
        }
        .btn-select:hover {
            background-color: #777;
        }
        .modal-content {
            background-color: #2c2c2c;
            color: white;
        }
        input.form-control {
            background-color: #444;
            color: white;
        }
    </style>
</head>
<body>

<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>

<div class="container mt-4">
    <h2 class="mb-4">🛠️ Build PC Của Bạn</h2>

    <!-- List Component -->
    <c:forEach var="category" items="${componentCategories}">
        <div class="component-card row align-items-center">
            <div class="col-md-6 d-flex align-items-center">
                <img src="${pageContext.request.contextPath}/images/home/${category.image}" alt="${category.categoryName}">
                <div class="ms-3">
                    <h5>${category.categoryName}</h5>
                </div>
            </div>
            <div class="col-md-6 text-end">
                <div class="d-flex flex-column justify-content-center align-items-end" style="min-height: 100px;">
                    <button class="btn btn-select mb-2"
                            data-category-id="${category.categoryId}"
                            data-category-name="${category.categoryName}"
                            onclick="handleOpenPopup(this)">
                        Chọn ${category.categoryName}
                    </button>
                    <div class="d-flex align-items-center">
                        <label class="me-2">Số lượng:</label>
                        <input type="number" min="1" value="1" class="form-control me-2"
                               style="width:70px;" onchange="updateQuantity(${category.categoryId}, this.value)">
                        <button class="btn btn-danger" onclick="removeComponent(${category.categoryId})">Xóa</button>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>

    <!-- Summary -->
    <div class="summary-table">
        <h4>Tóm tắt cấu hình</h4>
        <table class="table table-dark table-bordered">
            <thead>
                <tr>
                    <th>Danh mục</th>
                    <th>Tên linh kiện</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody id="summaryBody"></tbody>
            <tfoot>
                <tr>
                    <th colspan="4" class="text-end">Tổng:</th>
                    <th id="totalPrice">0 VNĐ</th>
                </tr>
            </tfoot>
        </table>
        <div class="d-flex justify-content-between">
            <button class="btn btn-primary" onclick="clearAll()">Xóa tất cả</button>
            <div>
                <button class="btn btn-success">Thêm tất cả vào giỏ</button>
                <button class="btn btn-info">In báo giá</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Popup -->
<div class="modal fade" id="componentModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="modalTitle">Chọn Component</h5>
                <button class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <input type="text" id="searchInput" class="form-control mb-3" placeholder="Tìm kiếm linh kiện...">
                <table class="table table-dark table-bordered">
                    <thead>
                        <tr>
                            <th>Hình ảnh</th>
                            <th>Tên</th>
                            <th>Giá</th>
                            <th>Chọn</th>
                        </tr>
                    </thead>
                    <tbody id="componentList"></tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
<script>
    const selectedComponents = {};

    function handleOpenPopup(button) {
        const categoryId = button.getAttribute('data-category-id');
        const categoryName = button.getAttribute('data-category-name');
            if (!categoryId) {
        alert('Không tìm thấy categoryId');
        return;
    }
    openPopup(categoryId, categoryName);
    }

    function openPopup(categoryId, categoryName) {
        $('#modalTitle').text(`Chọn ${categoryName}`);
        $('#searchInput').val('');
        const contextPath = '${pageContext.request.contextPath}';
        fetch(`${contextPath}/SWP391_G1/getComponentList?categoryId=${categoryId}`)
            .then(res => res.json())
            .then(data => {
                const rows = data.map(item => `
                    <tr>
                        <td><img src="${contextPath}/images/home/${item.image}" width="60"/></td>
                        <td>${item.name}</td>
                        <td>${item.price.toLocaleString()} VNĐ</td>
                        <td><button class="btn btn-sm btn-success"
                            onclick="selectComponent(${categoryId}, '${categoryName}', '${item.id}', '${item.name}', ${item.price})">
                            Chọn
                        </button></td>
                    </tr>
                `).join('');
                $('#componentList').html(rows);
                new bootstrap.Modal(document.getElementById('componentModal')).show();
            });
    }

    $('#searchInput').on('input', function () {
        const search = $(this).val().toLowerCase();
        $('#componentList tr').filter(function () {
            $(this).toggle($(this).text().toLowerCase().includes(search));
        });
    });

    function selectComponent(categoryId, categoryName, id, name, price) {
        selectedComponents[categoryId] = {categoryName, id, name, price, quantity: 1};
        updateSummary();
        bootstrap.Modal.getInstance(document.getElementById('componentModal')).hide();
    }

    function updateQuantity(categoryId, quantity) {
        if (selectedComponents[categoryId]) {
            selectedComponents[categoryId].quantity = parseInt(quantity);
            updateSummary();
        }
    }

    function removeComponent(categoryId) {
        delete selectedComponents[categoryId];
        updateSummary();
    }

    function clearAll() {
        for (let key in selectedComponents) delete selectedComponents[key];
        updateSummary();
    }

    function updateSummary() {
        const tbody = $('#summaryBody');
        tbody.empty();
        let total = 0;
        for (const key in selectedComponents) {
            const c = selectedComponents[key];
            const lineTotal = c.price * c.quantity;
            total += lineTotal;
            tbody.append(`
                <tr>
                    <td>${c.categoryName}</td>
                    <td>${c.name}</td>
                    <td>${c.price.toLocaleString()} VNĐ</td>
                    <td>${c.quantity}</td>
                    <td>${lineTotal.toLocaleString()} VNĐ</td>
                </tr>
            `);
        }
        $('#totalPrice').text(total.toLocaleString() + ' VNĐ');
    }
</script>

</body>
</html>
