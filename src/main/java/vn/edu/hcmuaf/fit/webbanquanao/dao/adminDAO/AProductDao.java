package vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class AProductDao {
    public Map<Integer, Product> listProduct;

    public AProductDao() {
        listProduct = getAllProducts();
    }

    public Map<Integer, Product> getAllProducts() {
        Map<Integer, Product> products = new LinkedHashMap<>();
        String sql = "SELECT\n" +
                "    p.id AS id,\n" +
                "    t.name AS type,\n" +
                "    c.name AS category,\n" +
                "    s.supplierName AS supplier,\n" +
                "    p.productname AS name,\n" +
                "    p.description AS description,\n" +
                "    p.releaseDate AS releaseDate,\n" +
                "    p.unitSold AS unitSold,\n" +
                "    p.unitPrice AS unitPrice,\n" +
                "    p.status AS status\n" +
                "FROM products p\n" +
                "JOIN product_details pd ON p.id = pd.productId\n" +
                "JOIN categories c ON p.categoryId = c.id\n" +
                "JOIN types t ON p.typeId = t.id\n" +
                "JOIN suppliers s ON p.supplierId = s.id \n" +
                "ORDER BY p.id DESC;  ";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setType(rs.getString("type"));
                    product.setCategory(rs.getString("category"));
                    product.setSupplier(rs.getString("supplier"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setReleaseDate(rs.getDate("releaseDate"));
                    product.setUnitSold(rs.getInt("unitSold"));
                    product.setUnitPrice(rs.getDouble("unitPrice"));
                    product.setStatus(rs.getBoolean("status"));
                    products.put(product.getId(), product);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach san pham: " + e.getMessage());
            }
            return products;
        });
    }


    public boolean create(Object obj) {
        return false;
    }


    public boolean update(Object obj, Integer id) {
        return JDBIConnector.get().withHandle(h -> {
            Product product = (Product) obj;
            listProduct.replace(id, product);
            String sql = "UPDATE products p\n" +
                    "JOIN product_details pd ON p.id = pd.productId\n" +
                    "JOIN categories c ON p.categoryId = c.id\n" +
                    "JOIN types t ON p.typeId = t.id\n" +
                    "JOIN suppliers s ON p.supplierId = s.id\n" +5
                    "SET\n" +
                    "    p.typeId = (SELECT id FROM types WHERE name = ?),\n" +
                    "    p.categoryId = (SELECT id FROM categories WHERE name = ?),\n" +
                    "    p.supplierId = (SELECT id FROM suppliers WHERE supplierName = ?),\n" +
                    "    p.productname = ?,\n" +
                    "    p.description = ?,\n" +
                    "    p.releaseDate = ?,\n" +
                    "    p.unitSold = ?,\n" +
                    "    p.unitPrice = ?,\n" +
                    "    p.status = ?\n" +
                    "WHERE p.id = ?;";
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setString(1, product.getType());
                ps.setString(2, product.getCategory());
                ps.setString(3, product.getSupplier());
                ps.setString(4, product.getName());
                ps.setString(5, product.getDescription());
                ps.setDate(6, product.getReleaseDate());
                ps.setInt(7, product.getUnitSold());
                ps.setDouble(8, product.getUnitPrice());
                ps.setBoolean(9, product.isStatus());
                ps.setInt(10 , id);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi update product: " + e.getMessage());
            }
            return false;
        });
    }


    public boolean delete(String userName) {
        return false;
    }

}
