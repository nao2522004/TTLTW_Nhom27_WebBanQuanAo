<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Product Detail</title>
    <link rel="stylesheet" href="./assets/css/products_detail.css">
    <link rel="stylesheet" href="./assets/css/responsive_luat.css">
</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    <!-- Make a space to split the other out of header -->
    <div style="height: 12rem;"></div>

    <!-- Products -->
    <section id="section_products" class="row container mx-auto mt-4">
        <!-- Product Gallery -->
        <div class="col-md-6 product-gallery">
            <div class="thumbnail-list">
                <img src="assets/imgs/men/list%20products/1/1.png" alt="Thumbnail">
                <img src="assets/imgs/men/list%20products/1/2.png" alt="Thumbnail">
                <img src="assets/imgs/men/list%20products/1/4.png" alt="Thumbnail">
            </div>
            <div id="mainCarousel" class="carousel slide main-image">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="assets/imgs/men/list%20products/1/1.png" class="d-block w-100" alt="Main Image">
                    </div>
                    <div class="carousel-item">
                        <img src="assets/imgs/men/list%20products/1/2.png" class="d-block w-100" alt="Main Image">
                    </div>
                    <div class="carousel-item">
                        <img src="assets/imgs/men/list%20products/1/4.png" class="d-block w-100" alt="Main Image">
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
            <h1>Quần dài Kaki dáng Baggy ống suông cạp chun Unisex nam</h1>
            <div class="price">250.000 đ</div>
            <!-- Color options -->
            <div class="color-options">
                <h5>Choose color:</h5>
                <div class="choose_color mt-3">
                    <span></span><span></span><span></span>
                </div>
            </div>
            <!-- Size options -->
            <div class="size-options mt-4">
                <h5>Choose size:</h5>
                <div class="choose_size mt-3">
                    <span>S</span><span>M</span><span>L</span><span>XL</span><span>XXL</span><span>XXXL</span>
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
                <a class="add_to_cart_btn">Add to cart</a>
                <a href="cart.jsp" class="buy_btn">Buy now</a>
            </div>
        </div>
    </section>

    <!-- Description -->
    <section id="section_description" class="container mx-auto">
        <h1 class="mb-5 pb-4 text-center">Thông tin chi tiết sản phẩm</h1>
        <div class="content">
            <ul class="text_content">
                <li>Đừng quên áp mã giảm giá trước khi mua sắm</li>
                <li>Shop đảm bảo màu sắc hiển thị trong video và ảnh giống 100%.</li>
                <li>Khách hàng có thể kiểm tra hàng trước khi thanh toán.</li>
                <li>Đổi trả trong vòng 15 ngày.</li>
                <li>Đóng gói đẹp và an toàn, giao hàng nhanh chóng.</li>
                <li>Hàng có sẵn, thời gian giao hàng tốt nhất.</li>
                <li>Chính sách đổi trả sản phẩm</li>
                <li>Điều kiện đổi trả:
                    <ul>
                        <li>Sản phẩm còn mới, đầy đủ tem nhãn, và chưa qua sử dụng.</li>
                        <li>Sản phẩm bị lỗi hoặc hư hỏng do vấn đề vận chuyển hoặc sản xuất.</li>
                    </ul>
                </li>
                <li>Chính sách bảo hành:
                    <ul>
                        <li>Sản phẩm đảm bảo 100% về chất lượng.</li>
                        <li>Việc đổi trả được thực hiện theo chính sách của Shopee.</li>
                        <li>Giao hàng toàn quốc, có hỗ trợ thanh toán khi nhận hàng (COD).</li>
                    </ul>
                </li>
                <li>Kích thước và mẫu mã</li>
                <li>Kích thước và mẫu mã của sản phẩm được hiển thị trong hình ảnh, vui lòng xem kỹ trước khi đặt hàng.
                    Do đo lường thủ công, có thể sai lệch 1-2 cm.</li>
                <li>Do sự khác biệt về màn hình và ánh sáng, hình ảnh có thể không phản ánh chính xác màu sắc thật của
                    tất cả sản phẩm. Mong quý khách thông cảm.</li>
                <li>Nếu khách hàng phát hiện sản phẩm có lỗi hoặc gặp phải thái độ không đúng mực từ nhân viên giao
                    hàng, vui lòng thông báo ngay với cửa hàng để được giải quyết tốt nhất!! Cuối cùng, rất mong nhận
                    được đánh giá 5 sao từ quý khách.</li>
            </ul>
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

    <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>

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
    <%@ include file="assets/includes/foot.jsp"%>
</body>

</html>