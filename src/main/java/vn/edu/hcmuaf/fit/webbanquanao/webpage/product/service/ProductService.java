package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;

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
        category = transformCategory(category);

        List<Product> list = new ArrayList<>();
        dao.getProductsByCategory(category).forEach((key, value) -> {
            list.add(value);
        });
        return list;
    }

    // Get filtered products
    public List<Product> getFilteredProducts(String category, String[] type, String[] size, Double priceMin, Double priceMax, String sortPrice) {
        category = transformCategory(category);
        List<Product> list = new ArrayList<>();
        dao.getFilteredProducts(category, type, size, priceMin, priceMax).forEach((key, value) -> {
            list.add(value);
        });
        if("asc".equals(sortPrice)){
            list.sort(Comparator.comparingDouble(Product::getUnitPrice));
        } else if("desc".equals(sortPrice)){
            list.sort(Comparator.comparingDouble(Product::getUnitPrice).reversed());
        }
        return list;
    }

    // Transform category
    public String transformCategory(String category) {
        return category = switch (category) {
            case "men" -> "Nam";
            case "women" -> "Nữ";
            case "children" -> "Trẻ em";
            default -> category;
        };
    }

    // Search product by name
    public List<Product> searchByName(String name) {
        List<Product> list = new ArrayList<>();
        dao.searchByName(name).forEach((key, value) -> {
            list.add(value);
        });
        return list;
    }

    // Get products by Pagination
    public List<Product> getProductsByPagination(int page, int pageSize, List<Product> products) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());
        if (start >= products.size()) {
            return new ArrayList<>();
        }
        return products.subList(start, end);
    }

    // Get product by id
    public Product getDetail(int id){
        try {
            return dao.getById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        ProductService service = new ProductService();
//        System.out.println(service.getFilteredProducts("Nam", new String[]{"Quần"}, new String[]{"M","L"}, null, null, "desc"));
//        System.out.println(service.searchByName("Quần tây"));
//        System.out.println(service.getProductsByPagination(2, 2));
    }
}
