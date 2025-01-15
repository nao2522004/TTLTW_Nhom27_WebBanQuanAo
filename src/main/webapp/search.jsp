<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="assets/includes/head.jsp" />
    <title>Search</title>
    <link rel="stylesheet" href="assets/css/search.css">
    <link rel="stylesheet" href="assets/css/responsive_luat.css">
    <style>
        .product-card {
            transition: opacity 0.3s ease-in-out;
        }

        .no-results {
            color: var(--lighter-second-color);
            font-weight: bold;
            transition: opacity 0.3s ease-in-out;
        }

        .no-results {
            animation: fadeIn 0.5s ease-in-out;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>

<body>
<header id="header">
    <jsp:include page="assets/includes/header.jsp"/>
</header>
<!-- Make a space to split the other out of header -->
<div style="height: 12rem;"></div>

<!-- Search Popup -->
<div class="search-popup" id="search-popup">
    <div class="search-box">
        <i class="fas fa-search search-icon"></i>
        <input type="text" placeholder="Nhập từ khoá sản phẩm bạn cần tìm...">
    </div>
</div>

<!-- List products -->
<section id="section_all_products">
    <div class="list_products">
        <div class="container mt-2">
            <div class="row">
                <!-- Product Card 1 -->
                <c:forEach var="p" items="${allProducts}">
                    <div class="col-md-3 mt-4">
                        <a href="${pageContext.request.contextPath}/productDetail?pid=${p.id}" class="product-card">
                            <img src="assets/product-imgs/${p.images[0]}" alt="${p.name}" class="product-image img-fluid">
                            <div class="product-title">${p.name}</div>
                            <div class="product-price" data-price="${p.unitPrice}"></div>
                        </a>
                    </div>
                </c:forEach>

                <c:if test="${empty allProducts}">
                    <h2 class="display-4 mt-5 text-center w-100">Không có sản phẩm nào.</h2>
                </c:if>
            </div>
        </div>
    </div>
</section>

<div class="no-results" id="no-results" style="display: none; text-align: center; margin-top: 20px;">
    <h4>Không tìm thấy sản phẩm nào phù hợp.</h4>
</div>

<footer id="footer">
    <jsp:include page="assets/includes/footer.jsp"/>
</footer>

<!-- jQuery, Popper.js, and Bootstrap 4.6.2 JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="./assets/bootstrap-4.6.2/js/bootstrap.min.js"></script>
<!-- Javascript Native -->
<script src="assets/js/base.js"></script>
<script>
    function removeVietnameseTones(str) {
        return str
            .normalize('NFD') // Chuyển thành dạng tổ hợp ký tự
            .replace(/[\u0300-\u036f]/g, '') // Loại bỏ dấu
            .replace(/đ/g, 'd') // Thay thế 'đ'
            .replace(/Đ/g, 'D') // Thay thế 'Đ'
            .toLowerCase(); // Chuyển về chữ thường
    }

    document.addEventListener('DOMContentLoaded', function () {
        const searchInput = document.querySelector('#search-popup input');
        const productContainer = document.querySelector('.row');
        const noResults = document.querySelector('#no-results');

        searchInput.addEventListener('keyup', function () {
            const keyword = removeVietnameseTones(searchInput.value.trim().toLowerCase());

            // Lấy tất cả các thẻ sản phẩm
            const productCards = productContainer.querySelectorAll('.product-card');
            let hasVisibleProduct = false;

            productCards.forEach(card => {
                const productName = card.querySelector('.product-title').textContent;
                const normalizedProductName = removeVietnameseTones(productName);

                if (normalizedProductName.includes(keyword)) {
                    card.parentElement.style.display = 'block'; // Hiện sản phẩm
                    card.parentElement.style.opacity = '1'; // Hiện mờ dần
                    hasVisibleProduct = true;
                } else {
                    card.parentElement.style.opacity = '0'; // Ẩn mờ dần
                    setTimeout(() => {
                        card.parentElement.style.display = 'none';
                    }, 300); // Đợi 300ms trước khi ẩn hoàn toàn
                }
            });

            // Hiển thị hoặc ẩn thông báo "Không tìm thấy sản phẩm"
            if (hasVisibleProduct) {
                noResults.style.display = 'none';
            } else {
                noResults.style.display = 'block';
            }
        });
    });
</script>
</body>

</html>