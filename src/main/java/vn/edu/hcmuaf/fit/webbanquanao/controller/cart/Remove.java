package vn.edu.hcmuaf.fit.webbanquanao.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.service.CartService;

import java.io.IOException;

@WebServlet(name = "Remove", value = "/del-cart")
public class Remove extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pro =-1;
        try {
            pro = Integer.parseInt(request.getParameter("pro"));
        } catch (NumberFormatException e) {
            response.sendRedirect("ShowCart");
        }
        HttpSession session = request.getSession(true);
        CartService cart = (CartService) session.getAttribute("cart");
        if(cart == null){
            cart = new CartService();
        }
        cart.remove(pro);
        session.setAttribute("cart",cart);
        response.sendRedirect("ShowCart");


    }
}
