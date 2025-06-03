package model;

public class ItemImage {

    private int imageId;
    private String imageContent;
    private String inventoryId;

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

    public String getImageContent() {
        return imageContent;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

}
