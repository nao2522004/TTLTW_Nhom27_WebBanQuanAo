package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

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

@WebServlet(name = "ManagerOrders", value = "/admin/api/orders/*")
public class OrdersApi extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrdersApi.class);
    private final AOrderService orderService = new AOrderService();
    private final UserLogsService logService = UserLogsService.getInstance();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Chuẩn bị: session, user, ip, logger/service
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("auth") : null;
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = req.getRemoteAddr();

        String pathInfo = req.getPathInfo(); // null, "/", or "/{id}"
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (pathInfo == null || "/".equals(pathInfo)) {
                // 1. GET /api/orders → Danh sách tất cả
                List<AOrder> list = new ArrayList<>(orderService.showOrders().values());

                // Chỉ log lần đầu trong session
                Boolean viewedAll = (Boolean) session.getAttribute("viewedAllOrders");
                if (viewedAll == null || !viewedAll) {
                    logger.info("User: {}, Action: View all orders", username);
                    logService.logAction("INFO", username, roles, "Xem danh sách tất cả đơn hàng", ip);
                    session.setAttribute("viewedAllOrders", true);
                }

                // Trả về JSON
                String json = gson.toJson(list);
                resp.getWriter().write(json);

            } else {
                // 2. GET /api/orders/{id} → Chi tiết
                String idStr = pathInfo.substring(1);
                int orderId = Integer.parseInt(idStr);

                AOrder order = orderService.showOrders().get(orderId);
                if (order != null) {
                    // Ghi log console
                    logger.info("User: {}, Action: View order detail, ID={}", username, orderId);
                    // Ghi log database
                    logService.logAction("INFO", username, roles, "Xem chi tiết đơn hàng ID=" + orderId, ip);

                    String json = gson.toJson(order);
                    resp.getWriter().write(json);
                } else {
                    // Không tìm thấy
                    logger.warn("User: {}, Action: View order detail failed, order not found ID={}", username, orderId);
                    logService.logAction("WARN", username, roles, "Không tìm thấy đơn hàng ID=" + orderId, ip);

                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"message\":\"Không tìm thấy đơn hàng\"}");
                }
            }
        } catch (NumberFormatException e) {
            // ID không hợp lệ
            logger.error("User: {}, Action: Invalid orderId format: {}", username, e.getMessage());
            logService.logAction("ERROR", username, roles, "orderId không hợp lệ: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"orderId không hợp lệ\"}");

        } catch (IOException e) {
            // Lỗi I/O khi viết response
            logger.error("User: {}, Action: IO error in doGet: {}", username, e.getMessage());
            logService.logAction("ERROR", username, roles, "Lỗi IO khi xử lý GET orders: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Lỗi server trong quá trình xử lý yêu cầu\"}");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // POST /api/orders       → tạo mới
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("auth") : null;
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = req.getRemoteAddr();

        try {
            // 1. Đọc toàn bộ body JSON
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            AOrder order = gson.fromJson(sb.toString(), AOrder.class);

            // 2. Validate bắt buộc
            if (order.getId() == null
                    || order.getFirstName() == null
                    || order.getOrderDate() == null
                    || order.getTotalPrice() <= 0) {
                throw new IllegalArgumentException("Thiếu hoặc sai dữ liệu bắt buộc");
            }

            // 3. Thực thi tạo
            Map<Integer,AOrder> all = orderService.showOrders();
            if (all.containsKey(order.getId())) {
                // conflict
                logger.warn("User: {}, Action: Create order failed, ID={} already exists", username, order.getId());
                logService.logAction("WARN", username, roles,
                        "Tạo đơn hàng thất bại: đã tồn tại ID=" + order.getId(), ip);

                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write("{\"message\":\"Đơn hàng đã tồn tại\"}");
            } else {
                all.put(order.getId(), order);
                logger.info("User: {}, Action: Created order successfully, ID={}", username, order.getId());
                logService.logAction("INFO", username, roles,
                        "Tạo đơn hàng mới thành công, ID=" + order.getId(), ip);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"Tạo đơn hàng thành công\"}");
            }
        } catch (JsonSyntaxException e) {
            logger.error("User: {}, Action: Invalid JSON format while creating order, Error: {}", username, e.getMessage());
            logService.logAction("ERROR", username, roles,
                    "JSON không hợp lệ khi tạo đơn hàng: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            logger.warn("User: {}, Action: Missing fields while creating order, Error: {}", username, e.getMessage());
            logService.logAction("WARN", username, roles,
                    "Dữ liệu đơn hàng thiếu khi tạo đơn: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("User: {}, Action: Server error while creating order, Error: {}", username, e.getMessage());
            logService.logAction("FATAL", username, roles,
                    "Lỗi server khi tạo đơn hàng: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Lỗi server trong quá trình tạo đơn\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // PUT /api/orders/{id}  → cập nhật
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("auth") : null;
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = req.getRemoteAddr();

        String pathInfo = req.getPathInfo(); // ví dụ "/8"
        if (pathInfo == null || pathInfo.length() < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"Thiếu ID trong URL\"}");
            return;
        }

        try {
            int orderId = Integer.parseInt(pathInfo.substring(1));

            // Đọc JSON tương tự
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            AOrder order = gson.fromJson(sb.toString(), AOrder.class);

            // Validate
            if (order.getFirstName() == null || order.getPaymentId() == null) {
                throw new IllegalArgumentException("Thiếu trường bắt buộc");
            }

            // Cập nhật
            boolean ok = orderService.updateOrder(order, orderId);
            if (ok) {
                logger.info("User: {}, Action: Updated order successfully, ID={}", username, orderId);
                logService.logAction("INFO", username, roles,
                        "Cập nhật đơn hàng ID=" + orderId + " thành công", ip);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\":\"Cập nhật thành công\"}");
            } else {
                logger.error("User: {}, Action: Failed to update order, ID={}", username, orderId);
                logService.logAction("ERROR", username, roles,
                        "Cập nhật đơn hàng ID=" + orderId + " thất bại", ip);

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"message\":\"Không tìm thấy đơn hàng\"}");
            }
        } catch (NumberFormatException e) {
            logger.error("User: {}, Action: Invalid orderId format: {}", username, e.getMessage());
            logService.logAction("ERROR", username, roles,
                    "orderId không hợp lệ khi cập nhật: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"orderId không hợp lệ\"}");
        } catch (JsonSyntaxException e) {
            logger.error("User: {}, Action: Invalid JSON format while updating order, Error: {}", username, e.getMessage());
            logService.logAction("ERROR", username, roles,
                    "JSON không hợp lệ khi cập nhật đơn hàng: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            logger.warn("User: {}, Action: Missing fields while updating order, Error: {}", username, e.getMessage());
            logService.logAction("WARN", username, roles,
                    "Thiếu thông tin khi cập nhật đơn hàng: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("User: {}, Action: Server error while updating order, Error: {}", username, e.getMessage());
            logService.logAction("FATAL", username, roles,
                    "Lỗi server khi cập nhật đơn hàng: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Lỗi server trong quá trình cập nhật\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // DELETE /api/orders/{id} → xóa
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("auth") : null;
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = req.getRemoteAddr();

        String pathInfo = req.getPathInfo(); // "/{id}"
        if (pathInfo == null || pathInfo.length() < 2) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"Thiếu ID trong URL\"}");
            return;
        }

        try {
            int orderId = Integer.parseInt(pathInfo.substring(1));

            boolean deleted = orderService.deleteOrder(orderId);
            if (deleted) {
                logger.info("User: {}, Action: Soft delete order successful, ID={}", username, orderId);
                logService.logAction("INFO", username, roles,
                        "Đã xóa mềm đơn hàng ID=" + orderId, ip);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"message\":\"Xóa đơn hàng thành công\"}");
            } else {
                logger.error("User: {}, Action: Order not found for delete, ID={}", username, orderId);
                logService.logAction("ERROR", username, roles,
                        "Không tìm thấy đơn hàng để xóa ID=" + orderId, ip);

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"message\":\"Không tìm thấy đơn hàng\"}");
            }
        } catch (NumberFormatException e) {
            logger.error("User: {}, Action: Invalid orderId format in delete: {}", username, e.getMessage());
            logService.logAction("ERROR", username, roles,
                    "orderId không hợp lệ khi xóa: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"orderId không hợp lệ\"}");
        } catch (Exception e) {
            logger.error("User: {}, Action: Server error while deleting order, Error: {}", username, e.getMessage());
            logService.logAction("FATAL", username, roles,
                    "Lỗi server khi xóa đơn hàng: " + e.getMessage(), ip);

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Lỗi server trong quá trình xóa\"}");
        }
    }
}



