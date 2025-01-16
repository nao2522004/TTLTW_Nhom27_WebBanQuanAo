package vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AOrder implements Serializable {
    Integer id;
    Integer userId;
    Integer paymentId;
    Integer couponId;
    LocalDateTime orderDate;
    double totalPrice;
    boolean status;

    public AOrder() {
    }

    public AOrder(Integer id, Integer userId, Integer paymentId, Integer couponId, LocalDateTime orderDate, double totalPrice, boolean status) {
        this.id = id;
        this.userId = userId;
        this.paymentId = paymentId;
        this.couponId = couponId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", paymentId=" + paymentId +
                ", couponId=" + couponId +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
