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
}
