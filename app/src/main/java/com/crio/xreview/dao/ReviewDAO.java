package com.crio.xreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.crio.xreview.model.Review;
import com.crio.xreview.util.DatabaseConnection;

public class ReviewDAO {
    private final DatabaseConnection databaseConnection;

    public ReviewDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Review createReview(int userId, int companyId, String reviewText, int rating) throws SQLException {
        String sql = "INSERT INTO reviews (user_id, company_id, review_text, rating) VALUES (?, ?, ?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, companyId);
            stmt.setString(3, reviewText);
            stmt.setInt(4, rating);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int reviewId = generatedKeys.getInt(1);
                    return new Review(reviewId, userId, companyId, reviewText, rating);
                } else {
                    throw new SQLException("Creating review failed, no ID obtained.");
                }
            }
        }
    }

    public Review getReviewById(int reviewId) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE review_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int companyId = rs.getInt("company_id");
                    String reviewText = rs.getString("review_text");
                    int rating = rs.getInt("rating");
                    return new Review(reviewId, userId, companyId, reviewText, rating);
                } else {
                    return null;
                }
            }
        }
    }

    public List<Review> getReviewsByCompanyId(int companyId, String sortBy) throws SQLException {
        String sql = "SELECT * FROM reviews WHERE company_id = ?";
        switch (sortBy.toLowerCase()) {
            case "newest":
                sql += " ORDER BY created_at DESC";
                break;
            case "highest_rating":
                sql += " ORDER BY rating DESC";
                break;
            case "lowest_rating":
                sql += " ORDER BY rating ASC";
                break;
            default:
                throw new IllegalArgumentException("Invalid sort by criteria");
        }

        List<Review> reviews = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int reviewId = rs.getInt("review_id");
                    int userId = rs.getInt("user_id");
                    String reviewText = rs.getString("review_text");
                    int rating = rs.getInt("rating");
                    reviews.add(new Review(reviewId, userId, companyId, reviewText, rating));
                }
            }
        }
        return reviews;
    }

    public void updateReview(int reviewId, String newReviewText, int newRating) throws SQLException {
        String sql = "UPDATE reviews SET review_text = ?, rating = ? WHERE review_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newReviewText);
            stmt.setInt(2, newRating);
            stmt.setInt(3, reviewId);
            stmt.executeUpdate();
        }
    }

    public void deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM reviews WHERE review_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.executeUpdate();
        }
    }
}