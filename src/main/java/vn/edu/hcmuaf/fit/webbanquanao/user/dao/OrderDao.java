package vn.edu.hcmuaf.fit.webbanquanao.user.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OrderDao {

    public Map<Integer, Order> listOrdersUser;

    public OrderDao() {
        listOrdersUser = getAllOrdersOfUser();
    }

    public Map<Integer, Order> getAllOrdersOfUser() {
        Map<Integer, Order> orders = new LinkedHashMap<>();

        String sql = "SELECT o.id, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status\n" + "from orders o\n" + "INNER JOIN payments p on o.paymentId = p.id\n" + "INNER JOIN coupons c on o.couponId = c.id\n" + "INNER JOIN users u on o.userId = u.id";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserName(rs.getString("userName"));
                    order.setFirstName(rs.getString("firstName"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setCode(rs.getString("code"));
                    order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setStatus(rs.getInt("status"));
                    orders.put(order.getId(), order);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach don hang: " + e.getMessage());
            }
            return orders;
        });
    }

    public Map<Integer, Order> getAllOrdersByUserName(String userName) {
        Map<Integer, Order> orders = new HashMap<>();

        // Lấy danh sách đơn hàng
        String sqlOrders = "SELECT o.id, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status " +
                "FROM orders o " +
                "LEFT JOIN payments p ON o.paymentId = p.id " +
                "LEFT JOIN coupons c ON o.couponId = c.id " +
                "INNER JOIN users u ON o.userId = u.id " +
                "WHERE u.userName = ?";

        // Lấy danh sách order item
        String sqlOrderItems = "SELECT oi.id, oi.orderId, p.productName, pd.color, pd.size, " +
                "oi.quantity, oi.unitPrice, oi.discount " +
                "FROM orderitem oi " +
                "LEFT JOIN products p ON oi.productId = p.id " +
                "LEFT JOIN product_details pd ON oi.productDetailId = pd.id " +
                "WHERE oi.orderId = ?";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sqlOrders)) {
                ps.setString(1, userName);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setFirstName(rs.getString("firstName"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setCode(rs.getString("code"));
                    order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setStatus(rs.getInt("status"));

                    // Lấy danh sách order items cho order này
                    try (PreparedStatement psItems = h.getConnection().prepareStatement(sqlOrderItems)) {
                        psItems.setInt(1, order.getId());
                        ResultSet rsItems = psItems.executeQuery();

                        List<OrderDetail> orderItems = new ArrayList<>();
                        while (rsItems.next()) {
                            OrderDetail item = new OrderDetail();
                            item.setId(rsItems.getInt("id"));
                            item.setOrderId(rsItems.getInt("orderId"));
                            item.setProductName(rsItems.getString("productName"));
                            item.setColor(rsItems.getString("color"));
                            item.setSize(rsItems.getString("size"));
                            item.setQuantity(rsItems.getInt("quantity"));
                            item.setUnitPrice(rsItems.getDouble("unitPrice"));
                            item.setDiscount(rsItems.getDouble("discount"));

                            orderItems.add(item);
                        }
                        order.setOrderDetails(orderItems); // Thêm danh sách OrderItem vào Order
                    }

                    orders.put(order.getId(), order);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi lấy danh sách đơn hàng: " + e.getMessage());
            }
            return orders;
        });
    }

}
