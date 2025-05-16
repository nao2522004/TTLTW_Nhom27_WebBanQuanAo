package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProduct;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AProductService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;

@WebServlet(name = "ManagerProducts", value = "/admin/api/products/*")
public class ProductsApi extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductsApi.class);
    private AProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new AProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

        String productId = request.getParameter("id");

        if (productId != null && !productId.isEmpty()) {
            try {
                int id = Integer.parseInt(productId);
                AProduct product = productService.getProductById(id);
                if (product != null) {
                    logger.info("User: {}, Action: View product details: ID {}", actorUsername, id);
                    UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Xem chi tiết sản phẩm ID: " + id, ip);

                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(gson.toJson(product));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Không tìm thấy sản phẩm\"}");
                    logger.warn("User: {}, Product not found: ID {}", actorUsername, id);
                    UserLogsService.getInstance().logAction("WARN", actorUsername, roles, "Sản phẩm không tồn tại ID: " + id, ip);
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"ID không hợp lệ\"}");
            }
        } else {
            Map<Integer, AProduct> products = productService.showProduct();
            List<AProduct> productList = products.values().stream().collect(Collectors.toList());

            logger.info("User: {}, Action: View all products", actorUsername);
            UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Xem danh sách tất cả sản phẩm", ip);

            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(gson.toJson(productList));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            AProduct aproduct = gson.fromJson(jsonBuffer.toString(), AProduct.class);

            if (aproduct.getName() == null || aproduct.getUnitPrice() == 0) {
                throw new IllegalArgumentException("Tên sản phẩm và giá không được để trống");
            }

            boolean isCreated = productService.createProduct(aproduct);

            JsonObject jsonResponse = new JsonObject();
            if (isCreated) {
                jsonResponse.addProperty("message", "Sản phẩm đã được tạo thành công!");
                response.setStatus(HttpServletResponse.SC_CREATED);
                logger.info("User: {}, Action: Created product: {}", actorUsername, aproduct.getName());
                UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Tạo sản phẩm mới: " + aproduct.getName(), ip);
            } else {
                jsonResponse.addProperty("message", "Không thể tạo sản phẩm. Lỗi dữ liệu");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                logger.warn("User: {}, Failed to create product: {}", actorUsername, aproduct.getName());
                UserLogsService.getInstance().logAction("WARN", actorUsername, roles, "Tạo sản phẩm thất bại: " + aproduct.getName(), ip);
            }

            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (JsonSyntaxException e) {
            logger.error("User: {}, JSON không hợp lệ: {}", actorUsername, e.getMessage());
            UserLogsService.getInstance().logAction("ERROR", actorUsername, roles, "Gửi dữ liệu JSON lỗi khi tạo sản phẩm", ip);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            logger.error("User: {}, Thiếu dữ liệu sản phẩm: {}", actorUsername, e.getMessage());
            UserLogsService.getInstance().logAction("ERROR", actorUsername, roles, "Thiếu trường dữ liệu sản phẩm", ip);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("User: {}, Lỗi hệ thống khi tạo sản phẩm: {}", actorUsername, e.getMessage(), e);
            UserLogsService.getInstance().logAction("FATAL", actorUsername, roles, "Lỗi hệ thống khi tạo sản phẩm", ip);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            AProduct product = gson.fromJson(jsonBuffer.toString(), AProduct.class);

            if (product.getId() == null || product.getId() <= 0)
                throw new IllegalArgumentException("ID sản phẩm không hợp lệ");
            if (product.getName() == null || product.getName().isEmpty())
                throw new IllegalArgumentException("Tên sản phẩm không được để trống");
            if (product.getUnitPrice() <= 0)
                throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0");
            if (product.getReleaseDate() == null)
                throw new IllegalArgumentException("Ngày phát hành không được để trống");

            try {
                LocalDate releaseDate = LocalDate.parse(product.getReleaseDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                product.setReleaseDate(Date.valueOf(releaseDate));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Ngày phát hành không hợp lệ. Định dạng đúng là yyyy-MM-dd");
            }

            boolean isUpdated = productService.updateProduct(product.getId(), product);

            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "Sản phẩm đã được cập nhật thành công!");
                response.setStatus(HttpServletResponse.SC_OK);
                logger.info("User: {}, Action: Updated product ID: {}", actorUsername, product.getId());
                UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Cập nhật sản phẩm ID: " + product.getId(), ip);
            } else {
                jsonResponse.addProperty("message", "Không thể cập nhật sản phẩm. Lỗi dữ liệu.");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                logger.warn("User: {}, Failed to update product ID: {}", actorUsername, product.getId());
                UserLogsService.getInstance().logAction("WARN", actorUsername, roles, "Cập nhật sản phẩm thất bại ID: " + product.getId(), ip);
            }

            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            logger.error("User: {}, Error updating product: {}", actorUsername, e.getMessage(), e);
            UserLogsService.getInstance().logAction("FATAL", actorUsername, roles, "Lỗi hệ thống khi cập nhật sản phẩm", ip);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User authUser = (session != null) ? (User) session.getAttribute("auth") : null;
        String actorUsername = (authUser != null) ? authUser.getUserName() : "unknown";
        List<String> roles = (authUser != null) ? authUser.getRoles() : List.of();
        String ip = request.getRemoteAddr();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("ID sản phẩm không được để trống");
            }

            int productId = Integer.parseInt(idStr);
            boolean isDeleted = productService.delete(productId);

            JsonObject jsonResponse = new JsonObject();
            if (isDeleted) {
                jsonResponse.addProperty("message", "Sản phẩm đã được xóa thành công!");
                response.setStatus(HttpServletResponse.SC_OK);
                logger.info("User: {}, Action: Deleted product ID: {}", actorUsername, productId);
                UserLogsService.getInstance().logAction("INFO", actorUsername, roles, "Xóa sản phẩm ID: " + productId, ip);
            } else {
                jsonResponse.addProperty("message", "Không thể xóa sản phẩm. Lỗi dữ liệu.");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                logger.warn("User: {}, Failed to delete product ID: {}", actorUsername, productId);
                UserLogsService.getInstance().logAction("WARN", actorUsername, roles, "Xóa sản phẩm thất bại ID: " + productId, ip);
            }

            response.getWriter().write(jsonResponse.toString());
        } catch (NumberFormatException e) {
            logger.error("User: {}, ID sản phẩm không hợp lệ", actorUsername);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"ID sản phẩm không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            logger.error("User: {}, {}", actorUsername, e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            logger.error("User: {}, Error deleting product: {}", actorUsername, e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi: " + e.getMessage() + "\"}");
        }
    }
}
