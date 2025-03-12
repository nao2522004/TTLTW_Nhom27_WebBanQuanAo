package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy tổng tiền từ request
//        String price = request.getParameter("price");

        // 2. Xoá toàn bộ sản phẩm trong giỏ hàng
        resetShoppingCart(request);

        // 3. Thêm toàn bộ sản phẩm vào đơn hàng
        addToOrder(request);

        // 4. Chuyển hướng người dùng đến trang theo dõi đơn hàng
        directToOrderPage(response);
    }

    /**
     * Xoá toàn bộ sản phẩm trong giỏ hàng của người dùng
     */
    private void resetShoppingCart(HttpServletRequest request) {
        // Gọi class xử lý database để xoá giỏ hàng
//        System.out.println("Giỏ hàng đã được xoá thành công!");
    }

    /**
     * Thêm sản phẩm đã thanh toán vào đơn hàng
     */
    private void addToOrder(HttpServletRequest request) {
        // Gọi class xử lý database để thêm dữ liệu vào bảng order
//        System.out.println("Sản phẩm đã được thêm vào đơn hàng thành công!");
    }

    /**
     * Chuyển hướng người dùng đến trang theo dõi đơn hàng
     */
    private void directToOrderPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("order.jsp");
    }
}