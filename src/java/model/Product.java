package model;

public class Product {

    private String productId;
    private int categoryId;

    public Product() {
    }

    public Product(String productId, int categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}
