// =============== Xử lý sự kiện showMain cho menu============//
document.addEventListener("DOMContentLoaded", function () {
    // Hiển thị phần tử main đầu tiên khi tải trang
    const dashboard = document.getElementById("dashboard");
    if (dashboard) {
        // Đảm bảo rằng chỉ có dashboard được hiển thị ban đầu
        dashboard.classList.add("block");
    }

    // Ẩn các phần tử main khác nếu có
    document.querySelectorAll("main").forEach((main) => {
        if (main.id !== "dashboard") {
            main.classList.remove("block");
        }
    });
});


function showMain(event, mainId) {
    event.preventDefault();

    // Xóa class active khỏi tất cả các thẻ a
    document.querySelectorAll("aside .sidebar a").forEach((link) => {
        link.classList.remove("active");
    });

    // Thêm class active vào thẻ a được nhấn
    event.currentTarget.classList.add("active");

    // Ẩn tất cả các thẻ main
    document.querySelectorAll("main").forEach((main) => {
        main.classList.remove("block");
    });

    // Hiển thị thẻ main được chọn
    const targetMain = document.getElementById(mainId);
    if (targetMain) {
        targetMain.classList.add("block");
    }
}

// Executes when document is loaded
document.addEventListener("DOMContentLoaded", (ev) => {

    // Orders Data
    document.getElementById("orders--table").appendChild(buildTableOrder());
    document
        .getElementById("orders-details--table")
        .appendChild(buildTableOrderDetails());

    // Others Data
    document.getElementById("sales--table").appendChild(buildTableSales());
    document
        .getElementById("collections--table")
        .appendChild(buildTableCollections());
    // edit Sales
    document
        .getElementById("sale_edit--table")
        .appendChild(buildEditTableSales());
    // edit Collections
    document
        .getElementById("collection_edit--table")
        .appendChild(buildEditTableCollections());

    // Updates Data
    document
        .getElementsByClassName("recent-updates")
        .item(0)
        .appendChild(buildUpdatesList());

    // Sales Analytics
    const salesAnalytics = document.getElementById("analytics");
    buildSalesAnalytics(salesAnalytics);
});

/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Orders

---------------------------------------------------------
----------------------------------------------------------*/

// ===============Recent_oder_data===============//
const buildTableBody = () => {
    const recentOrderData = RECENT_ORDER_DATA;

    const tbody = document.createElement("tbody");

    let bodyContent = "";
    for (const row of recentOrderData) {
        bodyContent += `
       <tr>
          <td>${row.orderId}</td>
          <td>${row.userName}</td>
          <td>${row.userPhone}</td>
          <td>${row.userAdress}</td>
          <td>${row.creationDate}</td>
          <td>${row.totalPrice}</td>
          <td>${row.payment}</td>
          <td class="${row.statusColor}">${row.status}</td>
          <td class="primary" onclick="showOverlay(event)">Chi Tiết</td>
        </tr>
      `;
    }

    tbody.innerHTML = bodyContent;

    return tbody;
};

// ===============Oder_data===============//
const buildTableOrder = () => {
    const orderData = ORDER_DATA;

    const tbody = document.createElement("tbody");

    let OrderContent = "";
    for (const row of orderData) {
        OrderContent += `
        <tr>
          <td>${row.orderId}</td>
          <td>${row.userName}</td>
          <td>${row.userPhone}</td>
          <td>${row.userAdress}</td>
          <td>${row.creationDate}</td>
          <td>${row.totalPrice}</td>
          <td>${row.payment}</td>
          <td class="${row.statusColor}">${row.status}</td>
          <td class="primary" onclick="showOverlay(event)">Chi Tiết</td>
        </tr>
      `;
    }

    tbody.innerHTML = OrderContent;

    return tbody;
};

// ===============Order details data===============//
const buildTableOrderDetails = () => {
    const orderDetaisData = ORDER_DETAILS_DATA;

    const tbody = document.createElement("tbody");

    let OrderDetaisContent = "";
    for (const row of orderDetaisData) {
        OrderDetaisContent += `
        <tr>
          <td>${row.proImg}</td>
          <td>${row.proId}</td>
          <td>${row.proName}</td>
          <td>${row.proSize}</td>
          <td>${row.proColor}</td>
          <td>${row.proAmount}</td>
          <td>${row.unitPrice}</td>
        </tr>
      `;
    }

    tbody.innerHTML = OrderDetaisContent;

    return tbody;
};

/*--------------------------------------------------------
---------------------------------------------------------

                       Manager User

---------------------------------------------------------
----------------------------------------------------------*/

