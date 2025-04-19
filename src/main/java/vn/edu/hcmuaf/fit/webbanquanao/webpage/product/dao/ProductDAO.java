package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductDAO {
    Map<Integer, Product> data = new HashMap<>();
    JDBIConnector conn;
    String query;

    public ProductDAO() {
        conn = new JDBIConnector();
        data = getAllProducts();
    }

    public Product getById(int id) {
        if (!data.containsKey(id)) {
            return null;
        }
        return data.get(id);
    }

    // Get all products
    public Map<Integer, Product> getAllProducts() {
        Map<Integer, Product> re = new HashMap<>();
        query = "SELECT" +
                "   p.id AS id," +
                "   t.name AS type," +
                "   c.name AS category," +
                "   s.supplierName AS supplier," +
                "   p.productname AS name," +
                "   p.description AS description," +
                "   p.releaseDate AS releaseDate," +
                "   p.unitSold AS unitSold," +
                "   p.unitPrice AS unitPrice," +
                "   p.status AS status," +
                "   pd.size AS size," +
                "   pd.stock AS stock," +
                "   pd.image AS image," +
                "   pd.color AS color, " +
                "   pd.id AS productDetailId " +
                "FROM products p " +
                "JOIN product_details pd ON p.id = pd.productId " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "JOIN suppliers s ON p.supplierId = s.id";

        return getProductsByQuery(query);
    }

    // Get sale products
    public Map<Integer, Product> getSaleProducts() {
        Map<Integer, Product> re = new HashMap<>();
        query = "SELECT" +
                "   p.id AS id," +
                "   t.name AS type," +
                "   c.name AS category," +
                "   s.supplierName AS supplier," +
                "   p.productname AS name," +
                "   p.description AS description," +
                "   p.releaseDate AS releaseDate," +
                "   p.unitSold AS unitSold," +
                "   p.unitPrice AS unitPrice," +
                "   p.status AS status," +
                "   pd.size AS size," +
                "   pd.stock AS stock," +
                "   pd.image AS image," +
                "   pd.color AS color, " +
                "   pd.id AS productDetailId " +
                "FROM products p " +
                "JOIN product_details pd ON p.id = pd.productId " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "JOIN suppliers s ON p.supplierId = s.id " +
                "JOIN sales_product sp ON p.id = sp.productId " +
                "JOIN sales ss ON ss.id = sp.saleId";

        return getProductsByQuery(query);
    }

    // Get best-selling products
    public Map<Integer, Product> getBestSellingProducts() {
        query = "SELECT" +
                "   p.id AS id," +
                "   t.name AS type," +
                "   c.name AS category," +
                "   s.supplierName AS supplier," +
                "   p.productname AS name," +
                "   p.description AS description," +
                "   p.releaseDate AS releaseDate," +
                "   p.unitSold AS unitSold," +
                "   p.unitPrice AS unitPrice," +
                "   p.status AS status," +
                "   pd.size AS size," +
                "   pd.stock AS stock," +
                "   pd.image AS image," +
                "   pd.color AS color, " +
                "   pd.id AS productDetailId " +
                "FROM products p " +
                "JOIN product_details pd ON p.id = pd.productId " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "JOIN suppliers s ON p.supplierId = s.id " +
                "JOIN orderitem oi ON p.id = oi.productId " +
                "JOIN orders o ON oi.orderId = o.id " +
                "WHERE MONTH(o.orderDate) = MONTH(CURDATE()) AND YEAR(o.orderDate) = YEAR(CURDATE()) " +
                "ORDER BY p.unitSold DESC";

        return getProductsByQuery(query);
    }

    // Get products by query
    public Map<Integer, Product> getProductsByQuery(String query) {
//        return conn.get().withHandle(h -> {
//            Map<Integer, Product> result = new HashMap<>();
//            try (PreparedStatement stmt = h.getConnection().prepareStatement(query);
//                 ResultSet rs = stmt.executeQuery()
//            ) {
//                while (rs.next()) {
//                    int productId = rs.getInt("id");
//                    Product p = result.getOrDefault(productId, new Product());
//
//                    p.setId(productId);
//                    p.setType(rs.getString("type"));
//                    p.setCategory(rs.getString("category"));
//                    p.setSupplier(rs.getString("supplier"));
//                    p.setName(rs.getString("name"));
//                    p.setDescription(rs.getString("description"));
//                    p.setReleaseDate(rs.getDate("releaseDate"));
//                    p.setUnitSold(rs.getInt("unitSold"));
//                    p.setUnitPrice(rs.getDouble("unitPrice"));
//                    p.setStatus(rs.getBoolean("status"));
//                    p.setStock(rs.getInt("stock"));
//                    p.setProductDetailId(rs.getInt("productDetailId"));
//
//                    String size = rs.getString("size");
//                    String image = rs.getString("image");
//                    String color = rs.getString("color");
//
//                    if (!p.getSizes().contains(size))
//                        p.getSizes().add(size);
//                    if (!p.getImages().contains(image))
//                        p.getImages().add(image);
//                    if (!p.getColors().contains(color))
//                        p.getColors().add(color);
//
//                    result.put(productId, p);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return result;
//        });
        Map<Integer, Product> result = new HashMap<>();
        return result;
    }

    // Get list products by category
    public Map<Integer, Product> getProductsByCategory(String category) {
//        query = "SELECT" +
//                "   p.id AS id," +
//                "   t.name AS type," +
//                "   c.name AS category," +
//                "   s.supplierName AS supplier," +
//                "   p.productname AS name," +
//                "   p.description AS description," +
//                "   p.releaseDate AS releaseDate," +
//                "   p.unitSold AS unitSold," +
//                "   p.unitPrice AS unitPrice," +
//                "   p.status AS status," +
//                "   pd.size AS size," +
//                "   pd.stock AS stock," +
//                "   pd.image AS image," +
//                "   pd.color AS color, " +
//                "   pd.id AS productDetailId " +
//                "FROM products p " +
//                "JOIN product_details pd ON p.id = pd.productId " +
//                "JOIN categories c ON p.categoryId = c.id " +
//                "JOIN types t ON p.typeId = t.id " +
//                "JOIN suppliers s ON p.supplierId = s.id " +
//                "WHERE c.name = ?";
//
//        return conn.get().withHandle(h -> {
//            Map<Integer, Product> result = new HashMap<>();
//            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
//                stmt.setString(1, category);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        int productId = rs.getInt("id");
//                        Product p = result.getOrDefault(productId, new Product());
//
//                        p.setId(productId);
//                        p.setType(rs.getString("type"));
//                        p.setCategory(rs.getString("category"));
//                        p.setSupplier(rs.getString("supplier"));
//                        p.setName(rs.getString("name"));
//                        p.setDescription(rs.getString("description"));
//                        p.setReleaseDate(rs.getDate("releaseDate"));
//                        p.setUnitSold(rs.getInt("unitSold"));
//                        p.setUnitPrice(rs.getDouble("unitPrice"));
//                        p.setStatus(rs.getBoolean("status"));
//                        p.setStock(rs.getInt("stock"));
//
//                        String size = rs.getString("size");
//                        String image = rs.getString("image");
//                        String color = rs.getString("color");
//
//                        if (!p.getSizes().contains(size))
//                            p.getSizes().add(size);
//                        if (!p.getImages().contains(image))
//                            p.getImages().add(image);
//                        if (!p.getColors().contains(color))
//                            p.getColors().add(color);
//
//                        result.put(productId, p);
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return result;
//        });
        Map<Integer, Product> result = new HashMap<>();
        return result;
    }

    // Get filtered products
    public Map<Integer, Product> getFilteredProducts(String category, String[] type, String[] sizeF, Double priceMin, Double priceMax) {
//        query = "SELECT" +
//                "   p.id AS id," +
//                "   t.name AS type," +
//                "   c.name AS category," +
//                "   s.supplierName AS supplier," +
//                "   p.productname AS name," +
//                "   p.description AS description," +
//                "   p.releaseDate AS releaseDate," +
//                "   p.unitSold AS unitSold," +
//                "   p.unitPrice AS unitPrice," +
//                "   p.status AS status," +
//                "   pd.size AS size," +
//                "   pd.stock AS stock," +
//                "   pd.image AS image," +
//                "   pd.color AS color, " +
//                "   pd.id AS productDetailId " +
//                "FROM products p " +
//                "JOIN product_details pd ON p.id = pd.productId " +
//                "JOIN categories c ON p.categoryId = c.id " +
//                "JOIN types t ON p.typeId = t.id " +
//                "JOIN suppliers s ON p.supplierId = s.id " +
//                "WHERE c.name = ?";
//
//        if (type != null && type.length > 0) {
//            query += " AND t.name IN (" + String.join(",", Collections.nCopies(type.length, "?")) + ")";
//        }
//        if (sizeF != null && sizeF.length > 0) {
//            query += " AND pd.size IN (" + String.join(",", Collections.nCopies(sizeF.length, "?")) + ")";
//        }
//        if (priceMin != null) {
//            query += " AND p.unitPrice >= ?";
//        }
//        if (priceMax != null) {
//            query += " AND p.unitPrice <= ?";
//        }
//
//        return conn.get().withHandle(h -> {
//            Map<Integer, Product> result = new HashMap<>();
//            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
//                int paramIndex = 1;
//                stmt.setString(paramIndex++, category);
//                if (type != null && type.length > 0) {
//                    for (String t : type) {
//                        stmt.setString(paramIndex++, t);
//                    }
//                }
//                if (sizeF != null && sizeF.length > 0) {
//                    for (String size : sizeF) {
//                        stmt.setString(paramIndex++, size);
//                    }
//                }
//                if (priceMin != null) {
//                    stmt.setDouble(paramIndex++, priceMin);
//                }
//                if (priceMax != null) {
//                    stmt.setDouble(paramIndex, priceMax);
//                }
//
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        int productId = rs.getInt("id");
//                        Product p = result.getOrDefault(productId, new Product());
//
//                        p.setId(productId);
//                        p.setType(rs.getString("type"));
//                        p.setCategory(rs.getString("category"));
//                        p.setSupplier(rs.getString("supplier"));
//                        p.setName(rs.getString("name"));
//                        p.setDescription(rs.getString("description"));
//                        p.setReleaseDate(rs.getDate("releaseDate"));
//                        p.setUnitSold(rs.getInt("unitSold"));
//                        p.setUnitPrice(rs.getDouble("unitPrice"));
//                        p.setStatus(rs.getBoolean("status"));
//                        p.setStock(rs.getInt("stock"));
//
//                        String size = rs.getString("size");
//                        String image = rs.getString("image");
//                        String color = rs.getString("color");
//
//                        if (!p.getSizes().contains(size))
//                            p.getSizes().add(size);
//                        if (!p.getImages().contains(image))
//                            p.getImages().add(image);
//                        if (!p.getColors().contains(color))
//                            p.getColors().add(color);
//
//                        result.put(productId, p);
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return result;
//        });
        Map<Integer, Product> result = new HashMap<>();
        return result;
    }

    public Map<Integer, Product> searchByName(String name) {
//        String query = "SELECT" +
//                "   p.id AS id," +
//                "   t.name AS type," +
//                "   c.name AS category," +
//                "   s.supplierName AS supplier," +
//                "   p.productname AS name," +
//                "   p.description AS description," +
//                "   p.releaseDate AS releaseDate," +
//                "   p.unitSold AS unitSold," +
//                "   p.unitPrice AS unitPrice," +
//                "   p.status AS status," +
//                "   pd.size AS size," +
//                "   pd.stock AS stock," +
//                "   pd.image AS image," +
//                "   pd.color AS color, " +
//                "   pd.id AS productDetailId " +
//                "FROM products p " +
//                "JOIN product_details pd ON p.id = pd.productId " +
//                "JOIN categories c ON p.categoryId = c.id " +
//                "JOIN types t ON p.typeId = t.id " +
//                "JOIN suppliers s ON p.supplierId = s.id " +
//                "WHERE p.productname LIKE ?";
//
//        return conn.get().withHandle(h -> {
//            Map<Integer, Product> result = new HashMap<>();
//            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
//                stmt.setString(1, "%" + name + "%");
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        int productId = rs.getInt("id");
//                        Product p = result.getOrDefault(productId, new Product());
//
//                        p.setId(productId);
//                        p.setType(rs.getString("type"));
//                        p.setCategory(rs.getString("category"));
//                        p.setSupplier(rs.getString("supplier"));
//                        p.setName(rs.getString("name"));
//                        p.setDescription(rs.getString("description"));
//                        p.setReleaseDate(rs.getDate("releaseDate"));
//                        p.setUnitSold(rs.getInt("unitSold"));
//                        p.setUnitPrice(rs.getDouble("unitPrice"));
//                        p.setStatus(rs.getBoolean("status"));
//                        p.setStock(rs.getInt("stock"));
//
//                        String size = rs.getString("size");
//                        String image = rs.getString("image");
//                        String color = rs.getString("color");
//
//                        if (!p.getSizes().contains(size))
//                            p.getSizes().add(size);
//                        if (!p.getImages().contains(image))
//                            p.getImages().add(image);
//                        if (!p.getColors().contains(color))
//                            p.getColors().add(color);
//
//                        result.put(productId, p);
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return result;
//        });
        Map<Integer, Product> result = new HashMap<>();
        return result;
    }
}