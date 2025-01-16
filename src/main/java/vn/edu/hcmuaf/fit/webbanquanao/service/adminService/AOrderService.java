package vn.edu.hcmuaf.fit.webbanquanao.service.adminService;

import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AOrderDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AOrder;

import java.util.Map;

public class AOrderService {
    AOrderDao aOrderDao;

    public AOrderService() {
        aOrderDao = new AOrderDao();
    }

    public Map<Integer, AOrder> showOrders() {
        return aOrderDao.listOrders;
    }

    public static void main(String[] args) {
        AOrderService a = new AOrderService();
        System.out.println(a.showOrders());
    }

}
