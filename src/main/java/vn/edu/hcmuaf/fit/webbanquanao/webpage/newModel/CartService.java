package vn.edu.hcmuaf.fit.webbanquanao.webpage.newModel;

public class CartService {
//    private ProductDAO productDao;

    public CartService() {
//        this.productDao = new ProductDAOImpl();
    }

    // Lấy giỏ hàng từ session hoặc tạo mới
//    public Cart getCart(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Cart cart = (Cart) session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
//        return cart;
//    }

    // Thêm sản phẩm vào giỏ hàng
//    public void addToCart(HttpServletRequest request, int productId, int quantity) {
//        if (quantity <= 0) throw new IllegalArgumentException("Số lượng phải > 0");
//
//        Cart cart = getCart(request);
//        Product product = productDao.getProductById(productId);
//
//        if (product == null) {
//            throw new RuntimeException("Sản phẩm không tồn tại");
//        }
//
//        cart.addItem(product, quantity);
//    }

    // Cập nhật số lượng sản phẩm
//    public void updateCart(HttpServletRequest request, int productId, int quantity) {
//        if (quantity <= 0) {
//            removeFromCart(request, productId); // Nếu quantity <= 0 thì xóa luôn
//            return;
//        }
//
//        Cart cart = getCart(request);
//        Product product = productDao.getProductById(productId);
//
//        if (product == null) {
//            throw new RuntimeException("Sản phẩm không tồn tại");
//        }
//
//        cart.updateItem(productId, quantity);
//    }

    // Xóa sản phẩm khỏi giỏ hàng
//    public void removeFromCart(HttpServletRequest request, int productId) {
//        Cart cart = getCart(request);
//        cart.removeItem(productId);
//    }

    // Tính tổng tiền (Bonus)
//    public double getCartTotal(HttpServletRequest request) {
//        return getCart(request).getTotal();
//    }
}
