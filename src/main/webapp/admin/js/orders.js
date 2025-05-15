/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Orders

---------------------------------------------------------
----------------------------------------------------------*/
// // Khi DOM load, gọi fetchOrders chỉ khi phần tử 'admin/manager-orders' cần hiển thị
// document.addEventListener('DOMContentLoaded', function () {
//     const  renderListOders = document.getElementById("admin/manager-orders");
//     if (renderListOders && renderListOders.classList.contains("block")) {
//         fetchOrders();
//     }
// });
// Hàm  fetchOrders khi load được gọi ở trong admin.js rôi

let orderTable;

// Lấy danh sách đơn hàng và khởi tạo DataTable
function fetchOrders() {
    $.ajax({
        url: '/WebBanQuanAo/admin/api/orders',    // Đã đổi endpoint
        type: 'GET',
        dataType: 'json',
        cache: false,
        success: function (orders) {
            const table = $('#recent-orders--table');

            // Nếu DataTable đã khởi tạo, chỉ clear và add data mới
            if (orderTable) {
                orderTable.clear();
                orderTable.rows.add(buildRowsData(orders));
                orderTable.draw(false);
            } else {
                orderTable = table.DataTable({
                    data: buildRowsData(orders),
                    columns: [
                        { title: 'ID' },
                        { title: 'Họ tên' },
                        { title: 'PT Thanh toán' },
                        { title: 'Mã giảm giá' },
                        { title: 'Ngày đặt' },
                        { title: 'Tổng tiền' },
                        { title: 'Trạng thái' },
                        { title: 'Thao tác', orderable: false }
                    ],
                    order: [[0, 'asc']],
                    language: { url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/vi.json' },
                    pageLength: 5,
                    lengthChange: true
                });
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching orders:', error);
            alert('Không thể lấy danh sách đơn hàng. Vui lòng thử lại.');
        }
    });
}

// Chuyển status thành text
function getStatusText(status) {
    const statusMap = { 0: 'Đã hủy', 1: 'Đang xử lý', 2: 'Đang giao', 3: 'Đã giao', 4: 'Đã nhận hàng', 5: 'Đã xóa' };
    return statusMap[status] || 'Không xác định';
}

// Tạo mảng row cho DataTable
function buildRowsData(orders) {
    return orders.map(order => ([
        order.id,
        order.firstName,
        order.paymentMethod,
        order.code,
        order.orderDate,
        order.totalPrice + ' VND',
        getStatusText(order.status),
        `<span onclick="openEditOrderPopup(event)" class="material-icons-sharp" data-orderId="${order.id}">edit</span>
         <span onclick="deleteOrder(event)" class="material-icons-sharp" data-orderId="${order.id}">delete</span>
         <span onclick="openOrderItemDetails(event)" class="material-icons-sharp" data-orderId="${order.id}">info</span>`
    ]));
}

// Hàm xóa đơn hàng
function deleteOrder(event) {
    const orderId = event.target.getAttribute("data-orderId");
    if (!confirm(`Bạn có chắc chắn muốn xóa đơn hàng ID=${orderId}?`)) return;

    $.ajax({
        url: `/WebBanQuanAo/admin/api/orders/${orderId}`,   // endpoint DELETE
        type: 'DELETE',
        cache: false,
        success: function (response) {
            alert(response.message || 'Xóa đơn hàng thành công!');
            // Remove row khỏi DataTable
            const row = $(btn).closest('tr');
            orderTable.row(row).remove().draw(false);
        },
        error: function (xhr) {
            console.error('Lỗi khi xóa đơn hàng:', xhr.responseText);
            alert(xhr.responseJSON?.message || 'Không thể xóa đơn hàng.');
        }
    });
}

function openEditOrderPopup(event) {
    const orderId = event.target.getAttribute('data-orderId');
    document.querySelector('.overlay').style.display = 'block';

    $.ajax({
        url: `/WebBanQuanAo/admin/api/orders/${orderId}`,
        type: 'GET',
        dataType: 'json',
        cache: false,
        success: function (data) {
            $('#edit-idOrder').val(data.id);
            $('#edit-firstNameOrder').val(data.firstName);
            $('#edit-paymentId').val(data.paymentId); // Gọn hơn, không cần find option
            $('#edit-code').val(data.code);
            $('#edit-orderDate').val(data.orderDate);
            $('#edit-totalPrice').val(data.totalPrice);
            $('#edit-statusOrder').val(data.status);
        },
        error: function (xhr) {
            console.error('Lỗi khi lấy chi tiết:', xhr.responseText);
            alert('Không thể tải thông tin đơn hàng.');
        }
    });
}

// Lưu chỉnh sửa
function saveOrderEdits(event) {
    event.preventDefault();
    const orderId = $('#edit-idOrder').val();
    const order = {
        firstName: $('#edit-firstNameOrder').val(),
        paymentId: parseInt($('#edit-paymentId').val()),
        code: $('#edit-code').val(),
        orderDate: new Date($('#edit-orderDate').val()).toISOString(),
        totalPrice: parseFloat($('#edit-totalPrice').val()),
        status: parseInt($('#edit-statusOrder').val())
    };

    $.ajax({
        url: `/WebBanQuanAo/admin/api/orders/${orderId}`,  // endpoint PUT
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(order),
        cache: false,
        success: function (response) {
            alert(response.message || 'Cập nhật thành công!');
            // Cập nhật row trong DataTable
            const rowIdx = orderTable.rows().indexes().filter(i => {
                return orderTable.row(i).data()[0] == orderId;
            })[0];
            if (rowIdx !== undefined) {
                const updatedRow = [
                    parseInt(orderId),
                    order.firstName,
                    $('#edit-paymentId option:selected').text(),
                    order.code,
                    order.orderDate,
                    order.totalPrice + ' VND',
                    getStatusText(order.status),
                    `<span onclick="openEditOrderPopup(event)" class="material-icons-sharp" data-order-id="${orderId}">edit</span>
                     <span onclick="deleteOrder(event)" class="material-icons-sharp" data-order-id="${orderId}">delete</span>
                     <span onclick="openOrderItemDetails(event)" class="material-icons-sharp" data-order-id="${orderId}">info</span>`
                ];
                orderTable.row(rowIdx).data(updatedRow).draw(false);
            }
            hideOverlay();
        },
        error: function (xhr) {
            console.error('Lỗi khi cập nhật:', xhr.responseText);
            alert(xhr.responseJSON?.message || 'Không thể cập nhật đơn hàng.');
        }
    });
}
