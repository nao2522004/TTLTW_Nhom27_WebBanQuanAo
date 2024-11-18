const form = document.querySelector('form');
const password = document.querySelector('input[name="new-password"]');
const confirmPassword = document.querySelector('input[name="confirm-password"]');

form.addEventListener('submit', (e) => {
    if (password.value !== confirmPassword.value) {
        e.preventDefault(); // Ngăn chặn gửi form
        alert('Mật khẩu và xác nhận mật khẩu không khớp. Vui lòng thử lại.');
    }
});


function togglePassword(button) {
    const input = button.previousElementSibling; // Lấy input trước nút
    const icon = button.querySelector('i');

    if (input.type === "password") {
        input.type = "text"; // Hiện mật khẩu
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = "password"; // Ẩn mật khẩu
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

