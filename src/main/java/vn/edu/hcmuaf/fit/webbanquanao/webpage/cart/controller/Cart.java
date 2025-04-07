package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CartService;

import java.io.IOException;

@WebServlet(name = "Cart", value = "/Cart")
public class Cart extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị giỏ hàng
//        Cart cart = cartService.getCart(request);
//        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    private void handleAdd(HttpServletRequest request, HttpServletResponse response, int productId)
            throws IOException {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        cartService.addToCart(request, productId, quantity);
        response.sendRedirect(request.getContextPath() + "/products"); // Quay lại trang sản phẩm
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, int productId)
            throws IOException {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        cartService.updateCart(request, productId, quantity);
        response.sendRedirect(request.getContextPath() + "/cart"); // Tải lại giỏ hàng
    }

    private void handleRemove(HttpServletRequest request, HttpServletResponse response, int productId)
            throws IOException {
//        cartService.removeFromCart(request, productId);
        response.sendRedirect(request.getContextPath() + "/cart"); // Tải lại giỏ hàng
    }
}