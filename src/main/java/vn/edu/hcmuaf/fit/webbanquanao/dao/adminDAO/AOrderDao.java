package vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class AOrderDao {
    public Map<Integer, AOrder> listOrders;

    public AOrderDao() {
        listOrders = getAllOrders();
    }

    public Map<Integer, AOrder> getAllOrders() {
        Map<Integer, AOrder> orders = new LinkedHashMap<>();

        String sql = "SELECT o.id, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status\n" +
                "from orders o\n" +
                "INNER JOIN payments p on o.paymentId = p.id\n" +
                "INNER JOIN coupons c on o.couponId = c.id\n" +
                "INNER JOIN users u on o.userId = u.id";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AOrder order = new AOrder();
                    order.setId(rs.getInt("id"));
                    order.setFirstName(rs.getString("firstName"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setCode(rs.getString("code"));
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

    public boolean update(Object obj, Integer id){
        return JDBIConnector.get().withHandle(h -> {
            AOrder order = (AOrder) obj;
            listOrders.replace(id, order);
            String sql = "UPDATE orders o " +
                    "INNER JOIN payments p ON o.paymentId = p.id " +
                    "INNER JOIN coupons c ON o.couponId = c.id " +
                    "INNER JOIN users u ON o.userId = u.id " +
                    "SET u.firstName = ?, p.paymentMethod = ?, c.code = ?, o.orderDate = ?, o.totalPrice = ?, o.status = ? " +
                    "WHERE o.id = ?";
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setString(1, order.getFirstName());
                ps.setString(2, order.getPaymentMethod());
                ps.setString(3, order.getCode());
                ps.setDate(4, java.sql.Date.valueOf(order.getOrderDate().toLocalDate()));
                ps.setDouble(5, order.getTotalPrice());
                ps.setBoolean(6, order.isStatus());
                ps.setInt(7, id);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi cap nhat don hang: " + e.getMessage());
            }
            return false;
        });
    }

    public boolean delete(Integer id) {
       listOrders.remove(id);
         String sql = "DELETE FROM orders WHERE id = ?";
            return JDBIConnector.get().withHandle(h -> {
                try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    return ps.executeUpdate() > 0;
                } catch (Exception e) {
                    System.out.println("Loi khi xoa don hang: " + e.getMessage());
                }
                return false;
            });
    }


}
