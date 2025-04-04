<%@ page import="vn.edu.hcmuaf.fit.webbanquanao.user.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header>
  <a href="homePage" class="logo"><img src="./assets/imgs/Logo/LASMANATE.png" alt=""></a>
  <div class="container-nav">
    <nav>
      <ul>
        <li><a href="navController?category=men" data-text="Nam">Nam</a></li>
        <li><a href="navController?category=women" data-text="Nữ">Nữ</a></li>
        <li><a href="navController?category=children" data-text="Trẻ em">Trẻ em</a></li>
        <li><a href="navController?category=sale" data-text="Khuyến mãi">Khuyến mãi</a></li>
        <li class="dropdown"><a href="#" data-text="Bộ sưu tập">Bộ sưu tập</a>
          <div class="content-collection">
            <a href="navController?category=collection-running" data-text="">Chạy bộ</a>
            <a href="navController?category=collection-winter" data-text="">Thu đông</a>
            <a href="navController?category=collection-summer" data-text="">Xuân hè</a>
          </div>
        </li>
        <li><a href="navController?category=new-product" data-text="Sản phẩm mới">Sản phẩm mới</a></li>
        <li><a href="navController?category=blog" data-text="Tin tức">Tin tức</a></li>
      </ul>
    </nav>
  </div>
  <div class="group_icons">
    <a href="productSearch"><i class="fa-solid fa-magnifying-glass custom_size"></i></a>
    <%
      Object authObj = session.getAttribute("auth");
      User user = null;
      if (authObj instanceof User) {
        user = (User) authObj;
      }

      if (user != null) {
    %>
    <!-- Nếu người dùng đã đăng nhập, chuyển đến trang người dùng -->
    <a href="user.jsp"><i class="fa-solid fa-user custom_size"></i></a>
    <%
    } else {
    %>
    <!-- Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập -->
    <a href="login.jsp"><i class="fa-solid fa-user custom_size"></i></a>
    <%
      }
    %>

    <a href="wishlist.jsp"><i class="fa-solid fa-heart custom_size"></i></a>
    <a href="ShowCart"><i class="fa-solid fa-cart-shopping custom_size"></i></a>
  </div>
  <div class="menu-toggle"><i class="fa-solid fa-bars"></i></div>

  <!-- Nút Back to Top -->
  <a href="#"><button id="backToTop" title="Back to Top">↑</button></a>
</header>