package vn.edu.hcmuaf.fit.webbanquanao.webpage.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.dao.CheckoutDAO;

public class CheckoutService {
    CheckoutDAO dao;

    public CheckoutService() {
        dao = new CheckoutDAO();
    }

    public boolean checkout() {
        return false;
    }
}
