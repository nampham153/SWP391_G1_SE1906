/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
import java.math.BigDecimal;

public class Item {

    private String serialNumber;
    private String itemName;
    private int stock;
    private BigDecimal price;
    private int views;
    private String description;
    private ItemImage image;
    // constructors, getters, setters...

    public Item() {
    }

    public Item(String serialNumber, String itemName, int stock, BigDecimal price, int views, String description) {
        this.serialNumber = serialNumber;
        this.itemName = itemName;
        this.stock = stock;
        this.price = price;
        this.views = views;
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public ItemImage getImage() {
        return image;
    }

    public void setImage(ItemImage image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
