<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp" %>
    <title>Thanh Toán</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/checkout.css">
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>
<!-- Check Out -->
<div id="check-out">
    <div class="container">
        <div class="py-5 text-center">
            <h2 style=" color: #335d4a !important"><b>Thanh Toán</b></h2>
            <hr>
        </div>
        <div class="row">
<%--            <div class="col-md-4 order-md-2 mb-4">--%>
<%--                <h4 class="d-flex justify-content-between align-items-center mb-3">--%>
<%--                    <span class="text-muted header-text" style=" color: #335d4a !important">Giỏ Hàng Của Bạn</span>--%>
<%--                    <span class="badge badge-secondary badge-pill"></span>--%>
<%--                </h4>--%>
<%--                <ul class="list-group mb-3">--%>
<%--                    <li class="list-group-item d-flex justify-content-between lh-condensed">--%>
<%--                        <div>--%>
<%--                            <h6 class="my-0 gr-text"></h6>--%>
<%--                            <small class="text-muted"></small>--%>
<%--                        </div>--%>
<%--                        <span class="text-muted"></span>--%>
<%--                    </li>--%>
<%--                    <li class="list-group-item d-flex justify-content-between bg-light">--%>
<%--                        <div class="text-success">--%>
<%--                            <h6 class="my-0 gr-text text-custom">Giao Hàng</h6>--%>
<%--                        </div>--%>
<%--                        <span class="text-success" style=" color: #335d4a !important"></span>--%>
<%--                    </li>--%>
<%--                    <li class="list-group-item d-flex justify-content-between bg-light">--%>
<%--                        <div class="text-success">--%>
<%--                            <h6 class="my-0 gr-text text-custom">Mã Giảm Giá</h6>--%>
<%--                        </div>--%>
<%--                        <span class="text-success" style=" color: #335d4a !important"></span>--%>
<%--                    </li>--%>
<%--                    <li class="list-group-item d-flex justify-content-between">--%>
<%--                        <span class="my-0 gr-text">Tổng Tiền</span>--%>
<%--                        <strong></strong>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </div>--%>
            <div class="col-md-8 order-md-1 mx-auto">
                <h4 class="mb-3 header-text" style=" color: #335d4a !important">Địa Chỉ Thanh Toán</h4>
                <form class="needs-validation" novalidate method="post">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="firstName"><i class="fa-solid fa-id-card"></i> Họ</label>
                            <input type="text" class="form-control" id="firstName" placeholder="" value="" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="lastName" class="label-text"><i class="fa-solid fa-id-card"></i> Tên</label>
                            <input type="text" class="form-control" id="lastName" placeholder="" value="" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="phone"><i class="fa-solid fa-phone"></i> Số Điện Thoại</label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">+84</span>
                            </div>
                            <input type="text" class="form-control" id="phone" placeholder="" required>
                            <div class="invalid-feedback" style="width: 100%;">
                                Vui lòng nhập số điện thoại
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="email"><i class="fa-solid fa-envelope"></i> Email <span
                                class="text-muted"></span></label>
                        <input type="email" class="form-control" id="email" placeholder="">
                        <div class="invalid-feedback">
                            Vui lòng nhập địa chỉ email hợp lệ để cập nhật thông tin vận chuyển
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="address"><i class="fa-solid fa-location-dot"></i> Địa chỉ</label>
                        <input type="text" class="form-control" id="address" placeholder="" required>
                        <div class="invalid-feedback">
                            Vui lòng nhập địa chỉ của bạn
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="state" class="lb-text">Tỉnh/Thành phố</label>
                            <select class="custom-select d-block w-100 large-select" id="state" required>
                                <option value="">Thành Phố Hồ Chí Minh</option>
                                <option>Tỉnh An Giang</option>
                                <option>Tỉnh Bà Rịa - Vũng Tàu</option>
                                <option>Tỉnh Bắc Giang</option>
                                <option>Tỉnh Bắc Kạn</option>
                                <option>Tỉnh Bạc Liêu</option>
                                <option>Tỉnh Bắc Ninh</option>
                                <option>Tỉnh Bến Tre</option>
                                <option>Tỉnh Bình Định</option>
                                <option>Tỉnh Bình Dương</option>
                                <option>Tỉnh Bình Phước</option>
                                <option>Tỉnh Bình Thuận</option>
                                <option>Tỉnh Cà Mau</option>
                                <option>Tỉnh Cao Bằng</option>
                                <option>Tỉnh Đắk Lắk</option>
                                <option>Tỉnh Đắk Nông</option>
                                <option>Tỉnh Điện Biên</option>
                                <option>Tỉnh Đồng Nai</option>
                                <option>Tỉnh Đồng Tháp</option>
                                <option>Tỉnh Gia Lai</option>
                                <option>Tỉnh Hà Giang</option>
                                <option>Tỉnh Hà Nam</option>
                                <option>Tỉnh Hà Tĩnh</option>
                                <option>Tỉnh Hải Dương</option>
                                <option>Thành phố Hải Phòng</option>
                                <option>Tỉnh Hậu Giang</option>
                                <option>Tỉnh Hòa Bình</option>
                                <option>Tỉnh Hưng Yên</option>
                                <option>Tỉnh Khánh Hòa</option>
                                <option>Tỉnh Kiên Giang</option>
                                <option>Tỉnh Kon Tum</option>
                                <option>Tỉnh Lai Châu</option>
                                <option>Tỉnh Lâm Đồng</option>
                                <option>Tỉnh Lạng Sơn</option>
                                <option>Tỉnh Lào Cai</option>
                                <option>Tỉnh Long An</option>
                                <option>Tỉnh Nam Định</option>
                                <option>Tỉnh Nghệ An</option>
                                <option>Tỉnh Ninh Bình</option>
                                <option>Tỉnh Ninh Thuận</option>
                                <option>Tỉnh Phú Thọ</option>
                                <option>Tỉnh Quảng Bình</option>
                                <option>Tỉnh Quảng Nam</option>
                                <option>Tỉnh Quảng Ngãi</option>
                                <option>Tỉnh Quảng Ninh</option>
                                <option>Tỉnh Quảng Trị</option>
                                <option>Tỉnh Sóc Trăng</option>
                                <option>Tỉnh Sơn La</option>
                                <option>Tỉnh Tây Ninh</option>
                                <option>Tỉnh Thái Bình</option>
                                <option>Tỉnh Thái Nguyên</option>
                                <option>Tỉnh Thanh Hóa</option>
                                <option>Tỉnh Thừa Thiên Huế</option>
                                <option>Tỉnh Tiền Giang</option>
                                <option>Tỉnh Trà Vinh</option>
                                <option>Tỉnh Tuyên Quang</option>
                                <option>Tỉnh Vĩnh Long</option>
                                <option>Tỉnh Vĩnh Phúc</option>
                                <option>Tỉnh Yên Bái</option>
                                <option>Thành phố Cần Thơ</option>
                                <option>Thành phố Đà Nẵng</option>
                            </select>
                            <div class="invalid-feedback">
                                Vui lòng chọn Tỉnh/Thành Phố của bạn
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="address2" class="lb-text">Địa chỉ bổ sung <small class="text-muted">(Không
                                bắt
                                buộc)</small></label>
                            <input type="text" class="form-control" id="address2" placeholder="Số căn hộ/phòng ">
                        </div>
                    </div>
