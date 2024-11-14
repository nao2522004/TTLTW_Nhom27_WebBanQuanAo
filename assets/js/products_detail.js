// JavaScript for future enhancements (e.g., gallery navigation, cart functionality)
document.addEventListener('DOMContentLoaded', () => {
    const leftNav = document.querySelector('.gallery-nav.left');
    const rightNav = document.querySelector('.gallery-nav.right');
    const galleryImages = document.querySelector('.gallery-images');

    // Placeholder functionality for gallery navigation
    leftNav.addEventListener('click', () => {
        galleryImages.scrollLeft -= 100;
    });

    rightNav.addEventListener('click', () => {
        galleryImages.scrollLeft += 100;
    });
});
