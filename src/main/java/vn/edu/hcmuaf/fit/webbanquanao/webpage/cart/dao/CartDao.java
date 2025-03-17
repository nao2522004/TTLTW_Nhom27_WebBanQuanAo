package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartProduct;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDao {

    JDBIConnector conn;
    String query;

    public CartDao() {
        conn = new JDBIConnector();
    }


    public Map<Integer, CartProduct> getCartProducts(){
        Map<Integer, CartProduct> cps = new HashMap<>();
        query = "SELECT " +
                "    p.id AS id," +
                "    p.productName AS name," +
                "    p.unitPrice AS unitPrice," +
                "    pd.image AS img," +
                "    cd.quantity AS quantity " +
                "    cd.size AS size," +
                "    cd.color AS color" +
                "FROM " +
                "    products p " +
                "JOIN " +
                "    product_details pd ON p.id = pd.productId " +
                "JOIN " +
                "    cartdetail cd ON p.id = cd.productId " +
                "JOIN " +
                "    cart c ON cd.cartId = c.id " +
                "WHERE " +
                "    c.id = ?";



        return conn.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(query)) {
                ps.setInt(1, 1);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CartProduct cp = new CartProduct();
                        cp.setId(rs.getInt("id"));
                        cp.setName(rs.getString("name"));
                        cp.setUnitPrice(rs.getDouble("unitPrice"));
                        cp.setQuantity(rs.getInt("quantity"));

                        String size = rs.getString("size");
                        String image = rs.getString("image");
                        String color = rs.getString("color");

                        if (!cp.getSizes().contains(size))
                            cp.getSizes().add(size);
                        if (!cp.getImages().contains(image))
                            cp.getImages().add(image);
                        if (!cp.getColors().contains(color))
                            cp.getColors().add(color);

                        cps.put(cp.getId(), cp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cps;
        });

    }

    // Add to Order
    public boolean addToOrder(int userId, int paymentId, int couponId, Date orderDate, double totalPrice, boolean status, List<CartProduct> items) {
        String orderQuery = "INSERT INTO orders (userId, paymentId, couponId, orderDate, totalPrice, status) VALUES (?, ?, ?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO orderitem (orderId, productId, quantity, unitPrice, discount) VALUES (?, ?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement orderStmt = h.getConnection().prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, userId);
                orderStmt.setInt(2, paymentId);
                orderStmt.setInt(3, couponId);
                orderStmt.setDate(4, orderDate);
                orderStmt.setDouble(5, totalPrice);
                orderStmt.setBoolean(6, status);
                orderStmt.executeUpdate();

                ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    try (PreparedStatement orderItemStmt = h.getConnection().prepareStatement(orderItemQuery)) {
                        for (CartProduct item : items) {
                            orderItemStmt.setInt(1, orderId);
                            orderItemStmt.setInt(2, item.getId());
                            orderItemStmt.setInt(3, item.getQuantity());
                            orderItemStmt.setDouble(4, item.getUnitPrice());
                            orderItemStmt.setDouble(5, 0.0);
                            orderItemStmt.addBatch();
                        }
                        orderItemStmt.executeBatch();
                    }

                    return true;
                } else {
                    throw new SQLException("Failed to retrieve the orderId.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    // Get order by user ID
    public List<CartDetail> getCartByUserId(int userId) {
        List<CartDetail> re = new ArrayList<>();
        query = "SELECT" +
                " cd.productId," +
                " cd.couponId," +
                " cd.quantity," +
                " cd.unitPrice" +
                " FROM cart c" +
                " JOIN cartdetail cd ON c.id = cd.cartId" +
                " WHERE c.userId = ?";

        return conn.get().withHandle(h -> {
            try(PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int productId = rs.getInt("productId");
                        int couponId = rs.getInt("couponId");
                        int quantity = rs.getInt("quantity");
                        double unitPrice = rs.getDouble("unitPrice");
                        CartDetail c = new CartDetail(productId, couponId, quantity, unitPrice);
                        re.add(c);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return re;
        });
    }
}
