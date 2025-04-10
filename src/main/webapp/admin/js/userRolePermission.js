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
                    <td><input type="text" value="${user.userName}" disabled></td>
                    <td><input type="text" value="${user.firstName}" disabled></td>
                    <td>
                        <div class="role-tags-container">
                            ${renderRoleTags(user.roles)}
                        </div>
                        <select class="role-select" multiple style="display: none;">
                            <option value="ADMIN" ${user.roles.includes('ADMIN') ? 'selected' : ''}>Admin</option>
                            <option value="STAFF" ${user.roles.includes('STAFF') ? 'selected' : ''}>Staff</option>
                            <option value="USER" ${user.roles.includes('USER') ? 'selected' : ''}>User</option>
                        </select>
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

function renderRoleTags(roles) {
    const roleNames = {
        'ADMIN': 'Admin',
        'STAFF': 'Nhân viên',
        'USER': 'Người dùng'
    };

    return roles.map(role => `
        <span class="role-tag" data-role="${role}">
            ${roleNames[role] || role}
            <span class="remove-role" onclick="removeRole(event)">×</span>
        </span>
    `).join('');
}

function renderPermissions(permissions) {
    let html = '<table class="permissions-table">';

    // Header
    html += '<tr><th>Tài nguyên</th><th>Đọc</th><th>Ghi</th><th>Thực thi</th></tr>';

    // Mỗi dòng cho một resource
    for (const [resource, permissionValue] of Object.entries(permissions)) {
        html += `
        <tr>
            <td>${resource}</td>
            <td><input type="checkbox" ${(permissionValue & 1) ? 'checked' : ''} 
                 data-resource="${resource}" data-permission="read"></td>
            <td><input type="checkbox" ${(permissionValue & 2) ? 'checked' : ''} 
                 data-resource="${resource}" data-permission="write"></td>
            <td><input type="checkbox" ${(permissionValue & 4) ? 'checked' : ''} 
                 data-resource="${resource}" data-permission="execute"></td>
        </tr>
        `;
    }

    return html + '</table>';
}

function toggleEditRolePermission(event) {
    const row = event.target.closest("tr");
    const tagsContainer = row.querySelector(".role-tags-container");
    const select = row.querySelector(".role-select");

    // Toggle giữa chế độ xem và chỉnh sửa
    if (tagsContainer.style.display === 'none') {
        tagsContainer.style.display = '';
        select.style.display = 'none';
        event.target.textContent = 'edit';
        row.querySelector("span[onclick='saveUserRolePermission']").style.display = 'none';
    } else {
        tagsContainer.style.display = 'none';
        select.style.display = 'block';
        event.target.textContent = 'cancel';
        row.querySelector("span[onclick='saveUserRolePermission']").style.display = 'inline';
    }
}

function removeRole(event) {
    event.stopPropagation();
    const roleTag = event.target.closest(".role-tag");
    const role = roleTag.getAttribute("data-role");
    const select = roleTag.closest("tr").querySelector(".role-select");

    // Bỏ chọn option tương ứng
    const option = select.querySelector(`option[value="${role}"]`);
    if (option) option.selected = false;

    roleTag.remove();
}