/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class ItemImage {
    private int imageId;
    private String imageContent;
    private String inventoryId;
    // constructors, getters, setters...

    public ItemImage() {
    }

    public ItemImage(int imageId, String imageContent, String inventoryId) {
        this.imageId = imageId;
        this.imageContent = imageContent;
        this.inventoryId = inventoryId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public String toString() {
        return "ItemImage{" + "imageId=" + imageId + ", imageContent=" + imageContent + ", inventoryId=" + inventoryId + '}';
    }
    
}
