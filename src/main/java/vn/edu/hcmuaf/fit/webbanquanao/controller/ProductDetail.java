package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "ProductDetail", value = "/productDetail")
public class ProductDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();

        String pidParam = request.getParameter("pid");
        if (pidParam != null) {
            // Product
            int id = Integer.parseInt(pidParam);
            Product detail = productService.getDetail(id);
            request.setAttribute("p", detail);

            // Sizes, colors, images of product
            List<String> sizes = new ArrayList<>();
            List<String> colors = new ArrayList<>();
            List<String> images = new ArrayList<>();
            for (Product pro : productService.getAllProducts()) {
                if (pro.getId() == id && !sizes.contains(pro.getSize())) {
                    sizes.add(pro.getSize());
                }
                if (pro.getId() == id && !colors.contains(pro.getColor())) {
                    colors.add(pro.getColor());
                }
                if (pro.getId() == id && !images.contains(pro.getImg())) {
                    images.add(pro.getImg());
                }
            }
            List<String> sizeOrder = Arrays.asList("S", "M", "L", "XL", "XXL", "XXXL", "XXXXL");
            sizes.sort(Comparator.comparingInt(sizeOrder::indexOf));
            request.setAttribute("sizes", sizes);
            request.setAttribute("colors", colors);
            request.setAttribute("images", images);
        }

        request.getRequestDispatcher("products_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}