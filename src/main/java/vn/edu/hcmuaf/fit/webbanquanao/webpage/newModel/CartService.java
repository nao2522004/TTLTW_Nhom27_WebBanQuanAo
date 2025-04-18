package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    // Lấy toàn bộ sản phẩm từ giỏ hàng
    public List<Product> getCart(int userId) {
        return cartDAO.getAllProducts(userId);
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(int userId, int couponId, int quantity, double unitPrice, int productDetailId) {
        int cartDetailId = cartDAO.hasProduct(userId, productDetailId);
        if(cartDetailId > 0) {
            return updateCart(cartDetailId, cartDAO.getQuantityOfProduct(userId, productDetailId) + quantity);
        } else {
            return cartDAO.addToCart(userId, couponId, quantity, unitPrice, productDetailId);
        }
    }

    // Cập nhật số lượng sản phẩm
    public boolean updateCart(int cartDetailId, int quantity) {
        return cartDAO.updateCart(cartDetailId, quantity);
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

    public static void main(String[] args) {
        CartService cartService = new CartService();
        System.out.println(cartService.addToCart(2,1,4,274550.00,1));
        System.out.println(cartService.addToCart(2,1,1,274550.00,2));
        System.out.println(cartService.addToCart(2,1,1,274550.00,3));
        System.out.println(cartService.addToCart(2,1,1,274550.00,4));
        System.out.println(cartService.getCart(2));
    }
}
