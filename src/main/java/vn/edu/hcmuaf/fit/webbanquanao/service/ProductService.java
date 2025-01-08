package vn.edu.hcmuaf.fit.webbanquanao.service;

import vn.edu.hcmuaf.fit.webbanquanao.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import java.util.*;

public class ProductService {
    ProductDAO dao;

    public ProductService() {
        dao = new ProductDAO();
    }

    // Get all products
    public List<Product> getAllProducts() {
        List<Product> re = new ArrayList<>();
        dao.getAllProducts().forEach((key, value) -> {
            re.add(value);
        });
        return re;
    }

    // Get sale products
    public List<Product> getSaleProducts() {
        List<Product> list = new ArrayList<>();
        dao.getSaleProducts().forEach((key, value) -> {
            list.add(value);
        });
        return list;
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        List<Product> list = new ArrayList<>();
        dao.getBestSellingProducts().forEach((key, value) -> {
            list.add(value);
        });
        return list;
    }

    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        category = switch (category) {
            case "men" -> "Nam";
            case "women" -> "Nữ";
            case "children" -> "Trẻ em";
            default -> category;
        };

        List<Product> list = new ArrayList<>();
        dao.getProductsByCategory(category).forEach((key, value) -> {
            list.add(value);
        });
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
        System.out.println(service.getAllProducts());
    }
}
