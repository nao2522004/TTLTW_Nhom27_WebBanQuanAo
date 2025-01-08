package vn.edu.hcmuaf.fit.webbanquanao.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.model.Product;

import vn.edu.hcmuaf.fit.webbanquanao.service.CartService;
import vn.edu.hcmuaf.fit.webbanquanao.service.ProductService;

import java.io.IOException;
import java.util.Collections;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ProductService service = new ProductService();
//        String id = request.getParameter("pid");
//        Product pro =  service.getDetail(Integer.parseInt(id));
//        if (pro == null) {
//            response.sendRedirect("productDetail?addCart=false");
//        } else {
//            HttpSession session = request.getSession(true);
//            CartService cart = (CartService) session.getAttribute("cart");
//            if (cart == null) {
//                cart = new CartService();
//            }
//            cart.add(pro);
//            session.setAttribute("cart", cart);
//        }
//
//        response.sendRedirect("productDetail?addCart=ok&pid=" + id);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        int productId = Integer.parseInt(request.getParameter("pid"));
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ProductService service = new ProductService();
        Product pro = service.getDetail(productId);
        // Cập nhật thông tin người dùng chọn
        pro.setColors(Collections.singletonList(color));
        pro.setSizes(Collections.singletonList(size));
        pro.setStock(quantity);

        HttpSession session = request.getSession(true);
        CartService cart = (CartService) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartService();
        }
        cart.add(pro);
        session.setAttribute("cart", cart);

        response.sendRedirect("productDetail?addCart=ok&pid=" + productId);
    }

}
