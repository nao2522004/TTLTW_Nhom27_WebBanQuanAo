package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;

import java.util.*;

public class ProductService {
    ProductDAO dao;

    public ProductService() {
        dao = new ProductDAO();
    }

    // Get all products
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
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

    public Product getDetail(int id){
        try {
            return dao.getById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        ProductService service = new ProductService();
        List<String> sizes = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        for (Product pro : service.getAllProducts()) {
            if(pro.getId() == 1 && !sizes.contains(pro.getSize())) {
                sizes.add(pro.getSize());
            }
            if(pro.getId() == 1 && !colors.contains(pro.getColor())) {
                colors.add(pro.getColor());
            }
        }
        List<String> sizeOrder = Arrays.asList("S", "M", "L", "XL", "XXL", "XXXL", "XXXXL");
        sizes.sort(Comparator.comparingInt(sizeOrder::indexOf));
        System.out.println(sizes);
        System.out.println(colors);
    }
}
