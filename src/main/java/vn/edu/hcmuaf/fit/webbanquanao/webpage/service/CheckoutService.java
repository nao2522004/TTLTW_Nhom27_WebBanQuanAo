package vn.edu.hcmuaf.fit.webbanquanao.webpage.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao.CartDao;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartProductDetail;

import java.util.List;

public class CheckoutService {

    public CheckoutService() {
    }

    public boolean checkout(int userId, int paymentId, int couponId, double totalPrice) {
        CartDao dao = new CartDao();
        boolean re = false;

        // get products of cart
        List<CartProductDetail> products = dao.getCartByUserId(userId);

        // create new order and get new orderId
        int orderId = dao.createNewOrder(userId, paymentId, couponId, totalPrice);

        // move products from cart to orderItem

        // remove products of cart

        return re;
    }
}
