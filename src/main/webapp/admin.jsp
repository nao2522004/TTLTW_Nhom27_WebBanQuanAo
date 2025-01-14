<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ADMIN</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/admin/favicon-32x32.png" type="image/png">

    <!-- Frameworks -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Sharp" rel="stylesheet"/>
    <!-- CSS native -->
    <link rel="stylesheet" href="./assets/css/base.css">
    <link rel="stylesheet" href="./assets/css/admin.css">
    <%-- Nhung cdn ckeditor  --%>
    <script src="https://cdn.ckeditor.com/ckeditor5/36.0.1/classic/ckeditor.js"></script>

</head>

<body>
<div class="container--admin">
    <aside>
        <div class="top">
            <div class="logo">
                <!-- <img src="./assets/imgs/admin/logoadmin.jpg" alt="Logo" /> -->
                <h2>LASMANATE</h2>
            </div>
            <div class="close" id="close-btn">
                <span class="material-icons-sharp"> close </span>
            </div>
        </div>

        <div class="sidebar">
            <a class="active" href="/admin" onclick="showMain(event, 'dashboard')">
                <span class="material-icons-sharp"> dashboard </span>
                <h3>Bảng thông tin</h3>
            </a>

            <a href="#" onclick="showMain(event, 'manager_orders')">
                <span class="material-icons-sharp"> receipt_long </span>
                <h3>Đơn hàng</h3>
            </a>
            <a href="admin/manager-users" onclick="showMain(event, 'admin/manager-users')">
                <span class="material-icons-sharp"> person_outline </span>
                <h3>Người dùng</h3>
            </a>
            <a href="" onclick="showMain(event, 'add_user')">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Người Dùng</h3>
            </a>
            <a href="/admin/manager-products" onclick="showMain(event, '/admin/manager-products')">
                <span class="material-icons-sharp"> inventory </span>
                <h3>Sản phẩm</h3>
            </a>
            <a href="" onclick="showMain(event, 'add_product')">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Sản Phẩm</h3>
            </a>
            <a href="#" onclick="showMain(event, 'manager_others')">
                <span class="material-icons-sharp"> inventory </span>
                <h3>Khác</h3>
            </a>
            <a href="" onclick="showMain(event, 'add_others')">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Khác</h3>
            </a>
            <a href="#">
                <span class="material-icons-sharp"> settings </span>
                <h3>Settings</h3>
            </a>
            <a href="user.jsp">
                <span class="material-icons-sharp"> logout </span>
                <h3>Logout</h3>
            </a>
        </div>
    </aside>

    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Info

---------------------------------------------------------
---------------------------------------------------------->
    <main id="dashboard">
        <h1>Bảng Thông Tin</h1>

        <div class="dash-top">


            <div class="date">
                <input type="date"/>
            </div>

            <div class="search-card">
                <form id="search-form">
                    <div class="search-group">
                        <input type="text" id="search-input" name="search" placeholder="Nhập từ khóa..." required>
                        <button type="submit" class="search-button">
                            <span class="material-icons-sharp">search</span>
                        </button>
                    </div>
                </form>
            </div>

        </div>
        <div class="insights">
            <!-- SALES -->
            <div class="sales">
                <span class="material-icons-sharp"> analytics </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Doanh Thu</h3>
                        <h1>2500VND</h1>

                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>81%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- EXPENSES -->
            <div class="expenses">
                <span class="material-icons-sharp"> bar_chart </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Chi Phí</h3>
                        <h1>1000VND</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>62%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- INCOME -->
            <div class="income">
                <span class="material-icons-sharp"> stacked_line_chart </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Lợi Nhuận</h3>
                        <h1>1500VND</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>44%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>
        </div>

        <div class="recent-orders">
            <h2>Đơn Hàng Gần Đây</h2>
            <table id="recent-orders--table">
                <thead>
                <tr>
                    <th>Mã Đơn Hàng</th>
                    <th>Khách Hàng</th>
                    <th>SDT</th>
                    <th>Địa Chỉ</th>
                    <th>Ngày Tạo</th>
                    <th>Tổng Tiền</th>
                    <th>Thanh Toán</th>
                    <th>Trạng Thái</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- product-order--details -->
        <div class="overlay">
            <div class="recent-orders popup">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chi Tiết Đơn Hàng</h2>
                <table id="orders-details--table">
                    <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Mã Sản Phẩm</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Size</th>
                        <th>Màu</th>
                        <th>Số Lượng</th>
                        <th>Đơn Giá</th>
                    </tr>
                    </thead>
                    <!-- Add tbody here | JS insertion -->
                </table>
                <a href="#">Show All</a>
            </div>
        </div>
    </main>


    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Orders
