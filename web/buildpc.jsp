<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="vi_VN"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Build PC</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <style>
        .build-pc-category_header {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .build-pc-category img {
            border: 1px solid #ccc;
            margin-right: 15px;
        }
        .selected-product {
            padding: 10px;
            border: 1px dashed #ccc;
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center mb-4">Tự Build Cấu Hình PC</h1>
    <div class="row" id="build-pc-section">

        <!-- Danh sách cố định các loại linh kiện -->
        <c:forEach var="spec" items="${fn:split('CPU,Mainboard,RAM,Card đồ họa,Ổ cứng,Nguồn,Tản nhiệt,Vỏ Case,Màn hình', ',')}">
            <c:set var="handle" value="${fn:replace(fn:toLowerCase(spec), ' ', '-')}" />
            <div class="col-md-6 build-pc-category my-4" data-handle="${handle}">
                <div class="build-pc-category_header p-3 bg-light border">
                    <div class="d-flex align-items-center">
                        <img src="images/spec-icons/${handle}.jpg" alt="${spec}" width="80" height="80" class="rounded">
                        <h5 class="ml-3 mb-0">${spec}</h5>
                    </div>
                    <button class="btn btn-primary" onclick="openProductModal('${handle}', '${spec}')">Chọn ${spec}</button>
                </div>
                <div class="selected-product mt-2" id="selected-${handle}"></div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Modal (giả lập tạm thời) -->
<div class="modal" id="productModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chọn linh kiện</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <input type="text" class="form-control mb-3" placeholder="Tìm theo tên...">
                <div id="product-list" class="row">
                    <!-- AJAX trả về danh sách linh kiện theo spec sẽ render ở đây -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- BẢNG TỔNG KẾT CẤU HÌNH ĐÃ CHỌN -->
<div class="container mt-5">
    <h3 class="text-center">Tổng kết cấu hình của bạn</h3>
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
            <tr>
                <th>Linh kiện</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Thành tiền</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${selectedComponents}">
                <tr>
                    <td>${entry.key}</td> <!-- tên category: CPU, RAM,... -->
                    <td>${entry.value.itemName}</td>
                    <td><fmt:formatNumber value="${entry.value.price}" type="currency"/></td>
                    <td>1</td>
                    <td><fmt:formatNumber value="${entry.value.price}" type="currency"/></td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <th colspan="4" class="text-right">Tổng cộng:</th>
                <th>
                    <fmt:formatNumber value="${totalPrice}" type="currency"/>
                </th>
            </tr>
        </tfoot>
    </table>
</div>


<script>
    function openProductModal(handle, specName) {
        $("#productModal .modal-title").text("Chọn " + specName);
        $("#product-list").html("<div class='col-12'>Đang tải...</div>");
        $("#productModal").modal("show");

        fetch(`get-items-by-spec?spec=${handle}`)
            .then(res => res.json())
            .then(items => {
                const html = items.map(item => `
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <img src="images/home/${item.image}" class="card-img-top" style="height: 180px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title">${item.name}</h5>
                                <p class="card-text"><strong>${item.price.toLocaleString()} VNĐ</strong></p>
                                <button class="btn btn-success btn-sm" onclick="selectItem('${handle}', '${item.serialNumber}', '${item.name}', ${item.price})">Chọn</button>
                            </div>
                        </div>
                    </div>`).join('');
                $("#product-list").html(html);
            });
    }

    function selectItem(handle, serial, name, price) {
        const html = `
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <strong>${name}</strong><br/>
                    Giá: ${price.toLocaleString()} VNĐ
                </div>
                <input type="number" value="1" min="1" class="form-control ml-3" style="width: 70px;">
            </div>`;
        document.getElementById("selected-" + handle).innerHTML = html;
        $('#productModal').modal('hide');
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
