package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartItem;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

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

    // Lấy ra tất cả các sản phẩm trong giỏ hàng
    public List<CartItem> getAllCartItems(int userId) {
        String sql = "SELECT cd.id, cd.cartId, cd.couponId, cd.quantity, cd.unitPrice, "
                + "pd.productId, pd.size, pd.stock, pd.image, pd.color, "
                + "p.productName as name "
                + "FROM cart c "
                + "JOIN cartdetail cd ON c.id = cd.cartId "
                + "JOIN product_details pd ON cd.productDetailsId = pd.id "
                + "JOIN products p ON pd.productId = p.id "
                + "WHERE c.userId = ?";

        return conn.get().withHandle(h -> {
           try(PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
               stmt.setInt(1, userId);
               try(ResultSet rs = stmt.executeQuery()) {
                   List<CartItem> cartItems = new ArrayList<>();
                   while (rs.next()) {
                       CartItem item = new CartItem(
                               rs.getInt("id"),
                               rs.getInt("cartId"),
                               rs.getInt("couponId"),
                               rs.getInt("quantity"),
                               rs.getDouble("unitPrice"),
                               rs.getInt("productId"),
                               rs.getString("size"),
                               rs.getInt("stock"),
                               rs.getString("image"),
                               rs.getString("color"),
                               rs.getString("name")
                       );
                       cartItems.add(item);
                   }
                   return cartItems;
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return null;
           }
        });
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(int userId, int couponId, int quantity, double unitPrice, int productDetailId) {
        int cartId = createCart(userId);
        return add(userId, cartId, couponId, quantity, unitPrice, productDetailId);
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
    public boolean add(int userId, int cartId, int couponId, int quantity, double unitPrice, int productDetailId) {
        String sql = "INSERT INTO cartdetail (cartId, couponId, quantity, unitPrice, productDetailsId) VALUES (?, ?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
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

    // Kiểm tra xem sản phẩm này đã tồn tại trong giỏ hàng chưa
    // Nếu có, trả về id của cartDetail, nếu không trả về -1
    public int hasProduct(int userId, int productDetailId) {
        String sql = "SELECT cd.id FROM cart c " +
                "JOIN cartdetail cd ON c.id = cd.cartId " +
                "WHERE c.userId = ? AND cd.productDetailsId = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, productDetailId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        });
    }

    // Cập nhật lại số lượng của một sản phẩm trong giỏ hàng
    public boolean updateCart(int userId, int productDetailId, int quantity) {
        String sql = "UPDATE cartdetail cd " +
                "JOIN cart c ON cd.cartId = c.id " +
                "SET cd.quantity = ? " +
                "WHERE c.userId = ? AND cd.productDetailsId = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, userId);
                stmt.setInt(3, productDetailId);

                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    // Lấy ra quantity của một sản phẩm trong giỏ hàng
    // Trả về -1 nếu không tìm thấy hoặc có lỗi
    public int getQuantityOfProduct(int userId, int productDetailId) {
        String sql = "SELECT cd.quantity FROM cart c " +
                "JOIN cartdetail cd ON c.id = cd.cartId " +
                "WHERE c.userId = ? AND cd.productDetailsId = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, productDetailId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("quantity");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        });
    }

    // Xoá một sản phẩm khỏi giỏ hàng
    public boolean removeItem(int cartDetailId) {
        String sql = "DELETE FROM cartdetail WHERE id = ?";

        return conn.get().withHandle(h -> {
           try(PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
               stmt.setInt(1, cartDetailId);
               return stmt.executeUpdate() > 0;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
        });
    }

    // Lấy ra chi tiết sản phẩm theo size và color
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

    // Tính tổng tiền của tất cả sản phẩm trong giỏ hàng
    public double getTotalPrice(int userId) {
        String sql = "SELECT SUM(cd.unitPrice * cd.quantity) AS Total " +
                        "FROM cartdetail cd " +
                        "JOIN cart c ON cd.cartId = c.id " +
                        "WHERE  c.userId = ?";

        return conn.get().withHandle(h -> {
           try(PreparedStatement stmt = h.getConnection().prepareStatement(sql)) {
               stmt.setInt(1, userId);
               ResultSet rs = stmt.executeQuery();
               if(rs.next()) {
                   return rs.getDouble("Total");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return 0.0;
        });
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
//        product.setId(rs.getInt("id"));
//        product.setName(rs.getString("name"));
//        product.setPrice(rs.getDouble("price"));
        return product;
    }
}
