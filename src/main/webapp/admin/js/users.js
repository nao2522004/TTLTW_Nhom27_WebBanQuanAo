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

/*--------------------------------------------------------
---------------------------------------------------------

                       Add Users

---------------------------------------------------------
----------------------------------------------------------*/

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
            fetchUsers()
        })
        .catch(err => {
            console.error('Error:', err);
            alert(err.message || 'Không thể tạo người dùng. Vui lòng thử lại.');
        });
}
