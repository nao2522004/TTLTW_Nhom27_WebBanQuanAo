package vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO;

import vn.edu.hcmuaf.fit.webbanquanao.dao.CRUIDDao;
import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class AProductDao implements CRUIDDao {
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


    @Override
    public boolean create(Object obj) {
        return false;
    }

    @Override
    public boolean update(Object obj, String userName) {
        return false;
    }

    @Override
    public boolean delete(String userName) {
        return false;
    }

}
