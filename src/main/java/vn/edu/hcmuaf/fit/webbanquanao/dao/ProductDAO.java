package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.SaleProduct;

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
        query = "SELECT" + "   p.proid AS id," + "   t.NAME AS type," + "   c.NAME AS category," + "   s.suppliersname AS supplier," + "   p.productname AS name," + "   p.DESCRIPTION," + "   p.releasedate AS releaseDate," + "   p.unitSold," + "   p.unitprice AS unitPrice," + "   p.STATUS," + "   pd.size," + "   pd.stock," + "   pd.image AS img," + "   pd.color " + "FROM products p " + "JOIN product_details pd ON p.proid = pd.pro_id " + "JOIN categories c ON p.category_id = c.categoryid " + "JOIN types t ON p.type_id = t.typeid " + "JOIN suppliers s ON p.supplier_id = s.suppliersid";

        return conn.get().withHandle(h -> {
            return h.createQuery(query).mapToBean(Product.class).list();
        });
    }

    // Get sale products
    public List<SaleProduct> getSaleProducts() {
        query = "SELECT" +
                "   p.proid AS id," +
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
                "   s.sales_name " +
                "   s.sales_description " +
                "   s.start_date " +
                "   s.end_date " +
                "FROM products p " +
                "JOIN product_details pd ON p.proid = pd.pro_id " +
                "JOIN sales_product sp ON p.proid = sp.product_id " +
                "JOIN sales s ON sp.sales_id = s.salesid";
        return null;
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        return null;
    }
}
