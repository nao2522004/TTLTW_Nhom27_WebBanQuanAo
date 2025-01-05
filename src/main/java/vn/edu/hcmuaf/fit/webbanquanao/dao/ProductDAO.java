package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.List;

public class ProductDAO {
    JDBIConnector conn;
    String query;

    public ProductDAO() {
        conn = new JDBIConnector();
    }

    // Get all products
    public List<Product> getAllProducts() {
        query = "SELECT" + "   p.proid AS id," + "   t.NAME AS type," + "   c.NAME AS category," + "   s.suppliersname AS supplier," + "   p.productname AS name," + "   p.DESCRIPTION," + "   p.releasedate AS releaseDate," + "   p.unitSold," + "   p.unitprice AS unitPrice," + "   p.STATUS," + "   pd.size," + "   pd.stock," + "   pd.image AS img," + "   pd.color " + "FROM products p " + "JOIN product_details pd ON p.proid = pd.pro_id " + "JOIN categories c ON p.category_id = c.categoryid " + "JOIN types t ON p.type_id = t.typeid " + "JOIN suppliers s ON p.supplier_id = s.suppliersid";

        return conn.get().withHandle(h -> {
            return h.createQuery(query).mapToBean(Product.class).list();
        });
    }

    // Get sale products
    public List<Product> getSaleProducts() {
        String query = "";

        return conn.get().withHandle(h -> {
            return h.createQuery(query).mapToBean(Product.class).list();
        });
    }

    // Lay ra danh sach san pham theo so luong va category
//    public List<Product> getProducts(int total, String category) {
//        query = "SELECT top(?) * FROM products p "
//                + "JOIN categories c ON p.category_id = c.id "
//                + "WHERE c.name = ?";
//        return conn.getJdbi().withHandle(h -> {
//            return h.createQuery(query)
//                    .bind(0, total)
//                    .bind(1, category)
//                    .mapToBean(Product.class)
//                    .list();
//        });
//    }
}