/// Lấy danh sách người dùng từ server và khởi tạo DataTables
function fetchUsers() {
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-users',
        type: 'GET',
        dataType: 'json',
        success: function (users) {
            const table = $("#users--table");

            // Xóa DataTables nếu đã được khởi tạo trước đó
            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy(); // Hủy DataTables
                table.find("tbody").empty(); // Xóa dữ liệu cũ
            }

            // Thêm dữ liệu mới vào bảng
            const tbody = buildTableUser(users);
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
                pageLength: 3, // Số bản ghi mỗi trang
                lengthChange: true, // Kích hoạt thay đổi số lượng bản ghi mỗi trang
            });
        },
        error: function (xhr, status, error) {
            console.error('Error fetching users:', error);
            alert("Failed to fetch users. Please try again later.");
        }
    });
}

// Tạo tbody từ danh sách người dùng
const buildTableUser = (users) => {
    let userContent = "";
    for (const user of users) {
        userContent += `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.userName}</td>
                    <td>${user.firstName}</td>
                     <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.avatar}</td>   
                    <td>${user.address}</td>
                    <td>${user.phone}</td>
                    <td>${user.createdAt}</td>
                    <td>${user.status === 1 ? 'Hoạt Động' : 'Không Hoạt Động'}</td>
                    <td>${user.roleId === 1 ? 'Admin' : 'User'}</td>
                    <td class="primary">
                        <span onclick="openEditPopup(event)" class="material-icons-sharp" data-username="${user.userName}"> edit </span>
                        <span onclick="deleteUser(event)" class="material-icons-sharp" data-username="${user.userName}"> delete </span>
                    </td>
                </tr>
            `;
    }
    return `<tbody>${userContent}</tbody>`;
};

// Khi DOM load, gọi fetchUsers để tải dữ liệu và khởi tạo DataTables
document.addEventListener('DOMContentLoaded', fetchUsers);



// Hàm xóa user
function deleteUser(event) {
    const userName = event.target.getAttribute("data-username");
    console.log(JSON.stringify({userName: userName}));
    if (confirm(`Bạn có chắc chắn muốn xóa người dùng: ${userName}?`)) {
        $.ajax({
            url: `/WebBanQuanAo/admin/manager-users?username=${userName}`,
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({userName: userName}), // Gửi username dưới dạng JSON
            success: function (response) {
                alert(response.message || "Xóa người dùng thành công!");
                fetchUsers(); // Refresh danh sách người dùng sau khi xóa
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi xóa người dùng:', error);
                alert(xhr.responseJSON?.message || "Không thể xóa người dùng. Vui lòng thử lại sau.");
            }
        });
    }
}


//---------------Edit user data--------------------//
function openEditPopup(event) {
    const userName = event.target.getAttribute("data-username");  // Lấy username từ thuộc tính data-username

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block"; // Hiển thị lớp phủ của main hiện tại

    // Gửi yêu cầu AJAX để lấy dữ liệu người dùng theo username
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-users', // Đảm bảo rằng URL này khớp với mapping của servlet
        type: 'GET', data: {username: userName},  // Gửi username dưới dạng tham số truy vấn
        success: function (data) {
            // Điền dữ liệu người dùng vào các trường trong form
            document.getElementById("edit-id").value = data.id;
            document.getElementById("edit-username").value = data.userName;
            document.getElementById("edit-lastName").value = data.lastName;
            document.getElementById("edit-firstName").value = data.firstName;
            document.getElementById("edit-email").value = data.email;
            document.getElementById("edit-address").value = data.address;
            document.getElementById("edit-phone").value = data.phone;
            document.getElementById("edit-createdDate").value = data.createdAt;
            document.getElementById("edit-status").value = data.status;
            document.getElementById("edit-role").value = data.roleId;
        }, error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu người dùng:", error);
            alert("Không thể lấy thông tin người dùng. Vui lòng thử lại.");
        }
    });
}


function saveUserEdits(event) {
    // Ngăn hành vi submit mặc định của form
    event.preventDefault();

    // Thu thập dữ liệu từ các trường nhập liệu
    const user = {
        id: parseInt(document.getElementById("edit-id").value),
        userName: document.getElementById("edit-username").value,
        firstName: document.getElementById("edit-firstName").value,
        lastName: document.getElementById("edit-lastName").value,
        email: document.getElementById("edit-email").value,
        avatar: document.getElementById("edit-avatar").value,
        address: document.getElementById("edit-address").value,
        phone: parseInt(document.getElementById("edit-phone").value),
        status: parseInt(document.getElementById("edit-status").value),
        createdAt: new Date(document.getElementById("edit-createdDate").value).toISOString(),
        roleId: parseInt(document.getElementById("edit-role").value)
    };

    // Chuyển đổi đối tượng `user` thành JSON
    const userJson = JSON.stringify(user);

    // Log để kiểm tra JSON đã tạo
    console.log("JSON object gửi đi:", userJson);

    // Gửi yêu cầu AJAX với JSON
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-users', type: 'PUT', contentType: 'application/json', // Định dạng dữ liệu gửi đi là JSON
        data: userJson, // Gửi JSON object
        success: function (response) {
            alert("Cập nhật thông tin người dùng thành công!");
            fetchUsers(); // Tải lại danh sách người dùng
            hideOverlay(); // Ẩn overlay
        }, error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật thông tin người dùng:", error);
            alert("Không thể cập nhật thông tin người dùng. Vui lòng kiểm tra lại dữ liệu và thử lại.");
        }
    });
}

