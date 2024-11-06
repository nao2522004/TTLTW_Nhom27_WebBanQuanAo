function showSection(sectionId) {
    document.getElementById('product-manager').style.display = 'none';
    document.getElementById('user-manager').style.display = 'none';
    document.getElementById('order-manager').style.display = 'none';

    document.getElementById(sectionId).style.display = 'block';
}