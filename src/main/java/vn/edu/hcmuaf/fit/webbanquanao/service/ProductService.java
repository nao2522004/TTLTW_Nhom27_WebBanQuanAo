package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    ProductDAO dao = new ProductDAO();

    // Lay ra tat ca san pham
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    // Lay ra danh sach san pham theo so luong va category
//    public List<Product> getProducts(int total, String category) {
//        return dao.getProducts(total, category);
//    }

    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();
        Map<Integer, Product> uniqueProducts = new HashMap<>();

        for (Product product : dao.getAllProducts()) {
            if(!uniqueProducts.containsKey(product.getId())) {
                uniqueProducts.put(product.getId(), product);
                list.add(product);
            }
        }

        return list;
    }
}
