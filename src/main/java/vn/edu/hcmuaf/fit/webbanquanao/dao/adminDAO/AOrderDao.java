package vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class AOrderDao {
    Map<Integer, AOrder> listOrders;

    public AOrderDao() {
        listOrders = getAllOrders();
    }

    public Map<Integer, AOrder> getAllOrders() {
        Map<Integer, AOrder> orders = new LinkedHashMap<>();

        String sql = "SELECT * FROM orders ORDERS BY id DESC;";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AOrder order = new AOrder();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("userId"));
                    order.setPaymentId(rs.getInt("paymentId"));
                    order.setCouponId(rs.getInt("couponId"));
                    order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setStatus(rs.getBoolean("status"));
                    orders.put(order.getId(), order);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach don hang: " + e.getMessage());
            }
            return orders;
        });
    }

    
}
