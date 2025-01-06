package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    ProductDAO dao;

    public ProductService() {
        dao = new ProductDAO();
    }

    // Get all products
    public List<Product> getAllProducts() {
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

    // Get sale products
    public List<Product> getSaleProducts() {
        List<Product> list = new ArrayList<>();
        Map<Integer, Product> uniqueProducts = new HashMap<>();
        for (Product product : dao.getSaleProducts()) {
            if(!uniqueProducts.containsKey(product.getId())) {
                uniqueProducts.put(product.getId(), product);
                list.add(product);
            }
        }
        return list;
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        List<Product> list = new ArrayList<>();
        Map<Integer, Product> uniqueProducts = new HashMap<>();
        for (Product product : dao.getBestSellingProducts()) {
            if(!uniqueProducts.containsKey(product.getId())) {
                uniqueProducts.put(product.getId(), product);
                list.add(product);
            }
        }
        return list;
    }

    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        List<Product> list = new ArrayList<>();
        Map<Integer, Product> uniqueProducts = new HashMap<>();
        for (Product product : dao.getProductsByCategory(category)) {
            if(!uniqueProducts.containsKey(product.getId())) {
                uniqueProducts.put(product.getId(), product);
                list.add(product);
            }
        }
        return list;
    }

    public Product getDetail(String pro){
        try {
            int id = Integer.parseInt(pro);
            return ProductDAO.getById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
