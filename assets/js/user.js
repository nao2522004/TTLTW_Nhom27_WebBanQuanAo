document.getElementById("openPopup").addEventListener("click", function () {
  document.getElementById("popup").style.display = "block";
});

document.getElementById("closePopup").addEventListener("click", function () {
  document.getElementById("popup").style.display = "none";
});

document.getElementById("overlay").addEventListener("click", function () {
  document.getElementById("popup").style.display = "none";
});

// =============== Xử lý sự kiện showMain cho menu============//
document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("profile-info").classList.add("block");
});
function showMain(event, mainId) {
  event.preventDefault(); // Ngừng hành động mặc định của sự kiện

  // Xóa class active khỏi tất cả các thẻ li
  document.querySelectorAll(".sidebar ul li").forEach((link) => {
    link.classList.remove("active");
  });

  // Thêm class active vào thẻ li được nhấn
  event.currentTarget.classList.add("active");

  // Ẩn tất cả các thẻ main
  document.querySelectorAll("main").forEach((main) => {
    main.classList.remove("block");
  });

  // Hiển thị thẻ main tương ứng với ID đã chọn
  const targetMain = document.getElementById(mainId);
  if (targetMain) {
    targetMain.classList.add("block");
  }
}
