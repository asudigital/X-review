package com.crio.xreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.crio.xreview.util.DatabaseConnection;

public class ReviewLikeDAO {
    private final DatabaseConnection databaseConnection;

    public ReviewLikeDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void likeReview(int reviewId, int userId) throws SQLException {
        String sql = "INSERT INTO review_likes (review_id, user_id) VALUES (?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void unlikeReview(int reviewId, int userId) throws SQLException {
        String sql = "DELETE FROM review_likes WHERE review_id = ? AND user_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public boolean isReviewLikedByUser(int reviewId, int userId) throws SQLException {
        String sql = "SELECT * FROM review_likes WHERE review_id = ? AND user_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}