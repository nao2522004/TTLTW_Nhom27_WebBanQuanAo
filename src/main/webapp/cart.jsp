<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp" %>
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/cart.css">
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>

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

            <c:forEach items="${sessionScope.cart.list}" var="cp">
                <div class="row border-top-bottom">
                    <div class="row main align-items-center">
                        <div class="col"><img class="img-fluid" src="assets/product-imgs/${cp.img}" alt=""></div>
                        <div class="col">
                            <div class="row text-muted">${cp.name}</div>
                            <div class="row text-muted">
                                    ${cp.size}
                                <div class="col text-muted">${cp.color}</div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="quantity-controls">
                                <button class="btn-decrease">-</button>
                                <span class="quantity">${cp.quantity}</span>
                                <button class="btn-increase">+</button>
                            </div>
                        </div>
                        <div class="product-price col" data-price="${cp.unitPrice * cp.quantity}"></div>
                        <a href="del-cart?pid=${cp.id}"><button class="close col">&#10005;</button></a>
                    </div>
                </div>
            </c:forEach>

            <div class="back-to-shop text-muted"><a href="index.jsp">&leftarrow; Trở về trang chủ</a>
            </div>
        </div>
        <div class="col-md-4 summary">
            <div>
                <h5>Hóa Đơn</h5>
            </div>
            <hr>
            <div class="row">
                <div class="col" style="padding-left: 12px;"> SẢN PHẨM</div>
                <div class="col text-right"></div>
            </div>
            <form>
                <p>VẬN CHUYỂN</p>
                <select>
                    <option class="text-muted">Giao Hàng Nhanh  đồng</option>
                    <option class="text-muted">Giao Hàng Tiết Kiệm  đồng</option>
                </select>
                <p>MÃ GIẢM GIÁ</p>
                <input id="code" placeholder="Nhập mã">
            </form>
            <div class="row" style="border-top: 1px solid rgba(0,0,0,.1); padding: 2vh 0;">
                <div class="col">
                    <p>TỔNG TIỀN:</p>
                </div>
                <div class="col text-right">
                    <p> đồng</p>
                </div>
            </div>
            <a href="checkout.jsp">
                <button class="btn btn-lg">THANH TOÁN</button>
            </a>
        </div>
    </div>
</div>
<!-- END: Shopping Cart -->

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

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