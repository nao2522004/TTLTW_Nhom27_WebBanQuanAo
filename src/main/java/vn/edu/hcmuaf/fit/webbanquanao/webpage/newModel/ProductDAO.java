package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(int productId, String color, String size, int quantity) {
        boolean result = false;
        return result;
    }

    // Cập nhật lại số lượng của một sản phẩm trong giỏ hàng
    public boolean updateCart(int productId, int quantity) {
        boolean result = false;
        return result;
    }

    // Xoá một sản phẩm khỏi giỏ hàng
    public boolean removeItem(int productId) {
        boolean result = false;
        return result;
    }

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

    public List<Product> getAllProducts() {
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

    // Tính tổng tiền của tất cả sản phẩm trong giỏ hàng
    public double getTotalPrice() {
        double result = 0;
        return result;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
//        product.setId(rs.getInt("id"));
//        product.setName(rs.getString("name"));
//        product.setPrice(rs.getDouble("price"));
        return product;
    }
}
