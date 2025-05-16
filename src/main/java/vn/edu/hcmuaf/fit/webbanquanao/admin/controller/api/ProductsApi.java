package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProduct;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AProductService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductsApi", urlPatterns = "/admin/api/products/*")
public class ProductsApi extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AProductService productService = new AProductService();
    private final UserLogsService logService = UserLogsService.getInstance();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);

        String id = extractId(req.getPathInfo());
        if (id == null) {
            List<AProduct> products = new ArrayList<>(productService.showProduct().values());
            // Chỉ log lần đầu xem tất cả sản phẩm trong session
            if (!ctx.session.getAttribute("viewAllProducts").equals(Boolean.TRUE)) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), "Product", ctx.permissions, ctx.ip, ctx.roles);
                ctx.session.setAttribute("viewAllProducts", Boolean.TRUE);
            }
            writeJson(resp, products);
        } else {
            handleGetById(id, ctx, req, resp);
        }
    }

    private void handleGetById(String id, ApiContext ctx, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int pid = Integer.parseInt(id);
            AProduct p = productService.getProductById(pid);
            if (p != null) {
//                logService.logAccessGranted(ctx.username, req.getRequestURI(), "Product", ctx.permissions, ctx.ip, ctx.roles);
                writeJson(resp, p);
            } else {
                logService.logCustom(ctx.username, "WARN", "Product not found ID=" + pid, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        try {
            AProduct p = gson.fromJson(readBody(req), AProduct.class);
            validateCreate(p);
            Map<Integer, AProduct> products = productService.showProduct();

            if (products.containsKey(p.getId())) {
                logService.logCreateEntity(ctx.username, "Product", String.valueOf(p.getId()), ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_CONFLICT, "Sản phẩm đã tồn tại");
            } else {
                products.put(p.getId(), p);
                logService.logCreateEntity(ctx.username, "Product", String.valueOf(p.getId()), ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo sản phẩm thành công");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo sản phẩm");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        String id = extractId(req.getPathInfo());
        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int pid = Integer.parseInt(id);

            // Tạo Gson mới với định dạng ngày
            Gson gsonWithDateFormat = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            // Đọc body
            String jsonBody = readBody(req);

            // Parse với Gson vừa tạo
            AProduct p = gsonWithDateFormat.fromJson(jsonBody, AProduct.class);

            validateUpdate(p);

            if (productService.updateProduct(pid, p)) {
                logService.logUpdateEntity(ctx.username, "Product", id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật sản phẩm");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareResponse(resp);
        ApiContext ctx = new ApiContext(req);
        String id = extractId(req.getPathInfo());
        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int pid = Integer.parseInt(id);
            if (productService.delete(pid)) {
                logService.logDeleteEntity(ctx.username, "Product", id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa sản phẩm thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Delete failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa sản phẩm");
        }
    }

    // Validation
    private void validateCreate(AProduct p) {
        if (p.getId() == null || p.getName() == null || p.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("Thiếu hoặc sai dữ liệu bắt buộc khi tạo sản phẩm");
        }
    }

    private void validateUpdate(AProduct p) {
        if (p.getName() == null || p.getName().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống");
        }
    }

    // Utility
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

    // Context holder (giống OrdersApi)
    private static class ApiContext {
        final String username;
        final Integer permissions;
        final List<String> roles;
        final String ip;
        final HttpSession session;

        ApiContext(HttpServletRequest req) {
            this.session = req.getSession();
            User u = (User) session.getAttribute("auth");
            this.username    = (u != null) ? u.getUserName() : "anonymous";
            this.roles       = (u != null) ? u.getRoles()    : List.of();
            this.permissions = (u != null) ? u.getPermissions().get("Product") : 0;
            this.ip          = req.getRemoteAddr();
            if (session.getAttribute("viewAllProducts") == null) {
                session.setAttribute("viewAllProducts", Boolean.FALSE);
            }
        }
    }
}
