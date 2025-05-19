package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrderItem;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AOrderService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.*;
import java.util.*;

@WebServlet(name = "ManagerOrderDetails", value = "/admin/manager-orderDetails")
public class OrderDetailsApi extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailsApi.class);
    private AOrderService orderItemService;
    private UserLogsService userLogsService;

    @Override
    public void init() throws ServletException {
        super.init();
        orderItemService  = new AOrderService();
        userLogsService   = UserLogsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session và user
        HttpSession session = request.getSession(false);
        User user           = session != null ? (User) session.getAttribute("auth") : null;
        String username     = user != null ? user.getUserName() : "unknown";
        List<String> roles  = user != null ? user.getRoles()    : Collections.emptyList();
        String ip           = request.getRemoteAddr();

        String orderid = request.getParameter("orderId");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (orderid == null || orderid.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Thiếu ID đơn hàng\"}");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderid);
            Map<Integer, AOrderItem> itemMap = orderItemService.showOrderItem(orderId);
            List<AOrderItem> items = new ArrayList<>(itemMap.values());

            // Ghi log view chi tiết order
            userLogsService.logAction(
                    "INFO", username, roles,
                    "Xem chi tiết đơn hàng ID=" + orderId, ip
            );
            logger.info("User: {}, Action: Xem chi tiết đơn hàng ID={}", username, orderId);

            // Trả về JSON
            String json = new Gson().toJson(items);
            response.getWriter().write(json);

        } catch (NumberFormatException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"ID không hợp lệ\"}");

            logger.error("User: {}, Invalid orderId format: {}", username, orderid);
            userLogsService.logAction(
                    "ERROR", username, roles,
                    "Xem chi tiết thất bại, orderId không hợp lệ: " + orderid, ip
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy session và user
        HttpSession session = request.getSession(false);
        User user           = session != null ? (User) session.getAttribute("auth") : null;
        String username     = user != null ? user.getUserName() : "unknown";
        List<String> roles  = user != null ? user.getRoles()    : Collections.emptyList();
        String ip           = request.getRemoteAddr();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (BufferedReader reader = request.getReader()) {
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) buf.append(line);
            AOrderItem orderItem = new Gson().fromJson(buf.toString(), AOrderItem.class);

            // Validation
            if (orderItem.getId() == null || orderItem.getId() <= 0)
                throw new IllegalArgumentException("ID chi tiết đơn hàng không hợp lệ");
            if (orderItem.getOrderId() == null || orderItem.getOrderId() <= 0)
                throw new IllegalArgumentException("ID đơn hàng không hợp lệ");
            if (orderItem.getProductName() == null || orderItem.getProductName().isBlank())
                throw new IllegalArgumentException("Tên sản phẩm không được để trống");
            if (orderItem.getQuantity() == null || orderItem.getQuantity() < 0)
                throw new IllegalArgumentException("Số lượng phải >= 0");
            if (orderItem.getUnitPrice() < 0)
                throw new IllegalArgumentException("Giá đơn vị phải >= 0");
            if (orderItem.getDiscount() < 0)
                throw new IllegalArgumentException("Giảm giá phải >= 0");

            boolean updated = orderItemService.updateOrderItem(
                    orderItem, orderItem.getId(), orderItem.getOrderId()
            );

            JsonObject resp = new JsonObject();
            if (updated) {
                resp.addProperty("message", "Cập nhật chi tiết đơn hàng thành công");
                response.setStatus(HttpServletResponse.SC_OK);

                // Log success
                userLogsService.logAction(
                        "INFO", username, roles,
                        "Cập nhật chi tiết đơn hàng ID=" + orderItem.getId() +
                                " (orderId=" + orderItem.getOrderId() + ")", ip
                );
                logger.info("User: {}, Action: Updated orderDetail ID={} for orderId={}",
                        username, orderItem.getId(), orderItem.getOrderId());

            } else {
                resp.addProperty("message", "Cập nhật chi tiết không thành công");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                // Log failure
                userLogsService.logAction(
                        "ERROR", username, roles,
                        "Cập nhật chi tiết đơn hàng ID=" + orderItem.getId() + " thất bại", ip
                );
                logger.error("User: {}, Action: Failed update orderDetail ID={}",
                        username, orderItem.getId());
            }
            response.getWriter().write(resp.toString());

        } catch (JsonParseException | IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"" + ex.getMessage() + "\"}");

            logger.warn("User: {}, Action: Invalid input for orderDetail update: {}",
                    username, ex.getMessage());
            userLogsService.logAction(
                    "WARN", username, roles,
                    "Dữ liệu không hợp lệ khi cập nhật chi tiết đơn hàng: " + ex.getMessage(), ip
            );

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Lỗi server: " + ex.getMessage() + "\"}");

            logger.error("User: {}, Action: Server error updating orderDetail: {}",
                    username, ex.getMessage());
            userLogsService.logAction(
                    "FATAL", username, roles,
                    "Lỗi server khi cập nhật chi tiết đơn hàng: " + ex.getMessage(), ip
            );
        }
    }
}
