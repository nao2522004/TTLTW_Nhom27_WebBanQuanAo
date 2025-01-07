package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;
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

        // Product
        int id = Integer.parseInt(request.getParameter("pid"));
        Product detail = productService.getDetail(id);
        request.setAttribute("p", detail);

        // Sizes list and colors list of product
        List<String> sizes = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        for (Product pro : productService.getAllProducts()) {
            if(pro.getId() == id && !sizes.contains(pro.getSize())) {
                sizes.add(pro.getSize());
            }
            if(pro.getId() == id && !colors.contains(pro.getColor())) {
                colors.add(pro.getColor());
            }
        }
        List<String> sizeOrder = Arrays.asList("S", "M", "L", "XL", "XXL", "XXXL", "XXXXL");
        sizes.sort(Comparator.comparingInt(sizeOrder::indexOf));
        request.setAttribute("sizes", sizes);
        request.setAttribute("colors", colors);

        request.getRequestDispatcher("products_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}