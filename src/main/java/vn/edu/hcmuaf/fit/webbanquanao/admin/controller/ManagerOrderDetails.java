package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrderItem;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AOrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ManagerOrderDetails", value = "/admin/manager-orderDetails")
public class ManagerOrderDetails extends HttpServlet {

    private AOrderService orderItemService;

    @Override
    public void init() throws ServletException {
        super.init();
        orderItemService = new AOrderService(); // Khởi tạo dịch vụ
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderid = request.getParameter("orderId");

        if (orderid != null && !orderid.isEmpty()) {
            try {
                int orderId = Integer.parseInt(orderid);

                // Lấy chi tiết sản phẩm theo ID
                Map<Integer, AOrderItem> orderItemMap = orderItemService.showOrderItem(orderId);
                List<AOrderItem> productDetailList = new ArrayList<>(orderItemMap.values());

                Gson gson = new Gson();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                String json = gson.toJson(productDetailList); // Chuyển chi tiết sản phẩm thành JSON
                out.print(json);
                out.flush();
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"ID không hợp lệ\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Thiếu ID sản phẩm\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Đọc JSON từ body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Parse JSON thành đối tượng AProductDetails
            Gson gson = new Gson();
            String json = jsonBuffer.toString();
            AOrderItem orderItem = gson.fromJson(json, AOrderItem.class);

            System.out.println(orderItem);

            // Kiểm tra các trường dữ liệu
            if (orderItem.getId() == null || orderItem.getId() <= 0) {
                throw new IllegalArgumentException("ID chi tiết đơn hàng không hợp lệ");
            }
            if (orderItem.getOrderId() == null || orderItem.getOrderId() <= 0) {
                throw new IllegalArgumentException("ID đơn hàng không hợp lệ");
            }
            if (orderItem.getProductName() == null || orderItem.getProductName().isEmpty()) {
                throw new IllegalArgumentException("Tên sản phẩm không được để trống");
            }
            if (orderItem.getQuantity() == null || orderItem.getQuantity() < 0) {
                throw new IllegalArgumentException("Số lượng phải lớn hơn hoặc bằng 0");
            }
            if (orderItem.getUnitPrice() < 0) {
                throw new IllegalArgumentException("Giá đơn vị phải lớn hơn hoặc bằng 0");
            }
            if (orderItem.getDiscount() < 0) {
                throw new IllegalArgumentException("Giảm giá phải lớn hơn hoặc bằng 0");
            }

            // Gọi service để cập nhật chi tiết sản phẩm
            boolean isUpdated = orderItemService.updateOrderItem(orderItem, orderItem.getId(), orderItem.getOrderId());

            // Phản hồi
            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "Chi tiết sản phẩm đã được cập nhật thành công");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Không thể cập nhật chi tiết sản phẩm");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.getWriter().write(gson.toJson(jsonResponse));

        } catch (JsonParseException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Lỗi định dạng JSON: " + e.getMessage() + "\"}");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu không hợp lệ: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
        }
    }
}