<%--                    <hr class="mb-4">--%>
<%--                    <div class="custom-control custom-checkbox">--%>
<%--                        <input type="checkbox" class="custom-control-input" id="same-address">--%>
<%--                        <label class="custom-control-label" for="same-address">Dùng địa chỉ thanh toán làm địa chỉ--%>
<%--                            giao--%>
<%--                            hàng</label>--%>
<%--                    </div>--%>
<%--                    <div class="custom-control custom-checkbox">--%>
<%--                        <input type="checkbox" class="custom-control-input" id="save-info">--%>
<%--                        <label class="custom-control-label" for="save-info">Lưu thông tin này cho lần sau</label>--%>
<%--                    </div>--%>
<%--                    <hr class="mb-4">--%>
<%--                    <div>--%>
<%--                        <h4 class="mb-3 header-text" style=" color: #335d4a !important">Phương Thức Thanh Toán</h4>--%>
<%--                        <div>--%>
<%--                            <img src="assets/imgs/CheckOut/logo_1.png" alt="">--%>
<%--                            <img src="assets/imgs/CheckOut/logo_2.png" alt="">--%>
<%--                            <img src="assets/imgs/CheckOut/logo_3.png" alt="">--%>
<%--                            <img src="assets/imgs/CheckOut/logo_6.webp" alt="">--%>
<%--                            <img src="assets/imgs/CheckOut/logo_4.png" alt="">--%>
<%--                            <img src="assets/imgs/CheckOut/logo_5.jpg" alt="">--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <!-- <div class="d-block my-3">
                        <div class="custom-control custom-radio">
                            <input id="credit" name="paymentMethod" type="radio" class="custom-control-input"
                                checked required>
                            <label class="custom-control-label" for="credit">Thẻ tín dụng</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="debit" name="paymentMethod" type="radio" class="custom-control-input"
                                required>
                            <label class="custom-control-label" for="debit">Thẻ ghi nợ</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="momo" name="paymentMethod" type="radio" class="custom-control-input"
                                required>
                            <label class="custom-control-label" for="momo">Momo</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="zalopay" name="paymentMethod" type="radio" class="custom-control-input"
                                required>
                            <label class="custom-control-label" for="zalopay">Zalo Pay</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="cod" name="paymentMethod" type="radio" class="custom-control-input" required>
                            <label class="custom-control-label" for="cod">Thanh toán khi nhận hàng (COD)</label>
                        </div>
                    </div> -->
