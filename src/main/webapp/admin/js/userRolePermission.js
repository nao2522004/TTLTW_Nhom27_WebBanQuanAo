function openRolePermissionUser(event) {
    const userName = event.target.getAttribute("data-username");
    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay-userRolePermissons");
    overlay.style.display = "block";

    $.ajax({
        url: '/WebBanQuanAo/admin/manager-userRolePermissions',
        type: 'GET',
        data: { userName: userName },
        cache: false,
        success: function(data) {
            console.log("Dữ liệu vai trò/quyền:", JSON.stringify(data));

            if (data && data.length > 0) {
                const user = data[0];
                const tbody = document.querySelector("#userRolePermissons--table tbody");

                // Hiển thị thông tin cơ bản
                tbody.innerHTML = `
                <tr>
                    <td style="width: 125px"><input type="text" value="${user.userName}" disabled></td>
                    <td style="width: 125px"><input type="text" value="${user.firstName}" disabled></td>
                    <td>
                        <div style="width: 150px; margin-right: 10px"" class="roles-container">
                            ${renderRoles(user.roles)}
                        </div>
                    </td>
                    <td>
                        <div class="permissions-container">
                            ${renderPermissions(user.permissions)}
                        </div>
                    </td>
                    <td>
                        <span onclick="toggleEditRolePermission(event)" class="material-icons-sharp">edit</span>
                        <span onclick="saveUserRolePermission(event)" class="material-icons-sharp" style="display:none">save</span>
                    </td>
                </tr>
                `;
            }
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi lấy vai trò/quyền người dùng:", error);
            alert("Không thể lấy thông tin. Vui lòng thử lại.");
        }
    });
}

function renderRoles(roles) {
    const allRoles = ['ADMIN', 'STAFF', 'USER'];

    let html = '<table class="roles-table">';
    html += '<tr><th>Vai trò</th><th>Chọn</th></tr>';

    allRoles.forEach(role => {
        html += `
        <tr>
            <td>${role}</td>
            <td>
                <input type="checkbox" value="${role}" ${roles.includes(role) ? 'checked' : ''} disabled>
            </td>
        </tr>
        `;
    });

    html += '</table>';

    return html;
}

// Chuyển đổi giữa chế độ chỉnh sửa và chế độ lưu
function toggleEditRolePermission(event) {
    const row = event.target.closest("tr");

    // Mở khóa checkbox role
    const roleCheckboxes = row.querySelectorAll(".roles-table input[type='checkbox']");
    roleCheckboxes.forEach(cb => cb.disabled = false);

    // Mở khóa checkbox permission
    const permissionCheckboxes = row.querySelectorAll(".permissions-table input[type='checkbox']");
    permissionCheckboxes.forEach(cb => cb.disabled = false);

    // Nút Save/Edit
    const saveBtn = row.querySelector("span[onclick='saveUserRolePermission(event)']");
    const editBtn = row.querySelector("span[onclick='toggleEditRolePermission(event)']");
    saveBtn.style.display = "inline";
    editBtn.style.display = "none";
}