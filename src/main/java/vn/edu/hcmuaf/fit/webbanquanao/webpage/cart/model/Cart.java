package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model;

import java.sql.Date;

public class Cart {
    private int id;
    private int userId;
    private Date createdDate;
    private Date updatedDate;

    public Cart() {
    }

    public Cart(int id, int userId, Date createdDate, Date updatedDate) {
        this.id = id;
        this.userId = userId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                "}\n";
    }
}
