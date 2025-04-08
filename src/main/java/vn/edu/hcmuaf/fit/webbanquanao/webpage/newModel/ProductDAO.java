package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";

//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToProduct(rs);
//                }
//            }
//        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                products.add(mapResultSetToProduct(rs));
//            }
//        }
        return products;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
//        product.setId(rs.getInt("id"));
//        product.setName(rs.getString("name"));
//        product.setPrice(rs.getDouble("price"));
        // Các trường khác nếu có
        return product;
    }

    public void updateProductStock(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ?";

//        try (Connection conn = DBUtil.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            conn.setAutoCommit(false);
//            stmt.setInt(1, quantity);
//            stmt.setInt(2, productId);
//            stmt.executeUpdate();
//            conn.commit();
//        } catch (SQLException e) {
//            conn.rollback();
//            throw e;
//        }
    }
}
