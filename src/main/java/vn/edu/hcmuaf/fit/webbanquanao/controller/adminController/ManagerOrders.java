package vn.edu.hcmuaf.fit.webbanquanao.controller.adminController;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AOrder;
import vn.edu.hcmuaf.fit.webbanquanao.service.adminService.AOrderService;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "ManagerOrders", value = "/admin/manager-orders")
public class ManagerOrders extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số 'orderId' từ yêu cầu (nếu có)
        String orderIdParam = request.getParameter("id");
        AOrderService orderService = new AOrderService();

        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                // Nếu có orderId, tìm đơn hàng theo orderId
                int orderId = Integer.parseInt(orderIdParam);
                Map<Integer, AOrder> orders = orderService.showOrders();
                AOrder order = orders.get(orderId);

                if (order != null) {
                    // Chuyển đổi đơn hàng thành JSON và trả về
                    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    String json = gson.toJson(order);
                    out.print(json);
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Không tìm thấy đơn hàng\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"orderId không hợp lệ\"}");
            }
        } else {
            // Nếu không có orderId, trả về danh sách tất cả đơn hàng
            Map<Integer, AOrder> orders = orderService.showOrders();
            List<AOrder> orderList = orders.values().stream().collect(Collectors.toList());

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            String json = gson.toJson(orderList);
            out.print(json);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Đọc dữ liệu JSON từ client
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String json = jsonBuffer.toString();

            // Parse JSON thành đối tượng AOrder
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            AOrder order = gson.fromJson(json, AOrder.class);

            // Kiểm tra các trường dữ liệu
            if (order.getfirstName() == null || order.getOrderDate() == null || order.getTotalPrice() == 0) {
                throw new IllegalArgumentException("Thông tin đơn hàng không đầy đủ");
            }

            // Lưu đơn hàng vào danh sách (tạm thời, chưa thêm xử lý lưu vào DB)
            AOrderService orderService = new AOrderService();
            Map<Integer, AOrder> orders = orderService.showOrders();
            if (orders.containsKey(order.getId())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"message\": \"Đơn hàng đã tồn tại\"}");
            } else {
                orders.put(order.getId(), order); // Tạm lưu đơn hàng
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\": \"Đơn hàng đã được tạo thành công\"}");
            }
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
        }
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
            String json = jsonBuffer.toString();
            // Log JSON nhận được
            System.out.println("JSON body received: " + json);

            // Parse JSON
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            AOrder order = gson.fromJson(json,AOrder.class);
            // Log order nhận được
            System.out.println("Order received: " + order);

            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
            if (order.getId() == null || order.getfirstName() == null || order.getPaymentMethod() == null) {
                throw new IllegalArgumentException("Thiếu thông tin đơn hàng");
            }

            // Gọi service để cập nhật
            AOrderService orderService = new AOrderService();
            boolean isUpdated = orderService.updateOrder(order, order.getId());

            // Phản hồi
            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "Order updated successfully");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Failed to update order");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid JSON format\"}");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Missing required fields\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Error processing request: " + e.getMessage() + "\"}");
        }
    }



}



