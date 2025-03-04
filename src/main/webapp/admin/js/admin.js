// =============== Xử lý sự kiện showMain cho menu============//
document.addEventListener("DOMContentLoaded", function () {
    // Hiển thị phần tử main đầu tiên khi tải trang
    const dashboard = document.getElementById("dashboard");
    if (dashboard) {
        // Đảm bảo rằng chỉ có dashboard được hiển thị ban đầu
        dashboard.classList.add("block");
    }

    // Ẩn các phần tử main khác nếu có
    document.querySelectorAll("main").forEach((main) => {
        if (main.id !== "dashboard") {
            main.classList.remove("block");
        }
    });
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

// Executes when document is loaded
document.addEventListener("DOMContentLoaded", (ev) => {

    // Orders Data
    document.getElementById("orders--table").appendChild(buildTableOrder());
    document
        .getElementById("orders-details--table")
        .appendChild(buildTableOrderDetails());

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


//========== start Xu ly su kien cho order detail ============//
function hideOverlay(event) {
    const overlay = event.target.closest("main").querySelector(".overlay");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function hideOverlayProductDetails(event) {
    const overlay = event.target.closest("main").querySelector(".overlay-productDetails");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function hideOverlayOrderItemDetails(event) {
    const overlay = event.target.closest("main").querySelector(".overlay-orderItemDetails");
    overlay.style.display = "none"; // Ẩn overlay khi nhấn vào nó
}

function showOverlay(event) {
    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block"; // Hiển thị overlay của main hiện tại
}

//========== end Xu ly su kien cho order detail ============//
