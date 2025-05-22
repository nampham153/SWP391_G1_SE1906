package model;

public class Wishlist {

    private String userId;
    private String inventoryId;
    private int rank;
    private int dateAdded;

    public Wishlist() {
    }

    public Wishlist(String userId, String inventoryId, int rank, int dateAdded) {
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.rank = rank;
        this.dateAdded = dateAdded;
    }

    public String getUserId() {
        return userId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public int getRank() {
        return rank;
    }

    public int getDateAdded() {
        return dateAdded;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDateAdded(int dateAdded) {
        this.dateAdded = dateAdded;
    }

}
