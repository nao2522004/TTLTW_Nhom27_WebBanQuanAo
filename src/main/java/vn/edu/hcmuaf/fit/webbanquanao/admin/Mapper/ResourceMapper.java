package vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper;

import java.util.Map;
import java.util.regex.Pattern;

public class ResourceMapper {
    private static final Map<Pattern, String> URL_TO_RESOURCE = Map.ofEntries(
            Map.entry(Pattern.compile("^/admin/.*$|^/admin.jsp$"), "Admin"),    // Admin
            Map.entry(Pattern.compile("^/Cart/.*$|^/add-cart$|^/del-cart$|^/updateCart$|^/ShowCart$|^/Checkout$"), "Cart"), // Giỏ hàng
            Map.entry(Pattern.compile("^/order/.*$"), "Order"),    // Đơn hàng
            Map.entry(Pattern.compile("^/payment/.*$|^/payments$"), "Payment"), // Thanh toán
            Map.entry(Pattern.compile("^/product/.*$|^/productDetail$|^/productFilter$|^/productPagination$|^/productSearch$"), "Product"), // Sản phẩm
            Map.entry(Pattern.compile("^/user/.*$|^/user.jsp$|^/verify.jsp"), "User") // Người dùng
    );

    public static String getResource(String path) {
        String resource = URL_TO_RESOURCE.entrySet().stream()
                .filter(entry -> entry.getKey().matcher(path).matches())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("default");

        return resource;
    }

}
