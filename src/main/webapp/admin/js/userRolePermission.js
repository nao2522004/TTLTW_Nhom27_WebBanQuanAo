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

function renderPermissions(permissions) {
    let html = '<table class="permissions-table">';

    // Header
    html += '<tr><th>Tài nguyên</th><th>Read</th><th>Write</th><th>Execute</th></tr>';

    // Mỗi dòng cho một resource
    for (const [resource, permissionValue] of Object.entries(permissions)) {
        // Kiểm tra từng bit để xác định quyền
        const canRead = (permissionValue & 1) !== 0;    // Bit thứ 1 (1)
        const canWrite = (permissionValue & 2) !== 0;   // Bit thứ 2 (2)
        const canExecute = (permissionValue & 4) !== 0; // Bit thứ 3 (4)

        html += `
        <tr>
            <td>${resource}</td>
            <td><input type="checkbox" ${canRead ? 'checked' : ''} disabled
                 data-resource="${resource}" data-permission="Read"></td>
            <td><input type="checkbox" ${canWrite ? 'checked' : ''} disabled
                 data-resource="${resource}" data-permission="Write"></td>
            <td><input type="checkbox" ${canExecute ? 'checked' : ''} disabled
                 data-resource="${resource}" data-permission="Execute"></td>
        </tr>
        `;
    }

    return html + '</table>';
}

function saveUserRolePermission(event) {
    const row = event.target.closest("tr");
    const userName = row.querySelector("td:first-child input").value;

    // Lấy danh sách role được chọn
    const roles = Array.from(row.querySelectorAll(".roles-table input[type='checkbox']:checked"))
        .map(cb => cb.value);

    // Thu thập permissions từ các checkbox
    const permissions = {};
    const checkboxes = row.querySelectorAll(".permissions-table input[type='checkbox']");
    checkboxes.forEach(checkbox => {
        const resource = checkbox.getAttribute("data-resource");
        const permissionType = checkbox.getAttribute("data-permission");

        if (!permissions[resource]) {
            permissions[resource] = 0;
        }

        if (checkbox.checked) {
            switch (permissionType) {
                case 'Read': permissions[resource] |= 1; break;
                case 'Write': permissions[resource] |= 2; break;
                case 'Execute': permissions[resource] |= 4; break;
            }
        }
    });

    // Gửi dữ liệu cập nhật quyền và vai trò
    $.ajax({
        url: '/WebBanQuanAo/admin/update-userRolePermissions',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            userName: userName,
            roles: roles,        // Gửi vai trò đã chọn
            permissions: permissions // Gửi quyền đã chọn
        }),
        success: function () {
            alert("Cập nhật quyền và vai trò thành công!");
            event.target.style.display = 'none';
            row.querySelector("span[onclick='toggleEditRolePermission(event)']").style.display = 'inline';
        },
        error: function (xhr, status, error) {
            alert("Cập nhật thất bại: " + error);
            console.log(this.data)
        }
    });
}
