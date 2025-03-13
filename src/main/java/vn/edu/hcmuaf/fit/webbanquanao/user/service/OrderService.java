package vn.edu.hcmuaf.fit.webbanquanao.user.service;

import vn.edu.hcmuaf.fit.webbanquanao.user.dao.OrderDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.Order;

import java.util.Map;

public class OrderService {
    OrderDao dao;

    public OrderService() {
        dao = new OrderDao();
    }

    public Map<Integer, Order> getAllOrdersByUserName(String userName) {
        return dao.getAllOrdersByUserName(userName);
    }

    public boolean cancelOrder(int orderId) {
        return dao.cancelOrderById(orderId);
    }

    public static void main(String[] args) {
        OrderService a = new OrderService();

        System.out.println(a.getAllOrdersByUserName("admin"));
    }

}
