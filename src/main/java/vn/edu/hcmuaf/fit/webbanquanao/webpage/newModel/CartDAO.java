package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private String query, query2;
    private JDBIConnector conn;

    public CartDAO() {
        conn = new JDBIConnector();
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(int userId, int couponId, int quantity, double unitPrice, int productDetailId) {
        int cartId = createCart(userId);
        return add(cartId, couponId, quantity, unitPrice, productDetailId);
    }

    // Lấy id giỏ hàng của user hoặc tạo mới giỏ hàng
    public Integer createCart(int userId) {
        query = "SELECT id FROM cart WHERE userId = ?";
        query2 = "INSERT INTO cart (userId) VALUES (?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt1 = h.getConnection().prepareStatement(query)) {
                stmt1.setInt(1, userId);
                ResultSet rs = stmt1.executeQuery();

                // Nếu giỏ hàng đã tồn tại thì lấy id ra
                if (rs.next()) {
                    return rs.getInt(1);
                }

                // Nếu không tồn tại thì tạo mới giỏ hàng
                try (PreparedStatement stmt2 = h.getConnection().prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
                    stmt2.setInt(1, userId);
                    int rowAffected = stmt2.executeUpdate();

                    if (rowAffected > 0) {
                        try (ResultSet generatedKeys = stmt2.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                return generatedKeys.getInt(1);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    // Thêm sản phẩm vào chi tiết giỏ hàng
    public boolean add(int cartId, int couponId, int quantity, double unitPrice, int productDetailId) {
        query = "INSERT INTO cartdetail (cartId, couponId, quantity, unitPrice, productDetailsId) VALUES (?, ?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, cartId);
                stmt.setInt(2, couponId);
                stmt.setInt(3, quantity);
                stmt.setDouble(4, unitPrice);
                stmt.setInt(5, productDetailId);

                int rowAffected = stmt.executeUpdate();
                return rowAffected > 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
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

    public ProductDetail getProductDetailBySizeColor(String color, String size) {
        String sql = "SELECT * FROM product_details WHERE color = ? AND size = ?";
        ProductDetail productDetail = new ProductDetail();

        return conn.get().withHandle(h -> {
           try(PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
               stmt.setString(1, color);
               stmt.setString(2, size);
               ResultSet rs = stmt.executeQuery();
               while(rs.next()) {
                   productDetail.setId(rs.getInt("id"));
                   productDetail.setProductId(rs.getInt("productId"));
                   productDetail.setSize(rs.getString("size"));
                   productDetail.setStock(rs.getInt("stock"));
                   productDetail.setImage(rs.getString("image"));
                   productDetail.setColor(rs.getString("color"));
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return productDetail;
        });
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
