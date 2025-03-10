package vn.edu.hcmuaf.fit.webbanquanao.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.controller.LocalDateTimeAdapter;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "OrderController", value = "/user/orderController")
public class OrderHistoryController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService(); // Khởi tạo OrderService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth"); // Lấy thông tin user từ session

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

        // Kiểm tra xem user có tồn tại không
        if (user == null) {
            out.print(gson.toJson(Map.of("error", "Người dùng chưa đăng nhập")));
            out.flush();
            return;
        }

        // Lấy danh sách đơn hàng theo userName
        String userName = user.getUserName(); // Lấy userName từ User object
        Map<Integer, Order> orders = orderService.getAllOrdersByUserName(userName);
        List<Order> orderList = orders.values().stream().collect(Collectors.toList());

        String json = gson.toJson(orderList);
        out.print(json);
        out.flush();
    }
}
