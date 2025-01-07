// Executes when document is loaded
document.addEventListener("DOMContentLoaded", (ev) => {
  // Recent Orders Data
  document.getElementById("recent-orders--table").appendChild(buildTableBody());

  // Orders Data
  document.getElementById("orders--table").appendChild(buildTableOrder());
  document
    .getElementById("orders-details--table")
    .appendChild(buildTableOrderDetails());

  // edit user
  document
    .getElementById("user_edit--table")
    .appendChild(buildEdittableTableUser());
  // table.innerHTML = "";

  // Product Data
  document.getElementById("product--table").appendChild(buildTableProduct());
  // edit product
  document
    .getElementById("product_edit--table")
    .appendChild(buildEditTableProduct());
  // table.innerHTML = "";

  // Others Data
  document.getElementById("sales--table").appendChild(buildTableSales());
  document
    .getElementById("collections--table")
    .appendChild(buildTableCollections());
  // edit Sales
  document
    .getElementById("sale_edit--table")
    .appendChild(buildEditTableSales());
  // edit Collections
  document
    .getElementById("collection_edit--table")
    .appendChild(buildEditTableCollections());

  // Updates Data
  document
    .getElementsByClassName("recent-updates")
    .item(0)
    .appendChild(buildUpdatesList());

  // Sales Analytics
  const salesAnalytics = document.getElementById("analytics");
  buildSalesAnalytics(salesAnalytics);
});

/*--------------------------------------------------------
---------------------------------------------------------
 
                       Manager Orders  
 
---------------------------------------------------------
----------------------------------------------------------*/

// ===============Recent_oder_data===============//
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
          <td class="primary" onclick="showOverlay(event)">Chi Tiết</td>
        </tr>
      `;
  }

  tbody.innerHTML = bodyContent;

  return tbody;
};

// ===============Oder_data===============//
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
          <td class="primary" onclick="showOverlay(event)">Chi Tiết</td>
        </tr>
      `;
  }

  tbody.innerHTML = OrderContent;

  return tbody;
};

// ===============Order details data===============//
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

/*--------------------------------------------------------
---------------------------------------------------------
 
                       Manager User  
 
---------------------------------------------------------
----------------------------------------------------------*/
// Lấy danh sách người dùng từ server
function fetchUsers() {
  $.ajax({
    url: 'admin/manager-users',
    type: 'GET',
    dataType: 'json',
    success: function (users) {
      const table = document.getElementById("users--table");
      const oldTbody = table.querySelector("tbody");

      // Xóa tbody cũ nếu có
      if (oldTbody) {
        table.removeChild(oldTbody);
      }

      // Thêm tbody mới vào bảng
      table.appendChild(buildTableUser(users));
    },
    error: function (xhr, status, error) {
      console.error('Error fetching users:', error);
      alert("Failed to fetch users. Please try again later.");
    }
  });
}

// Gửi dữ liệu user mới lên server
function saveUserToServer(user) {
  $.ajax({
    url: 'admin/manager-users',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(user),
    success: function () {
      alert("User added successfully!");
      fetchUsers(); // Làm mới danh sách người dùng sau khi thêm thành công
    },
    error: function (xhr, status, error) {
      console.error('Error saving user:', error);
      alert("Failed to save user. Please check the input and try again.");
    }
  });
}

// Khi DOM load, gọi fetchUsers để tải dữ liệu
document.addEventListener('DOMContentLoaded', fetchUsers);

// Tạo bảng từ danh sách người dùng
const buildTableUser = (users) => {
  const tbody = document.createElement("tbody");

  let userContent = "";
  for (const user of users) {
    userContent += `
        <tr>
          <td>${user.id}</td>
          <td>${user.userName}</td>
          <td>${user.firstName}</td>
          <td>${user.lastName}</td>
          <td>${user.email}</td>
          <td><img src="${user.avatar}" alt="Avatar" width="50" height="50"></td>
          <td>${user.address}</td>
          <td>${user.phone}</td>
          <td>${user.status}</td>
          <td>${user.createdAt}</td>
          <td>${user.roleId}</td>
          <td class="primary">
            <span onclick="showOverlay(event)" class="material-icons-sharp"> edit </span>
            <span class="material-icons-sharp"> delete </span>
          </td>
        </tr>
      `;
  }

  tbody.innerHTML = userContent;

  return tbody;
};


