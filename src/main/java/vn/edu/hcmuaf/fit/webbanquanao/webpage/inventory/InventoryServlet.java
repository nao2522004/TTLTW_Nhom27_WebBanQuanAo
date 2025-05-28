package vn.edu.hcmuaf.fit.webbanquanao.webpage.inventory;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InventoryServlet", value = "/inventory")
public class InventoryServlet extends HttpServlet {
    private final InventoryService inventoryService = new InventoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equals("view")) {
            List<ProductDetailInventory> list = inventoryService.getAllInventory();
            request.setAttribute("inventoryList", list);
            request.getRequestDispatcher("/inventory.jsp").forward(request, response);

        } else if (action.equals("lowstock")) {
            String thresholdParam = request.getParameter("threshold");
            int threshold = thresholdParam != null ? Integer.parseInt(thresholdParam) : 5;
            List<ProductDetailInventory> lowStockList = inventoryService.getLowStockItems(threshold);
            request.setAttribute("inventoryList", lowStockList);
            request.setAttribute("threshold", threshold);
            request.getRequestDispatcher("/inventory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        int id = Integer.parseInt(request.getParameter("id"));
        int amount = Integer.parseInt(request.getParameter("amount"));

        boolean success = false;

        switch (type) {
            case "update":
                success = inventoryService.updateStock(id, amount);
                break;
            case "increase":
                success = inventoryService.increaseStock(id, amount);
                break;
            case "decrease":
                success = inventoryService.decreaseStock(id, amount);
                break;
        }

        if (success) {
            response.sendRedirect("inventory?action=view");
        } else {
            response.sendRedirect("inventory?action=view&error=true");
        }
    }
}