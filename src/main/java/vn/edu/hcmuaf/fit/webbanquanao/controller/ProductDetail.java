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
            int id = Integer.parseInt(pidParam);
            Product detail = productService.getDetail(id);
            request.setAttribute("p", detail);
        }

        request.getRequestDispatcher("productComment").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}