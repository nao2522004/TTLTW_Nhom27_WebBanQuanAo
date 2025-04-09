// Hàm mở popup hiển thị vai trò và quyền của user
function openRolePermissionUser(event) {
    const userName = event.target.getAttribute("data-username");

    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay-userRolePermissons");
    overlay.style.display = "block";

    // Gửi yêu cầu AJAX để lấy dữ liệu vai trò + quyền của user theo username
    $.ajax({
        url: '/WebBanQuanAo/admin/manager-userRolePermissions',
        type: 'GET',
        data: { userName: userName },
        cache: false,
        success: function (data) {

            console.log("Dữ liệu vai trò/quyền:", JSON.stringify(data));

            if (data && data.length > 0) {
                let content = '';
                data.forEach(user => {

                    const roles = user.roles.join(", ");
                    const permissions = Object.entries(user.permissions)
                        .map(([resource, permission]) => `${resource}: ${permission}`)
                        .join('\n'); // DÙNG \n thay cho <br> để hiển thị đúng trong input

                    content += `
                    <tr>
                        <td><input type="text" value="${user.userName}" disabled></td>
                        <td><input type="text" value="${user.firstName}" disabled></td>
                        <td><input type="text" value="${roles}" disabled></td>
                        <td><input type="text" class="permission-input" value="${permissions}" readonly></td>
                        <td>
                            <span onclick="editUserRolePermission(event)" class="material-icons-sharp">edit</span>
                        </td>
                    </tr>
                    `;
                });

                const tbody = document.querySelector("#userRolePermissons--table tbody");
                tbody.innerHTML = content;
            } else {
                alert("Không tìm thấy thông tin vai trò và quyền của người dùng này.");
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy vai trò/quyền người dùng:", error);
            alert("Không thể lấy thông tin. Vui lòng thử lại.");
        }
    });
}
