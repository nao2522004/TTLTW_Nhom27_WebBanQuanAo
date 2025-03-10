/*--------------------------------------------------------
---------------------------------------------------------

                       Manager OrderDetails

---------------------------------------------------------
----------------------------------------------------------*/
// Hàm mở chi tiết orderItem
function openOrderItemDetails(event) {
    const orderId = event.target.getAttribute("data-orderItemsId");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay-orderItemDetails");
    overlay.style.display = "block";

    // Gửi yêu cầu AJAX để lấy dữ liệu chi tiết orderItem theo orderId
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-orderDetails',
        type: 'GET',
        data: {orderId: orderId},
        cache: false,
        success: function (data) {
            console.log(JSON.stringify(data)); // In ra dữ liệu nhận được từ server

            if (data && data.length > 0) {
                let orderItemDetailListContent = '';

                // Duyệt qua tất cả các chi tiết orderItem và tạo HTML để hiển thị
                data.forEach(orderItem => {
                    orderItemDetailListContent += `
                    <tr>
                        <td><input type="number" value="${orderItem.id}" data-field="id" disabled></td>
                        <td><input type="number" value="${orderItem.orderId}" data-field="orderId" disabled></td>
                        <td><input type="text" value="${orderItem.productName}" data-field="productName" disabled></td>
                        <td><input type="text" value="${orderItem.color}" data-field="color" disabled></td>
                        <td><input type="text" value="${orderItem.size}" data-field="size" disabled></td>
                        <td><input type="number" value="${orderItem.quantity}" class="editable" data-id="${orderItem.id}" data-field="quantity"></td>
                        <td><input type="number" value="${orderItem.unitPrice}" class="editable" data-id="${orderItem.id}" data-field="unitPrice"></td>
                        <td><input type="number" value="${orderItem.discount}" class="editable" data-id="${orderItem.id}" data-field="discount"></td>
                        <td><span onclick="saveOrderItemEdits(event)" class="primary material-icons-sharp">save</span></td>
                    </tr>
                   `;

                });

                // Điền dữ liệu vào phần <tbody> của bảng
                const tableBody = document.querySelector("#orderItem-details--table tbody");
                tableBody.innerHTML = orderItemDetailListContent;

                // Thêm sự kiện để chỉnh sửa các trường thông tin
                const editableFields = document.querySelectorAll(".editable");
                editableFields.forEach(field => {
                    field.addEventListener("change", handleFieldChange);
                });
            } else {
                alert("Không có chi tiết đơn hàng nào.");
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu chi tiết đơn hàng:", error);
            alert("Không thể lấy thông tin chi tiết đơn hàng. Vui lòng thử lại.");
        }
    });
}

// Hàm lưu chỉnh sửa chi tiết orderItem
function saveOrderItemEdits(event) {
    event.preventDefault();

    // Lấy hàng chứa nút "save" được nhấn
    const row = event.target.closest("tr");

    // Lấy các giá trị từ các trường trong hàng
    const orderItemDetail = {
        id: parseInt(row.querySelector("input[data-field='id']").value),
        orderId: parseInt(row.querySelector("input[data-field='orderId']").value),
        productName: row.querySelector("input[data-field='productName']").value,
        color: row.querySelector("input[data-field='color']").value,
        size: row.querySelector("input[data-field='size']").value,
        quantity: parseInt(row.querySelector("input[data-field='quantity']").value),
        unitPrice: parseFloat(row.querySelector("input[data-field='unitPrice']").value),
        discount: parseFloat(row.querySelector("input[data-field='discount']").value)
    };

    console.log(JSON.stringify(orderItemDetail));

    // Kiểm tra các giá trị bắt buộc
    if (orderItemDetail.quantity <= 0 || orderItemDetail.unitPrice <= 0) {
        alert("Vui lòng nhập số lượng và đơn giá hợp lệ.");
        return;
    }

    // Gửi yêu cầu AJAX để lưu chi tiết orderItem
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-orderDetails', // Đảm bảo đường dẫn chính xác
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(orderItemDetail),
        cache: false,
        success: function (response) {
            alert("Cập nhật chi tiết đơn hàng thành công!");
            // Thêm logic cập nhật giao diện nếu cần thiết
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật chi tiết đơn hàng:", error);
            alert("Không thể cập nhật chi tiết đơn hàng. Vui lòng thử lại.");
        }
    });
}


