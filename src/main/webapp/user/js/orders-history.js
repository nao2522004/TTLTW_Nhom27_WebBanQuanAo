// Lấy danh sách đơn hàng và hiển thị lịch sử đơn hàng của user
$(document).ready(function () {
    $.ajax({
        url: "/WebBanQuanAo/user/orderController", // API lấy danh sách đơn hàng
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.error) {
                $("#order-list").html(`<p class="text-danger">${data.error}</p>`);
                return;
            }

            let html = "";
            data.forEach(order => {
                html += `
                        <div class="order-card bg-light p-4 rounded mb-4">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="text-second-color">Đơn hàng #${order.id}</h5>
                                    <p class="text-muted">Khách hàng: ${order.userName}</p>
                                    <p class="text-muted">Ngày đặt: ${order.orderDate}</p>
                                    <p class="text-muted">Thanh toán: ${order.paymentMethod}</p>
                                    <p class="text-muted">
                                        Trạng thái: <strong class="text-warning">${order.status}</strong>
                                    </p>
                                </div>
                                <div>
                                    <button class="btn btn-danger btn-sm cancel-order" data-id="${order.id}">Hủy đơn hàng</button>
                                </div>
                            </div>
                            <hr>
                            <ul class="list-group">
                                ${order.items.map(item => `
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                            <img src="${item.imageUrl}" alt="${item.name}" class="order-product-img rounded mr-3" style="width: 50px; height: 50px;">
                                            <div>
                                                <h6 class="mb-1">${item.name}</h6>
                                                <p class="text-muted mb-0">Số lượng: ${item.quantity}</p>
                                            </div>
                                        </div>
                                        <div class="text-right">
                                            <p class="mb-1"><s>${item.oldPrice}đ</s></p>
                                            <p class="text-danger mb-0"><strong>${item.newPrice}đ</strong></p>
                                        </div>
                                    </li>
                                `).join("")}
                            </ul>
                            <hr>
                            <div class="d-flex justify-content-between">
                                <p class="text-muted mb-0">Phí ship:</p>
                                <p class="text-muted mb-0">${order.shippingFee}đ</p>
                            </div>
                            <div class="d-flex justify-content-between">
                                <p class="font-weight-bold mb-0">Tổng cộng:</p>
                                <p class="font-weight-bold mb-0 text-primary">${order.totalPrice}đ</p>
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
    // $(document).on("click", ".cancel-order", function () {
    //     let orderId = $(this).data("id");
    //     if (confirm("Bạn có chắc chắn muốn hủy đơn hàng #" + orderId + "?")) {
    //         $.ajax({
    //             url: "/cancelOrder",
    //             type: "POST",
    //             data: {orderId: orderId},
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