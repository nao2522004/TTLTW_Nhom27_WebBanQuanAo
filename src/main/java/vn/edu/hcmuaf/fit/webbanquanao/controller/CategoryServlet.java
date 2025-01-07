package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.dao.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String temp = request.getParameter("category");
        String category;
        switch (temp) {
            case "men":
                category = "Nam";
                break;
            case "women":
                category = "Nữ";
                break;
            case "children":
                category = "Trẻ em";
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category is required");
                return;
        }

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