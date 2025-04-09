package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private ProductDAO productDao;

    public CartService() {
        this.productDao = new ProductDAO();
    }

    // Lấy toàn bộ sản phẩm từ giỏ hàng
    public List<Product> getCart() {
        List<Product> cart = productDao.getAllProducts();
        return cart;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(int productId, String color, String size, int quantity) {
        productDao.addToCart(productId, color, size, quantity);
    }

    // Cập nhật số lượng sản phẩm
    public void updateCart(int productId, int quantity) {
        productDao.updateCart(productId, quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(int productId) {
        productDao.removeItem(productId);
    }

    // Tính tổng tiền
    public double getCartTotal() {
        return productDao.getTotalPrice();
    }
}
