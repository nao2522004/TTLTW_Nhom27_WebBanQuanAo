package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.SaleProduct;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    static Map<Integer, Product> data = new HashMap<>();
    JDBIConnector conn;
    String query;

    public ProductDAO() {
        conn = new JDBIConnector();
    }

    public static Product getById(int id) {
        if (!data.containsKey(id)) {
            return null;
        }
        return data.get(id);
    }

    // Get all products
    public List<Product> getAllProducts() {
        query = "SELECT" +
                "   p.proid AS id," +
                "   t.NAME AS type," +
                "   c.NAME AS category," +
                "   s.suppliersname AS supplier," +
                "   p.productname AS name," +
                "   p.DESCRIPTION," +
                "   p.releasedate AS releaseDate," +
                "   p.unitSold," +
                "   p.unitprice AS unitPrice," +
                "   p.STATUS," +
                "   pd.size," +
                "   pd.stock," +
                "   pd.image AS img," +
                "   pd.color " +
                "FROM products p " +
                "JOIN product_details pd ON p.proid = pd.pro_id " +
                "JOIN categories c ON p.category_id = c.categoryid " +
                "JOIN types t ON p.type_id = t.typeid " +
                "JOIN suppliers s ON p.supplier_id = s.suppliersid";

        return conn.get().withHandle(h -> {
            return h.createQuery(query).mapToBean(Product.class).list();
        });
    }

    // Get sale products
    public List<Product> getSaleProducts() {
        query = "SELECT" +
                "   p.id AS id," +
                "   t.name AS type," +
                "   c.name AS category," +
                "   s.supplierName AS supplier," +
                "   p.productname AS name," +
                "   p.description," +
                "   p.releaseDate AS releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice AS unitPrice," +
                "   p.status," +
                "   pd.size," +
                "   pd.stock," +
                "   pd.image AS img," +
                "   pd.color " +
                "FROM products p " +
                "JOIN product_details pd ON p.id = pd.productId " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "JOIN suppliers s ON p.supplierId = s.id " +
                "JOIN sales_product sp ON p.id = sp.productId " +
                "JOIN sales ss ON ss.id = sp.saleId";

        return conn.get().withHandle(h -> {
           return h.createQuery(query).mapToBean(Product.class).list();
        });
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        query = "SELECT" +
                "   p.id AS id," +
                "   t.name AS type," +
                "   c.name AS category," +
                "   s.supplierName AS supplier," +
                "   p.productname AS name," +
                "   p.description," +
                "   p.releaseDate AS releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice AS unitPrice," +
                "   p.status," +
                "   pd.size," +
                "   pd.stock," +
                "   pd.image AS img," +
                "   pd.color " +
                "FROM products p " +
                "JOIN product_details pd ON p.id = pd.productId " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "JOIN suppliers s ON p.supplierId = s.id " +
                "JOIN orderitem oi ON p.id = oi.productId " +
                "JOIN orders o ON oi.orderId = o.id " +
                "WHERE MONTH(oi.orderDate) = MONTH(CURDATE()) AND YEAR(oi.orderDate) = YEAR(CURDATE()) " +
                "ORDER BY p.unitSold DESC " +
                "LIMIT 4";

        return conn.get().withHandle(h -> {
            return h.createQuery(query).mapToBean(Product.class).list();
        });
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.getBestSellingProducts());
    }
}
