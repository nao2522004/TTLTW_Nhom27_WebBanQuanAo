<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp" %>
    <title>Product Detail</title>
    <link rel="stylesheet" href="assets/css/products_detail.css">
    <link rel="stylesheet" href="assets/css/responsive_luat.css">
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>
<!-- Make a space to split the other out of header -->
<div style="height: 12rem;"></div>

<!-- Products -->
<section id="section_products" class="row container mx-auto mt-4">
    <!-- Product Gallery -->
    <div class="col-md-6 product-gallery">
        <div class="thumbnail-list">
            <c:forEach var="i" items="${images}" end="2">
                <img src="assets/product-imgs/${i}" alt="Thumbnail">
            </c:forEach>
        </div>
        <div id="mainCarousel" class="carousel slide main-image">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/product-imgs/${images[0]}" class="d-block w-100" alt="Main Image">
                </div>
                <div class="carousel-item">
                    <img src="assets/product-imgs/${images[1]}" class="d-block w-100" alt="Main Image">
                </div>
                <div class="carousel-item">
                    <img src="assets/product-imgs/${images[2]}" class="d-block w-100" alt="Main Image">
                </div>
            </div>
            <a class="carousel-control-prev" href="#mainCarousel" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            </a>
            <a class="carousel-control-next" href="#mainCarousel" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
            </a>
        </div>
    </div>
    <!-- Product Info -->
    <div class="col-md-6 product-info">

            <h1>${p.name}</h1>
            <div class="price"><f:formatNumber value="${p.unitPrice}"/> đ</div>
            <!-- Color options -->
            <div class="color-options">
                <h5>Choose color:</h5>
                <div class="choose_color mt-3">
                    <c:forEach var="co" items="${colors}">
                        <span style="background-color: ${co}"></span>
                    </c:forEach>
                </div>
            </div>
            <!-- Size options -->
            <div class="size-options mt-4">
                <h5>Choose size:</h5>
                <div class="choose_size mt-3">
                    <c:forEach var="s" items="${sizes}">
                        <span>${s}</span>
                    </c:forEach>
                </div>
            </div>
            <!-- Instructions for choosing size -->
            <div class="instructions mt-2">
                <a href="#" id="open-popup">How to choose the exactly size <i class="fa fa-pencil"></i></a>
            </div>
            <!-- Popup -->
            <div class="overlay" id="popup-overlay"></div>
            <div class="popup" id="popup">
                <img src="assets/imgs/products%20detail/size%20guild/2.png" alt="Size Guide">
                <button class="close-btn" id="close-popup">Close</button>
            </div>
            <!-- Quantity -->
            <div class="quantity-selector mt-4">
                <h5>Quantity:</h5>
                <button class="btn btn-outline-secondary ml-3" id="decrease-btn">-</button>
                <input type="text" value="1" id="quantity-input" readonly>
                <button class="btn btn-outline-secondary" id="increase-btn">+</button>
            </div>
            <!-- Buttons: add to cart, buy -->
            <div class="mt-5">

                <a class="add_to_cart_btn" href="add-cart?pro=${p.id}">Add to cart</a>
                <a href="cart.jsp" class="buy_btn">Buy now</a>
            </div>
    </div>
</section>

<!-- Description -->
<section id="section_description" class="container mx-auto">
    <h1 class="mb-5 pb-4 text-center">Thông tin chi tiết sản phẩm</h1>
    <div class="content">
        <p class="text_content">
            ${p.description}
        </p>
    </div>
</section>

<!-- Comments -->
<section id="section_comments" class="container mx-auto">
    <h1 class="mb-5 pb-4 text-center">Đánh giá từ những khách hàng đã mua</h1>
    <!-- Review by customer -->
    <div class="review-container container">
        <div class="item">
            <div class="user-info">
                <img src="assets/imgs/products%20detail/1.png" alt="User Avatar" class="avatar">
                <div class="user-name">
                    <span>Nguyễn Văn A</span>
                    <div class="review-date">Thời gian: 22:11 2023-10-10</div>
                </div>
            </div>
            <div class="rating">
                <span>★★★★★</span>
            </div>
            <div class="review-content">
                <p>Shop chuẩn bị hàng và giao hàng nhanh. Shipper vui tính nhiệt tình. Chất vải phù hợp với giá
                    tiền,
                    gửi
                    đúng mẫu. Mẫu mã bạn nhà mình rất thích. Tặng shop 5 sao</p>
            </div>
            <div class="review-media">
                <img src="assets/imgs/products%20detail/2.webp" alt="Review Image 1">
                <img src="assets/imgs/products%20detail/3.jpg" alt="Review Image 2">
            </div>
        </div>
        <div class="item">
            <div class="user-info">
                <img src="assets/imgs/products%20detail/1.png" alt="User Avatar" class="avatar">
                <div class="user-name">
                    <span>Hoàng Thái B</span>
                    <div class="review-date">Thời gian: 22:11 2023-10-10</div>
                </div>
            </div>
            <div class="rating">
                <span>★★★★★</span>
            </div>
            <div class="review-content">
                <p>Rất đẹp và hài lòng lần sau sẽ ủng hộ shop tiếp</p>
            </div>
            <div class="review-media">
                <img src="assets/imgs/products%20detail/6.webp" alt="Review Image 1">
                <img src="assets/imgs/products%20detail/7.webp" alt="Review Image 2">
            </div>
        </div>
        <div class="item">
            <div class="user-info">
                <img src="assets/imgs/products%20detail/1.png" alt="User Avatar" class="avatar">
                <div class="user-name">
                    <span>Lý Thành C</span>
                    <div class="review-date">Thời gian: 22:11 2023-10-10</div>
                </div>
            </div>
            <div class="rating">
                <span>★★★★</span>
            </div>
            <div class="review-content">
                <p>Vải dầy, cạp to dầy, mặc thoải mãi rộng, vừa hết lạnh, ko biết có ấm ko. Shop tư vấn sai nên quần
                    dài quá luôn, shop nên tham khảo nhiều hơn, muốn cho 2 sao nhưng thấy cũng oki mặc ở nhà nên 4
                    sao nha</p>
            </div>
            <div class="review-media">
                <img src="assets/imgs/products%20detail/4.webp" alt="Review Image 1">
                <img src="assets/imgs/products%20detail/5.webp" alt="Review Image 2">
            </div>
        </div>
    </div>
</section>

<!-- Similar -->
<section id="section_similar" class="container similar_container">
    <h2 class="position-relative mb-5">Sản phẩm tương tự</h2>
    <div id="carousel_similar" class="carousel slide">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <div class="row">
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/1.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/2.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/3.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/4.png" alt=""></div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/1.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/2.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/3.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/4.png" alt=""></div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/1.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/2.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/3.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/4.png" alt=""></div>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-target="#carousel_similar" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-target="#carousel_similar" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </button>
    </div>
</section>

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

<!-- Javascript Native -->
<script>
    // Transition for Header
    window.addEventListener("scroll", () => {
        if (window.scrollY > 50) {
            header.classList.add("scrolled");
        } else {
            header.classList.remove("scrolled");
        }
    });
</script>
<script src="./assets/js/products_detail.js"></script>
<%@ include file="assets/includes/foot.jsp" %>
</body>

</html>