//---------------Edit user data--------------------//
const buildEdittableTableUser = () => {
  const userEdits = USER_DATA; // Lấy dữ liệu người dùng

  const tbody = document.createElement("tbody");

  let userEditContent = "";
  for (const [index, row] of userEdits.entries()) {
    userEditContent += `
        <tr data-index="${index}">
          <td>${row.userId}</td>
          <td><input type="text" value="${
            row.userName
          }" data-key="userName" /></td>
          <td><input type="text" value="${
            row.userPhone
          }" data-key="userPhone" /></td>
          <td><input type="text" value="${
            row.userAddress
          }" data-key="userAddress" /></td>
          <td><input type="email" value="${
            row.userEmail
          }" data-key="userEmail" /></td>
          <td>
            <select data-key="userPermission">
              <option value="Admin" ${
                row.userPermission === "Admin" ? "selected" : ""
              }>Admin</option>
              <option value="User" ${
                row.userPermission === "User" ? "selected" : ""
              }>User</option>
            </select>
          </td>
          <td class="primary">
            <span onclick="saveUser(event)" class="material-icons-sharp"> save </span>
          </td>
        </tr>
      `;
  }

  tbody.innerHTML = userEditContent;

  return tbody;
};

//---------------save user data--------------------//
function saveUser(event) {
  const row = event.target.closest("tr"); // Lấy hàng hiện tại
  const index = row.getAttribute("data-index"); // Lấy chỉ số hàng
  const inputs = row.querySelectorAll("[data-key]"); // Lấy tất cả các input/select trong hàng

  // Cập nhật lại USER_DATA
  inputs.forEach((input) => {
    const key = input.getAttribute("data-key");
    USER_DATA[index][key] = input.value;
  });

  alert("Thông tin đã được lưu!");
}

/*--------------------------------------------------------
---------------------------------------------------------
 
                       Manager Product  
 
---------------------------------------------------------
----------------------------------------------------------*/

//---------------Product Data--------------------//

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
         <td class="primary"> <span onclick="showOverlay(event)" class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
  }

  tbody.innerHTML = ProContent;

  return tbody;
};

//---------------Edit Product Data--------------------//
const buildEditTableProduct = () => {
  const productEdit = PRODUCTS_DATA; // Dữ liệu sản phẩm

  const tbody = document.createElement("tbody");

  let productEditContent = "";
  for (const [index, row] of productEdit.entries()) {
    productEditContent += `
        <tr data-index="${index}">
          <td>${row.proId}</td>
          <td><input type="text" value="${row.proCategory}" data-key="proCategory" /></td>
          <td><input type="text" value="${row.proName}" data-key="proName" /></td>
          <td><input type="text" value="${row.proImg}" data-key="proImg" /></td>
          <td><input type="number" value="${row.proPrice}" data-key="proPrice" /></td>
          <td><input type="text" value="${row.proSize}" data-key="proSize" /></td>
          <td><input type="text" value="${row.proColor}" data-key="proColor" /></td>
          <td><input type="number" value="${row.proAmount}" data-key="proAmount" /></td>
          <td><input type="date" value="${row.proStockInDate}" data-key="proStockInDate" /></td>
          <td class="primary">
            <span onclick="saveProduct(event)" class="material-icons-sharp"> save </span>
          </td>
        </tr>
      `;
  }

  tbody.innerHTML = productEditContent;

  return tbody;
};

//---------------save user data--------------------//
function saveProduct(event) {
  const row = event.target.closest("tr"); // Lấy hàng hiện tại
  const index = row.getAttribute("data-index"); // Lấy chỉ số hàng
  const inputs = row.querySelectorAll("[data-key]"); // Lấy tất cả các input/select trong hàng

  // Cập nhật lại USER_DATA
  inputs.forEach((input) => {
    const key = input.getAttribute("data-key");
    PRODUCTS_DATA[index][key] = input.value;
  });

  alert("Thông tin đã được lưu!");
}

/*--------------------------------------------------------
---------------------------------------------------------
 
                       Manager Others  
 
---------------------------------------------------------
----------------------------------------------------------*/

