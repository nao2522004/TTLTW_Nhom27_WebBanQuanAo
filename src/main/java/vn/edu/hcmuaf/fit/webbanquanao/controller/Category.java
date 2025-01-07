package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/categoryServlet")
public class Category extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String temp = request.getParameter("category");
        String category = switch (temp) {
            case "men" -> "Nam";
            case "women" -> "Nữ";
            case "children" -> "Trẻ em";
            default -> "";
        };

        ProductService productService = new ProductService();
        List<Product> allProducts = productService.getProductsByCategory(category);

        request.setAttribute("allProducts", allProducts);
        String page = "/".concat(temp.toLowerCase()).concat(".jsp");
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}