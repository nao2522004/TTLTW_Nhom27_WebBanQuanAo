<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/cart.css">
</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>

    <!-- Shopping Cart -->
    <div id="card">
        <div class="row">
            <div class="col-md-8 cart">
                <div class="title">
                    <div class="row align-items-center">
                        <div class="col">
                            <h4>Giỏ Hàng</h4>
                        </div>
                    </div>
                </div>
                <div class="row border-top-bottom">
                    <div class="row main align-items-center">
                        <div class="col-2"><img class="img-fluid" src="assets/imgs/Cart/T_shirt1.jpg"></div>
                        <div class="col">
                            <div class="row text-muted">Áo Thun</div>
                            <div class="row">Cotton T-shirt</div>
                        </div>
                        <div class="col">
                            <div class="quantity-controls">
                                <button class="btn-decrease">-</button>
                                <span class="quantity">1</span>
                                <button class="btn-increase">+</button>
                            </div>
                        </div>
                        <div class="col"> 100.000 đồng<span class="close">&#10005;</span></div>
                    </div>
                </div>
                <div class="row">
                    <div class="row main align-items-center">
                        <div class="col-2"><img class="img-fluid" src="assets/imgs/Cart/T_shirt2.jpg"></div>
                        <div class="col">
                            <div class="row text-muted">Áo Thun</div>
                            <div class="row">Cotton T-shirt</div>
                        </div>
                        <div class="col">
                            <div class="quantity-controls">
                                <button class="btn-decrease">-</button>
                                <span class="quantity">1</span>
                                <button class="btn-increase">+</button>
                            </div>
                        </div>
                        <div class="col">100.000 đồng <span class="close">&#10005;</span></div>
                    </div>
                </div>
                <div class="row border-top-bottom">
                    <div class="row main align-items-center">
                        <div class="col-2"><img class="img-fluid" src="assets/imgs/Cart/T_shirt3.jpg"></div>
                        <div class="col">
                            <div class="row text-muted">Áo Thun</div>
                            <div class="row">Cotton T-shirt</div>
                        </div>
                        <div class="col">
                            <div class="quantity-controls">
                                <button class="btn-decrease">-</button>
                                <span class="quantity">1</span>
                                <button class="btn-increase">+</button>
                            </div>
                        </div>
                        <div class="col">100.000 đồng <span class="close">&#10005;</span></div>
                    </div>
                </div>
                <div class="back-to-shop text-muted"><a href="index.jsp">&leftarrow; Trở về trang chủ</a>
                </div>
            </div>
            <div class="col-md-4 summary">
                <div>
                    <h5>Hóa Đơn</h5>
                </div>
                <hr>
                <div class="row">
                    <div class="col" style="padding-left: 12px;">3 SẢN PHẨM</div>
                    <div class="col text-right">300.000 đồng</div>
                </div>
                <form>
                    <p>VẬN CHUYỂN</p>
                    <select>
                        <option class="text-muted">Giao Hàng Nhanh 30.000 đồng</option>
                        <option class="text-muted">Giao Hàng Tiết Kiệm 40.000 đồng</option>
                    </select>
                    <p>MÃ GIẢM GIÁ</p>
                    <input id="code" placeholder="Nhập mã">
                </form>
                <div class="row" style="border-top: 1px solid rgba(0,0,0,.1); padding: 2vh 0;">
                    <div class="col">
                        <p>TỔNG TIỀN:</p>
                    </div>
                    <div class="col text-right">
                        <p>330.000 đồng</p>
                    </div>
                </div>
                <a href="checkout.jsp"><button class="btn btn-lg">THANH TOÁN</button></a>
            </div>
        </div>
    </div>
    <!-- END: Shopping Cart -->

    <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>

    <!-- JS Cart -->
    <script>
        const decreaseBtns = document.querySelectorAll('.btn-decrease');
        const increaseBtns = document.querySelectorAll('.btn-increase');
        const quantityDisplays = document.querySelectorAll('.quantity');

        decreaseBtns.forEach((btn, index) => {
            btn.addEventListener('click', () => {
                let currentQuantity = parseInt(quantityDisplays[index].textContent);
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantityDisplays[index].textContent = currentQuantity;
                }
            });
        });

        increaseBtns.forEach((btn, index) => {
            btn.addEventListener('click', () => {
                let currentQuantity = parseInt(quantityDisplays[index].textContent);
                currentQuantity++;
                quantityDisplays[index].textContent = currentQuantity;
            });
        });
    </script>


    <!-- base js -->
    <script src="./assets/js/base.js"></script>
</body>

</html>