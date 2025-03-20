/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Product

---------------------------------------------------------
----------------------------------------------------------*/

//---------------Product Data--------------------//
// Lấy danh sách sản phẩm từ server
function fetchProducts() {
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-products',
        type: 'GET',
        dataType: 'json',
        success: function (products) {
            const table = $("#products--table");

            // Xóa DataTables nếu đã được khởi tạo trước đó
            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy(); // Hủy DataTables
                table.find("tbody").empty(); // Xóa dữ liệu cũ
            }

            // Thêm tbody mới vào bảng
            const tbody = buildTableProduct(products);
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
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi lấy danh sách sản phẩm:', error);
            alert("Không thể lấy danh sách sản phẩm. Vui lòng thử lại sau.");
        }
    });
}

// Tạo bảng từ danh sách sản phẩm
const buildTableProduct = (products) => {
    const tbody = document.createElement("tbody");

    let productContent = "";
    for (const product of products) {
        let typeIdOptionsForId = `
            <select disabled>
                <option value="1" ${product.typeId === 1 ? 'selected' : ''}>Áo</option>
                <option value="2" ${product.typeId === 2 ? 'selected' : ''}>Quần</option>
            </select>
        `;

        let supplierOptionsForId = `
            <select disabled>
                <option value="1" ${product.supplierId === 1 ? 'selected' : ''}>PEALO</option>
                <option value="2" ${product.supplierId === 2 ? 'selected' : ''}>B Brown Studio</option>
                <option value="3" ${product.supplierId === 3 ? 'selected' : ''}>BBRAND</option>
                <option value="4" ${product.supplierId === 4 ? 'selected' : ''}>RUYCH STUDIO</option>
            </select>
        `;

        let categoryOptionsForId = `
            <select disabled>
                <option value="1" ${product.categoryId === 1 ? 'selected' : ''}>Áo nam</option>
                <option value="2" ${product.categoryId === 2 ? 'selected' : ''}>Áo Nữ</option>
                <option value="3" ${product.categoryId === 3 ? 'selected' : ''}>Áo trẻ em</option>
                <option value="4" ${product.categoryId === 4 ? 'selected' : ''}>Quần nam</option>
                <option value="5" ${product.categoryId === 5 ? 'selected' : ''}>Quần nữ</option>
                <option value="6" ${product.categoryId === 6 ? 'selected' : ''}>Quần trẻ em</option>
            </select>
        `;

        let statusText = "";
        switch (product.status) {
            case 0: statusText = "Đã xóa"; break;
            case 1: statusText = "Còn"; break;
            case 2: statusText = "Hết"; break;
        }

        productContent += `
        <tr>
          <td>${product.id}</td>
          <td>${typeIdOptionsForId}</td>
          <td>${categoryOptionsForId}</td>
          <td>${supplierOptionsForId}</td>
          <td>${product.name}</td>
          <td>${product.description}</td>
          <td>${product.releaseDate}</td>
          <td>${product.unitSold}</td>
          <td>${product.unitPrice}</td>
          <td>${statusText}</td>
          <td class="primary">
            <span onclick="openEditProductPopup(event)" class="material-icons-sharp" data-product-id="${product.id}"> edit </span>
            <span onclick="deleteProduct(event)" class="material-icons-sharp" data-product-id="${product.id}"> delete </span>
            <span onclick="openProductDetails(event)" class="material-icons-sharp" data-productId-details="${product.id}"> info </span>
          </td>
        </tr>
      `;
    }

    tbody.innerHTML = productContent;

    return tbody;
};

// Khi DOM load, gọi fetchProducts để tải dữ liệu và khởi tạo DataTables
document.addEventListener('DOMContentLoaded', fetchProducts);

