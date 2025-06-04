package model;
import java.sql.Timestamp;

public class Review {

    private int reviewId;
    private String reviewContent;
    private String reviewImage;
    private int reviewRating;
    private String customerId;
    private int orderId;
    private String itemId;
    private Timestamp reviewDate;

    public Review() {
    }

    public Review(int reviewId, String reviewContent, String reviewImage,
            int reviewRating, String customerId, int orderId, String itemId, Timestamp reviewDate) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.reviewRating = reviewRating;
        this.customerId = customerId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }
    
}
