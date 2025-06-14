/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class Comment {
    private int commentId;
    private String commentContent;
    private String commentImage;
    private String commenterName;
    private String customerId;
    private String inventoryId;
    // constructors, getters, setters...

    public Comment() {
    }

    public Comment(int commentId, String commentContent, String commentImage, String commenterName, String customerId, String inventoryId) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentImage = commentImage;
        this.commenterName = commenterName;
        this.customerId = customerId;
        this.inventoryId = inventoryId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(String commentImage) {
        this.commentImage = commentImage;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public String toString() {
        return "Comment{" + "commentId=" + commentId + ", commentContent=" + commentContent + ", commentImage=" + commentImage + ", commenterName=" + commenterName + ", customerId=" + customerId + ", inventoryId=" + inventoryId + '}';
    }
    
}

