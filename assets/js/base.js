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
window.addEventListener("scroll", () => {
  const header = document.querySelector("header");
  const logo = header.querySelector(".logo");
  const windowWidth = window.innerWidth;

  if (windowWidth > 1456) {
    if (window.scrollY > 100) {
      if (!header.classList.contains("scrolled")) {
        header.classList.add("scrolled"); // Thêm trạng thái cuộn
        logo.style.transition = "none"; // Ngắt hiệu ứng tạm thời
        setTimeout(() => {
          logo.classList.add("hidden"); // Ẩn logo sau khi cuộn
          logo.style.transition = "transform 1s ease-in-out, opacity 1s ease-in-out";
        }, 1000); // Đảm bảo thời gian trùng với transition
      }
    } else {
      header.classList.remove("scrolled");
      logo.style.transition = "none"; // Ngắt hiệu ứng tạm thời
      logo.classList.add("hidden"); // Ẩn logo ngay lập tức
      setTimeout(() => {
        logo.style.transition = ""; // Kích hoạt lại hiệu ứng
        logo.classList.remove("hidden"); // Hiện lại logo mượt mà
      }, 1000); // Đảm bảo logo di chuyển về vị trí
    }
  } else {
    header.classList.remove("scrolled");
    logo.classList.remove("hidden");
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
// xu ly chuyen doi don vi tien te cua Luat
document.addEventListener("DOMContentLoaded", () => {
  const priceElements = document.querySelectorAll(".product-price");

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

// // Thanh tim kiem tren header
// document.addEventListener('DOMContentLoaded', () => {
//   const headerContainer = document.getElementById('header-container');

//   // Fetch header content and inject into DOM
//   fetch('header.html')
//     .then((response) => response.text())
//     .then((html) => {
//       headerContainer.innerHTML = html;

//       // Sau khi header được chèn, lấy các phần tử liên quan
//       const searchIcon = document.getElementById('search-icon');
//       const searchPopup = document.getElementById('search-popup');
//       const closePopup = document.getElementById('close-popup');

//       if (searchIcon && searchPopup && closePopup) {
//         // Show popup
//         searchIcon.addEventListener('click', () => {
//           searchPopup.classList.add('active');
//         });

//         // Close popup
//         closePopup.addEventListener('click', () => {
//           searchPopup.classList.remove('active');
//         });

//         // Close popup when clicking outside the search box
//         searchPopup.addEventListener('click', (e) => {
//           if (e.target === searchPopup) {
//             searchPopup.classList.remove('active');
//           }
//         });
//       } else {
//         console.error('One or more required elements were not found.');
//       }
//     })
//     .catch((error) => console.error('Failed to fetch header:', error));
// });