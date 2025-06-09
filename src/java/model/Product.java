package model;

public class Product {

    private String productId;
    private int categoryId;
    private Item item;//Thêm thuộc tính Item và getter/setter

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
    
    public Item getItem() {
        return item;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }

}
