package vn.edu.hcmuaf.fit.webbanquanao.user.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderDao {

    public Map<Integer, Order> listOrdersUser;

    public OrderDao() {
        listOrdersUser = getAllOrdersOfUser();
    }

    public Map<Integer, Order> getAllOrdersOfUser() {
        Map<Integer, Order> orders = new LinkedHashMap<>();

        String sql = "SELECT o.id, u.userName, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status\n" + "from orders o\n" + "INNER JOIN payments p on o.paymentId = p.id\n" + "INNER JOIN coupons c on o.couponId = c.id\n" + "INNER JOIN users u on o.userId = u.id";

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
        Map<Integer, Order> orders = new HashMap<Integer, Order>();
        String sql = "SELECT o.id, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status\n" +
                "from orders o\n" +
                "INNER JOIN payments p on o.paymentId = p.id\n" +
                "INNER JOIN coupons c on o.couponId = c.id\n" +
                "INNER JOIN users u on o.userId = u.id\n" +
                "WHERE u.userName = ?";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
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
                    orders.put(order.getId(), order);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach don hang: " + e.getMessage());
            }
            return orders;
        });
    }
}