function createUser(event) {
    event.preventDefault()

    const form = document.getElementById('add-user-form');
    const formData = new FormData(form);

    const userData = Object.fromEntries(formData.entries());

    fetch('/WebBanQuanAo/admin/manager-users', {
        method: 'POST', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(userData)
    })
        .then(response => {
            if (response.ok) return response.json();
            return response.json().then(err => Promise.reject(err));
        })
        .then(data => {
            alert(data.message || 'Người dùng đã được tạo thành công!');
        })
        .catch(err => {
            console.error('Error:', err);
            alert(err.message || 'Không thể tạo người dùng. Vui lòng thử lại.');
        });
}

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

    console.log(JSON.stringify(products));

    let productContent = "";
    for (const product of products) {
        let typeIdOptionsForId = `
            <select>
                <option value="1" ${product.typeId === 1 ? 'selected' : ''}>Áo</option>
                <option value="2" ${product.typeId === 2 ? 'selected' : ''}>Quần</option>
            </select>
        `;

        let supplierOptionsForId = `
            <select>
                <option value="1" ${product.supplierId === 1 ? 'selected' : ''}>PEALO</option>
                <option value="2" ${product.supplierId === 2 ? 'selected' : ''}>B Brown Studio</option>
                <option value="3" ${product.supplierId === 3 ? 'selected' : ''}>BBRAND</option>
                <option value="4" ${product.supplierId === 4 ? 'selected' : ''}>RUYCH STUDIO</option>
            </select>
        `;

        let categoryOptionsForId = `
            <select>
                <option value="1" ${product.categoryId === 1 ? 'selected' : ''}>Áo nam</option>
                <option value="2" ${product.categoryId === 2 ? 'selected' : ''}>Áo Nữ</option>
                <option value="3" ${product.categoryId === 3 ? 'selected' : ''}>Áo trẻ em</option>
                <option value="4" ${product.categoryId === 4 ? 'selected' : ''}>Quần nam</option>
                <option value="5" ${product.categoryId === 5 ? 'selected' : ''}>Quần nữ</option>
                <option value="6" ${product.categoryId === 6 ? 'selected' : ''}>Quần trẻ em</option>
            </select>
        `;

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
          <td>${product.status ? 'Còn' : 'Hết'}</td>
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
        status: document.getElementById("edit-statusProduct").value === "true",
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



// Thêm sản phẩm mới
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
        })
        .catch(err => {
            console.error('Lỗi:', err);
            alert(err.message || 'Không thể tạo sản phẩm. Vui lòng thử lại.');
        });
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
        })
        .catch(err => {
            console.error('Lỗi:', err);
            alert(err.message || 'Không thể tạo chi tiết sản phẩm. Vui lòng thử lại.');
        });
}


/*--------------------------------------------------------
---------------------------------------------------------

                       Manager Others

---------------------------------------------------------
----------------------------------------------------------*/

//===============Sales Data==================//
const buildTableSales = () => {
    const sales = SALES_DATA;

    const tbody = document.createElement("tbody");

    let SalesContent = "";
    for (const row of sales) {
        SalesContent += `
        <tr>
          <td>${row.saleid}</td>
          <td>${row.salename}</td>
          <td>${row.start_date}</td>
          <td>${row.end_date}</td>
          <td>${row.sale_decrip}</td>
          <td class="primary"> <span onclick="showOverlay(event)" class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
    }

    tbody.innerHTML = SalesContent;

    return tbody;
};

//===============Collection Data==================//
const buildTableCollections = () => {
    const collections = COLLECTIONS_DATA;

    const tbody = document.createElement("tbody");

    let CollectionsContent = "";
    for (const row of collections) {
        CollectionsContent += `
        <tr>
          <td>${row.coid}</td>
          <td>${row.coname}</td>
          <td>${row.start_date}</td>
          <td>${row.co_decrip}</td>
          <td class="primary"> <span onclick="showOverlay(event)" class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
    }

    tbody.innerHTML = CollectionsContent;

    return tbody;
};

