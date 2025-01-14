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
        String sql = "SELECT * FROM products ORDER BY id DESC;";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setTypeId(rs.getInt("typeId"));
                    product.setCategoryId(rs.getInt("categoryId"));
                    product.setSupplierId(rs.getInt("supplierId"));
                    product.setName(rs.getString("productName"));
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
                    "JOIN suppliers s ON p.supplierId = s.id\n" +
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
