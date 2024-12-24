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
  event.preventDefault(); 


  document.querySelectorAll(".sidebar ul li").forEach((link) => {
    link.classList.remove("active");
  });


  event.currentTarget.classList.add("active");


  document.querySelectorAll("main").forEach((main) => {
    main.classList.remove("block");
  });


  const targetMain = document.getElementById(mainId);
  if (targetMain) {
    targetMain.classList.add("block");
  }
}
