package vn.edu.hcmuaf.fit.webbanquanao.webpage.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.sql.*;

public class CheckoutDAO {
    JDBIConnector conn;
    String sql1, sql2;

    public CheckoutDAO() {
        conn = new JDBIConnector();
    }

    public boolean addToOrder(int userId, int paymentId, Integer couponId, double totalPrice, int status,
                              int productId, int quantity, double unitPrice, float discount, int productDetailId) {
        sql1 = "INSERT INTO `orders` (`userId`, `paymentId`, `couponId`, `orderDate`, `totalPrice`, `status`) " +
                "VALUES (?, ?, ?, NOW(), ?, ?)";
        sql2 = "INSERT INTO `orderitem` (`orderId`, `productId`, `quantity`, `unitPrice`, `discount`, `productDetailId`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try(Connection connection = h.getConnection()) {
                connection.setAutoCommit(false);

                try(PreparedStatement stmt1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
                    // add data for stmt1
                    stmt1.setInt(1, userId);
                    stmt1.setInt(2, paymentId);
                    if(couponId != null) {
                        stmt1.setInt(3, couponId);
                    } else {
                        stmt1.setNull(3, java.sql.Types.INTEGER);
                    }
                    stmt1.setDouble(4, totalPrice);
                    stmt1.setInt(5, status);

                    int rowsAffected = stmt1.executeUpdate();

                    if(rowsAffected > 0) {
                        try(ResultSet generatedKeys = stmt1.getGeneratedKeys()) {
                            if(generatedKeys.next()) {
                                int orderId = generatedKeys.getInt(1);

                                try(PreparedStatement stmt2 = connection.prepareStatement(sql2)) {
                                    stmt2.setInt(1, orderId);
                                    stmt2.setInt(2, productId);
                                    stmt2.setInt(3, quantity);
                                    stmt2.setDouble(4, unitPrice);
                                    stmt2.setFloat(5, discount);
                                    stmt2.setInt(6, productDetailId);

                                    int rowsAffected2 = stmt2.executeUpdate();
                                    if(rowsAffected2 > 0) {
                                        connection.commit();
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                connection.rollback();
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
