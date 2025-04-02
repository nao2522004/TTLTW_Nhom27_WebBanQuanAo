package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.newModel;

import java.sql.Date;
import java.util.List;

public class Product {
    private int id;
    private int typeId;
    private int categoryId;
    private int supplierId;
    private String productName;
    private String description;
    private Date releaseDate;
    private int unitSold;
    private double unitPrice;
    private int status;
    private List<ProductDetail> details;

    public Product() {}

    public Product(int id, int typeId, int categoryId, int supplierId, String productName, String description, Date releaseDate, int unitSold, double unitPrice, int status, List<ProductDetail> details) {
        this.id = id;
        this.typeId = typeId;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.productName = productName;
        this.description = description;
        this.releaseDate = releaseDate;
        this.unitSold = unitSold;
        this.unitPrice = unitPrice;
        this.status = status;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getUnitSold() {
        return unitSold;
    }

    public void setUnitSold(int unitSold) {
        this.unitSold = unitSold;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ProductDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ProductDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", unitSold=" + unitSold +
                ", unitPrice=" + unitPrice +
                ", status=" + status +
                ", details=" + details +
                "} \n";
    }
}
