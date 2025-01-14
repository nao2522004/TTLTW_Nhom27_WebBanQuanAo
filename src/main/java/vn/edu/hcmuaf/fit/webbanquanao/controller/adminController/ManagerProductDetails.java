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
    }
}
