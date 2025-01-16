package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.service.CartService;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "Payments", value = "/payments")
public class Payments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CartService cart = (CartService) session.getAttribute("cart");
        User auth = (User) session.getAttribute("auth");
        int userId = auth.getId();
        int paymentId = 1;
        int couponId = 1;
        Date orderDate = new Date(System.currentTimeMillis());
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        boolean status = false;

        // Add items to Order and OrderItem
        boolean addOrder = cart.addToOrder(userId, paymentId, couponId, orderDate, totalPrice, status);
        request.setAttribute("payments", addOrder);
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }
}