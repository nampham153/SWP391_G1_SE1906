/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private String reviewContent;
    private String reviewImage;
    private int reviewRating;
    private Timestamp reviewDate;
    private String customerId;
    private String itemId;
    private int orderId;
    // constructors, getters, setters...

    public Review() {
    }

    public Review(int reviewId, String reviewContent, String reviewImage, int reviewRating, Timestamp reviewDate, String customerId, String itemId, int orderId) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.customerId = customerId;
        this.itemId = itemId;
        this.orderId = orderId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", reviewContent=" + reviewContent + ", reviewImage=" + reviewImage + ", reviewRating=" + reviewRating + ", reviewDate=" + reviewDate + ", customerId=" + customerId + ", itemId=" + itemId + ", orderId=" + orderId + '}';
    }
    
}
