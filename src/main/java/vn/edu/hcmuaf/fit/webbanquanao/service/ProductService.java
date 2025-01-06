package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    ProductDAO dao;

    // Get all products
    public List<Product> getAllProducts() {
        dao = new ProductDAO();
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