//===============Sales Data==================//
const buildTableSales = () => {
  const sales = SALES_DATA;

  const tbody = document.createElement("tbody");

  let SalesContent = "";
  for (const row of sales) {
    SalesContent += `
        <tr>
          <td>${row.saleid}</td>
          <td>${row.salename}</td>
          <td>${row.start_date}</td>
          <td>${row.end_date}</td>
          <td>${row.sale_decrip}</td>
          <td class="primary"> <span onclick="showOverlay(event)" class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
  }

  tbody.innerHTML = SalesContent;

  return tbody;
};

//===============Collection Data==================//
const buildTableCollections = () => {
  const collections = COLLECTIONS_DATA;

  const tbody = document.createElement("tbody");

  let CollectionsContent = "";
  for (const row of collections) {
    CollectionsContent += `
        <tr>
          <td>${row.coid}</td>
          <td>${row.coname}</td>
          <td>${row.start_date}</td>
          <td>${row.co_decrip}</td>
          <td class="primary"> <span onclick="showOverlay(event)" class="material-icons-sharp"> edit </span> <span class="material-icons-sharp"> delete </span></td>
        </tr>
      `;
  }

  tbody.innerHTML = CollectionsContent;

  return tbody;
};

//=============== Edit Sales Data==================//
const buildEditTableSales = () => {
  const salesEdit = SALES_DATA;

  const tbody = document.createElement("tbody");

  let SalesEditContent = "";
  for (const [index, row] of salesEdit.entries()) {
    SalesEditContent += `
        <tr data-index="${index}">
          <td>${row.saleid}</td>
          <td><input type="text" value="${row.salename}" data-key="salename" /></td>
          <td><input type="date" value="${row.start_date}" data-key="start_date" /></td>
          <td><input type="date" value="${row.end_date}" data-key="end_date" /></td>
          <td><input type="text" value="${row.sale_decrip}" data-key="sale_decrip" /></td>
          <td class="primary"> 
            <span onclick="saveOthers(event)" class="material-icons-sharp"> save </span> 
          </td>
        </tr>
      `;
  }

  tbody.innerHTML = SalesEditContent;

  return tbody;
};

//===============Edit Collection Data==================//
const buildEditTableCollections = () => {
  const collectionsEdit = COLLECTIONS_DATA;

  const tbody = document.createElement("tbody");

  let CollectionsEditContent = "";
  for (const [index, row] of collectionsEdit.entries()) {
    CollectionsEditContent += `
        <tr data-index="${index}">
          <td>${row.coid}</td>
          <td><input type="text" value="${row.coname}" data-key="coname" /></td>
          <td><input type="date" value="${row.start_date}" data-key="start_date" /></td>
          <td><input type="text" value="${row.co_decrip}" data-key="co_decrip" /></td>
          <td class="primary"> 
            <span onclick="saveOthers(event)" class="material-icons-sharp"> save </span> 
          </td>
        </tr>
      `;
  }

  tbody.innerHTML = CollectionsEditContent;

  return tbody;
};


//---------------Save edit sale and collection data--------------------//
function saveOthers(event) {
  const row = event.target.closest("tr"); // Lấy hàng hiện tại
  const index = row.getAttribute("data-index"); // Lấy chỉ số hàng
  const inputs = row.querySelectorAll("[data-key]"); // Lấy tất cả các input/select trong hàng

  // Cập nhật lại USER_DATA
  inputs.forEach((input) => {
    const key = input.getAttribute("data-key");
    SALES_DATA[index][key] = input.value;
    COLLECTIONS_DATA[index][key] = input.value;
  });

  alert("Thông tin đã được lưu!");
}

// =============================================== //
//================================================//
//================================================//
//================================================//
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

// =============== Xử lý sự kiện showMain cho menu============//
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
function hideOverlay(event) {
  const overlay = event.target.closest("main").querySelector(".overlay");
  overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function showOverlay(event) {
  const main = event.target.closest("main");
  const overlay = main.querySelector(".overlay");
  overlay.style.display = "block"; // Hiển thị overlay của main hiện tại
}
//========== end Xu ly su kien cho order detail ============//
