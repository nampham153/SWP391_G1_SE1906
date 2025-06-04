/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import model.Review;

/**
 *
 * @author tuananh
 */
public class ReviewDAO extends DBContext {
    public Review createReview(String reviewContent, String reviewImage, int reviewRating, String customerId, String itemId, int orderId, Timestamp reviewDate)
    {
        try {
            String query = "INSERT INTO Review "
                    + "(ReviewContent, ReviewImage, ReviewRating, CustomerId, itemId, orderId, reviewDate) "
                    + "VALUES (?, ? , ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, reviewContent);
            stm.setString(2, reviewImage);
            stm.setInt(3, reviewRating);
            stm.setString(4, customerId);
            stm.setString(5, itemId);
            stm.setInt(6, orderId);
            stm.setTimestamp(7, reviewDate);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void deleteReview(int reviewId)
    {
        try {
            String query = "DELETE FROM Review WHERE ReviewId = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, reviewId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ArrayList<Review> getReviewsByItemId(String itemId) 
    {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            String query = "SELECT "
                    + "ReviewId, ReviewContent, ReviewImage, ReviewRating "
                    + "FROM Review AS r LEFT JOIN "
                    + "ItemOrder AS io ON r.ReviewId = io.ReviewId "
                    + "WHERE ItemId = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, itemId);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Review review = new Review();
                review.setReviewId(rs.getInt("ReviewId"));
                review.setReviewContent(rs.getString("ReviewContent"));
                review.setReviewImage(rs.getString("ReviewImage"));
                review.setReviewRating(rs.getInt("ReviewRating"));
                review.setReviewDate(rs.getTimestamp("reviewDate"));
                reviews.add(review);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reviews;
    }
    
    public ArrayList<Review> getReviewsByCustomerId(String customerId)
    {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            String query = "SELECT ReviewId, ReviewContent, ReviewImage, ReviewRating, reviewDate "
                    + "FROM Review WHERE CustomerId = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, customerId);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Review review = new Review();
                review.setReviewId(rs.getInt("ReviewId"));
                review.setReviewContent(rs.getString("ReviewContent"));
                review.setReviewImage(rs.getString("ReviewImage"));
                review.setReviewRating(rs.getInt("ReviewRating"));
                review.setReviewDate(rs.getTimestamp("ReviewDate"));
                reviews.add(review);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reviews;
    }
    
    public void updateReview(Review review)
    {
        try {
            String query = "UPDATE Review SET ReviewContent = ?, ReviewImage = ?, ReviewRating = ? WHERE ReviewId = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, review.getReviewContent());
            stm.setString(2, review.getReviewImage());
            stm.setInt(3, review.getReviewRating());
            stm.setInt(4, review.getReviewId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReviewDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