<%--                    <div class="md-6 mb-3">--%>
<%--                        <label for="paymentMethod" class="lb-text"></label>--%>
<%--                        <select class="custom-select d-block w-100 large-select" id="paymentMethod" name="paymentMethod"--%>
<%--                                required>--%>
<%--                            <option value="">Chọn phương thức thanh toán</option>--%>
<%--                            <option value="credit">Thẻ tín dụng</option>--%>
<%--                            <option value="debit">Thẻ ghi nợ</option>--%>
<%--                            <option value="momo">Momo</option>--%>
<%--                            <option value="zalopay">Zalo Pay</option>--%>
<%--                            <option value="cod">Thanh toán khi nhận hàng (COD)</option>--%>
<%--                        </select>--%>
<%--                        <div class="invalid-feedback">--%>
<%--                            Vui lòng chọn phương thức thanh toán--%>
<%--                        </div>--%>
<%--                    </div>--%>

<%--                    <div class="row">--%>
<%--                        <div class="col-md-6 mb-3">--%>
<%--                            <label for="cc-name">Tên trên thẻ</label>--%>
<%--                            <input type="text" class="form-control" id="cc-name" placeholder="" required>--%>
<%--                            <small class="text-muted">Họ và tên đầy đủ hiển thị trên thẻ</small>--%>
<%--                            <div class="invalid-feedback">--%>
<%--                                Tên trên thẻ là bắt buộc--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div class="col-md-6 mb-3">--%>
<%--                            <label for="cc-number">Số thẻ tín dụng</label>--%>
<%--                            <input type="text" class="form-control" id="cc-number" placeholder="" required>--%>
<%--                            <div class="invalid-feedback">--%>
<%--                                Số thẻ tín dụng là bắt buộc--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div class="row">--%>
<%--                        <div class="col-md-3 mb-3">--%>
<%--                            <label for="cc-expiration">Ngày hết hạn</label>--%>
<%--                            <input type="text" class="form-control" id="cc-expiration" placeholder="MM/YY" required>--%>
<%--                            <div class="invalid-feedback">--%>
<%--                                Ngày hết hạn là bắt buộc--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div class="col-md-3 mb-3">--%>
<%--                            <label for="cc-cvv">CVV</label>--%>
<%--                            <input type="text" class="form-control" id="cc-cvv" placeholder="* * *" required>--%>
<%--                            <div class="invalid-feedback">--%>
<%--                                Mã bảo mật là bắt buộc--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <hr class="mb-4">
                    <input type="hidden" name="totalPrice" value="${totalPrice}">
                    <%-- Hiển thị thông báo nếu có --%>
                    <c:if test="${not empty message}">
                        <div style="color: #335d4a; font-weight: bold; margin-top: 20px;" class="text-center" >
                                ${message}
                        </div>
                    </c:if>

                    <button class="btn btn-primary btn-lg btn-block" type="submit">THANH TOÁN</button>
                </form>
                <div class="back-to-shop text-muted"><a href="cart.jsp">&leftarrow; Trở về giỏ hàng</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END: Check Out -->

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

<!-- base js -->
<script src="./assets/js/base.js"></script>
</body>

</html>