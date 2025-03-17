package vn.edu.hcmuaf.fit.webbanquanao.webpage.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao.CartDao;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.dao.CheckoutDAO;

public class CheckoutService {
    CheckoutDAO dao;

    public CheckoutService() {
        dao = new CheckoutDAO();
    }

    public boolean checkout(int userId) {
        CartDao cartDao = new CartDao();
        CheckoutDAO checkoutDao = new CheckoutDAO();
        boolean re = false;

        for (CartDetail cartDetail : cartDao.getCartByUserId(userId)) {

        }
        return re;
    }
}
