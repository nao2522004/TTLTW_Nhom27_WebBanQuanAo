package vn.edu.hcmuaf.fit.webbanquanao.webpage.order.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CartService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.dao.OrderDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.OrderItem;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDAO orderDAO;
    private final CartService cartService;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.cartService = new CartService();
    }

    // Add Order
    public int addOrder(int userId) {
        // create order
        BigDecimal totalPrice = BigDecimal.valueOf(cartService.getCartTotal(userId));
        int orderId = orderDAO.createOrder(userId, 2, 1, totalPrice, 1);

        // add order item
        List<CartDetail> cartDetails = cartService.getCartItems(userId);
        for (CartDetail cd : cartDetails) {
            orderDAO.addOrderItem(orderId, cd.getProductId(), cd.getQuantity(), BigDecimal.valueOf(cd.getUnitPrice()), 0.0f, cd.getProductDetailsId());

            // remove this product from cart
            cartService.removeFromCart(cd.getId());
        }

        return orderId;
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

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        System.out.println(orderService.addOrder(2));
    }
}
