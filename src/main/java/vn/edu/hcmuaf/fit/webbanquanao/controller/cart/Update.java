package vn.edu.hcmuaf.fit.webbanquanao.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.service.CartService;

import java.io.IOException;

@WebServlet(name = "Update", value = "/updateCart")
public class Update extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = -1;
        String action = request.getParameter("action");
        String param = request.getParameter("pid");
        try {
            id = Integer.parseInt(request.getParameter("pid"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        HttpSession session = request.getSession();
        CartService cart = (CartService) session.getAttribute("cart");
        if(action.equals("up")) {
            cart.update(id, cart.data.get(id).getQuantity() + 1);
        } else {
            cart.update(id, cart.data.get(id).getQuantity() - 1);
        }
        session.setAttribute("cart", cart);
        response.sendRedirect("ShowCart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}