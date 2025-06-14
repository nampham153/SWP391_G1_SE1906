/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
import java.sql.Date;

public class Wishlist {
    private String userId;
    private String inventoryId;
    private int itemRank;
    private Date dateAdded;
    // constructors, getters, setters...

    public Wishlist() {
    }

    public Wishlist(String userId, String inventoryId, int itemRank, Date dateAdded) {
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.itemRank = itemRank;
        this.dateAdded = dateAdded;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getItemRank() {
        return itemRank;
    }

    public void setItemRank(int itemRank) {
        this.itemRank = itemRank;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Wishlist{" + "userId=" + userId + ", inventoryId=" + inventoryId + ", itemRank=" + itemRank + ", dateAdded=" + dateAdded + '}';
    }
    
}

