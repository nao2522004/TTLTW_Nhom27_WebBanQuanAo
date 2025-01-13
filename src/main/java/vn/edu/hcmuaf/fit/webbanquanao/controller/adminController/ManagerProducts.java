package vn.edu.hcmuaf.fit.webbanquanao.controller.adminController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.*;
import vn.edu.hcmuaf.fit.webbanquanao.service.adminService.AProductService;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "ManagerProducts", value = "/admin/manager-products")
public class ManagerProducts extends HttpServlet {

    private AProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new AProductService(); // Khởi tạo dịch vụ
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách sản phẩm từ service
        Map<Integer, Product> products = productService.showProduct();

        // Chuyển đổi danh sách sản phẩm thành JSON
        List<Product> productList = products.values().stream().collect(Collectors.toList());

        // Tạo Gson và thiết lập kiểu dữ liệu trả về là JSON
        Gson gson = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // In ra JSON danh sách sản phẩm
        PrintWriter out = response.getWriter();
        String json = gson.toJson(productList);
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        try {
//            // Đọc dữ liệu JSON từ client
//            StringBuilder jsonBuffer = new StringBuilder();
//            String line;
//            try (BufferedReader reader = request.getReader()) {
//                while ((line = reader.readLine()) != null) {
//                    jsonBuffer.append(line);
//                }
//            }
//            String json = jsonBuffer.toString();
//
//            // Parse JSON thành đối tượng Product
//            Gson gson = new Gson();
//            Product product = gson.fromJson(json, Product.class);
//
//            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
//            if (product.getName() == null || product.getUnitPrice() == null) {
//                throw new IllegalArgumentException("Tên sản phẩm và giá không được để trống");
//            }
//
//            // Gọi service để thêm sản phẩm
//            boolean isCreated = productService.createProduct(product);
//
//            // Phản hồi
//            JsonObject jsonResponse = new JsonObject();
//            if (isCreated) {
//                jsonResponse.addProperty("message", "Sản phẩm đã được tạo thành công!");
//                response.setStatus(HttpServletResponse.SC_CREATED);
//            } else {
//                jsonResponse.addProperty("message", "Không thể tạo sản phẩm. Lỗi dữ liệu");
//                response.setStatus(HttpServletResponse.SC_CONFLICT);
//            }
//            response.getWriter().write(gson.toJson(jsonResponse));
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
//        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            // Đọc JSON từ body
//            StringBuilder jsonBuffer = new StringBuilder();
//            String line;
//            try (BufferedReader reader = request.getReader()) {
//                while ((line = reader.readLine()) != null) {
//                    jsonBuffer.append(line);
//                }
//            }
//            String json = jsonBuffer.toString();
//            // Parse JSON thành đối tượng Product
//            Gson gson = new Gson();
//            Product product = gson.fromJson(json, Product.class);
//
//            // Kiểm tra các trường dữ liệu
//            if (product.getId() == null || product.getName() == null) {
//                throw new IllegalArgumentException("ID và tên sản phẩm không được để trống");
//            }
//
//            // Gọi service để cập nhật sản phẩm
//            boolean isUpdated = productService.updateProduct(product);
//
//            // Phản hồi
//            JsonObject jsonResponse = new JsonObject();
//            if (isUpdated) {
//                jsonResponse.addProperty("message", "Sản phẩm đã được cập nhật");
//                response.setStatus(HttpServletResponse.SC_OK);
//            } else {
//                jsonResponse.addProperty("message", "Không thể cập nhật sản phẩm");
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            }
//            response.getWriter().write(gson.toJson(jsonResponse));
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"message\": \"Đã xảy ra lỗi trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
//        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            // Đọc dữ liệu JSON từ body
//            StringBuilder jsonBuffer = new StringBuilder();
//            String line;
//            try (BufferedReader reader = request.getReader()) {
//                while ((line = reader.readLine()) != null) {
//                    jsonBuffer.append(line);
//                }
//            }
//            String json = jsonBuffer.toString();
//            // Parse JSON để lấy ID sản phẩm
//            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//            Integer productId = jsonObject.get("id").getAsInt();
//
//            // Kiểm tra ID
//            if (productId == null) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"message\": \"ID sản phẩm không được để trống\"}");
//                return;
//            }
//
//            // Gọi service để xóa sản phẩm
//            boolean isDeleted = productService.deleteProduct(productId);
//
//            // Phản hồi
//            if (isDeleted) {
//                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().write("{\"message\": \"Sản phẩm đã được xóa\"}");
//            } else {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write("{\"message\": \"Không tìm thấy sản phẩm\"}");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("{\"message\": \"Có lỗi xảy ra trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
//        }
    }
}
