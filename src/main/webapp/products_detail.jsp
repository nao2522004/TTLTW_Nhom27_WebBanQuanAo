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
            <c:forEach var="i" items="${p.images}" end="2">
                <img src="assets/product-imgs/${i}" alt="Thumbnail">
            </c:forEach>
        </div>
        <div id="mainCarousel" class="carousel slide main-image">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/product-imgs/${p.images[0]}" class="d-block w-100" alt="Main Image">
                </div>
                <div class="carousel-item">
                    <img src="assets/product-imgs/${p.images[1]}" class="d-block w-100" alt="Main Image">
                </div>
                <div class="carousel-item">
                    <img src="assets/product-imgs/${p.images[2]}" class="d-block w-100" alt="Main Image">
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

        <form id="add-cart-form" action="add-cart" method="post" class="mt-5">
            <!-- Color options -->
            <div class="color-options">
                <h5>Choose color:</h5>
                <div class="choose_color mt-3">
                    <c:forEach var="co" items="${p.colors}">
                        <label>
                            <input class="d-none" type="radio" name="color" value="${co}">
                            <span>${co}</span>
                        </label>
                    </c:forEach>
                </div>
            </div>

            <!-- Size options -->
            <div class="size-options mt-4">
                <h5>Choose size:</h5>
                <div class="choose_size mt-3">
                    <c:forEach var="s" items="${p.sizes}">
                        <label>
                            <input class="d-none" type="radio" name="size" value="${s}">
                            <span>${s}</span>
                        </label>
                    </c:forEach>
                </div>
            </div>

            <!-- Quantity -->
            <div class="quantity-selector mt-4">
                <h5>Quantity:</h5>
                <button type="button" class="btn btn-outline-secondary ml-3" id="decrease-btn">-</button>
                <input type="number" name="quantity" value="1" id="quantity-input" required>
                <button type="button" class="btn btn-outline-secondary" id="increase-btn">+</button>
            </div>

            <!-- Alert Message -->
            <h4 id="alert-message" class="d-none mt-3" style="color: red">Vui lòng chọn màu và kích cỡ</h4>

            <!-- Hidden input for product ID -->
            <input type="hidden" name="pid" value="${p.id}">

            <!-- Buttons -->
            <div class="mt-5">
                <button type="submit" class="btn btn-primary mt-4 add_to_cart_btn">Thêm vào giỏ hàng</button>
            </div>
        </form>

        <!-- Popup overlay -->
        <div class="addOk-overlay"></div>
        <!-- Popup content -->
        <div class="addOk" id="addToCartPopup">
            <img alt="green-tick" src="assets/imgs/Green-Tick.png">
            <h3 class="mt-3">Sản phẩm đã được thêm vào giỏ hàng của bạn.</h3>
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

<!-- jQuery, Popper.js, and Bootstrap 4.6.2 JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/bootstrap-4.6.2/js/bootstrap.min.js"></script>

<!-- Javascript Native -->
<script src="${pageContext.request.contextPath}/assets/js/base.js"></script>
<script>
    // Transition for Header
    window.addEventListener("scroll", () => {
        if (window.scrollY > 50) {
            header.classList.add("scrolled");
        } else {
            header.classList.remove("scrolled");
        }
    });

    // Get the current URL
    const urlParams = new URLSearchParams(window.location.search);
    // Check if 'addCart=ok' is in the URL
    if (urlParams.get('addCart') === 'ok') {
        // Show the popup
        const popup = document.getElementById('addToCartPopup');
        const overlay = document.querySelector('.addOk-overlay');
        popup.classList.add('show');
        overlay.classList.add('show');

        // Auto-close popup after 3 seconds
        setTimeout(() => {
            popup.classList.remove('show');
            overlay.classList.remove('show');
        }, 1500);
    }

    // Alert if user didn't choose color or size
    document.getElementById('add-cart-form').addEventListener('submit', function (event) {
        const colorSelected = document.querySelector('input[name="color"]:checked');
        const sizeSelected = document.querySelector('input[name="size"]:checked');
        const alertMessage = document.getElementById('alert-message');

        if (!colorSelected || !sizeSelected) {
            event.preventDefault();
            alertMessage.classList.remove('d-none');
        } else {
            alertMessage.classList.add('d-none');
        }
    });

    // Quantity
    const decreaseBtn = document.getElementById("decrease-btn");
    const increaseBtn = document.getElementById("increase-btn");
    const quantityInput = document.getElementById("quantity-input");
    increaseBtn.addEventListener("click", () => {
        let currentValue = parseInt(quantityInput.value, 10);
        quantityInput.value = currentValue + 1;
    });
    decreaseBtn.addEventListener("click", () => {
        let currentValue = parseInt(quantityInput.value, 10);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    });
</script>

<script src="${pageContext.request.contextPath}/assets/js/products_detail.js"></script>
</body>

</html>