package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model;

public class CartProductDetail {
    private int productDetailId;
    private int couponId;
    private int quantity;
    private double unitPrice;

    public CartProductDetail(int productDetailId, int couponId, int quantity, double unitPrice) {
        this.productDetailId = productDetailId;
        this.couponId = couponId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
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
}
