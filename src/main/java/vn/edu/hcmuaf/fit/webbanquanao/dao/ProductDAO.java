package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    Map<Integer, Product> data = new HashMap<>();
    JDBIConnector conn;
    String query;

    public ProductDAO() {
        conn = new JDBIConnector();
        loadData();
    }

    public Product getById(int id) {
        if (!data.containsKey(id)) {
            return null;
        }
        return data.get(id);
    }

    // Get all products
    public List<Product> getAllProducts() {
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
                "JOIN suppliers s ON p.supplierId = s.id";

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

    // Get list products by category
    public List<Product> getProductsByCategory(String category) {
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
                "WHERE c.name = :category";

        return conn.get().withHandle(h -> {
            return h.createQuery(query).bind("category", category).mapToBean(Product.class).list();
        });
    }

    public void loadData() {
        for (Product product : getAllProducts()) {
            if(!data.containsKey(product.getId())) {
                data.put(product.getId(), product);
            }
        }
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.data);
    }
}