---------------------------------------------------------
---------------------------------------------------------->
    <main id="manager_orders">
        <h1>Quản lý đơn hàng</h1>

        <div class="dash-top">


            <div class="date">
                <input type="date"/>
            </div>

            <div class="search-card">
                <form id="search-form">
                    <div class="search-group">
                        <input type="text" id="search-input" name="search" placeholder="Nhập từ khóa..." required>
                        <button type="submit" class="search-button">
                            <span class="material-icons-sharp">search</span>
                        </button>
                    </div>
                </form>
            </div>

        </div>


        <div class="insights">
            <!-- SALES -->
            <div class="sales">
                <span class="material-icons-sharp"> receipt_long </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Đơn Hàng</h3>
                        <h1>2500</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>81%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- EXPENSES -->
            <div class="expenses">
                <span class="material-icons-sharp"> price_check </span>
                <div class="middle">
                    <div class="left">
                        <h3>Số Đơn Hàng Thành Công</h3>
                        <h1>1000</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>62%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- INCOME -->
            <div class="income">
                <span class="material-icons-sharp"> unpublished </span>
                <div class="middle">
                    <div class="left">
                        <h3>Số Đơn Hàng Thất Bại</h3>
                        <h1>1500</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>44%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>
        </div>

        <div class="recent-orders">
            <h2>Danh Sách Đơn Hàng</h2>
            <table id="orders--table">
                <thead>
                <tr>
                    <th>Mã Đơn Hàng</th>
                    <th>Khách Hàng</th>
                    <th>SDT</th>
                    <th>Địa Chỉ</th>
                    <th>Ngày Tạo</th>
                    <th>Tổng Tiền</th>
                    <th>Thanh Toán</th>
                    <th>Trạng Thái</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- product-order--details -->
        <div class="overlay">
            <div class="recent-orders popup">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chi Tiết Đơn Hàng</h2>
                <table id="orders-details--table">
                    <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Mã Sản Phẩm</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Size</th>
                        <th>Màu</th>
                        <th>Số Lượng</th>
                        <th>Đơn Giá</th>
                    </tr>
                    </thead>
                    <!-- Add tbody here | JS insertion -->
                </table>
                <a href="#">Show All</a>
            </div>
        </div>
    </main>

    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager User

