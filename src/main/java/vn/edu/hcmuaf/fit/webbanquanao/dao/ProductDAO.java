package vn.edu.hcmuaf.fit.webbanquanao.dao;

import vn.edu.hcmuaf.fit.webbanquanao.dao.db.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private JDBIConnector conn;

    public List<Product> getListProducts() {
        List<Product> list = new ArrayList<Product>();
        conn = new JDBIConnector();
        list = conn.getJdbi().withHandle(h -> {
            return h.createQuery("select * from products").mapToBean(Product.class).list();
        });
        return list;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.getListProducts());
    }
}
