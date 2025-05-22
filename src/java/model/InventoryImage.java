package model;

public class InventoryImage {

    private int imageId;
    private byte[] imageContent;
    private String inventoryId;

    public InventoryImage() {
    }

    public InventoryImage(int imageId, byte[] imageContent, String inventoryId) {
        this.imageId = imageId;
        this.imageContent = imageContent;
        this.inventoryId = inventoryId;
    }

    public int getImageId() {
        return imageId;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

}
