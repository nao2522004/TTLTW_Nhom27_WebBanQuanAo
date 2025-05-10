package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import com.google.gson.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProductDetails;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AProductService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.*;
import java.util.*;

@WebServlet(name = "ManagerProductDetails", value = "/admin/manager-productDetails")
public class ManagerProductDetails extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ManagerProductDetails.class);
    private AProductService productService;
    private UserLogsService userLogsService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new AProductService();
        userLogsService = UserLogsService.getInstance();
    }

    private User extractUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null ? (User) session.getAttribute("auth") : null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = extractUser(request);
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = request.getRemoteAddr();

        String productId = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (productId == null || productId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Thiếu ID sản phẩm\"}");
            return;
        }

        try {
            int id = Integer.parseInt(productId);
            Map<Integer, AProductDetails> map = productService.showProductDetails(id);
            List<AProductDetails> list = new ArrayList<>(map.values());

            userLogsService.logAction("INFO", username, roles,
                    "Xem chi tiết sản phẩm ID=" + id, ip);
            logger.info("User: {}, Action: Xem chi tiết sản phẩm ID={}", username, id);

            String json = new Gson().toJson(list);
            response.getWriter().write(json);
        } catch (NumberFormatException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"ID không hợp lệ\"}");

            userLogsService.logAction("ERROR", username, roles,
                    "ID không hợp lệ khi xem chi tiết sản phẩm: " + productId, ip);
            logger.error("User: {}, Invalid productId format: {}", username, productId);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Lỗi server: " + ex.getMessage() + "\"}");

            userLogsService.logAction("FATAL", username, roles,
                    "Lỗi server khi xem chi tiết sản phẩm ID=" + productId + ": " + ex.getMessage(), ip);
            logger.error("User: {}, Server error viewing productDetails ID={}: {}",
                    username, productId, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = extractUser(request);
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = request.getRemoteAddr();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder buf = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) buf.append(line);
            }
            AProductDetails pd = new Gson().fromJson(buf.toString(), AProductDetails.class);

            if (pd.getProductId() == null) {
                throw new IllegalArgumentException("ID sản phẩm không được để trống");
            }

            boolean created = productService.createProductDetails(pd);
            JsonObject resp = new JsonObject();
            if (created) {
                resp.addProperty("message", "Tạo chi tiết sản phẩm thành công");
                response.setStatus(HttpServletResponse.SC_CREATED);

                userLogsService.logAction("INFO", username, roles,
                        "Tạo chi tiết sản phẩm cho productId=" + pd.getProductId(), ip);
                logger.info("User: {}, Created productDetails for productId={}", username, pd.getProductId());
            } else {
                resp.addProperty("message", "Không thể tạo chi tiết sản phẩm");
                response.setStatus(HttpServletResponse.SC_CONFLICT);

                userLogsService.logAction("ERROR", username, roles,
                        "Tạo chi tiết sản phẩm thất bại cho productId=" + pd.getProductId(), ip);
                logger.error("User: {}, Failed to create productDetails for productId={}", username, pd.getProductId());
            }
            response.getWriter().write(resp.toString());

        } catch (JsonSyntaxException | IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"" + ex.getMessage() + "\"}");

            userLogsService.logAction("WARN", username, roles,
                    "Dữ liệu không hợp lệ khi tạo chi tiết sản phẩm: " + ex.getMessage(), ip);
            logger.warn("User: {}, Invalid input creating productDetails: {}", username, ex.getMessage());
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Lỗi server: " + ex.getMessage() + "\"}");

            userLogsService.logAction("FATAL", username, roles,
                    "Lỗi server khi tạo chi tiết sản phẩm: " + ex.getMessage(), ip);
            logger.error("User: {}, Server error creating productDetails: {}", username, ex.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = extractUser(request);
        String username = user != null ? user.getUserName() : "unknown";
        List<String> roles = user != null ? user.getRoles() : Collections.emptyList();
        String ip = request.getRemoteAddr();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder buf = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) buf.append(line);
            }
            AProductDetails pd = new Gson().fromJson(buf.toString(), AProductDetails.class);

            // Validation
            if (pd.getId() == null || pd.getId() <= 0)
                throw new IllegalArgumentException("ID chi tiết không hợp lệ");
            if (pd.getProductId() == null || pd.getProductId() <= 0)
                throw new IllegalArgumentException("ID sản phẩm không hợp lệ");
            if (pd.getSize() == null || pd.getSize().isEmpty())
                throw new IllegalArgumentException("Kích thước không được để trống");
            if (pd.getStock() == null || pd.getStock() < 0)
                throw new IllegalArgumentException("Số lượng phải >= 0");
            if (pd.getColor() == null || pd.getColor().isEmpty())
                throw new IllegalArgumentException("Màu sắc không được để trống");
            if (pd.getImage() == null || pd.getImage().isEmpty())
                throw new IllegalArgumentException("Hình ảnh không được để trống");

            boolean updated = productService.updateProductDetails(pd, pd.getId(), pd.getProductId());
            JsonObject resp = new JsonObject();
            if (updated) {
                resp.addProperty("message", "Cập nhật chi tiết sản phẩm thành công");
                response.setStatus(HttpServletResponse.SC_OK);

                userLogsService.logAction("INFO", username, roles,
                        "Cập nhật chi tiết sản phẩm ID=" + pd.getId(), ip);
                logger.info("User: {}, Updated productDetails ID={}", username, pd.getId());
            } else {
                resp.addProperty("message", "Cập nhật chi tiết không thành công");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                userLogsService.logAction("ERROR", username, roles,
                        "Cập nhật chi tiết sản phẩm ID=" + pd.getId() + " thất bại", ip);
                logger.error("User: {}, Failed updating productDetails ID={}", username, pd.getId());
            }
            response.getWriter().write(resp.toString());

        } catch (JsonParseException | IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"" + ex.getMessage() + "\"}");

            userLogsService.logAction("WARN", username, roles,
                    "Dữ liệu không hợp lệ khi cập nhật sản phẩm: " + ex.getMessage(), ip);
            logger.warn("User: {}, Invalid input updating productDetails: {}", username, ex.getMessage());
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\":\"Lỗi server: " + ex.getMessage() + "\"}");

            userLogsService.logAction("FATAL", username, roles,
                    "Lỗi server khi cập nhật chi tiết sản phẩm: " + ex.getMessage(), ip);
            logger.error("User: {}, Server error updating productDetails: {}", username, ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
