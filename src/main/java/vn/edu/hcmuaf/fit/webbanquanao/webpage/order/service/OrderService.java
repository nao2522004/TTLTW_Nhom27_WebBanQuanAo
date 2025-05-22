package vn.edu.hcmuaf.fit.webbanquanao.webpage.order.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.dao.OrderDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    // Create Order
    public int createOrder(int userId, int paymentId, Integer couponId, BigDecimal totalPrice, int status,
                           int orderId, int productId, int quantity, BigDecimal unitPrice, float discount, int productDetailId) {
        return 0;
    }

    // Get order by orderId
    public Optional<Order> getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    // Get orders by userId
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    // Get OrderItem by orderId
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }

    // Cancel Order
    public void cancelOrder(int orderId, String reason) {
        orderDAO.cancelOrder(orderId, reason);
    }

    // Update Order's status
    public void updateOrderStatus(int orderId, int status) {
        orderDAO.updateOrderStatus(orderId, status);
    }
}
