package model;

public class Review {

    private int reviewId;
    private String reviewContent;
    private byte[] reviewImage;
    private int reviewRating;
    private String customerId;
    private int orderId;

    public Review() {
    }

    public Review(int reviewId, String reviewContent, byte[] reviewImage,
            int reviewRating, String customerId, int orderId) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.reviewRating = reviewRating;
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public byte[] getReviewImage() {
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

    public void setReviewImage(byte[] reviewImage) {
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

}
