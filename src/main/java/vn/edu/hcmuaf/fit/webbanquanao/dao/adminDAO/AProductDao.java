package vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO;

import vn.edu.hcmuaf.fit.webbanquanao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AProduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class AProductDao {
    public Map<Integer, AProduct> listProduct;

    public AProductDao() {
        listProduct = getAllProducts();
    }

    public Map<Integer, AProduct> getAllProducts() {
        Map<Integer, AProduct> products = new LinkedHashMap<>();
        String sql = "SELECT * FROM products ORDER BY id DESC;";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AProduct product = new AProduct();
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
            AProduct product = (AProduct) obj;
            listProduct.replace(id, product);
            String sql = "UPDATE products SET id = ?, typeId = ?, categoryId = ?, supplierId = ?, productName = ?, description = ?, releaseDate = ?, unitSold = ?, unitPrice = ?, status = ? WHERE id = ?;";
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setInt(1, product.getId());
                ps.setInt(2, product.getTypeId());
                ps.setInt(3, product.getCategoryId());
                ps.setInt(4, product.getSupplierId());
                ps.setString(5, product.getName());
                ps.setString(6, product.getDescription());
                ps.setDate(7, product.getReleaseDate());
                ps.setInt(8, product.getUnitSold());
                ps.setDouble(9, product.getUnitPrice());
                ps.setBoolean(10, product.isStatus());
                ps.setInt(11 , id);
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
