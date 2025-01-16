package vn.edu.hcmuaf.fit.webbanquanao.model.modelAdmin;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AOrder implements Serializable {
    Integer id;
    String username;
    String paymentMethod;
    String code;
    LocalDateTime orderDate;
    double totalPrice;
    boolean status;

    public AOrder() {
    }

    public AOrder(Integer id, String username, String paymentMethod, String code, LocalDateTime orderDate, double totalPrice, boolean status) {
        this.id = id;
        this.username = username;
        this.paymentMethod = paymentMethod;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCode() {
        return code;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCode(String code) {
        this.code = code;
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
}
