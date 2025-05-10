package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrder;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AOrderService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "ManagerOrders", value = "/admin/manager-orders")
public class ManagerOrders extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ManagerOrders.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdParam = request.getParameter("id");
        AOrderService orderService = new AOrderService();

        // Lấy session và thông tin người dùng
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("auth");
        String username = (user != null) ? user.getUserName() : "unknown";
        List<String> roles = (user != null) ? user.getRoles() : new ArrayList<>();
        String ip = request.getRemoteAddr();

        // Xác định đường dẫn hiện tại (bao gồm cả ?id= nếu có)
        String currentPath = request.getRequestURI() + (orderIdParam != null ? "?id=" + orderIdParam : "");
        String lastLoggedPath = (String) session.getAttribute("lastLoggedPath");

        boolean shouldLog = !currentPath.equals(lastLoggedPath);

        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            try {
                int orderId = Integer.parseInt(orderIdParam);
                Map<Integer, AOrder> orders = orderService.showOrders();
                AOrder order = orders.get(orderId);

                if (order != null) {
                    if (shouldLog) {
                        UserLogsService.getInstance().logAction(
                                "INFO", username, roles,
                                "Xem chi tiết đơn hàng ID=" + orderId, ip
                        );
                        logger.info("User: {}, Action: Xem chi tiết đơn hàng ID={}", username, orderId);
                        session.setAttribute("lastLoggedPath", currentPath);
                    }

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                            .create();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    String json = gson.toJson(order);
                    out.print(json);
                    out.flush();
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Không tìm thấy đơn hàng\"}");
                    logger.warn("User: {}, Order not found", username);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"orderId không hợp lệ\"}");
                logger.error("User: {}, Invalid orderId format", username);
            }
        } else {
            Map<Integer, AOrder> orders = orderService.showOrders();
            List<AOrder> orderList = new ArrayList<>(orders.values());

            if (shouldLog) {
                UserLogsService.getInstance().logAction(
                        "INFO", username, roles,
                        "Xem danh sách tất cả đơn hàng", ip
                );
                logger.info("User: {}, Action: View all orders", username);
                session.setAttribute("lastLoggedPath", currentPath);
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();
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

        // Lấy thông tin người dùng từ session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("auth");
        String username = (user != null) ? user.getUserName() : "unknown";
        List<String> roles = (user != null) ? user.getRoles() : new ArrayList<>();
        String ip = request.getRemoteAddr();

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
            if (order.getFirstName() == null || order.getOrderDate() == null || order.getTotalPrice() == 0) {
                throw new IllegalArgumentException("Thông tin đơn hàng không đầy đủ");
            }

            // Lưu đơn hàng vào danh sách (tạm thời)
            AOrderService orderService = new AOrderService();
            Map<Integer, AOrder> orders = orderService.showOrders();
            if (orders.containsKey(order.getId())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"message\": \"Đơn hàng đã tồn tại\"}");

                // Ghi log: tạo đơn hàng thất bại
                logger.warn("User: {}, Action: Create order failed, ID={} already exists", username, order.getId());

                UserLogsService.getInstance().logAction(
                        "WARN",
                        username,
                        roles,
                        "Tạo đơn hàng thất bại: đã tồn tại ID=" + order.getId(),
                        ip
                );
            } else {
                orders.put(order.getId(), order); // Tạm lưu đơn hàng
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\": \"Đơn hàng đã được tạo thành công\"}");

                // Ghi log: tạo đơn hàng thành công
                logger.info("User: {}, Action: Created order successfully, ID={}", username, order.getId());

                UserLogsService.getInstance().logAction(
                        "INFO",
                        username,
                        roles,
                        "Tạo đơn hàng mới thành công, ID=" + order.getId(),
                        ip
                );
            }
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");

            logger.error("User: {}, Action: Invalid JSON format while creating order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "ERROR",
                    username,
                    roles,
                    "JSON không hợp lệ khi tạo đơn hàng: " + e.getMessage(),
                    ip
            );
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");

            logger.warn("User: {}, Action: Missing required fields while creating order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "WARN",
                    username,
                    roles,
                    "Dữ liệu đơn hàng thiếu khi tạo đơn: " + e.getMessage(),
                    ip
            );
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");

            logger.error("User: {}, Action: Server error while creating order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "FATAL",
                    username,
                    roles,
                    "Lỗi server khi tạo đơn hàng: " + e.getMessage(),
                    ip
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy thông tin người dùng từ session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("auth");
        String username = (user != null) ? user.getUserName() : "unknown";
        List<String> roles = (user != null) ? user.getRoles() : new ArrayList<>();
        String ip = request.getRemoteAddr();

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

            // Parse JSON
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
            AOrder order = gson.fromJson(json, AOrder.class);

            // Kiểm tra dữ liệu bắt buộc
            if (order.getId() == null || order.getFirstName() == null || order.getPaymentId() == null) {
                throw new IllegalArgumentException("Thiếu thông tin đơn hàng");
            }

            // Gọi service để cập nhật
            AOrderService orderService = new AOrderService();
            boolean isUpdated = orderService.updateOrder(order, order.getId());

            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "Cập nhật đơn hàng thành công");
                response.setStatus(HttpServletResponse.SC_OK);

                logger.info("User: {}, Action: Updated order successfully, ID={}", username, order.getId());

                UserLogsService.getInstance().logAction(
                        "INFO",
                        username,
                        roles,
                        "Cập nhật đơn hàng ID=" + order.getId() + " thành công",
                        ip
                );
            } else {
                jsonResponse.addProperty("message", "Cập nhật đơn hàng thất bại");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                logger.error("User: {}, Action: Failed to update order, ID={}", username, order.getId());

                UserLogsService.getInstance().logAction(
                        "ERROR",
                        username,
                        roles,
                        "Cập nhật đơn hàng ID=" + order.getId() + " thất bại",
                        ip
                );
            }
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Định dạng JSON không hợp lệ\"}");

            logger.error("User: {}, Action: Invalid JSON format while updating order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "ERROR",
                    username,
                    roles,
                    "Lỗi JSON khi cập nhật đơn hàng: " + e.getMessage(),
                    ip
            );
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Thiếu trường bắt buộc\"}");

            logger.warn("User: {}, Action: Missing required fields while updating order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "WARN",
                    username,
                    roles,
                    "Thiếu thông tin khi cập nhật đơn hàng: " + e.getMessage(),
                    ip
            );
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi xử lý yêu cầu: " + e.getMessage() + "\"}");

            logger.error("User: {}, Action: Server error while updating order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "FATAL",
                    username,
                    roles,
                    "Lỗi server khi cập nhật đơn hàng: " + e.getMessage(),
                    ip
            );
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy thông tin user từ session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("auth");
        String username = (user != null) ? user.getUserName() : "unknown";
        List<String> roles = (user != null) ? user.getRoles() : new ArrayList<>();
        String ip = request.getRemoteAddr();

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

            // Parse JSON để lấy ID đơn hàng
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            Integer orderId = jsonObject.get("id").getAsInt();

            // Kiểm tra ID đơn hàng hợp lệ
            if (orderId == null || orderId <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"ID đơn hàng không hợp lệ\"}");

                logger.warn("User: {}, Action: Failed to delete order - invalid ID {}", username, orderId);

                UserLogsService.getInstance().logAction(
                        "WARN",
                        username,
                        roles,
                        "Xóa đơn hàng thất bại - ID không hợp lệ: " + orderId,
                        ip
                );
                return;
            }

            // Gọi service để thực hiện xóa mềm
            AOrderService orderService = new AOrderService();
            boolean isDeleted = orderService.deleteOrder(orderId);

            // Phản hồi kết quả và ghi log
            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Xóa mềm đơn hàng thành công\"}");

                logger.info("User: {}, Action: Soft delete order successful, ID={}", username, orderId);

                UserLogsService.getInstance().logAction(
                        "INFO",
                        username,
                        roles,
                        "Đã xóa mềm đơn hàng ID=" + orderId,
                        ip
                );
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy đơn hàng\"}");

                logger.error("User: {}, Action: Order not found for delete, ID={}", username, orderId);

                UserLogsService.getInstance().logAction(
                        "ERROR",
                        username,
                        roles,
                        "Không tìm thấy đơn hàng để xóa mềm ID=" + orderId,
                        ip
                );
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu\"}");

            logger.error("User: {}, Action: Error while deleting order, Error: {}", username, e.getMessage());

            UserLogsService.getInstance().logAction(
                    "FATAL",
                    username,
                    roles,
                    "Lỗi khi xóa đơn hàng: " + e.getMessage(),
                    ip
            );
        }
    }
}



