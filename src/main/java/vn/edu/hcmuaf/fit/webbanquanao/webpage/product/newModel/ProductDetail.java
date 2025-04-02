package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.newModel;

public class ProductDetail {
    private int id;
    private int productId;
    private String size;
    private int stock;
    private String image;
    private String color;

    public ProductDetail() {}

    public ProductDetail(int id, int productId, String size, int stock, String image, String color) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.stock = stock;
        this.image = image;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", productId=" + productId +
                ", size='" + size + '\'' +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
