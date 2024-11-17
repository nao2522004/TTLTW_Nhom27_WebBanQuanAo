// Js xu ly su kien menu cua header khi khi nho man hinh
document.addEventListener("DOMContentLoaded", function () {
  // Đảm bảo rằng menu không có lớp active khi trang được load lần đầu
  document.querySelector('header .part2 ul').classList.remove('active');

    document.querySelector(' .menu-toggle').addEventListener('click', function () {
      document.querySelector('header .part2 ul').classList.toggle('active');
    });
  });
  // end Js xu ly su kien menu cua header khi khi nho man hinh