//=============== Edit Sales Data==================//
const buildEditTableSales = () => {
    const salesEdit = SALES_DATA;

    const tbody = document.createElement("tbody");

    let SalesEditContent = "";
    for (const [index, row] of salesEdit.entries()) {
        SalesEditContent += `
        <tr data-index="${index}">
          <td>${row.saleid}</td>
          <td><input type="text" value="${row.salename}" data-key="salename" /></td>
          <td><input type="date" value="${row.start_date}" data-key="start_date" /></td>
          <td><input type="date" value="${row.end_date}" data-key="end_date" /></td>
          <td><input type="text" value="${row.sale_decrip}" data-key="sale_decrip" /></td>
          <td class="primary"> 
            <span onclick="saveOthers(event)" class="material-icons-sharp"> save </span> 
          </td>
        </tr>
      `;
    }

    tbody.innerHTML = SalesEditContent;

    return tbody;
};

//===============Edit Collection Data==================//
const buildEditTableCollections = () => {
    const collectionsEdit = COLLECTIONS_DATA;

    const tbody = document.createElement("tbody");

    let CollectionsEditContent = "";
    for (const [index, row] of collectionsEdit.entries()) {
        CollectionsEditContent += `
        <tr data-index="${index}">
          <td>${row.coid}</td>
          <td><input type="text" value="${row.coname}" data-key="coname" /></td>
          <td><input type="date" value="${row.start_date}" data-key="start_date" /></td>
          <td><input type="text" value="${row.co_decrip}" data-key="co_decrip" /></td>
          <td class="primary"> 
            <span onclick="saveOthers(event)" class="material-icons-sharp"> save </span> 
          </td>
        </tr>
      `;
    }

    tbody.innerHTML = CollectionsEditContent;

    return tbody;
};


//---------------Save edit sale and collection data--------------------//
function saveOthers(event) {
    const row = event.target.closest("tr"); // Lấy hàng hiện tại
    const index = row.getAttribute("data-index"); // Lấy chỉ số hàng
    const inputs = row.querySelectorAll("[data-key]"); // Lấy tất cả các input/select trong hàng

    // Cập nhật lại USER_DATA
    inputs.forEach((input) => {
        const key = input.getAttribute("data-key");
        SALES_DATA[index][key] = input.value;
        COLLECTIONS_DATA[index][key] = input.value;
    });

    alert("Thông tin đã được lưu!");
}

// =============================================== //
//================================================//
//================================================//
//================================================//
const buildUpdatesList = () => {
    const updateData = UPDATE_DATA;

    const div = document.createElement("div");
    div.classList.add("updates");

    let updateContent = "";
    for (const update of updateData) {
        updateContent += `
        <div class="update">
          <div class="profile-photo">
            <img src="${update.imgSrc}" />
          </div>
          <div class="message">
            <p><b>${update.profileName}</b> ${update.message}</p>
            <small class="text-muted">${update.updatedTime}</small>
          </div>
        </div>
      `;
    }

    div.innerHTML = updateContent;

    return div;
};

const buildSalesAnalytics = (element) => {
    const salesAnalyticsData = SALES_ANALYTICS_DATA;

    for (const analytic of salesAnalyticsData) {
        const item = document.createElement("div");
        item.classList.add("item");
        item.classList.add(analytic.itemClass);

        const itemHtml = `
        <div class="icon">
          <span class="material-icons-sharp"> ${analytic.icon} </span>
        </div>
        <div class="right">
          <div class="info">
            <h3>${analytic.title}</h3>
            <small class="text-muted"> Last 24 Hours </small>
          </div>
          <h5 class="${analytic.colorClass}">${analytic.percentage}%</h5>
          <h3>${analytic.sales}</h3>
        </div>
      `;

        item.innerHTML = itemHtml;

        element.appendChild(item);
    }
};

// Document operation functions
const sideMenu = document.querySelector("aside");
const menuBtn = document.querySelector("#menu-btn");
const closeBtn = document.querySelector("#close-btn");
const themeToggler = document.querySelector(".theme-toggler");

// Show Sidebar
menuBtn.addEventListener("click", () => {
    sideMenu.style.display = "block";
});

// Hide Sidebar
closeBtn.addEventListener("click", () => {
    sideMenu.style.display = "none";
});

// Change Theme
themeToggler.addEventListener("click", () => {
    document.body.classList.toggle("dark-theme-variables");

    themeToggler.querySelector("span:nth-child(1)").classList.toggle("active");
    themeToggler.querySelector("span:nth-child(2)").classList.toggle("active");
});


//========== start Xu ly su kien cho order detail ============//
function hideOverlay(event) {
    const overlay = event.target.closest("main").querySelector(".overlay");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function hideOverlayProductDetails(event) {
    const overlay = event.target.closest("main").querySelector(".overlay-productDetails");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function showOverlay(event) {
    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block"; // Hiển thị overlay của main hiện tại
}

//========== end Xu ly su kien cho order detail ============//
