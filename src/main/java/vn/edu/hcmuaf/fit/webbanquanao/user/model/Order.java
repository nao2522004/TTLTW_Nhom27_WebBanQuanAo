package vn.edu.hcmuaf.fit.webbanquanao.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    Integer id;
    String userName;
    String firstName;
    String paymentMethod;
    String code;
    LocalDateTime orderDate;
    double totalPrice;
    Integer status;

    public Order() {
    }

    public Order(Integer id,String userName, String firstName, String paymentMethod, String code, LocalDateTime orderDate, double totalPrice, Integer status) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.paymentMethod = paymentMethod;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", code='" + code + '\'' +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
