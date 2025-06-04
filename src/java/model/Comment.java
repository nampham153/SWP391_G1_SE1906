package model;

public class Comment {

    private int commentId;
    private String commentContent;
    private String commentImage;
    private String commenterName;
    private String customerId;
    private String inventoryId;

    public Comment() {
    }

    public Comment(int commentId, String commentContent, String commentImage,
            String commenterName, String customerId, String inventoryId) {
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

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentImage() {
        return commentImage;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentImage(String commentImage) {
        this.commentImage = commentImage;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

}
