package vn.edu.hcmuaf.fit.webbanquanao.webpage.checkout;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.service.CheckoutService;

import java.io.IOException;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy userId từ session
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("auth") == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            int userId = (int) session.getAttribute("auth");

            // Checkout
            boolean isSuccess = false;

            if (isSuccess) {
                response.sendRedirect("homePage");
            } else {
                response.sendRedirect("checkout.jsp?error=checkout_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}