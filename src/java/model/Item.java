package model;

public class Item {

    private String serialNumber;
    private String itemName;
    private int stock;
    private double price;
    private int views;
    private int categoryId;

    public Item() {
    }

    public Item(String serialNumber, String itemName, int stock,
            double price, int views, int categoryId) {
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.stock = stock;
        this.price = price;
        this.views = views;
        this.categoryId = categoryId;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getViews() {
        return views;
    }

    public int getCategoryId() {
        return categoryId;
    }

}
