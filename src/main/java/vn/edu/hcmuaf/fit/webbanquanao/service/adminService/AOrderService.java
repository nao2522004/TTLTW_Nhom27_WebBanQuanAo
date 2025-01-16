package vn.edu.hcmuaf.fit.webbanquanao.service.adminService;

import vn.edu.hcmuaf.fit.webbanquanao.dao.adminDAO.AOrderDao;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AOrder;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AOrderItem;

import java.util.Map;

public class AOrderService {
    AOrderDao aOrderDao;

    public AOrderService() {
        aOrderDao = new AOrderDao();
    }

    public Map<Integer, AOrder> showOrders() {
        return aOrderDao.listOrders;
    }

    public boolean updateOrder(Object obj, Integer id) {
        if (!aOrderDao.listOrders.containsKey(id)) return false;
        return aOrderDao.update(obj, id);
    }

    public boolean deleteOrder(Integer id) {
        if (!aOrderDao.listOrders.containsKey(id)) return false;
        return aOrderDao.delete(id);
    }

    public Map<Integer, AOrderItem> showOrderItem(Integer id) {
        if (!aOrderDao.listOrders.containsKey(id)) return null;
        return aOrderDao.getAllOrderItems(id);
    }

    public boolean updateOrderItem(AOrderItem orderItem, Integer id, Integer orderId) {
        if (!aOrderDao.listOrders.containsKey(orderId)) return false;
        return aOrderDao.updateOrderDetails( orderItem, id, orderId);
    }

    public static void main(String[] args) {
        AOrderService a = new AOrderService();

        System.out.println(a.showOrderItem(7));
    }

}
