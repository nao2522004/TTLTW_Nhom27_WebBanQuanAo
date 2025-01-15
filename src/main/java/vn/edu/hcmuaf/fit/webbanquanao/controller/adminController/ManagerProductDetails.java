package vn.edu.hcmuaf.fit.webbanquanao.controller.adminController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AProductDetails;
import vn.edu.hcmuaf.fit.webbanquanao.service.adminService.AProductService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@WebServlet(name = "ManagerProductDetails", value = "/admin/manager-productDetails")
public class ManagerProductDetails extends HttpServlet {
    private AProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new AProductService(); // Khởi tạo dịch vụ
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("id");

        if (productId != null && !productId.isEmpty()) {
            try {
                int id = Integer.parseInt(productId);

                // Lấy chi tiết sản phẩm theo ID
                Map<Integer, AProductDetails> productDetails = productService.showProductDetails(id);
                List<AProductDetails> productDetailList = new ArrayList<>(productDetails.values());

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

            Gson gson = new Gson();

            // Parse JSON thành đối tượng AProductDetails
            AProductDetails aProductDetails = gson.fromJson(json, AProductDetails.class);

            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
            if (aProductDetails.getProductId() == null) {
                throw new IllegalArgumentException("id sản phẩm không được để trống");
            }

            // Gọi service để thêm sản phẩm
            boolean isCreated = productService.createProductDetails(aProductDetails);

            // Phản hồi
            JsonObject jsonResponse = new JsonObject();
            if (isCreated) {
                jsonResponse.addProperty("message", "Sản phẩm đã được tạo thành công!");
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                jsonResponse.addProperty("message", "Không thể tạo sản phẩm. Lỗi dữ liệu");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
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

            // Parse JSON thành đối tượng AProductDetails
            Gson gson = new Gson();
            String json = jsonBuffer.toString();
            AProductDetails productDetails = gson.fromJson(json, AProductDetails.class);

            // Kiểm tra các trường dữ liệu
            if (productDetails.getId() == null || productDetails.getId() <= 0) {
                throw new IllegalArgumentException("ID chi tiết sản phẩm không hợp lệ");
            }
            if (productDetails.getProductId() == null || productDetails.getProductId() <= 0) {
                throw new IllegalArgumentException("ID sản phẩm không hợp lệ");
            }
            if (productDetails.getSize() == null || productDetails.getSize().isEmpty()) {
                throw new IllegalArgumentException("Kích thước không được để trống");
            }
            if (productDetails.getStock() == null || productDetails.getStock() < 0) {
                throw new IllegalArgumentException("Số lượng phải lớn hơn hoặc bằng 0");
            }
            if (productDetails.getColor() == null || productDetails.getColor().isEmpty()) {
                throw new IllegalArgumentException("Màu sắc không được để trống");
            }
            if (productDetails.getImage() == null || productDetails.getImage().isEmpty()) {
                throw new IllegalArgumentException("Hình ảnh không được để trống");
            }

            // Gọi service để cập nhật chi tiết sản phẩm
            boolean isUpdated = productService.updateProductDetails(productDetails, productDetails.getId(), productDetails.getProductId());

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


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
