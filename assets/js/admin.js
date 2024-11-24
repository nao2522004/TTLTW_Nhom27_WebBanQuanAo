// Executes when document is loaded
document.addEventListener("DOMContentLoaded", (ev) => {
  // Recent Orders Data
  document.getElementById("recent-orders--table").appendChild(buildTableBody());
  // Orders Data
  document.getElementById("orders--table").appendChild(buildTableOrder());
  document.getElementById("orders-details--table").appendChild(buildTableOrderDetails());
  // usertomer Data
  document.getElementById("users--table").appendChild(buildTableUser());
  // Product Data
  document.getElementById("product--table").appendChild(buildTableProduct());

  // Updates Data
  document
    .getElementsByClassName("recent-updates")
    .item(0)
    .appendChild(buildUpdatesList());

  // Sales Analytics
  const salesAnalytics = document.getElementById("analytics");
  buildSalesAnalytics(salesAnalytics);
});

// Document Builder

// Recent_oder_data
const buildTableBody = () => {
  const recentOrderData = RECENT_ORDER_DATA;

  const tbody = document.createElement("tbody");

  let bodyContent = "";
  for (const row of recentOrderData) {
    bodyContent += `
       <tr>
          <td>${row.orderId}</td>
          <td>${row.userName}</td>
          <td>${row.userPhone}</td>
          <td>${row.userAdress}</td>
          <td>${row.creationDate}</td>
          <td>${row.totalPrice}</td>
          <td>${row.payment}</td>
          <td class="${row.statusColor}">${row.status}</td>
          <td class="primary"> <a href="" id="product-details">Chi Tiết</a> </td>
        </tr>
      `;
  }

  tbody.innerHTML = bodyContent;

  return tbody;
};

// order_data
const buildTableOrder = () => {
  const orderData = ORDER_DATA;

  const tbody = document.createElement("tbody");

  let OrderContent = "";
  for (const row of orderData) {
    OrderContent += `
        <tr>
          <td>${row.orderId}</td>
          <td>${row.userName}</td>
          <td>${row.userPhone}</td>
          <td>${row.userAdress}</td>
          <td>${row.creationDate}</td>
          <td>${row.totalPrice}</td>
          <td>${row.payment}</td>
          <td class="${row.statusColor}">${row.status}</td>
          <td class="primary" onclick="showOverlay()">Details</td>
        </tr>
      `;
  }

  tbody.innerHTML = OrderContent;

  return tbody;
};

// order detais data
const buildTableOrderDetails = () => {
  const orderDetaisData = ORDER_DETAILS_DATA;

  const tbody = document.createElement("tbody");

  let OrderDetaisContent = "";
  for (const row of orderDetaisData) {
    OrderDetaisContent += `
        <tr>
          <td>${row.proImg}</td>
          <td>${row.proId}</td>
          <td>${row.proName}</td>
          <td>${row.proSize}</td>
          <td>${row.proColor}</td>
          <td>${row.proAmount}</td>
          <td>${row.unitPrice}</td>
        </tr>
      `;
  }

  tbody.innerHTML = OrderDetaisContent;

  return tbody;
};

// users_data
const buildTableUser = () => {
  const users = USER_DATA;

  const tbody = document.createElement("tbody");

  let userContent = "";
  for (const row of users) {
    userContent += `
        <tr>
          <td>${row.userId}</td>
          <td>${row.userName}</td>
          <td>${row.userPhone}</td>
          <td>${row.userAddress}</td>
          <td>${row.userEmail}</td>
          <td>${row.userPermission}</td>
          <td class="primary"> <span class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
  }

  tbody.innerHTML = userContent;

  return tbody;
};

// Product_data
const buildTableProduct = () => {
  const products = PRODUCTS_DATA;

  const tbody = document.createElement("tbody");

  let ProContent = "";
  for (const row of products) {
    ProContent += `
        <tr>
          <td>${row.proId}</td>
          <td>${row.proCategory}</td>
          <td>${row.proName}</td>
          <td>${row.proImg}</td>
          <td>${row.proPrice}</td>
          <td>${row.proSize}</td>
          <td>${row.proColor}</td>
          <td>${row.proAmount}</td>
          <td>${row.proStockInDate}</td>
          <td class="primary"> <span class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
  }

  tbody.innerHTML = ProContent;

  return tbody;
};

const buildUpdatesList = () => {
  const updateData = UPDATE_DATA;

  const div = document.createElement("div");
  div.classList.add("updates");

  let updateContent = "";
  for (const update of updateData) {
    updateContent += `
        <div class="update">
          <div class="profile-photo">
            <img src="${update.imgSrc}" />
          </div>
          <div class="message">
            <p><b>${update.profileName}</b> ${update.message}</p>
            <small class="text-muted">${update.updatedTime}</small>
          </div>
        </div>
      `;
  }

  div.innerHTML = updateContent;

  return div;
};

const buildSalesAnalytics = (element) => {
  const salesAnalyticsData = SALES_ANALYTICS_DATA;

  for (const analytic of salesAnalyticsData) {
    const item = document.createElement("div");
    item.classList.add("item");
    item.classList.add(analytic.itemClass);

    const itemHtml = `
        <div class="icon">
          <span class="material-icons-sharp"> ${analytic.icon} </span>
        </div>
        <div class="right">
          <div class="info">
            <h3>${analytic.title}</h3>
            <small class="text-muted"> Last 24 Hours </small>
          </div>
          <h5 class="${analytic.colorClass}">${analytic.percentage}%</h5>
          <h3>${analytic.sales}</h3>
        </div>
      `;

    item.innerHTML = itemHtml;

    element.appendChild(item);
  }
};

// Document operation functions
const sideMenu = document.querySelector("aside");
const menuBtn = document.querySelector("#menu-btn");
const closeBtn = document.querySelector("#close-btn");
const themeToggler = document.querySelector(".theme-toggler");

// Show Sidebar
menuBtn.addEventListener("click", () => {
  sideMenu.style.display = "block";
});

// Hide Sidebar
closeBtn.addEventListener("click", () => {
  sideMenu.style.display = "none";
});

// Change Theme
themeToggler.addEventListener("click", () => {
  document.body.classList.toggle("dark-theme-variables");

  themeToggler.querySelector("span:nth-child(1)").classList.toggle("active");
  themeToggler.querySelector("span:nth-child(2)").classList.toggle("active");
});

// Xử lý sự kiện showMain cho menu
document.addEventListener("DOMContentLoaded", function () {
  // Hiển thị phần tử main đầu tiên khi tải trang
  document.getElementById("dashboard").classList.add("block");
});

function showMain(event, mainId) {
  event.preventDefault();

  // Xóa class active khỏi tất cả các thẻ a
  document.querySelectorAll("aside .sidebar a").forEach((link) => {
    link.classList.remove("active");
  });

  // Thêm class active vào thẻ a được nhấn
  event.currentTarget.classList.add("active");

  // Ẩn tất cả các thẻ main
  document.querySelectorAll("main").forEach((main) => {
    main.classList.remove("block");
  });

  // Hiển thị thẻ main được chọn
  const targetMain = document.getElementById(mainId);
  if (targetMain) {
    targetMain.classList.add("block");
  }
}

//========== start Xu ly su kien cho order detail ============//
function hideOverlay() {
  const overlay = document.querySelector("main .overlay");
  overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function showOverlay() {
  const show = document.querySelector("main .overlay");
  show.style.display = "block";
}
//========== end Xu ly su kien cho order detail ============//