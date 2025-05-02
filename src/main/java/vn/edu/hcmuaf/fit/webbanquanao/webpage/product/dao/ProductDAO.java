package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao;

import jakarta.annotation.Nullable;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductDAO {
    List<Product> data;
    JDBIConnector conn;
    String query, query2;

    public ProductDAO() {
        conn = new JDBIConnector();
        data = getAllProducts();
    }

    // Get product by id
    public Product getById(int id) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Get all products
    public List<Product> getAllProducts() {
        Map<Integer, Product> re = new HashMap<>();
        query = "SELECT" +
                "   id," +
                "   typeId," +
                "   categoryId," +
                "   supplierId," +
                "   productname," +
                "   description," +
                "   releaseDate," +
                "   unitSold," +
                "   unitPrice," +
                "   status," +
                "   id AS parameter " +
                "FROM products p";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        return getProductsByQuery(query, query2, null);
    }

    // Get sale products
    public List<Product> getSaleProducts() {
        Map<Integer, Product> re = new HashMap<>();
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status," +
                "   p.id AS parameter " +
                "FROM products p " +
                "JOIN sales_product sp ON p.id = sp.productId " +
                "JOIN sales s ON s.id = sp.saleId";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        return getProductsByQuery(query, query2, null);
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status," +
                "   oi.productDetailId AS parameter " +
                "FROM products p " +
                "JOIN orderitem oi ON p.id = oi.productId " +
                "JOIN orders o ON oi.orderId = o.id " +
                "WHERE YEAR(o.orderDate) = YEAR(CURDATE()) " +
                "ORDER BY p.unitSold DESC";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE id = ?";

        return getProductsByQuery(query, query2, null);
    }

    // Get list products by category
    public List<Product> getProductsByCategory(String category) {
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status," +
                "   p.id AS parameter " +
                "FROM products p " +
                "JOIN categories c ON p.categoryId = c.id " +
                "WHERE c.name = ?";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE id = ?";

        return getProductsByQuery(query, query2, category);
    }

    // Get products by query
    public List<Product> getProductsByQuery(String sql1, String sql2, @Nullable String category) {
        List<Product> list = new ArrayList<>();
        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(sql1)) {
                if (category != null) {
                    stmt.setString(1, category);
                }
                try (ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        Product p = new Product();
                        p.setId(rs.getInt("id"));
                        p.setTypeId(rs.getInt("typeId"));
                        p.setCategoryId(rs.getInt("categoryId"));
                        p.setSupplierId(rs.getInt("supplierId"));
                        p.setProductName(rs.getString("productname"));
                        p.setDescription(rs.getString("description"));
                        p.setReleaseDate(rs.getDate("releaseDate"));
                        p.setUnitSold(rs.getInt("unitSold"));
                        p.setUnitPrice(rs.getInt("unitPrice"));
                        p.setStatus(rs.getInt("status"));

                        try (PreparedStatement stmt2 = h.getConnection().prepareStatement(sql2)) {
                            stmt2.setInt(1, rs.getInt("parameter"));
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                while(rs2.next()) {
                                    ProductDetail detail = new ProductDetail();
                                    detail.setId(rs2.getInt("id"));
                                    detail.setProductId(rs2.getInt("productId"));
                                    detail.setSize(rs2.getString("size"));
                                    detail.setStock(rs2.getInt("stock"));
                                    detail.setImage(rs2.getString("image"));
                                    detail.setColor(rs2.getString("color"));
                                    p.getDetails().add(detail);
                                }
                            }
                        }

                        int index = list.indexOf(p);
                        if (index != -1) {
                            Product existing = list.get(index);
                            existing.addDetails(p.getDetails());
                        } else {
                            list.add(p);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        });
    }

    // Get filtered products
    public List<Product> getFilteredProducts(String category, String[] type, String[] sizeF, Double priceMin, Double priceMax) {
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status " +
                "FROM products p " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "WHERE c.name = ?";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        if (type != null && type.length > 0) {
            query += " AND t.name IN (" + String.join(",", Collections.nCopies(type.length, "?")) + ")";
        }

        List<Product> re = conn.get().withHandle(h -> {
            List<Product> list = new ArrayList<>();
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                int paramIndex = 1;
                stmt.setString(paramIndex++, category);
                if (type != null && type.length > 0) {
                    for (String t : type) {
                        stmt.setString(paramIndex++, t);
                    }
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Product p = new Product();
                        p.setId(rs.getInt("id"));
                        p.setTypeId(rs.getInt("typeId"));
                        p.setCategoryId(rs.getInt("categoryId"));
                        p.setSupplierId(rs.getInt("supplierId"));
                        p.setProductName(rs.getString("productname"));
                        p.setDescription(rs.getString("description"));
                        p.setReleaseDate(rs.getDate("releaseDate"));
                        p.setUnitSold(rs.getInt("unitSold"));
                        p.setUnitPrice(rs.getDouble("unitPrice"));
                        p.setStatus(rs.getInt("status"));

                        try (PreparedStatement stmt2 = h.getConnection().prepareStatement(query2)) {
                            stmt2.setInt(1, p.getId());
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                while (rs2.next()) {
                                    ProductDetail detail = new ProductDetail();
                                    detail.setId(rs2.getInt("id"));
                                    detail.setProductId(rs2.getInt("productId"));
                                    detail.setSize(rs2.getString("size"));
                                    detail.setStock(rs2.getInt("stock"));
                                    detail.setImage(rs2.getString("image"));
                                    detail.setColor(rs2.getString("color"));
                                    p.getDetails().add(detail);
                                }
                            }
                        }

                        list.add(p);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        });

        if (sizeF != null && sizeF.length > 0) {
            for (Product product : re) {
                boolean hasSize = false;

                sizeLoop:
                for (String size : sizeF) {
                    for (ProductDetail productDetail : product.getDetails()) {
                        if (productDetail.getSize().equals(size)) {
                            hasSize = true;
                            break sizeLoop;
                        }
                    }
                }

                if (!hasSize) {
                    re.remove(product);
                }
            }
        }
        if (priceMin != null) {
            re.removeIf(product -> product.getUnitPrice() < priceMin);
        }
        if (priceMax != null) {
            re.removeIf(product -> product.getUnitPrice() > priceMax);
        }

        return re;
    }

    // search products by name
    public Map<Integer, Product> searchByName(String name) {
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
                "WHERE p.productname LIKE ?";

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

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.getFilteredProducts("Nam", new String[]{"Quần"}, new String[]{"M"}, null, null));
    }
}