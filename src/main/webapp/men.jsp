<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="assets/includes/head.jsp" />
    <title>Đồ nam</title>
    <link rel="stylesheet" href="assets/css/men.css">
    <link rel="stylesheet" href="assets/css/responsive_luat.css">
</head>

<body>
    <header id="header"><jsp:include page="assets/includes/header.jsp" /></header>
    <!-- Make a space to split the other out of header -->
    <div style="height: 12rem;"></div>

    <!-- Banner -->
    <section id="section_banner" class="container-fluid p-0">
        <img src="assets/imgs/men/banner/3.jpg" alt="" class="w-100">
    </section>

    <!-- Featured -->
    <section id="section_featured" class="container">
        <div class="row">
            <a href="" class="col-md-3"><img src="assets/imgs/men/featured/2.jpg" alt="" class="w-100"></a>
            <a href="" class="col-md-3"><img src="assets/imgs/men/featured/3.jpg" alt="" class="w-100"></a>
            <a href="" class="col-md-3"><img src="assets/imgs/men/featured/4.jpg" alt="" class="w-100"></a>
            <a href="" class="col-md-3"><img src="assets/imgs/men/featured/5.jpg" alt="" class="w-100"></a>
        </div>
    </section>

    <!-- All products -->
    <section id="section_all_products">
        <!-- Top -->
        <div class="container-fluid" style="border-bottom: solid 1px #000;    height: 5rem;">
            <div class="top">
                <div class="filter">
                    <h2>Lọc sản phẩm</h2>
                    <i class="fa-solid fa-filter custom_icon"></i>
                </div>
                <h1>Tất cả sản phẩm</h1>
                <div class="sort">
                    <label for="sortBy">Sắp xếp theo:</label>
                    <select id="sortBy" class="custom-select">
                        <option value="default">Chọn tiêu chí sắp xếp</option>
                        <option value="priceAsc">Giá: Thấp đến Cao</option>
                        <option value="priceDesc">Giá: Cao đến Thấp</option>
                        <option value="newest">Sản Phẩm Mới Nhất</option>
                        <option value="bestSeller">Bán Chạy Nhất</option>
                        <option value="highestRated">Đánh Giá Cao Nhất</option>
                    </select>
                </div>
            </div>
        </div>
        <!-- Bottom -->
        <div class="container-fluid">
            <div class="bottom">
                <!-- Filter -->
                <div class="filter">
                    <!-- Loại Sản Phẩm -->
                    <div id="filter-category">
                        <h4>Loại Sản Phẩm <span class="arrow"><i
                                    class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="category" value="Shirts"> Áo</label>
                            <label><input type="checkbox" name="category" value="Pants"> Quần</label>
                            <label><input type="checkbox" name="category" value="Jackets"> Áo khoác</label>
                            <label><input type="checkbox" name="category" value="Accessories"> Phụ kiện</label>
                            <label><input type="checkbox" name="category" value="Shoes"> Giày</label>
                        </div>
                    </div>

                    <!-- Kích Thước -->
                    <div id="filter-size">
                        <h4>Kích Thước <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="size" value="S"> S</label>
                            <label><input type="checkbox" name="size" value="M"> M</label>
                            <label><input type="checkbox" name="size" value="L"> L</label>
                            <label><input type="checkbox" name="size" value="XL"> XL</label>
                            <label><input type="checkbox" name="size" value="XXL"> XXL</label>
                        </div>
                    </div>

                    <!-- Màu Sắc -->
                    <div id="filter-color">
                        <h4>Màu Sắc <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="color" value="Black"> Đen</label>
                            <label><input type="checkbox" name="color" value="White"> Trắng</label>
                            <label><input type="checkbox" name="color" value="Blue"> Xanh</label>
                            <label><input type="checkbox" name="color" value="Gray"> Xám</label>
                        </div>
                    </div>

                    <!-- Giá -->
                    <div id="filter-price">
                        <h4>Giá <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                        <div class="filter-content">
                            <input type="number" name="price-min" placeholder="Giá tối thiểu">
                            <input type="number" name="price-max" placeholder="Giá tối đa">
                            <select name="price-sort">
                                <option value="low-to-high">Thấp đến cao</option>
                                <option value="high-to-low">Cao đến thấp</option>
                            </select>
                        </div>
                    </div>

                    <!-- Thương Hiệu -->
                    <div id="filter-brand">
                        <h4>Thương Hiệu <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="brand" value="Gucci"> Gucci</label>
                            <label><input type="checkbox" name="brand" value="Louis"> Louis Vuitton</label>
                            <label><input type="checkbox" name="brand" value="Balenciaga"> Balenciaga</label>
                            <label><input type="checkbox" name="brand" value="Dior"> Dior</label>
                            <label><input type="checkbox" name="brand" value="Chanel"> Chanel</label>
                        </div>
                    </div>

                    <!-- Chất Liệu -->
                    <div id="filter-material">
                        <h4>Chất Liệu <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="material" value="Cotton"> Cotton</label>
                            <label><input type="checkbox" name="material" value="Wool"> Len</label>
                            <label><input type="checkbox" name="material" value="Leather"> Da</label>
                            <label><input type="checkbox" name="material" value="Polyester"> Polyester</label>
                        </div>
                    </div>

                    <!-- Phong Cách -->
                    <div id="filter-style">
                        <h4>Phong Cách <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="style" value="Sporty"> Thể thao</label>
                            <label><input type="checkbox" name="style" value="Business"> Công sở</label>
                            <label><input type="checkbox" name="style" value="Casual"> Thường ngày</label>
                            <label><input type="checkbox" name="style" value="High-fashion"> Thời trang cao cấp</label>
                        </div>
                    </div>
                </div>
                <!-- Right -->
                <div class="list_products">
                    <div class="container mt-2">
                        <div class="row">
                            <!-- Product Card 1 -->
                            <c:forEach var="p" items="${allProducts}">
                                <div class="col-md-3 mt-4">
                                    <div class="product-card" onclick="redirectToProductPage()">
                                        <img src="assets/imgs/men/list%20products/1/1.png" alt="Product 1"
                                             class="product-image img-fluid">
                                        <div class="product-title">${p.productName}</div>
                                        <div class="product-price" data-price="${p.unitPrice}"></div>
                                    </div>
                                </div>
                            </c:forEach>

                            <c:if test="${empty allProducts}">
                                <h2 class="display-4 mt-5 text-center w-100">Không có sản phẩm nào.</h2>
                            </c:if>
                        </div>
                    </div>
                    <!-- Pagination -->
                    <div id="pagination">
                        <div class="product-pagination">
                            <button class="pagination-btn prev-btn"><i class="fa-solid fa-chevron-left"></i></button>

                            <div id="pagination_numbers"></div>

                            <button class="pagination-btn next-btn"><i class="fa-solid fa-chevron-right"></i></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer id="footer"><jsp:include page="assets/includes/footer.jsp"/></footer>

    <!-- Pagination -->
    <script>
        const totalPages = 20; // Total number of pages
        let currentPage = 1; // Current page

        const paginationNumbers = document.getElementById('pagination_numbers');

        // Function to render the pagination numbers
        function renderPagination() {
            paginationNumbers.innerHTML = ''; // Clear previous pagination

            // Render "<" button only if not on the first page
            if (currentPage > 1) {
                document.querySelector('.prev-btn').disabled = false;
            } else {
                document.querySelector('.prev-btn').disabled = true;
            }

            // Add first page and dots if current page is greater than 4
            if (currentPage > 3) {
                const firstPage = document.createElement('div');
                firstPage.classList.add('page-number');
                firstPage.textContent = 1;
                firstPage.addEventListener('click', () => goToPage(1));
                paginationNumbers.appendChild(firstPage);

                if (currentPage > 4) {
                    const dots = document.createElement('div');
                    dots.classList.add('dots');
                    dots.textContent = '...';
                    paginationNumbers.appendChild(dots);
                }
            }

            // Display pages around the current page
            for (let i = Math.max(1, currentPage - 2); i <= Math.min(totalPages, currentPage + 2); i++) {
                const pageItem = document.createElement('div');
                pageItem.classList.add('page-number');
                if (i === currentPage) pageItem.classList.add('active');
                pageItem.textContent = i;
                pageItem.addEventListener('click', () => goToPage(i));
                paginationNumbers.appendChild(pageItem);
            }

            // Add dots and last page if current page is far from the last page
            if (currentPage < totalPages - 2) {
                if (currentPage < totalPages - 3) {
                    const dots = document.createElement('div');
                    dots.classList.add('dots');
                    dots.textContent = '...';
                    paginationNumbers.appendChild(dots);
                }
                const lastPage = document.createElement('div');
                lastPage.classList.add('page-number');
                lastPage.textContent = totalPages;
                lastPage.addEventListener('click', () => goToPage(totalPages));
                paginationNumbers.appendChild(lastPage);
            }

            // Render ">" button only if not on the last page
            if (currentPage < totalPages) {
                document.querySelector('.next-btn').disabled = false;
            } else {
                document.querySelector('.next-btn').disabled = true;
            }
        }

        // Go to specific page
        function goToPage(page) {
            currentPage = page;
            renderPagination();
        }

        // Event listeners for previous and next buttons
        document.querySelector('.prev-btn').addEventListener('click', () => {
            if (currentPage > 1) {
                currentPage--;
                renderPagination();
            }
        });

        document.querySelector('.next-btn').addEventListener('click', () => {
            if (currentPage < totalPages) {
                currentPage++;
                renderPagination();
            }
        });

        // Initial render
        renderPagination();
    </script>
    <script>
        function redirectToProductPage() {
            window.location.href = 'products_detail.jsp';
        }
    </script>
    <jsp:include page="assets/includes/foot.jsp"/>
</body>

</html>