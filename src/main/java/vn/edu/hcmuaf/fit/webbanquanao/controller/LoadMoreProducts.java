package vn.edu.hcmuaf.fit.webbanquanao.controller; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException; 
@WebServlet(name = "LoadMoreProducts", value = "/LoadMoreProducts") 
public class LoadMoreProducts extends HttpServlet { 

@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

}
@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }
}