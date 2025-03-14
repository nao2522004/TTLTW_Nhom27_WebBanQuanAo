package vn.edu.hcmuaf.fit.webbanquanao.webpage.checkout;

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
        // Get total price in order to transfer to payment pop-up
        double totalPrice = (double) request.getAttribute("totalPrice");

        // Get information from user
        // Checkout
        // Remove all products from shopping cart
        // Add all products to order
    }

    public boolean checkout() {
        return false;
    }

    public boolean removeAllProducts() {
        return false;
    }

    public boolean addAllProducts() {
        return false;
    }
}