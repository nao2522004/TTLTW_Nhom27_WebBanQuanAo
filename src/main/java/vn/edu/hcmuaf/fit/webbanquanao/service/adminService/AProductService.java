package vn.edu.hcmuaf.fit.webbanquanao.service.adminService;

import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AProductDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AProduct;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AProductDetails;


import java.sql.Date;
import java.util.Map;

public class AProductService {
    AProductDao productDao;

    public AProductService() {
        productDao = new AProductDao();
    }

    public Map<Integer, AProduct> showProduct() {
        return productDao.listProduct;
    }

    public Map<Integer, AProductDetails> showProductDetails(Integer id) {
        return productDao.getAllProductDetails(id);
    }

    public AProduct getProductById(Integer id) {
        if (!productDao.listProduct.containsKey(id)) return null;
        return productDao.listProduct.get(id);
    }

    public boolean updateProduct(Integer id, AProduct product) {
        if (!productDao.listProduct.containsKey(id)) return false;
        return productDao.update(product, id);
    }

    public static void main(String[] args) {
        AProductService a = new AProductService();
      System.out.println(a.showProductDetails(1));

    }


}
