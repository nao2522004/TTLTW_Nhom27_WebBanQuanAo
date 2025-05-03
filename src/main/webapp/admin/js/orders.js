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

// ===============Recent_order_data===============//
// Lấy danh sách đơn hàng từ server và khởi tạo DataTables
function fetchOrders() {
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-orders', // Địa chỉ URL của API
        type: 'GET',
        dataType: 'json',
        cache: false,
        success: function (orders) {
            const table = $("#recent-orders--table");

            // Xóa DataTables nếu đã được khởi tạo trước đó
            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy(); // Hủy DataTables
                table.find("tbody").empty(); // Xóa dữ liệu cũ
            }

            // Thêm dữ liệu mới vào bảng
            const tbody = buildTableOrders(orders);
            table.append(tbody);

            // Khởi tạo lại DataTables với phân trang và tìm kiếm
            table.DataTable({
                searching: true, // Kích hoạt tìm kiếm
                info: true, // Hiển thị thông tin tổng số bản ghi
                order: [[0, 'asc']], // Sắp xếp mặc định theo cột đầu tiên (Id)
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/vi.json' // Ngôn ngữ Tiếng Việt
                },
                paging: true, // Kích hoạt phân trang
                pageLength: 5, // Số bản ghi mỗi trang
                lengthChange: true, // Kích hoạt thay đổi số lượng bản ghi mỗi trang
            });

            // Đánh dấu là đã tải dữ liệu
            targetMain.classList.add("data-loaded");
        },
        error: function (xhr, status, error) {
            console.error('Error fetching orders:', error);
            alert("Không thể lấy danh sách đơn hàng. Vui lòng thử lại.");
        }
    });
}

// Hàm chuyển đổi status từ số sang chuỗi tiếng Việt
function getStatusText(status) {
    const statusMap = {
        0: "Đã hủy",
        1: "Đang xử lý",
        2: "Đang giao",
        3: "Đã giao",
        4: "Đã nhận hàng",
        5: "Đã xóa"
    };
    return statusMap[status] || "Không xác định";
}

// Tạo tbody từ danh sách đơn hàng
const buildTableOrders = (orders) => {
    let orderContent = "";
    for (const order of orders) {
        orderContent += `
            <tr>
                <td>${order.id}</td>
                <td>${order.firstName}</td>
                <td>${order.paymentMethod}</td>
                <td>${order.code}</td>
                <td>${order.orderDate}</td>
                <td>${order.totalPrice} VND</td>
                <td>${getStatusText(order.status)}</td> <!-- Hiển thị trạng thái bằng tiếng Việt -->
                <td class="primary">
                    <span onclick="openEditOrderPopup(event)" class="material-icons-sharp" data-orderId="${order.id}"> edit </span>
                    <span onclick="deleteOrder(event)" class="material-icons-sharp" data-orderId="${order.id}">delete</span>
                    <span onclick="openOrderItemDetails(event)" class="material-icons-sharp" data-orderItemsId="${order.id}"> info </span>
                </td>
            </tr>
        `;
    }
    return `<tbody>${orderContent}</tbody>`;
};

// Hàm xóa đơn hàng
function deleteOrder(event) {
    const orderId = event.target.getAttribute("data-orderId"); // Lấy id của đơn hàng
    console.log(JSON.stringify({id: orderId}));

    if (confirm(`Bạn có chắc chắn muốn xóa đơn hàng với ID: ${orderId}?`)) {
        $.ajax({
            url: `/WebBanQuanAo/admin/manager-orders?id=${orderId}`, // Đường dẫn API xóa đơn hàng
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({id: orderId}), // Gửi ID đơn hàng dưới dạng JSON
            cache: false,
            success: function (response) {
                // Ghi log chỉ khi xóa thành công
                if (response.message.includes("Xóa mềm đơn hàng thành công")) {
                    console.log("Đã xóa đơn hàng thành công, bắt đầu ghi log.");
                    alert(response.message || "Xóa đơn hàng thành công!");
                }
                fetchOrders(); // Refresh danh sách đơn hàng sau khi xóa
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi xóa đơn hàng:', error);
                alert(xhr.responseJSON?.message || "Không thể xóa đơn hàng. Vui lòng thử lại sau.");
            }
        });
    }
}


// Mở popup chỉnh sửa đơn hàng
function openEditOrderPopup(event) {
    const orderId = event.target.getAttribute("data-orderId"); // Lấy id đơn hàng từ data-orderId

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block"; // Hiển thị lớp phủ

    // Gửi yêu cầu AJAX để lấy dữ liệu đơn hàng theo id
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-orders', // Đảm bảo URL khớp với servlet
        type: 'GET',
        data: {id: orderId},
        cache: false,
        success: function (data) {
            // Điền dữ liệu đơn hàng vào các trường trong form
            document.getElementById("edit-idOrder").value = data.id;
            document.getElementById("edit-firstNameOrder").value = data.firstName;

            // Tìm và chọn phương thức thanh toán theo paymentId
            const paymentSelect = document.getElementById("edit-paymentId");
            const paymentOption = paymentSelect.querySelector(`option[value="${data.paymentId}"]`);
            if (paymentOption)
                paymentOption.selected = true; // Chọn đúng option
            else
                console.warn("Không tìm thấy phương thức thanh toán phù hợp:", data.paymentId);


            document.getElementById("edit-code").value = data.code;
            document.getElementById("edit-orderDate").value = data.orderDate;
            document.getElementById("edit-totalPrice").value = data.totalPrice;

            // Gán giá trị status vào select dropdown
            document.getElementById("edit-statusOrder").value = data.status;
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu đơn hàng:", error);
            alert("Không thể lấy thông tin đơn hàng. Vui lòng thử lại.");
        }
    });
}


// Lưu chỉnh sửa đơn hàng
function saveOrderEdits(event) {
    // Ngăn hành vi submit mặc định của form
    event.preventDefault();

    // Thu thập dữ liệu từ các trường nhập liệu
    const order = {
        id: parseInt(document.getElementById("edit-idOrder").value),
        firstName: document.getElementById("edit-firstNameOrder").value,
        paymentId: parseInt(document.getElementById("edit-paymentId").value),
        code: document.getElementById("edit-code").value,
        orderDate: new Date(document.getElementById("edit-orderDate").value).toISOString(),
        totalPrice: parseFloat(document.getElementById("edit-totalPrice").value),
        status: parseInt(document.getElementById("edit-statusOrder").value), // Chuyển đổi sang số nguyên
    };

    // Chuyển đổi đối tượng `order` thành JSON
    const orderJson = JSON.stringify(order);

    // Log để kiểm tra JSON đã tạo
    console.log("JSON object gửi đi:", orderJson);

    // Gửi yêu cầu AJAX với JSON
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-orders',
        type: 'PUT',
        contentType: 'application/json', // Định dạng dữ liệu gửi đi là JSON
        data: orderJson, // Gửi JSON object
        cache: false,
        success: function (response) {
            alert("Cập nhật thông tin đơn hàng thành công!");
            fetchOrders(); // Tải lại danh sách đơn hàng
            hideOverlay(); // Ẩn overlay
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật thông tin đơn hàng:", error);
            alert("Không thể cập nhật thông tin đơn hàng. Vui lòng kiểm tra lại dữ liệu và thử lại.");
        }
    });
}
