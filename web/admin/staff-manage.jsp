<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Staff" %>
<%
    String error = (String) request.getAttribute("error");
    List<Staff> staffList = (List<Staff>) request.getAttribute("staffList");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Director | General UI</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <meta name="description" content="Developed By M Abdur Rokib Promy">
        <meta name="keywords" content="Admin, Bootstrap 3, Template, Theme, Responsive">
        <!-- bootstrap 3.0.2 -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Ionicons -->
        <link href="${pageContext.request.contextPath}/css/ionicons.min.css" rel="stylesheet" type="text/css" />

        <link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
        <!-- Theme style -->
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />


        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="skin-black">
        <!-- header logo: style can be found in header.less -->
        <header class="header">
            <a href="index.html" class="logo">
                <!-- Add the class icon to your logo image or logo icon to add the margining -->
                Director
            </a>
            <!-- Header Navbar: style can be found in header.less -->
            <nav class="navbar navbar-static-top" role="navigation">
                <!-- Sidebar toggle button-->
                <a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                        <!-- Messages: style can be found in dropdown.less-->
                        <li class="dropdown messages-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-envelope"></i>
                                <span class="label label-success">4</span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="header">You have 4 messages</li>
                                <li>
                                    <!-- inner menu: contains the actual data -->
                                    <ul class="menu">
                                        <li><!-- start message -->
                                            <a href="#">
                                                <div class="pull-left">
                                                    <img src="${pageContext.request.contextPath}/img/avatar3.png" class="img-circle" alt="User Image"/>
                                                </div>
                                                <h4>
                                                    Support Team
                                                    <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                                </h4>
                                                <p>Why not buy a new awesome theme?</p>
                                            </a>
                                        </li><!-- end message -->
                                        <li>
                                            <a href="#">
                                                <div class="pull-left">
                                                    <img src="${pageContext.request.contextPath}/img/avatar2.png" class="img-circle" alt="user image"/>
                                                </div>
                                                <h4>
                                                    Director Design Team
                                                    <small><i class="fa fa-clock-o"></i> 2 hours</small>
                                                </h4>
                                                <p>Why not buy a new awesome theme?</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <div class="pull-left">
                                                    <img src="${pageContext.request.contextPath}/img/avatar.png" class="img-circle" alt="user image"/>
                                                </div>
                                                <h4>
                                                    Developers
                                                    <small><i class="fa fa-clock-o"></i> Today</small>
                                                </h4>
                                                <p>Why not buy a new awesome theme?</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <div class="pull-left">
                                                    <img src="${pageContext.request.contextPath}/img/avatar2.png" class="img-circle" alt="user image"/>
                                                </div>
                                                <h4>
                                                    Sales Department
                                                    <small><i class="fa fa-clock-o"></i> Yesterday</small>
                                                </h4>
                                                <p>Why not buy a new awesome theme?</p>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <div class="pull-left">
                                                    <img src="${pageContext.request.contextPath}/img/avatar.png" class="img-circle" alt="user image"/>
                                                </div>
                                                <h4>
                                                    Reviewers
                                                    <small><i class="fa fa-clock-o"></i> 2 days</small>
                                                </h4>
                                                <p>Why not buy a new awesome theme?</p>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="footer"><a href="#">See All Messages</a></li>
                            </ul>
                        </li>

                        <!-- Tasks: style can be found in dropdown.less -->
                        <li class="dropdown tasks-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-tasks"></i>
                                <span class="label label-danger">9</span>
                            </a>
                            <ul class="dropdown-menu">

                                <li class="header">You have 9 tasks</li>
                                <li>
                                    <!-- inner menu: contains the actual data -->
                                    <ul class="menu">
                                        <li><!-- Task item -->
                                            <a href="#">
                                                <h3>
                                                    Design some buttons
                                                    <small class="pull-right">20%</small>
                                                </h3>
                                                <div class="progress progress-striped xs">
                                                    <div class="progress-bar progress-bar-success" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">20% Complete</span>
                                                    </div>
                                                </div>
                                            </a>
                                        </li><!-- end task item -->
                                        <li><!-- Task item -->
                                            <a href="#">
                                                <h3>
                                                    Create a nice theme
                                                    <small class="pull-right">40%</small>
                                                </h3>
                                                <div class="progress progress-striped xs">
                                                    <div class="progress-bar progress-bar-danger" style="width: 40%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">40% Complete</span>
                                                    </div>
                                                </div>
                                            </a>
                                        </li><!-- end task item -->
                                        <li><!-- Task item -->
                                            <a href="#">
                                                <h3>
                                                    Some task I need to do
                                                    <small class="pull-right">60%</small>
                                                </h3>
                                                <div class="progress progress-striped xs">
                                                    <div class="progress-bar progress-bar-info" style="width: 60%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">60% Complete</span>
                                                    </div>
                                                </div>
                                            </a>
                                        </li><!-- end task item -->
                                        <li><!-- Task item -->
                                            <a href="#">
                                                <h3>
                                                    Make beautiful transitions
                                                    <small class="pull-right">80%</small>
                                                </h3>
                                                <div class="progress progress-striped xs">
                                                    <div class="progress-bar progress-bar-warning" style="width: 80%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                        <span class="sr-only">80% Complete</span>
                                                    </div>
                                                </div>
                                            </a>
                                        </li><!-- end task item -->
                                    </ul>
                                </li>
                                <li class="footer">
                                    <a href="#">View all tasks</a>
                                </li>

                            </ul>
                        </li>
                        <!-- User Account: style can be found in dropdown.less -->
                        <li class="dropdown user user-menu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-user"></i>
                                <span>Jane Doe <i class="caret"></i></span>
                            </a>
                            <ul class="dropdown-menu dropdown-custom dropdown-menu-right">
                                <li class="dropdown-header text-center">Account</li>

                                <li>
                                    <a href="#">
                                        <i class="fa fa-clock-o fa-fw pull-right"></i>
                                        <span class="badge badge-success pull-right">10</span> Updates</a>
                                    <a href="#">
                                        <i class="fa fa-envelope-o fa-fw pull-right"></i>
                                        <span class="badge badge-danger pull-right">5</span> Messages</a>
                                    <a href="#"><i class="fa fa-magnet fa-fw pull-right"></i>
                                        <span class="badge badge-info pull-right">3</span> Subscriptions</a>
                                    <a href="#"><i class="fa fa-question fa-fw pull-right"></i> <span class=
                                                                                                      "badge pull-right">11</span> FAQ</a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="#">
                                        <i class="fa fa-user fa-fw pull-right"></i>
                                        Profile
                                    </a>
                                    <a data-toggle="modal" href="#modal-user-settings">
                                        <i class="fa fa-cog fa-fw pull-right"></i>
                                        Settings
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="#"><i class="fa fa-ban fa-fw pull-right"></i> Logout</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="wrapper row-offcanvas row-offcanvas-left">
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="left-side sidebar-offcanvas">
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="${pageContext.request.contextPath}/img/avatar3.png" class="img-circle" alt="User Image" />
                        </div>
                        <div class="pull-left info">
                            <p>Hello, Jane</p>

                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!-- search form -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search..."/>
                            <span class="input-group-btn">
                                <button type='submit' name='seach' id='search-btn' class="btn btn-flat"><i class="fa fa-search"></i></button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->
                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    <ul class="sidebar-menu">
                        <li>
                            <a href="index.html">
                                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                            </a>
                        </li>
                        <li class="treeview active">
                            <a href="#">
                                <i class="fa fa-gavel"></i> <span>General</span>
                                <i class="fa fa-angle-left pull-right"></i>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="general-overview.html"><i class="fa fa-circle-o"></i> Overview</a></li>
                                <li><a href="general-settings.html"><i class="fa fa-circle-o"></i> Settings</a></li>
                                <li><a href="general-reports.html"><i class="fa fa-circle-o"></i> Reports</a></li>
                            </ul>
                        </li>

                        <li>
                            <a href="basic_form.html">
                                <i class="fa fa-globe"></i> <span>Basic Elements</span>
                            </a>
                        </li>

                        <li>
                            <a href="simple.html">
                                <i class="fa fa-glass"></i> <span>Simple tables</span>
                            </a>
                        </li>

                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>

            <div class="right-side">
                <h2 class="h4 mb-3">Quản lý nhân viên</h2>
                <!-- Form tìm kiếm -->
                <form action="${pageContext.request.contextPath}/admin/staff" method="get" class="row g-3 mb-3 align-items-center">
                    <input type="hidden" name="action" value="search" />

                    <!-- Tên -->
                    <div class="col-md-4">
                        <input type="text" class="form-control" name="keyword" placeholder="Tìm theo tên..." value="${param.keyword}" />
                    </div>

                    <!-- Trạng thái + Tìm kiếm -->
                    <div class="col-md-5 d-flex align-items-center gap-2">
                        <select class="form-select" name="status" style="max-width: 200px;">
                            <option value="all" ${param.status == null || param.status == 'all' ? "selected" : ""}>-- Tất cả --</option>
                            <option value="true" ${param.status == "true" ? "selected" : ""}>Hoạt động</option>
                            <option value="false" ${param.status == "false" ? "selected" : ""}>Ngưng hoạt động</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </div>

                    <!-- Thêm nhân viên -->
                    <div class="col-md-3 text-end">
                        <button type="button" class="btn btn-success" onclick="openAddModal()">+ Thêm nhân viên</button>
                    </div>
                </form>

                <% if (request.getAttribute("error") != null) {%>
                <div class="alert alert-danger"><%= request.getAttribute("error")%></div>
                <% } %>

                <!-- Bảng danh sách -->
                <div class="card">
                    <div class="card-body">
                        <table class="table table-bordered align-middle">
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
                                        <button type="button" class="btn btn-warning btn-sm"
                                                onclick='openEditModal(
                                                "<%= s.getStaffId()%>",
                                                "<%= s.getStaffName().replace("\"", "\\\"")%>",
                                                "<%= s.getStaffTitle().replace("\"", "\\\"")%>",
                                                "<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(s.getStaffBirthDate())%>",
                                                <%= s.isStaffGender()%>,
                                                <%= s.getDepartmentId()%>,
                                                <%= s.isStatus()%>,
                                                "<%= s.getStaffAddress().replace("\"", "\\\"")%>"
                                                )'>Sửa</button>
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

                <!-- Modal -->
                <div id="staffModal" class="modal" tabindex="-1" style="display:none;">
                    <div class="modal-dialog">
                        <div class="modal-content p-3">
                            <div class="modal-header">
                                <h5 id="modalTitle" class="modal-title">Thêm nhân viên</h5>
                                <button type="button" class="btn-close" onclick="closeModal()"></button>
                            </div>
                            <form id="staffForm" action="${pageContext.request.contextPath}/admin/staff" method="post">
                                <div class="modal-body">
                                    <input type="hidden" name="action" id="formAction" value="add" />

                                    <!-- Khi thêm thì hiện select để chọn staffId -->
                                    <div id="staffIdSelectContainer">
                                        ID:
                                        <select class="form-select mb-2" name="staffId" id="staffIdSelect" required>
    <option value="">-- Chọn Mã nhân viên --</option>
    <%
        List<String> staffIdOptions = (List<String>) request.getAttribute("staffIdOptions");
        model.Staff staff = (model.Staff) request.getAttribute("staff");
        String selectedId = (staff != null && staff.getStaffId() != null) ? staff.getStaffId() : "";
        if (staffIdOptions != null) {
            for (String id : staffIdOptions) {
    %>
    <option value="<%= id %>" <%= id.equals(selectedId) ? "selected" : "" %>>
        <%= id %>
    </option>
    <%
            }
        }
    %>
