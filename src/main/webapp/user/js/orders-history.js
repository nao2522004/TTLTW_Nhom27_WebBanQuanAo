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

            // Sắp xếp đơn hàng theo ngày đặt hàng giảm dần (mới nhất lên đầu)
            data.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));

            let html = `<table id="order-table" class="table table-bordered" style="width: 100%; font-size: 1.4rem; text-align: center; vertical-align: middle;">`;
            html += `<thead><tr>
                <th>Đơn hàng</th>
                <th>Ngày đặt</th>
                <th>Thanh toán</th>
                <th>Trạng thái</th>
                <th>Sản phẩm</th>
                <th>Tổng cộng</th>
                <th>Hành động</th>
            </tr></thead><tbody>`;

            data.forEach(order => {
                const orderDate = new Date(order.orderDate);
                const formattedDate = `${orderDate.getDate()}-${orderDate.getMonth() + 1}-${orderDate.getFullYear()}`;

                let productHtml = "";
                if (order.orderDetails && order.orderDetails.length > 0) {
                    order.orderDetails.forEach(item => {
                        productHtml += `
                            <div class="d-flex align-items-center mb-2" style="text-align: left;">
                                <img src="${item.imageUrl || 'assets/imgs/default.jpg'}" alt="${item.productName}"
                                     class="order-product-img rounded mr-3" style="width: 80px; height: 80px;">
                                <div>
                                    <h3 style="font-size: 1.6rem;">${item.productName}</h3>
                                    <p class="text-muted mb-0">Màu: ${item.color || 'Không rõ'}, Size: ${item.size || 'N/A'}</p>
                                    <p class="text-muted mb-0">Số lượng: ${item.quantity}</p>
                                    <p class="mb-1"><s>${item.unitPrice.toLocaleString()}đ</s></p>
                                    <p class="text-danger mb-0"><strong>${(item.unitPrice - item.discount).toLocaleString()}đ</strong></p>
                                </div>
                            </div>`;
                    });
                } else {
                    productHtml = `<p>Không có sản phẩm nào.</p>`;
                }

                html += `
                    <tr>
                        <td style="vertical-align: middle;">#${order.id}</td>
                        <td style="vertical-align: middle;">${formattedDate || 'N/A'}</td>
                        <td style="vertical-align: middle;">${order.paymentMethod || 'Không rõ'}</td>
                        <td style="vertical-align: middle;"><span class="${order.status === 1 ? 'text-success' : 'text-warning'}">${order.status === 1 ? 'Đã thanh toán' : 'Chưa thanh toán'}</span></td>
                        <td style="text-align: left;">${productHtml}</td>
                        <td style="vertical-align: middle;">${order.totalPrice.toLocaleString()}đ</td>
                        <td style="vertical-align: middle;">
                            <button id="order-cancel-btn" class="btn btn-danger btn-sm" data-id="${order.id}" style="font-size: 1.4rem;">Hủy đơn hàng</button>
                        </td>
                    </tr>`;
            });

            html += `</tbody></table>`;

            $("#order-list").html(html);

            // Khởi tạo DataTable với tìm kiếm và phân trang
            $('#order-table').DataTable({
                "paging": true,
                "searching": true,
                "pageLength": 5,
                "language": {
                    "search": "Tìm kiếm:",
                    "lengthMenu": "Hiển thị _MENU_ đơn hàng",
                    "info": "Hiển thị _START_ đến _END_ của _TOTAL_ đơn hàng",
                    "paginate": {
                        "first": "Đầu",
                        "last": "Cuối",
                        "next": "Tiếp",
                        "previous": "Trước"
                    }
                }
            });
        },
        error: function () {
            $("#order-list").html(`<p class="text-danger">Lỗi khi tải lịch sử đơn hàng!</p>`);
        }
    });
});
