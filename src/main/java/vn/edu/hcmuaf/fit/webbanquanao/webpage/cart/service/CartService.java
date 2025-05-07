package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao.CartDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartItem;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.util.List;

public class CartService {
    private CartDAO cartDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    // Lấy toàn bộ sản phẩm từ giỏ hàng
    public List<CartItem> getCart(int userId) {
        return null;
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(int userId, int couponId, int quantity, double unitPrice, int productDetailId) {
        int cartDetailId = cartDAO.hasProduct(userId, productDetailId);
        if(cartDetailId > 0) {
            return updateCart(userId, productDetailId, cartDAO.getQuantityOfProduct(userId, productDetailId) + quantity);
        } else {
            return cartDAO.addToCart(userId, couponId, quantity, unitPrice, productDetailId);
        }
    }

    // Cập nhật số lượng sản phẩm
    public boolean updateCart(int userId, int productDetailId, int quantity) {
        int temp = cartDAO.getQuantityOfProduct(userId, productDetailId) + quantity;
        if(temp > 0) {
            return cartDAO.updateCart(userId, productDetailId, temp);
        }
        return false;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public boolean removeFromCart(int cartDetailId) {
        return cartDAO.removeItem(cartDetailId);
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
