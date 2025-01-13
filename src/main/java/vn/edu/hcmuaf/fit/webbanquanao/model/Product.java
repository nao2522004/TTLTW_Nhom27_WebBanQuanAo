package vn.edu.hcmuaf.fit.webbanquanao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private Integer id;
    private String type;
    private String category;
    private String supplier;
    private String name;
    private String description;
    private Date releaseDate;
    private Integer unitSold;
    private double unitPrice;
    private boolean status;
    private List<String> sizes;
    private Integer stock;
    private List<String> images;
    private List<String> colors;

    public Product() {
        this.sizes = new ArrayList<String>();
        this.images = new ArrayList<String>();
        this.colors = new ArrayList<String>();
    }

    public Product(Integer id, String type, String category, String supplier, String name, String description, Date releaseDate, Integer unitSold, double unitPrice, boolean status, List<String> sizes, Integer stock, List<String> imgs, List<String> colors) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.supplier = supplier;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.unitSold = unitSold;
        this.unitPrice = unitPrice;
        this.status = status;
        this.sizes = sizes;
        this.stock = stock;
        this.images = imgs;
        this.colors = colors;
    }

    public Product(Integer id, String type, String category, String supplier, String name, String description, Date releaseDate, Integer unitSold, double unitPrice, boolean status) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.supplier = supplier;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.unitSold = unitSold;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getReleaseDate() {
        return (java.sql.Date) releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getUnitSold() {
        return unitSold;
    }

    public void setUnitSold(Integer unitSold) {
        this.unitSold = unitSold;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", supplier='" + supplier + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", unitSold=" + unitSold +
                ", unitPrice=" + unitPrice +
                ", status=" + status +
                ", sizes=" + sizes +
                ", stock=" + stock +
                ", imgs=" + images +
                ", colors=" + colors +
                "} \n";
    }


}