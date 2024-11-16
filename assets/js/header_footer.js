// Header and footer
const header = document.getElementById('header');
const footer = document.getElementById('footer');
fetch('./assets/component/header.html').then(response => response.text()).then(html => header.innerHTML = html);
fetch('./assets/component/footer.html').then(response => response.text()).then(html => footer.innerHTML = html);

// Transition for Header
window.addEventListener("scroll", () => {
    if (window.scrollY > 50) {
        header.classList.add("scrolled");
    } else {
        header.classList.remove("scrolled");
    }
});

// Responsive for Header
document.addEventListener("DOMContentLoaded", function () {
    const menuToggle = document.querySelector('.menu-toggle');
    const navMenu = document.querySelector('header nav ul');

    menuToggle.addEventListener('click', function () {
        navMenu.classList.toggle('active');
    });
});