---------------------------------------------------------
---------------------------------------------------------->

    <main id="admin/manager-users">
        <h1>Quản lý người dùng</h1>
        <div class="dash-top">


            <div class="date">
                <input type="date"/>
            </div>

            <div class="search-card">
                <form id="search-form">
                    <div class="search-group">
                        <input type="text" id="search-input" name="search" placeholder="Nhập từ khóa..." required>
                        <button type="submit" class="search-button">
                            <span class="material-icons-sharp">search</span>
                        </button>
                    </div>
                </form>
            </div>

        </div>


        <div class="insights">
            <!-- Tổng khách hàng -->
            <div class="sales">
                        <span class="material-icons-sharp">
                            group
                        </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Người Dùng</h3>
                        <h1>10000</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>81%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- Khách hàng online -->
            <div class="expenses" style="opacity: 0;">
                <span class="material-icons-sharp"> person </span>
                <div class="middle">
                    <div class="left">
                        <h3>Người Dùng online</h3>
                        <h1>1000</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>62%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- Khách hàng online -->
            <div class="expenses" style="opacity: 0;">
                <span class="material-icons-sharp"> person </span>
                <div class="middle">
                    <div class="left">
                        <h3>Người Dùng online</h3>
                        <h1>1000</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>62%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>
        </div>


        <div class="recent-orders">
            <h2>Danh Sách Người Dùng</h2>
            <table id="users--table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Tài khoản</th>
                    <th>Họ</th>
                    <th>Tên</th>
                    <th>Email</th>
                    <th>Avatar</th>
                    <th>Địa chỉ</th>
                    <th>SDT</th>
                    <th>Ngày tạo</th>
                    <th>Trạng thái</th>
                    <th>Quyền</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- edit users -->
        <div class="overlay">
            <div class="recent-orders popup">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chỉnh Sửa Người Dùng</h2>
                <form id="user-edit-form">
                    <table id="user_edit--table">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Tài khoản</th>
                            <th>Họ</th>
                            <th>Tên</th>
                            <th>Email</th>
                            <th>Avatar</th>
                            <th>Địa chỉ</th>
                            <th>SDT</th>
                            <th>Ngày tạo</th>
                            <th>Trạng thái</th>
                            <th>Quyền</th>
                        </tr>
                        </thead>

                        <tbody>
                        <!-- Các trường nhập sẽ được thêm vào ở đây qua JavaScript hoặc từ server -->
                        <tr>
                            <td><input type="number" name="id" id="edit-id" readonly></td>
                            <td><input type="text" name="username" id="edit-username" readonly></td>
                            <td><input type="text" name="lastName" id="edit-lastName" required></td>
                            <td><input type="text" name="firstName" id="edit-firstName" required></td>
                            <td><input type="text" name="email" id="edit-email" required></td>
                            <td><input type="text" name="avatar" id="edit-avatar"></td>
                            <td><input type="text" name="address" id="edit-address" required></td>
                            <td><input type="number" name="phone" id="edit-phone" required></td>
                            <td><input type="datetime-local" name="createdDate" id="edit-createdDate" readonly></td>
                            <td>
                                <select name="status" id="edit-status" required>
                                    <option value="1">Hoạt Động</option>
                                    <option value="0">Không Hoạt Động</option>
                                </select>
                            </td>
                            <td>
                                <select name="role" id="edit-role" required>
                                    <option value="1">Admin</option>
                                    <option value="2">User</option>
                                </select>
                            </td>
                            <td><span type="submit" onclick="saveUserEdits(event)" class="primary material-icons-sharp">save</span>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </form>
            </div>
        </div>

    </main>

    <!------------------------------------ Add User ------------------------------------>
    <main id="add_user">
        <h1>Thêm Người Dùng</h1>
        <form id="add-user-form">
            <div class="form-group">
                <div class="form-group--items">
                    <label for="userName">Tên đăng nhập</label>
                    <input type="text" id="userName" name="userName" placeholder="Nhập tên đăng nhập." required>
                </div>
                <div class="form-group--items">
                    <label for="passWord">Mật Khẩu</label>
                    <input type="passWord" id="passWord" name="passWord" placeholder="Nhập mật khẩu." required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="firstName">Họ</label>
                    <input type="text" id="firstName" name="firstName" placeholder="Nhập họ." required>
                </div>
                <div class="form-group--items">
                    <label for="lastName">Tên</label>
                    <input type="text" id="lastName" name="lastName" placeholder="Nhập tên." required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Nhập email." required>
                </div>
                <%--                <div action="/upload" method="post" enctype="multipart/form-data" class="form-group--items"--%>
                <%--                     style="position: relative;">--%>
                <%--                    <label for="product-image">Chọn Ảnh</label>--%>
                <%--                    <input type="file" style="z-index: 100;" id="product-image" name="product-image"--%>
                <%--                           accept="image/*" placeholder="Tải têp.">--%>
                <%--                    <input type="text" id="product-img" name="product-img" placeholder="Tải ảnh lên." required--%>
                <%--                           style="position: absolute; top: 42%;">--%>
                <%--                </div>--%>
                <div class="form-group--items">
                    <label for="avatar">Email</label>
                    <input type="text" id="avatar" name="avatar" placeholder="Nhập avatar." required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="address">Địa Chỉ</label>
                    <input type="text" id="address" name="address" placeholder="Nhập địa chỉ." required>
                </div>
                <div class="form-group--items">
                    <label for="phone">Số Điện Thoại</label>
                    <input type="tel" id="phone" name="phone" placeholder="Nhập số điện thoại." required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="status">Trạng Thái</label>
                    <select id="status" name="status" required>
                        <option type="number" value="1">Hoạt Động</option>
                        <option type="number" value="0">Không Hoạt Động</option>
                    </select>
                </div>
                <div class="form-group--items">
                    <label for="roleId">Quyền</label>
                    <select id="roleId" name="roleId" required>
                        <option type="number" value="1">Admin</option>
                        <option type="number" value="2">User</option>
                    </select>
                </div>
            </div>

            <button type="submit" onclick="createUser(event)" class="btn-primary">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Người Dùng</h3>
            </button>
        </form>
    </main>


    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Products

