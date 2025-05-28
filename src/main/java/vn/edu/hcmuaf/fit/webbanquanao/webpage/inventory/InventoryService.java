package vn.edu.hcmuaf.fit.webbanquanao.webpage.inventory;

import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    // Get all inventory products
    public List<ProductDetailInventory> getAllInventory() {
        return inventoryDAO.getAllInventory();
    }

    // Update new inventory quantity
    public boolean updateStock(int productDetailId, int newStock) {
        return inventoryDAO.updateStock(productDetailId, newStock);
    }

    // Increase inventory
    public boolean increaseStock(int productDetailId, int amount) {
        return inventoryDAO.increaseStock(productDetailId, amount);
    }

    // Decrease inventory
    public boolean decreaseStock(int productDetailId, int amount) {
        return inventoryDAO.decreaseStock(productDetailId, amount);
    }

    // Get low stock products list
    public List<ProductDetailInventory> getLowStockItems(int threshold) {
        return inventoryDAO.getLowStockItems(threshold);
    }
}
