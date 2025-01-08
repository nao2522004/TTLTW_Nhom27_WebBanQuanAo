<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header>
  <a href="homePage" class="logo"><img src="./assets/imgs/Logo/LASMANATE.png" alt=""></a>
  <div class="container-nav">
    <nav>
      <ul>
        <li><a href="categoryController?category=men" data-text="Nam">Nam</a></li>
        <li><a href="categoryController?category=women" data-text="Nữ">Nữ</a></li>
        <li><a href="categoryController?category=children" data-text="Trẻ em">Trẻ em</a></li>
        <li><a href="categoryController?category=sale" data-text="Khuyến mãi">Khuyến mãi</a></li>
        <li class="dropdown"><a href="#" data-text="Bộ sưu tập">Bộ sưu tập</a>
          <div class="content-collection">
            <a href="categoryController?category=collection-running" data-text="">Chạy bộ</a>
            <a href="categoryController?category=collection-winter" data-text="">Thu đông</a>
            <a href="categoryController?category=collection-summer" data-text="">Xuân hè</a>
          </div>
        </li>
        <li><a href="categoryController?category=new-product" data-text="Sản phẩm mới">Sản phẩm mới</a></li>
        <li><a href="categoryController?category=blog" data-text="Tin tức">Tin tức</a></li>
      </ul>
    </nav>
  </div>
  <div class="group_icons">
    <a href="login.jsp"><i class="fa-solid fa-user custom_size"></i></a>
    <a href="wishlist.jsp"><i class="fa-solid fa-heart custom_size"></i></a>
    <a href="cart.jsp"><i class="fa-solid fa-cart-shopping custom_size"></i></a>
  </div>
  <div class="menu-toggle"><i class="fa-solid fa-bars"></i></div>

  <!-- Nút Back to Top -->
  <a href="#"><button id="backToTop" title="Back to Top">↑</button></a>
</header>