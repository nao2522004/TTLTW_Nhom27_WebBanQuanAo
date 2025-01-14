package vn.edu.hcmuaf.fit.webbanquanao.service.adminService;

import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AProductDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import java.util.Map;

public class AProductService {
    AProductDao productDao;

    public AProductService() {
        productDao = new AProductDao();
    }

    public Map<Integer, Product> showProduct() {
        return productDao.listProduct;
    }

    public Product getProductById(Integer id){
        if(!productDao.listProduct.containsKey(id)) return null;
        return productDao.listProduct.get(id);
    }

    public boolean updateProduct(Integer id, Product product){
        if(!productDao.listProduct.containsKey(id)) return false;
        return productDao.update(product, id);
    }

    public static void main(String[] args) {
        AProductService a = new AProductService();

        System.out.println(a.showProduct());
    }


}
