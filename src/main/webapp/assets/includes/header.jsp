<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header>
  <a href="index.jsp" class="logo"><img src="./assets/imgs/Logo/LASMANATE.png" alt=""></a>
  <div class="container-nav">
    <nav>
      <ul>
        <li><a href="men.jsp" data-text="Nam">Nam</a></li>
        <li><a href="women.jsp" data-text="Nữ">Nữ</a></li>
        <li><a href="children.jsp" data-text="Trẻ em">Trẻ em</a></li>
        <li><a href="sale.jsp" data-text="Khuyến mãi">Khuyến mãi</a></li>
        <li class="dropdown"><a href="#" data-text="Bộ sưu tập">Bộ sưu tập</a>
          <div class="content-collection">
            <a href="collection-running.jsp" data-text="">Chạy bộ</a>
            <a href="collection-winter.jsp" data-text="">Thu đông</a>
            <a href="collection-summer.jsp" data-text="">Xuân hè</a>
          </div>
        </li>
        <li><a href="new-product.jsp" data-text="Sản phẩm mới">Sản phẩm mới</a></li>
        <li><a href="blog.jsp" data-text="Tin tức">Tin tức</a></li>
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