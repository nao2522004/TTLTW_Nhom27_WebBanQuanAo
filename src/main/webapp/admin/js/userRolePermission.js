function openRolePermissionUser(event) {
    const userName = event.target.getAttribute("data-username");
    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay-userRolePermissons");
    overlay.style.display = "block";

    $.ajax({
        url: `/WebBanQuanAo/admin/api/roleUser/${encodeURIComponent(userName)}`,
        type: 'GET',
        cache: false,
        success: function(data) {
            console.log("Data origin:", JSON.stringify(data));

            if (data && data.length > 0) {
                const user = data[0];
                const tbody = document.querySelector("#userRolePermissons--table tbody");

                // Hiển thị thông tin cơ bản
                tbody.innerHTML = `
                <tr>
                    <td style="width: 125px"><input type="text" value="${user.userName}" disabled></td>
                    <td style="width: 125px"><input type="text" value="${user.firstName}" disabled></td>
                    <td>
                        <div style="width: 150px; margin-right: 10px" class="roles-container">
                            ${renderRoles(user.roles)}
                        </div>
                    </td>
                    <!--<td>
                        <div class="permissions-container">
                          renderPermissions(user.permissions)
                        </div>
                    </td>-->
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
    const allRoles = ['ADMIN','MANAGER','STAFF','USER'];

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

function toggleEditRolePermission(event) {
    const row = event.target.closest("tr");

    // Mở khóa checkbox role
    const roleCheckboxes = row.querySelectorAll(".roles-table input[type='checkbox']");
    roleCheckboxes.forEach(cb => cb.disabled = false); // Đây là bắt buộc

    // Hiển thị nút Save, ẩn nút Edit
    const saveBtn = row.querySelector("span[onclick='saveUserRolePermission(event)']");
    const editBtn = row.querySelector("span[onclick='toggleEditRolePermission(event)']");
    saveBtn.style.display = "inline";
    editBtn.style.display = "none";
}

function saveUserRolePermission(event) {
    const row = event.target.closest("tr");
    const userName = row.querySelector("td:first-child input").value;

    // Đảm bảo checkbox không bị disabled để lấy đúng trạng thái checked
    const roleCheckboxes = row.querySelectorAll(".roles-table input[type='checkbox']");
    roleCheckboxes.forEach(cb => cb.disabled = false);

    const roles = Array.from(roleCheckboxes)
        .filter(cb => cb.checked)
        .map(cb => cb.value);

    $.ajax({
        url: `/WebBanQuanAo/admin/api/roleUser/${encodeURIComponent(userName)}`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            userName: userName,
            roles: roles
        }),
        success: function () {
            alert("Cập nhật vai trò thành công!");
            event.target.style.display = 'none';
            row.querySelector("span[onclick='toggleEditRolePermission(event)']").style.display = 'inline';
            // Disable lại các checkbox sau khi lưu
            roleCheckboxes.forEach(cb => cb.disabled = true);
            console.log("Save success: " + this.data)
        },
        error: function (xhr, status, error) {
            alert("Cập nhật thất bại: " + error);
            console.log("Save Failed:" + this.data)
            console.log("Roles selected:", roles);
        }
    });
}
