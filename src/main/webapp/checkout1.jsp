<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #f8f8f8;
        }

        .checkout-container {
            max-width: 900px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        /* .progress-bar {
            position: relative;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
            padding: 0 15rem 0 15rem;
        }

        .progress-bar::after {
            content: '';
            position: absolute;
            left: 50%;
            top: 27%;
            transform: translate(-50%, -50%);
            width: 5rem;
            height: 3px;
            background-color: #ccc;
        }

        .progress-bar .half {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .progress-bar .num {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #000;
            color: #fff;
            font-weight: bold;
        } */

        .shipping-box {
            border: 2px solid #5a54f9;
            padding: 15px;
            border-radius: 8px;
        }

        .btn-primary {
            background-color: #5a54f9;
            border: none;
        }

        .btn-secondary {
            background-color: #fff;
            border: 1px solid #ccc;
            color: #000;
        }

        .summary-box {
            background: #f5f5f5;
            padding: 15px;
            border-radius: 8px;
        }
    </style>
</head>

<body>
    <div class="container checkout-container">
        <!-- <div class="progress-bar">
            <div class="half col-md-5">
                <div class="num num_one">1</div>
                <span class="mx-2">Địa chỉ</span>
            </div>
            <div class="half col-md-5">
                <div class="num num_two">2</div>
                <span class="mx-2">Phương thức thanh toán</span>
            </div>
        </div> -->

        <div class="row">
            <div class="col-md-12">
                <h5>Địa chỉ nhận hàng</h5>
                <div class="shipping-box">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" checked>
                        <label class="form-check-label">Nhập địa chỉ</label>
                    </div>
                    <form>
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="Họ và tên">
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="Số điện thoại">
                            </div>
                        </div>
                        <input type="text" class="form-control mt-2" placeholder="Địa chỉ nơi nhận hàng">
                    </form>
                </div>
            </div>

            <div class="col-md-12">
                <h5 class="mt-4">Phương thức thanh toán</h5>
                <div class="shipping-box">
                    <form>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="paymentMethod" checked>
                            <label class="form-check-label">Credit/Debit Card</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="paymentMethod">
                            <label class="form-check-label">PayPal</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="paymentMethod">
                            <label class="form-check-label">Cash on Delivery</label>
                        </div>
                    </form>
                </div>
            </div>

            <div class="col-md-12">
                <h5 class="mt-4">Thông tin chi tiết thẻ Tín dụng/Ghi nợ</h5>
                <div class="shipping-box">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" checked>
                        <label class="form-check-label">Nhập địa chỉ</label>
                    </div>
                    <form>
                        <input type="text" class="form-control mt-3" placeholder="Số thẻ">
                        <div class="row mt-2">
                            <div class="col-md-7">
                                <input type="text" class="form-control mt-2" placeholder="Ngày hết hạn (MM/YY)">
                            </div>
                            <div class="col-md-5">
                                <input type="text" class="form-control mt-2" placeholder="Mã CVV">
                            </div>
                        </div>
                        <input type="text" class="form-control mt-2" placeholder="Họ và tên chủ thẻ">
                        <input type="text" class="form-control mt-2" placeholder="Địa chỉ đăng ký thẻ">
                        <input type="text" class="form-control mt-2" placeholder="Mã bưu chính">
                    </form>
                </div>
            </div>

            <!-- <div class="col-md-5">
                <div class="summary-box">
                    <p class="mt-2 text-muted">By placing your order, you agree to our Privacy Policy and Conditions of Use.</p>
                    <hr>
                    <h6>Order Summary</h6>
                    <p>Items (3): <span class="float-end">$56.73</span></p>
                    <p>Shipping & Handling: <span class="float-end">$5.50</span></p>
                    <p>Before Tax: <span class="float-end">$62.23</span></p>
                    <p>Tax Collected: <span class="float-end">$8.21</span></p>
                    <hr>
                    <h5>Order Total: <span class="float-end">$70.44</span></h5>
                </div>
            </div> -->
        </div>

        <div class="d-flex justify-content-between mt-3">
            <button type="button" class="btn btn-secondary">Huỷ giao dịch</button>
            <button type="submit" class="btn btn-primary">Xác nhận giao dịch</button>
        </div>
    </div>
</body>

</html>