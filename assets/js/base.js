// Js xu ly su kien menu cua header khi khi nho man hinh
document.addEventListener("DOMContentLoaded", function () {

  const menu = document.querySelector("header .container-nav");
  const menu1 = document.querySelector("header .container-nav nav");

    if (menu && menu1) {
        // Kiểm tra menu có tồn tại hay không
        menu.classList.remove("active");
        menu1.classList.remove("active");

        document
            .querySelector(".menu-toggle")
            .addEventListener("click", function () {
                menu.classList.toggle("active");
                menu1.classList.toggle("active");
            });
    }

});
// end Js xu ly su kien menu cua header khi khi nho man hinh

// Hiệu ứng chuyển động cho Header khi người dùng cuộn trang
window.addEventListener("scroll", () => {
  const header = document.querySelector("header");
  const windowWidth = window.innerWidth;

  if (windowWidth > 1456) {
      if (window.scrollY > 50) {
          header.classList.add("scrolled");
      } else {
          header.classList.remove("scrolled");
      }
  } else {
      header.classList.remove("scrolled"); // Loại bỏ class nếu màn hình nhỏ hơn 991px
  }
});

// Kiểm tra và cập nhật ngay khi resize màn hình
window.addEventListener("resize", () => {
  const header = document.querySelector("header");
  const windowWidth = window.innerWidth;

  if (windowWidth <= 1456) {
      header.classList.remove("scrolled"); // Đảm bảo không áp dụng hiệu ứng cho màn hình nhỏ
  }
});
// end

// xu ly chuyen doi atr thanh vnd
document.addEventListener("DOMContentLoaded", () => {
  const priceElements = document.querySelectorAll(".product-decrip div");

  priceElements.forEach((el) => {
    const price = Number(el.getAttribute("data-price"));

    const formattedPrice = new Intl.NumberFormat("vi-VN", {
      style: "currency",
      currency: "VND",
    }).format(price);

    el.textContent = formattedPrice;
  });
});

//

// <!-- Filter -->
document.addEventListener("DOMContentLoaded", function () {
  const filterHeaders = document.querySelectorAll(
    "#section_all_products .filter h4"
  );

  filterHeaders.forEach((header) => {
    header.addEventListener("click", function () {
      // Toggle the visibility of filter content
      const filterContent = header.nextElementSibling;
      // filterContent.style.display = filterContent.style.display === "block" ? "none" : "block";
      if (filterContent.classList.contains("active")) {
        filterContent.classList.remove("active");
      } else {
        filterContent.classList.add("active");
      }

      // Toggle the arrow direction
      const arrow = header.querySelector(".arrow");
      arrow.classList.toggle("active");
    });
  });
});