</select>

                                    </div>

                                    <!-- Khi sửa thì ẩn select, hiện input readonly -->
                                    <div id="staffIdInputContainer" style="display:none;">
                                        ID: <input type="text" class="form-control mb-2" name="staffId" id="staffIdInput" readonly style="background:#eee" />
                                    </div>

                                    Tên: <input type="text" class="form-control mb-2" name="staffName" id="staffName" required>
                                    Chức danh: <input type="text" class="form-control mb-2" name="staffTitle" id="staffTitle">
                                    Địa chỉ: <input type="text" class="form-control mb-2" name="staffAddress" id="staffAddress" required>
                                    Ngày sinh: <input type="date" class="form-control mb-2" name="staffBirthDate" id="staffBirthDate">
                                    Giới tính:
                                    <select name="staffGender" id="staffGender" class="form-select mb-2">
                                        <option value="true">Nam</option>
                                        <option value="false">Nữ</option>
                                    </select>
                                    Phòng ban:
                                    <input type="number" class="form-control mb-2" name="departmentId" id="departmentId">
                                    Trạng thái:
                                    <select name="status" id="status" class="form-select mb-2">
                                        <option value="true">Hoạt động</option>
                                        <option value="false">Ngưng hoạt động</option>
                                    </select>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                    <button type="button" class="btn btn-secondary" onclick="closeModal()">Đóng</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="footer-main">
                    Copyright &copy Director, 2014
                </div>

                <!-- Scripts -->
                <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
                <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
                <script src="${pageContext.request.contextPath}/js/Director/app.js" type="text/javascript"></script>

                <script>
                                        function openAddModal() {
                                            document.getElementById('modalTitle').innerText = "Thêm nhân viên";
                                            document.getElementById('staffForm').reset();

                                            // Reset giá trị select staffId
                                            document.getElementById('staffIdSelect').value = "";

                                            // Hiển thị select staffId, ẩn input readonly staffId
                                            document.getElementById('staffIdSelectContainer').style.display = 'block';
                                            document.getElementById('staffIdInputContainer').style.display = 'none';

                                            // Bật required cho select staffId, tắt required input staffIdInput
                                            document.getElementById('staffIdSelect').setAttribute('required', 'required');
                                            document.getElementById('staffIdInput').removeAttribute('required');

                                            document.getElementById('formAction').value = "add";
                                            document.getElementById('staffModal').style.display = 'block';
                                        }

                                        function openEditModal(id, name, title, birthDate, gender, departmentId, status, address) {
                                            document.getElementById('modalTitle').innerText = "Cập nhật nhân viên";

                                            // Ẩn select staffId, hiện input readonly staffId
                                            document.getElementById('staffIdSelectContainer').style.display = 'none';
                                            document.getElementById('staffIdInputContainer').style.display = 'block';

                                            // Gán id cho input readonly
                                            document.getElementById('staffIdInput').value = id;

                                            // Gán các trường khác
                                            document.getElementById('staffName').value = name;
                                            document.getElementById('staffTitle').value = title;
                                            document.getElementById('staffAddress').value = address;
                                            document.getElementById('staffBirthDate').value = birthDate;
                                            document.getElementById('staffGender').value = gender.toString();
                                            document.getElementById('departmentId').value = departmentId;
                                            document.getElementById('status').value = status.toString();

                                            // Bật required cho input staffIdInput, tắt required select staffId
                                            document.getElementById('staffIdInput').setAttribute('required', 'required');
                                            document.getElementById('staffIdSelect').removeAttribute('required');

                                            document.getElementById('formAction').value = "edit";
                                            document.getElementById('staffModal').style.display = 'block';
                                        }

                                        function closeModal() {
                                            document.getElementById('staffModal').style.display = 'none';
                                        }
                </script>
            </div>

    </body>
</html>
