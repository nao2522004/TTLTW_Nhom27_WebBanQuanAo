package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model;

public class CartDetail {
    private int id;
    private int cartId;
    private int couponId;
    private int quantity;
    private double unitPrice;
    private int productDetailsId;

    public CartDetail() {
    }

    public CartDetail(int id, int cartId, int couponId, int quantity, double unitPrice, int productDetailsId) {
        this.id = id;
        this.cartId = cartId;
        this.couponId = couponId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productDetailsId = productDetailsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductDetailsId() {
        return productDetailsId;
    }

    public void setProductDetailsId(int productDetailsId) {
        this.productDetailsId = productDetailsId;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", couponId=" + couponId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", productDetailsId=" + productDetailsId +
                '}';
    }
}
