package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CartService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.IOException;
import java.util.Collections;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get data from form
        int productId = Integer.parseInt(request.getParameter("pid"));
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ProductService service = new ProductService();
        Product pro = service.getDetail(productId);
        // update user selected information
        Product cartProduct = new Product(pro);
        cartProduct.setColors(Collections.singletonList(color));
        cartProduct.setSizes(Collections.singletonList(size));
        cartProduct.setStock(quantity);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // get userId from session
        User user = (User) session.getAttribute("auth");
        int userId = user.getId();

        // save cart into session
        CartService cart = (CartService) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartService();
        }
        cart.add(cartProduct, userId);
        session.setAttribute("cart", cart);

        response.sendRedirect("productDetail?addCart=ok&pid=" + productId);
    }

}