---------------------------------------------------------
---------------------------------------------------------->
    <main id="/admin/manager-products">
        <h1>Quản lý sản phẩm</h1>

        <div class="dash-top">


            <div class="date">
                <input type="date"/>
            </div>

            <div class="search-card">
                <form id="search-form">
                    <div class="search-group">
                        <input type="text" id="search-input" name="search" placeholder="Nhập từ khóa..." required>
                        <button type="submit" class="search-button">
                            <span class="material-icons-sharp">search</span>
                        </button>
                    </div>
                </form>
            </div>

        </div>


        <div class="insights">
            <!-- SALES -->
            <div class="sales">
                <span class="material-icons-sharp"> inventory </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Sản Phẩm</h3>
                        <h1>2500</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>81%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- EXPENSES -->
            <div class="expenses" style="opacity: 0;">
                <span class="material-icons-sharp"> bar_chart </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Chi Phí</h3>
                        <h1>1000VND</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>62%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- INCOME -->
            <div class="income" style="opacity: 0;">
                <span class="material-icons-sharp"> stacked_line_chart </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Lợi Nhuận</h3>
                        <h1>1500VND</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>44%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>
        </div>

        <div class="recent-orders">
            <h2>Danh Sách Sản Phẩm</h2>
            <table id="products--table">
                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Loại</th>
                    <th>Danh mục</th>
                    <th>Nhà cung cấp</th>
                    <th style="width: 150px">Tên</th>
                    <th style="width: 250px">Mô tả</th>
                    <th>Ngày Nhập Kho</th>
                    <th>Số Lượng</th>
                    <th>Giá Tiền</th>
                    <th>Tháo Tác</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- edit products -->
        <div class="overlay">
            <div class="recent-orders popup">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chỉnh Sửa Sản Phẩm</h2>
                <form id="product-edit-form">
                    <table id="product_edit--table">
                        <thead>
                        <tr>
                            <th>Mã</th>
                            <th>Loại</th>
                            <th>Danh mục</th>
                            <th>Nhà cung cấp</th>
                            <th>Tên</th>
                            <th>Mô tả</th>
                            <th>Ngày Nhập Kho</th>
                            <th>Số Lượng</th>
                            <th>Giá Tiền</th>
                            <th>Trạng thái</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td style="width: 10px;"><input type="number" name="id" id="edit-idProduct" readonly></td>
                            <td>
                                <%--                               <input type="number" name="typeId" id="edit-typeId" required>--%>
                                <select name="typeId" id="edit-typeId" required>
                                    <option value="1">Áo</option>
                                    <option value="2">Quần</option>
                                </select>
                            </td>
                            <td>
                                <%--                                <input type="number" name="categoryId" id="edit-categoryId" required>--%>
                                <select name="categoryId" id="edit-categoryId" required>
                                    <option value="1">Áo nam</option>
                                    <option value="2">Áo nữ</option>
                                    <option value="3">Áo trẻ em</option>
                                    <option value="4">Quần nam</option>
                                    <option value="5">Quần nữ</option>
                                    <option value="6">Quần trẻ em</option>
                                </select>
                            </td>
                            <td>
                                <%--                                <input type="number" name="supplierId" id="edit-supplierId" required>--%>
                                <select name="supplierId" id="edit-supplierId" required>
                                    <option value="1">PEALO</option>
                                    <option value="2">B Brown Studio</option>
                                    <option value="3">BBRAND</option>
                                    <option value="4">RUYCH STUDIO</option>
                                </select>
                            </td>
                            <td><input type="text" name="name" id="edit-name" required></td>
                            <td><textarea name="description" id="edit-description" required></textarea></td>
                            <td><input type="date" name="releaseDate" id="edit-releaseDate" required></td>
                            <td><input type="number" name="unitSold" id="edit-unitSold" required></td>
                            <td><input type="number" name="unitPrice" id="edit-unitPrice" step="0.01" required></td>
                            <td>
                                <select name="status" id="edit-statusProduct" required>
                                    <option value="true">Còn</option>
                                    <option value="false">Hết</option>
                                </select>
                            </td>
                            <td><span type="submit" onclick="saveProductEdits(event)"
                                      class="primary material-icons-sharp">save</span></td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>

    </main>

    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Others

