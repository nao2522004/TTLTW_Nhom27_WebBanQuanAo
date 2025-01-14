package vn.edu.hcmuaf.fit.webbanquanao.service.adminService;

import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AProductDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AProduct;


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
//        System.out.println(a.showProduct());
        // Chuyển chuỗi thành đối tượng Date
        String releaseDateString = "2024-10-06";
        Date releaseDate = Date.valueOf(releaseDateString);
        a.updateProduct(5, new AProduct(5, 2, 4, 4, "QUẦN DÁNG XUÔNG XẾP LY BY RUYCH.", "Size: M, L, XL...\n... (Rút gọn nếu cần)", releaseDate, 110, 369000.00, false));
    }


}
