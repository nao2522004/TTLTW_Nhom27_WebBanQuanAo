
function openProductDetails(event) {
    const productId = event.target.getAttribute("data-productId-details");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay-productDetails");
    overlay.style.display = "block";

    // Gửi yêu cầu AJAX để lấy dữ liệu chi tiết sản phẩm theo productId
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-productDetails',
        type: 'GET',
        data: {id: productId},
        success: function (data) {
            console.log(JSON.stringify(data));  // In ra dữ liệu nhận được từ server

            if (data && data.length > 0) {
                let productDetailListContent = '';

                // Duyệt qua tất cả các chi tiết sản phẩm và tạo HTML để hiển thị
                data.forEach(productDetail => {
                    productDetailListContent += `
                    <tr>
                        <td><input type="number" value="${productDetail.id}" data-field="id" disabled></td>
                        <td><input type="number" value="${productDetail.productId}" data-field="productId" disabled></td>
                        <td><input type="text" value="${productDetail.size}" class="editable" data-id="${productDetail.id}" data-field="size"></td>
                        <td><input type="number" value="${productDetail.stock}" class="editable" data-id="${productDetail.id}" data-field="stock"></td>
                        <td><input type="text" value="${productDetail.image}" class="editable" data-id="${productDetail.id}" data-field="image"></td>
                        <td><input type="text" value="${productDetail.color}" class="editable" data-id="${productDetail.id}" data-field="color"></td>
                        <td><span onclick="saveProductDetailEdits(event)" class="primary material-icons-sharp">save</span></td>
                    </tr>
                   `;

                });

                // Điền dữ liệu vào phần <tbody> của bảng
                const tableBody = document.querySelector("#product-details--table tbody");
                tableBody.innerHTML = productDetailListContent;

                // Thêm sự kiện để chỉnh sửa các trường thông tin
                const editableFields = document.querySelectorAll(".editable");
                editableFields.forEach(field => {
                    field.addEventListener("change", handleFieldChange);
                });
            } else {
                alert("Không có chi tiết sản phẩm nào.");
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu chi tiết sản phẩm:", error);
            alert("Không thể lấy thông tin chi tiết sản phẩm. Vui lòng thử lại.");
        }
    });
}


function saveProductDetailEdits(event) {
    event.preventDefault()

    // Lấy hàng chứa nút "save" được nhấn
    const row = event.target.closest("tr");

    // Lấy các giá trị từ các trường trong hàng
    const productDetail = {
        id: parseInt(row.querySelector("input[data-field='id']").value),
        productId: parseInt(row.querySelector("input[data-field='productId']").value),
        size: row.querySelector("input[data-field='size']").value,
        stock: parseInt(row.querySelector("input[data-field='stock']").value),
        image: row.querySelector("input[data-field='image']").value,
        color: row.querySelector("input[data-field='color']").value
    };

    console.log(JSON.stringify(productDetail));

    // Kiểm tra các giá trị bắt buộc
    if (!productDetail.size || !productDetail.color) {
        alert("Vui lòng điền đầy đủ thông tin kích thước và màu sắc.");
        return;
    }

    // Gửi yêu cầu AJAX để lưu chi tiết sản phẩm
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-productDetails', // Đảm bảo đường dẫn chính xác
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(productDetail),
        success: function (response) {
            alert("Cập nhật chi tiết sản phẩm thành công!");
            // Thêm logic cập nhật giao diện nếu cần thiết
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật chi tiết sản phẩm:", error);
            alert("Không thể cập nhật chi tiết sản phẩm. Vui lòng thử lại.");
        }
    });
}

/*--------------------------------------------------------
---------------------------------------------------------

                       Add Product Details

---------------------------------------------------------
----------------------------------------------------------*/
function showAddProductDetailsForm() {
    const overlay = document.querySelector(".overlay-addproductDetails");
    overlay.style.display = "flex"; // Hiển thị lớp phủ
}


function hideAddProductDetailsForm() {
    const overlay = document.querySelector(".overlay-addproductDetails");
    const form = document.getElementById("add-productDetails-form");
    form.reset(); // Xóa dữ liệu trong form
    overlay.style.display = "none";
}


// Thêm chi tiết sản phẩm mới
function createProductDetails(event) {
    event.preventDefault();

    const form = document.getElementById('add-productDetails-form');
    const formData = new FormData(form);

    const productDetails = Object.fromEntries(formData.entries());

    // Chuyển các giá trị của các input thành đúng kiểu dữ liệu (parseInt, parseFloat, hoặc boolean)
    productDetails.productId = parseInt(productDetails.productId);
    productDetails.size = productDetails.size;
    productDetails.stock = parseInt(productDetails.stock);
    productDetails.color = productDetails.color;
    productDetails.image = productDetails.image.trim();

    console.log(JSON.stringify(productDetails));

    fetch('/WebBanQuanAo/admin/manager-productDetails', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productDetails)
    })
        .then(response => {
            if (response.ok) return response.json();
            return response.json().then(err => Promise.reject(err));
        })
        .then(data => {
            alert(data.message || 'Chi tiết sản phẩm đã được tạo thành công!');
            fetchProducts()
            hideAddProductDetailsForm()
        })
        .catch(err => {
            console.error('Lỗi:', err);
            alert(err.message || 'Không thể tạo chi tiết sản phẩm. Vui lòng thử lại.');
        });
}

window.addEventListener("DOMContentLoaded", hideAddProductDetailsForm);

// Xử lý sự kiện click ra ngoài form để ẩn form
document.addEventListener("click", function(event) {
    const overlay = document.querySelector(".overlay-addproductDetails");
    const form = document.getElementById("add-productDetails-form");

    // Kiểm tra nếu form đang hiển thị và click không nằm trong form hoặc nút hiển thị form
    if (overlay.style.display === "flex" &&
        !form.contains(event.target) &&
        !event.target.closest("button")) {
        hideAddProductDetailsForm();
    }
});
