package vn.edu.hcmuaf.fit.webbanquanao.webpage.inventory;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.util.List;

public class InventoryDAO {
    private final Jdbi jdbi;

    public InventoryDAO() {
        this.jdbi = JDBIConnector.get();
    }

    // Get all products
    public List<ProductDetailInventory> getAllInventory() {
        String sql = "SELECT * FROM product_details";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(ProductDetailInventory.class)
                        .list()
        );
    }

    // Update stock
    public boolean updateStock(int productDetailId, int newStock) {
        String sql = "UPDATE product_details SET stock = :stock WHERE id = :id";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("stock", newStock)
                        .bind("id", productDetailId)
                        .execute()
        );
        return rows > 0;
    }

    // Increase stock
    public boolean increaseStock(int productDetailId, int amount) {
        String sql = "UPDATE product_details SET stock = stock + :amount WHERE id = :id";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("amount", amount)
                        .bind("id", productDetailId)
                        .execute()
        );
        return rows > 0;
    }

    // Decrease stock
    public boolean decreaseStock(int productDetailId, int amount) {
        String sql = "UPDATE product_details SET stock = stock - :amount WHERE id = :id AND stock >= :amount";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("amount", amount)
                        .bind("id", productDetailId)
                        .execute()
        );
        return rows > 0;
    }

    // Get list of products with stock below threshold
    public List<ProductDetailInventory> getLowStockItems(int threshold) {
        String sql = "SELECT * FROM product_details WHERE stock < :threshold";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("threshold", threshold)
                        .mapToBean(ProductDetailInventory.class)
                        .list()
        );
    }
}