---------------------------------------------------------
---------------------------------------------------------->
    <main id="manager_others">
        <h1>Quản lý sản phẩm</h1>

        <div class="dash-top">


            <div class="date">
                <input type="date"/>
            </div>

            <div class="search-card">
                <form id="search-form">
                    <div class="search-group">
                        <input type="text" id="search-input" name="search" placeholder="Nhập từ khóa..." required>
                        <button type="submit" class="search-button">
                            <span class="material-icons-sharp">search</span>
                        </button>
                    </div>
                </form>
            </div>

        </div>


        <div class="recent-orders">
            <h2>Chương Trình Giảm Giá.</h2>
            <table id="sales--table">
                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Tên</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Mô tả</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>


        <div class="recent-orders">
            <h2>Bộ sưu tầm.</h2>
            <table id="collections--table">
                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Tên</th>
                    <th>Ngày bắt đầu</th>
                    <th>Mô tả</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- edit others -->
        <div class="overlay">
            <div class="recent-orders popup" style="top: -20rem;">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chỉnh Sửa Chương Trình Giảm Giá</h2>
                <table id="sale_edit--table">
                    <thead>
                    <tr>
                        <th>Mã</th>
                        <th>Tên</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Mô tả</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <!-- Add tbody here | JS insertion -->
                </table>
                <h2 style="margin-top: 1rem;">Chỉnh Sửa Bộ Sưu Tập</h2>
                <table id="collection_edit--table">
                    <thead>
                    <tr>
                        <th>Mã</th>
                        <th>Tên</th>
                        <th>Ngày bắt đầu</th>
                        <th>Mô tả</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <!-- Add tbody here | JS insertion -->
                </table>
                <a href="#">Show All</a>
            </div>
        </div>
    </main>


    <!------------------------------------ Add Product ------------------------------------>
    <main id="add_product">
        <h1>Thêm sản phẩm</h1>
        <form id="add-product-form">
            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-name">Tên Sản Phẩm</label>
                    <input type="text" id="product-name" name="product-name" placeholder="Nhập tên sản phẩm."
                           required>
                </div>
                <div class="form-group--items">
                    <label for="product-category">Danh Mục</label>
                    <input type="text" id="product-category" name="product-category"
                           placeholder="Nhập tên danh mục." required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-brand">Thương Hiệu</label>
                    <input type="text" id="product-brand" name="product-brand" placeholder="Nhập tên hãng."
                           required>
                </div>
                <div class="form-group--items">
                    <label for="product-style">Loại</label>
                    <input type="text" id="product-style" name="product-style" placeholder="Nhập tên loại."
                           required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-price">Giá</label>
                    <input type="number" id="product-price" name="product-price" placeholder="Nhập giá sản phẩm."
                           required>
                </div>
                <div class="form-group--items">
                    <label for="product-discount">Giảm Giá</label>
                    <input type="text" id="product-discount" name="product-discount"
                           placeholder="Nhập phần trăm giảm." required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-size">Kích Cỡ</label>
                    <input type="text" id="product-size" name="product-size" placeholder="Nhập kích thước."
                           required>
                </div>
                <div class="form-group--items"><label for="product-color">Màu Sắc</label>
                    <input type="text" id="product-color" name="product-color" placeholder="Nhập màu sắc." required>
                </div>
            </div>


            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-material">Chất Liệu</label>
                    <input type="text" id="product-material" name="product-material"
                           placeholder="Nhập chất liệu sản phẩm" required>
                </div>
                <div class="form-group--items">
                    <label for="product-amount">Số Lượng</label>
                    <input type="number" id="product-amount" name="product-amount"
                           placeholder="Nhập số lượng sản phẩm" required>
                </div>

            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-stock-in-date">Ngày Nhập Kho</label>
                    <input type="date" id="product-stock-in-date" name="product-stock-in-date" required>
                </div>
                <div action="/upload" method="post" enctype="multipart/form-data" class="form-group--items"
                     style="position: relative;">
                    <label for="product-image">Chọn Ảnh</label>
                    <input type="file" style="z-index: 100;" id="product-image" name="product-image"
                           accept="image/*" placeholder="Tải têp.">
                    <input type="text" id="product-img" name="product-img" placeholder="Tải ảnh lên." required
                           style="position: absolute; top: 42%;">
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="product-decription">Mô Tả</label>
                    <textarea rows="10" cols="20" type="text" name="product-decription" id="product-decription"
                              placeholder="Nhập mô tả."></textarea>
                </div>
            </div>


            <button type="submit" class="btn-primary"><span class="material-icons-sharp"> add </span>
                <h3>Thêm Sản Phẩm</h3>
            </button>
        </form>
    </main>


    <!------------------------------------ Add Others ------------------------------------>
    <main id="add_others">
        <!------------------------------------ Add Collections ------------------------------------>
        <h1>Thêm Bộ Sưu Tập</h1>
        <form id="add-product-form" action="/add-collection" method="POST">
            <div class="form-group">
                <div class="form-group--items">
                    <label for="collections_name">Tên Bộ Sưu Tập</label>
                    <input type="text" id="collections_name" name="collections_name"
                           placeholder="Nhập tên bộ sưu tập." required>
                </div>
                <div class="form-group--items">
                    <label for="start_date">Ngày Bắt Đầu</label>
                    <input type="date" id="start_date" name="start_date" required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="collections_description">Mô Tả Bộ Sưu Tập</label>
                    <textarea id="collections_description" name="collections_description"
                              placeholder="Nhập mô tả bộ sưu tập." required></textarea>
                </div>
            </div>

            <button type="submit" class="btn-primary">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Bộ Sưu Tập</h3>
            </button>
        </form>

        <!------------------------------------ Add Sales ------------------------------------>
        <h1>Thêm Chương Trình Khuyến Mãi</h1>
        <form id="add-product-form" action="/add-sales" method="POST">
            <div class="form-group">
                <div class="form-group--items">
                    <label for="sales_name">Tên Chương Trình</label>
                    <input type="text" id="sales_name" name="sales_name" placeholder="Nhập tên chương trình."
                           required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="start_date">Ngày Bắt Đầu</label>
                    <input type="date" id="start_date" name="start_date" required>
                </div>
                <div class="form-group--items">
                    <label for="end_date">Ngày Kết Thúc</label>
                    <input type="date" id="end_date" name="end_date" required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="sales_description">Mô Tả Chương Trình</label>
                    <textarea id="sales_description" name="sales_description" placeholder="Nhập mô tả chương trình."
                              required></textarea>
                </div>
            </div>


            <button type="submit" class="btn-primary">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Chương Trình Khuyến Mãi</h3>
            </button>
        </form>

        <!-- Gui thong bao -->
        <h1>Gửi Thông Báo</h1>
        <form id="add-product-form">
            <div class="form-group">
                <div class="form-group--items">
                    <label for="notification-date">Ngày Thông Báo</label>
                    <input type="datetime-local" id="notification-date" name="notification-date" required>
                </div>
                <div class="form-group--items">
                    <label for="is-read">Trạng Thái Đọc</label>
                    <select id="is-read" name="is-read" required>
                        <option value="0">Chưa Đọc</option>
                        <option value="1">Đã Đọc</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <div class="form-group--items">
                    <label for="message">Nội Dung Thông Báo</label>
                    <textarea id="message" name="message" placeholder="Nhập nội dung thông báo."
                              required></textarea>
                </div>
            </div>

            <button type="submit" class="btn-primary">
                <span class="material-icons-sharp"> send </span>
                <h3>Gửi Thông Báo</h3>
            </button>
        </form>
    </main>


    <div class="right">
        <div class="top">
            <button id="menu-btn">
                <span class="material-icons-sharp"> menu </span>
            </button>
            <div class="theme-toggler">
                <span class="material-icons-sharp active"> light_mode </span>
                <span class="material-icons-sharp"> dark_mode </span>
            </div>
            <div class="profile">
                <div class="info">
                    <p>Hey, <b>ManhDZ</b></p>
                    <small class="text-muted">Admin</small>
                </div>
                <div class="profile-photo">
                    <img src="assets/imgs/admin/logoadmin.jpg" alt="Profile Picture"/>
                </div>
            </div>
        </div>

        <div class="recent-updates">
            <h2>Cập Nhập Gần Đây</h2>
            <!-- Add updates div here | JS insertion -->
        </div>

        <div class="sales-analytics">
            <h2>Phân tích bán hàng</h2>
            <div id="analytics">
                <!-- Add items div here | JS insertion -->
            </div>
            <!-- <div class="item add-product">
                <div>
                    <span class="material-icons-sharp"> add </span>
                    <h3>Thêm Sản Phẩm</h3>
                </div>
            </div> -->
        </div>
    </div>
</div>

<script src="./assets/js/admin-data/recent-order-data.js"></scri>
<script src="./assets/js/admin-data/sales-analytics-data.js"></script>
<script src="./assets/js/admin-data/update-data.js"></script>
<script src="./assets/js/admin-data/user-data.js"></script>
<script src="./assets/js/admin-data/product-data.js"></script>
<script src="./assets/js/admin-data/order-data.js"></script>
<script src="./assets/js/admin-data/order-details-data.js"></script>
<script src="./assets/js/admin-data/others-data.js"></script>
<script src="./assets/js/admin.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script>
    ClassicEditor
        .create(document.querySelector('#product-decription'), {
            toolbar: [
                'heading', '|',
                'bold', 'italic', 'link', '|',
                'bulletedList', 'numberedList', '|',
                'imageUpload', 'blockQuote', '|',
                'insertTable', '|',
                'undo', 'redo'
            ],
            image: {
                toolbar: ['imageTextAlternative', 'imageStyle:full', 'imageStyle:side']
            },
            table: {
                contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells']
            }
        })
        .catch(error => {
            console.error(error);
        });
</script>

</body>

</html>