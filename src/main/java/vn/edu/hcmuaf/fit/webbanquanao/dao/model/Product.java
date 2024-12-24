package vn.edu.hcmuaf.fit.webbanquanao.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private int proid;
    private int typeID;
    private int categoryID;
    private int supplierID;
    private String productName;
    private String size;
    private int stock;
    private String images;
    private String color;
    private Date releaseDate;
    private int unitSold;
    private double unitPrice;

    public Product(int proid, int typeID, int categoryID, int supplierID, String productName, String size, int stock, String images, String color, Date releaseDate, int unitSold, double unitPrice) {
        this.proid = proid;
        this.typeID = typeID;
        this.categoryID = categoryID;
        this.supplierID = supplierID;
        this.productName = productName;
        this.size = size;
        this.stock = stock;
        this.images = images;
        this.color = color;
        this.releaseDate = releaseDate;
        this.unitSold = unitSold;
        this.unitPrice = unitPrice;
    }

    public int getProid() {
        return proid;
    }

    public int getTypeID() {
        return typeID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getProductName() {
        return productName;
    }

    public String getSize() {
        return size;
    }

    public int getStock() {
        return stock;
    }

    public String getImages() {
        return images;
    }

    public String getColor() {
        return color;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getUnitSold() {
        return unitSold;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUnitSold(int unitSold) {
        this.unitSold = unitSold;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "proid=" + proid +
                ", typeID=" + typeID +
                ", categoryID=" + categoryID +
                ", supplierID=" + supplierID +
                ", productName='" + productName + '\'' +
                ", size='" + size + '\'' +
                ", stock=" + stock +
                ", images='" + images + '\'' +
                ", color='" + color + '\'' +
                ", releaseDate=" + releaseDate +
                ", unitSold=" + unitSold +
                ", unitPrice=" + unitPrice +
                '}';
    }
}