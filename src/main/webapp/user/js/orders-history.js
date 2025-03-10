$(document).ready(function () {
    $.ajax({
        url: "/WebBanQuanAo/user/orderController",
        type: "GET",
        dataType: "json",
        cache: false,
        success: function (data) {
            if (!Array.isArray(data)) {
                $("#order-list").html(`<p class="text-danger">Dữ liệu không hợp lệ!</p>`);
                return;
            }

            if (data.length === 0) {
                $("#order-list").html(`<p class="text-muted">Bạn chưa có đơn hàng nào.</p>`);
                return;
            }

            let html = "";
            data.forEach(order => {
                html += `
                    <div class="order-card bg-light p-4 rounded mb-4">
                        <!-- Thông tin đơn hàng -->
                        <div id="order-info" class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="text-second-color">Đơn hàng #${order.id}</h5>
                                <p class="text-muted">Khách hàng: ${order.firstName || 'Không rõ'}</p>
                                <p class="text-muted">Ngày đặt: ${order.orderDate || 'N/A'}</p>
                                <p class="text-muted">Thanh toán: ${order.paymentMethod || 'Không rõ'}</p>
                                <p class="text-muted">
                                    Trạng thái: 
                                    <strong class="${order.status === 1 ? 'text-success' : 'text-warning'}">
                                        ${order.status === 1 ? 'Đã thanh toán' : 'Chưa thanh toán'}
                                    </strong>
                                </p>
                            </div>
                            <div>
                                <button id="order-cancel-btn" class="btn btn-danger btn-sm" data-id="${order.id}">
                                    Hủy đơn hàng
                                </button>
                            </div>
                        </div>

                        <hr>

                        <!-- Danh sách sản phẩm (tĩnh, chưa có dữ liệu từ API) -->
                        <ul id="order-product-list" class="list-group">
                            <li id="order-product-item-1" class="list-group-item d-flex justify-content-between align-items-center">
                                <div class="d-flex align-items-center">
                                    <img src="assets/imgs/Collection-running/epitem2.webp" alt="Sản phẩm 1"
                                         class="order-product-img rounded mr-3">
                                    <div>
                                        <h6 id="product-name" class="mb-1">Sản phẩm 1</h6>
                                        <p id="product-qty" class="text-muted mb-0">Số lượng: 1</p>
                                    </div>
                                </div>
                                <div class="text-right">
                                    <p id="product-old-price" class="mb-1"><s>300.000đ</s></p>
                                    <p id="product-new-price" class="text-danger mb-0"><strong>200.000đ</strong></p>
                                </div>
                            </li>
                        </ul>

                        <hr>

                        <!-- Phí ship & Tổng tiền -->
                        <div id="order-fee" class="d-flex justify-content-between">
                            <p class="text-muted mb-0">Phí ship:</p>
                            <p class="text-muted mb-0">50.000đ</p>
                        </div>
                        <div id="order-total-section" class="d-flex justify-content-between">
                            <p class="font-weight-bold mb-0">Tổng cộng:</p>
                            <p class="font-weight-bold mb-0" style="color: var(--second-color);">
                                ${order.totalPrice.toLocaleString()}đ
                            </p>
                        </div>
                    </div>`;
            });

            $("#order-list").html(html);
        },
        error: function () {
            $("#order-list").html(`<p class="text-danger">Lỗi khi tải lịch sử đơn hàng!</p>`);
        }
    });

    // Xử lý sự kiện hủy đơn hàng
    // $(document).on("click", "#order-cancel-btn", function () {
    //     let orderId = $(this).data("id");
    //     if (confirm("Bạn có chắc chắn muốn hủy đơn hàng #" + orderId + "?")) {
    //         $.ajax({
    //             url: "/cancelOrder",
    //             type: "POST",
    //             data: { orderId: orderId },
    //             success: function (response) {
    //                 alert(response.message);
    //                 location.reload();
    //             },
    //             error: function () {
    //                 alert("Lỗi khi hủy đơn hàng!");
    //             }
    //         });
    //     }
    // });

});
