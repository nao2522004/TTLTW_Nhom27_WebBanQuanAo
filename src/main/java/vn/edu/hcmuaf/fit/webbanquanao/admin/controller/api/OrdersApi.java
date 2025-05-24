package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrder;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AOrderService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet(name = "OrdersApi", urlPatterns = "/admin/api/orders/*")
public class OrdersApi extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AOrderService orderService = new AOrderService();
    private final UserLogsService logService = UserLogsService.getInstance();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req, "Order");
        String id = extractId(req.getPathInfo());

        if (id == null) {
            List<AOrder> orders = new ArrayList<>(orderService.showOrders().values());

            Object viewedFlag = ctx.session.getAttribute("viewAllOrders");
            if (!Boolean.TRUE.equals(viewedFlag)) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), "Order", ctx.permissions, ctx.ip, ctx.roles);
                ctx.session.setAttribute("viewAllOrders", Boolean.TRUE);
            }
            writeJson(resp, orders);
        } else {
            handleGetById(id, ctx, req, resp);
        }
    }

    private void handleGetById(String id, ApiContext ctx, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int orderId = Integer.parseInt(id);
            AOrder order = orderService.showOrders().get(orderId);
            if (order != null) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), "Order", ctx.permissions, ctx.ip, ctx.roles);
                writeJson(resp, order);
            } else {
                logService.logCustom(ctx.username, "WARN", "Order not found ID=" + orderId, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req, "Order");
        try {
            AOrder order = gson.fromJson(readBody(req), AOrder.class);
            validateCreate(order);
            Map<Integer, AOrder> orders = orderService.showOrders();

            if (orders.containsKey(order.getId())) {
                logService.logCreateEntity(ctx.username, "Order", String.valueOf(order.getId()), ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_CONFLICT, "Đơn hàng đã tồn tại");
            } else {
                orders.put(order.getId(), order);
                logService.logCreateEntity(ctx.username, "Order", String.valueOf(order.getId()), ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo đơn hàng thành công");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo đơn hàng");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req, "Order");
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int orderId = Integer.parseInt(id);
            AOrder order = gson.fromJson(readBody(req), AOrder.class);
            validateUpdate(order);

            if (orderService.updateOrder(order, orderId)) {
                logService.logUpdateEntity(ctx.username, "Order", id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật đơn hàng");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req, "Order");
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int orderId = Integer.parseInt(id);
            if (orderService.deleteOrder(orderId)) {
                logService.logDeleteEntity(ctx.username, "Order", id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa đơn hàng thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Delete failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa đơn hàng");
        }
    }

    // Validation
    private void validateCreate(AOrder o) {
        if (o.getId() == null || o.getFirstName() == null || o.getOrderDate() == null || o.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Thiếu hoặc sai dữ liệu bắt buộc khi tạo Order: ID = " + o.getId());
        }
    }

    private void validateUpdate(AOrder o) {
        if (o.getFirstName() == null || o.getPaymentId() == null) {
            throw new IllegalArgumentException("Thiếu hoặc sai dữ liệu bắt buộc khi cập nhật Order: ID = " + o.getId());
        }
    }

    // Utility methods
    private void prepareResponse(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private String extractId(String pathInfo) {
        return (pathInfo == null || "/".equals(pathInfo)) ? null : pathInfo.substring(1);
    }

    private String readBody(HttpServletRequest req) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            return sb.toString();
        }
    }

    private <T> void writeJson(HttpServletResponse resp, T data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(data));
    }

    private void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        gson.toJson(Map.of("message", message), resp.getWriter());
    }

    private void sendSuccess(HttpServletResponse resp, int status, String message) throws IOException {
        sendError(resp, status, message);
    }
}