// Hàm xóa sản phẩm
function deleteProduct(event) {
    const productId = parseInt(event.target.getAttribute("data-product-id"), 10); // Chuyển đổi thành int
    console.log(JSON.stringify({id: productId}));

    if (isNaN(productId)) { // Kiểm tra xem productId có phải là số hợp lệ không
        alert("ID sản phẩm không hợp lệ.");
        return;
    }

    if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm ID: ${productId}?`)) {
        $.ajax({
            url: `/WebBanQuanAo/admin/manager-products?id=${productId}`,
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({id: productId}), // Gửi ID sản phẩm dưới dạng JSON
            cache: false,
            success: function (response) {
                alert(response.message || "Xóa sản phẩm thành công!");
                fetchProducts(); // Refresh danh sách sản phẩm sau khi xóa
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi xóa sản phẩm:', error);
                alert(xhr.responseJSON?.message || "Không thể xóa sản phẩm. Vui lòng thử lại sau.");
            }
        });
    }
}


//---------------Edit sản phẩm--------------------//
function openEditProductPopup(event) {
    const productId = event.target.getAttribute("data-product-id");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block";

    // Gửi yêu cầu AJAX để lấy dữ liệu sản phẩm theo ID
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-products', type: 'GET', data: {id: productId}, success: function (data) {
            // Điền dữ liệu sản phẩm vào các trường trong form
            document.getElementById("edit-idProduct").value = data.id;
            document.getElementById("edit-typeId").value = data.typeId;
            document.getElementById("edit-categoryId").value = data.categoryId;
            document.getElementById("edit-supplierId").value = data.supplierId;
            document.getElementById("edit-name").value = data.name;
            document.getElementById("edit-description").value = data.description;

            // Đoạn mã trong openEditProductPopup
            const releaseDate = new Date(data.releaseDate);
            const formattedDate = releaseDate.getFullYear() + '-' + ('0' + (releaseDate.getMonth() + 1)).slice(-2) + '-' + ('0' + releaseDate.getDate()).slice(-2);

            document.getElementById("edit-releaseDate").value = formattedDate;


            document.getElementById("edit-unitSold").value = data.unitSold;
            document.getElementById("edit-unitPrice").value = data.unitPrice;
            document.getElementById("edit-statusProduct").value = data.status;
        }, error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu sản phẩm:", error);
            alert("Không thể lấy thông tin sản phẩm. Vui lòng thử lại.");
        }
    });
}

function saveProductEdits(event) {
    // Ngăn hành vi submit mặc định của form
    event.preventDefault();

    // Thu thập dữ liệu từ các trường nhập liệu
    const releaseDateRaw = document.getElementById("edit-releaseDate").value;

    // Chỉ lấy giá trị ngày mà không sử dụng toISOString để tránh lỗi múi giờ
    const releaseDate = releaseDateRaw.split('T')[0]; // Chỉ lấy phần ngày (yyyy-MM-dd)

    const product = {
        id: parseInt(document.getElementById("edit-idProduct").value),
        typeId: parseInt(document.getElementById("edit-typeId").value),
        categoryId: parseInt(document.getElementById("edit-categoryId").value),
        supplierId: parseInt(document.getElementById("edit-supplierId").value),
        name: document.getElementById("edit-name").value,
        description: document.getElementById("edit-description").value,
        releaseDate: releaseDate, // Gắn giá trị đã định dạng
        unitSold: parseInt(document.getElementById("edit-unitSold").value),
        unitPrice: parseFloat(document.getElementById("edit-unitPrice").value).toFixed(2),
        status: parseInt(document.getElementById("edit-statusProduct").value),
    };

    // Chuyển đổi đối tượng `product` thành JSON
    const productJson = JSON.stringify(product);

    // Gửi yêu cầu AJAX với JSON
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-products', type: 'PUT', contentType: 'application/json', // Định dạng dữ liệu gửi đi là JSON
        data: productJson, // Gửi JSON object
        success: function (response) {
            alert("Cập nhật thông tin sản phẩm thành công!");
            fetchProducts(); // Tải lại danh sách sản phẩm
            hideOverlay(); // Ẩn overlay
        }, error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật thông tin sản phẩm:", error);
            alert("Không thể cập nhật thông tin sản phẩm. Vui lòng kiểm tra lại dữ liệu và thử lại.");
        }
    });
}

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

                       Add Product

---------------------------------------------------------
----------------------------------------------------------*/

// Hiển thị form thêm người dùng
function showAddProductForm() {
    const overlay = document.querySelector(".overlay-addProduct");
    overlay.style.display = "flex"; // Hiển thị lớp phủ
}


function hideAddProductForm() {
    const overlay = document.querySelector(".overlay-addProduct");
    const form = document.getElementById("add-product-form");
    form.reset(); // Xóa dữ liệu trong form
    overlay.style.display = "none";
}


function createProduct(event) {
    event.preventDefault();

    const form = document.getElementById('add-product-form');
    const formData = new FormData(form);

    const productData = Object.fromEntries(formData.entries());

    // Chuyển các giá trị của các select thành số (parseInt)
    productData.typeId = parseInt(productData.typeId);
    productData.categoryId = parseInt(productData.categoryId);
    productData.supplierId = parseInt(productData.supplierId);
    productData.unitSold = parseInt(productData.unitSold);
    productData.unitPrice = parseFloat(productData.unitPrice);
    productData.status = productData.status === 'true'; // Trạng thái là boolean

    console.log(JSON.stringify(productData));

    fetch('/WebBanQuanAo/admin/manager-products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    })
        .then(response => {
            if (response.ok) return response.json();
            return response.json().then(err => Promise.reject(err));
        })
        .then(data => {
            alert(data.message || 'Sản phẩm đã được tạo thành công!');
            fetchProducts();
            hideAddProductForm()
        })
        .catch(err => {
            console.error('Lỗi:', err);
            alert(err.message || 'Không thể tạo sản phẩm. Vui lòng thử lại.');
        });
}

window.addEventListener("DOMContentLoaded", hideAddProductForm);

// Xử lý sự kiện click ra ngoài form để ẩn form
document.addEventListener("click", function(event) {
    const overlay = document.querySelector(".overlay-addProduct");
    const form = document.getElementById("add-product-form");

    // Kiểm tra nếu form đang hiển thị và click không nằm trong form hoặc nút hiển thị form
    if (overlay.style.display === "flex" &&
        !form.contains(event.target) &&
        !event.target.closest("button")) {
        hideAddProductForm();
    }
});

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
