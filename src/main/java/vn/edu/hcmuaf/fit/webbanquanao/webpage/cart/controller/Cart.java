package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Cart", value = "/Cart")
public class Cart extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị giỏ hàng
        List<Product> cart = cartService.getCart();
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("productId"));

        switch (action.toLowerCase()) {
            case "add":
                handleAdd(request, response, productId);
                break;
            case "update":
                handleUpdate(request, response, productId);
                break;
            case "remove":
                handleRemove(request, response, productId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response, int productId) throws IOException {
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        cartService.addToCart(productId, color, size, quantity);
        response.sendRedirect(request.getContextPath() + "/productDetail?productId=" + productId); // Tải lại trang chi tiết sản phẩm, thông báo kết quả thêm
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, int productId) throws IOException, ServletException {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        cartService.updateCart(productId, quantity);
        doGet(request, response);// Tải lại giỏ hàng
    }

    private void handleRemove(HttpServletRequest request, HttpServletResponse response, int productId) throws IOException, ServletException {
        cartService.removeFromCart(productId);
        doGet(request, response);// Tải lại giỏ hàng
    }
}