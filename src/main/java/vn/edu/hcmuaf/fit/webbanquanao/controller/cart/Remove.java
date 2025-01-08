package vn.edu.hcmuaf.fit.webbanquanao.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.service.CartService;

import java.io.IOException;

@WebServlet(name = "del", value = "/del-cart")
public class Remove extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pid = -1;
        try {
            pid = Integer.parseInt(request.getParameter("pid"));
        } catch (NumberFormatException e) {
            response.sendRedirect("ShowCart");
        }
        HttpSession session = request.getSession(true);
        CartService cart = (CartService) session.getAttribute("cart");
        if(cart == null){
            cart = new CartService();
        }
        cart.remove(pid);
        session.setAttribute("cart",cart);
        response.sendRedirect("ShowCart");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
