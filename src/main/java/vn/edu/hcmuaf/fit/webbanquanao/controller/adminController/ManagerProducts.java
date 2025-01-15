package vn.edu.hcmuaf.fit.webbanquanao.controller.adminController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin.AProduct;
import vn.edu.hcmuaf.fit.webbanquanao.service.adminService.AProductService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;


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
        // Lấy tham số 'id' từ yêu cầu (nếu có)
        String productId = request.getParameter("id");

        // Tạo đối tượng ProductService để truy vấn dữ liệu sản phẩm
        AProductService productService = new AProductService();

        // Nếu có tham số 'id', tìm sản phẩm theo id
        if (productId != null && !productId.isEmpty()) {
            try {
                // Chuyển đổi productId thành Integer
                int id = Integer.parseInt(productId);

                // Tìm sản phẩm theo id
                AProduct product = productService.getProductById(id);
                if (product != null) {
                    // Tạo Gson để chuyển đối tượng thành JSON
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    String json = gson.toJson(product); // Chuyển đổi sản phẩm thành JSON
                    out.print(json);
                    out.flush();
                } else {
                    // Nếu không tìm thấy sản phẩm, trả về lỗi 404
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Khong tim thay san pham\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"ID không hợp lệ\"}");
            }
        } else {
            // Nếu không có 'id', trả về tất cả sản phẩm
            Map<Integer, AProduct> products = productService.showProduct();
            List<AProduct> productList = products.values().stream().collect(Collectors.toList());

            // Tạo Gson để chuyển danh sách sản phẩm thành JSON
            Gson gson = new Gson();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String json = gson.toJson(productList); // Sử dụng Gson để chuyển danh sách sản phẩm thành JSON
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

            // Cấu hình Gson
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")  // Định dạng ngày tháng
                    .create();
            AProduct aproduct = gson.fromJson(json, AProduct.class);

            // Kiểm tra các trường dữ liệu, đảm bảo không có giá trị null
            if (aproduct.getName() == null || aproduct.getUnitPrice() == 0) {
                throw new IllegalArgumentException("Tên sản phẩm và giá không được để trống");
            }

            // Gọi service để thêm sản phẩm
            boolean isCreated = productService.createProduct(aproduct);

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
            // Cấu hình Gson
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")  // Định dạng ngày tháng
                    .create();
            String json = jsonBuffer.toString(); // Parse JSON thành đối tượng Product
            AProduct product = gson.fromJson(json, AProduct.class);
            // Kiểm tra các trường dữ liệu
            if (product.getId() == null || product.getId() <= 0)
                throw new IllegalArgumentException("ID sản phẩm không hợp lệ");
            if (product.getName() == null || product.getName().isEmpty())
                throw new IllegalArgumentException("Tên sản phẩm không được để trống");
            if (product.getUnitPrice() <= 0)
                throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0");
            if (product.getReleaseDate() == null)
                throw new IllegalArgumentException("Ngày phát hành không được để trống");
            // Xử lý chuỗi ngày thành LocalDate và chuyển đổi sang java.sql.Date
            try {
                LocalDate releaseDate = LocalDate.parse(product.getReleaseDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                product.setReleaseDate(Date.valueOf(releaseDate)); // Cập nhật lại ngày
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Ngày phát hành không hợp lệ. Định dạng đúng là yyyy-MM-dd");
            }

            // Gọi service để cập nhật sản phẩm
            boolean isUpdated = productService.updateProduct(product.getId(), product);

            // Phản hồi
            JsonObject jsonResponse = new JsonObject();
            if (isUpdated) {
                jsonResponse.addProperty("message", "Sản phẩm đã được cập nhật");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Không thể cập nhật sản phẩm");
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            // Đọc dữ liệu JSON từ body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String json = jsonBuffer.toString();
            // Parse JSON để lấy ID sản phẩm
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            Integer productId = jsonObject.get("id").getAsInt();

            // Kiểm tra ID
            if (productId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"ID sản phẩm không được để trống\"}");
                return;
            }

            // Gọi service để xóa sản phẩm
            boolean isDeleted = productService.delete(productId);

            // Phản hồi
            if (isDeleted) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Sản phẩm đã được xóa\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy sản phẩm\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Có lỗi xảy ra trong quá trình xử lý yêu cầu: " + e.getMessage() + "\"}");
        }
    }
}
