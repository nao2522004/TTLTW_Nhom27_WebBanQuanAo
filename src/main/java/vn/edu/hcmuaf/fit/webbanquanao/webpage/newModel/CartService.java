package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    // Lấy toàn bộ sản phẩm từ giỏ hàng
    public List<Product> getCart() {
        List<Product> cart = new ArrayList<>();
        return cart;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(int userId, int couponId, int quantity, double unitPrice, int productDetailId) {
        cartDAO.addToCart(userId, couponId, quantity, unitPrice, productDetailId);
    }

    // Cập nhật số lượng sản phẩm
    public void updateCart(int cartDetailId, int quantity) {
        cartDAO.updateCart(cartDetailId, quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(int productId) {
        cartDAO.removeItem(productId);
    }

    // Tính tổng tiền
    public double getCartTotal(int userId) {
        return cartDAO.getTotalPrice(userId);
    }

    // Lấy sản phẩm chi tiết dựa theo size và color
    public ProductDetail getProductDetailBySizeColor(String color, String size) {
        return cartDAO.getProductDetailBySizeColor(color, size);
    }
}
