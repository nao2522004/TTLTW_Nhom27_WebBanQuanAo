<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/Favicon/Luat/favicon-32x32.png" type="image/png">
    <!-- Frameworks -->
    <!-- Reset CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
        integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- CSS native -->
    <link rel="stylesheet" href="./assets/css/base.css">
    <link rel="stylesheet" href="./assets/css/header-footer.css">
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/responsive_luat.css">
</head>

<body>
    <header id="header"></header>
    <!-- Make a space to split the other out of header -->
    <div style="height: 12rem;"></div>

    <!-- Carousel -->
    <div id="customCarousel" class="carousel slide" data-ride="carousel">
        <!-- Carousel Inner -->
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="assets/imgs/HomePage/carousel/5.png" class="d-block w-100" alt="Slide 1">
            </div>
            <div class="carousel-item">
                <img src="assets/imgs/HomePage/carousel/6.png" class="d-block w-100" alt="Slide 2">
            </div>
        </div>

        <!-- Carousel captions -->
        <div class="carousel_caption">
            <h5></h5>
            <p></p>
            <a href="#" class="btn btn-link">Discover</a>
        </div>

        <!-- Controls -->
        <a class="carousel-control-prev" href="#customCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#customCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!-- Sale products -->
    <section id="section_sale">
        <div class="container">
            <h2>Giảm giá lên đến 70% cho ngày Black Friday</h2>
            <div class="row">
                <!-- Item 1 -->
                <div class="col-md-3">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/1.webp" alt="Image 1">
                        <h4>Quần Kaki Ống Suông</h4>
                        <div class="product-price" data-price="289000"></div>
                    </div>
                </div>
                <!-- Item 2 -->
                <div class="col-md-3">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/2.webp" alt="Image 2">
                        <h4>Double Knee Reetro</h4>
                        <div class="product-price" data-price="350000"></div>
                    </div>
                </div>
                <!-- Item 3 -->
                <div class="col-md-3">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/5.webp" alt="Image 3">
                        <h4>Cargo pant carhartt</h4>
                        <div class="product-price" data-price="300000"></div>
                    </div>
                </div>
                <!-- Item 4 -->
                <div class="col-md-3">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/4.webp" alt="Image 4">
                        <h4>Quần Cargo pant</h4>
                        <div class="product-price" data-price="280000"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Collections -->
    <section id="section_banner_collection" class="container-fluid p-0">
        <img src="assets/imgs/HomePage/collections/banner/1.jpg" alt="" class="w-100">
    </section>
    <section id="section_collection">
        <div class="container">
            <!-- Collection Header -->
            <div class="collection-header text-left mb-4">
                <h2>Bộ sưu tập theo mùa</h2>
                <h3>Sản phẩm theo mùa thời trang nhất năm 2024</h3>
                <a href="" class="see-more">Xem thêm</a>
            </div>
            <!-- Product Grid -->
            <div class="row product-grid mt-5">
                <div class="col-md-4 col-lg-3 mb-4">
                    <div class="product-item">
                        <img src="assets/imgs/HomePage/collections/1.png" alt="Product 1">
                        <div class="product-price" data-price="1086207"></div>
                    </div>
                </div>
                <div class="col-md-4 col-lg-3 mb-4">
                    <div class="product-item">
                        <img src="assets/imgs/HomePage/collections/2.png" alt="Product 2">
                        <div class="product-price" data-price="2240896"></div>
                    </div>
                </div>
                <div class="col-md-4 col-lg-3 mb-4">
                    <div class="product-item">
                        <img src="assets/imgs/HomePage/collections/3.png" alt="Product 3">
                        <div class="product-price" data-price="318302"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- New arrivals -->
    <section id="section_new_arrivals">
        <div class="container">
            <h2>Sản phẩm bán chạy và được yêu thích nhiều nhất</h2>
            <div class="row">
                <!-- Item 1 -->
                <div class="col-md-4">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/1.webp" alt="Image 1">
                        <h4>Quần Kaki Ống Suông</h4>
                        <p>289.000đ</p>
                    </div>
                </div>
                <!-- Item 2 -->
                <div class="col-md-4">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/2.webp" alt="Image 2">
                        <h4>Double Knee Reetro</h4>
                        <p>450.000đ</p>
                    </div>
                </div>
                <!-- Item 3 -->
                <div class="col-md-4">
                    <div class="item">
                        <img src="assets/imgs/HomePage/section1/5.webp" alt="Image 3">
                        <h4>Cargo pant carhartt</h4>
                        <p>300.000đ</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer id="footer"></footer>

    <!-- Load Components -->
    <script>
        // Header and footer
        const header = document.getElementById('header');
        const footer = document.getElementById('footer');
        fetch('./assets/component/header.html').then(response => response.text()).then(html => header.innerHTML = html);
        fetch('./assets/component/footer.html').then(response => response.text()).then(html => footer.innerHTML = html);
    </script>
    <!-- jQuery, Popper.js, and Bootstrap 4.6.2 JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
    <script src="./assets/bootstrap-4.6.2/js/bootstrap.min.js"></script>
    <!-- Javascript Native -->
    <script src="./assets/js/base.js"></script>
    <script src="./assets/js/main.js"></script>
</body>

</html>