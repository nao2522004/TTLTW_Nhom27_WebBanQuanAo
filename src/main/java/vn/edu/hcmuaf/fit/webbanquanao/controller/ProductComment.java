package vn.edu.hcmuaf.fit.webbanquanao.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.model.CommentProduct;
import vn.edu.hcmuaf.fit.webbanquanao.service.CommentService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductComment", value = "/productComment")
public class ProductComment extends HttpServlet {
    //khai báo các hằng số cấu hình
    private static final String UPLOAD_DIRECTORY = "images";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 4; // 4MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 5; // 5MB

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("pid"));

        CommentService service = new CommentService();
        List<CommentProduct> comments = service.getCommentByProductId(productId);

        request.setAttribute("comments", comments);
        request.getRequestDispatcher("products_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}