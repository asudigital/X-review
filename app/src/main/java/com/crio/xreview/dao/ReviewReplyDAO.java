package com.crio.xreview.dao;

import com.crio.xreview.model.ReviewReply;
import com.crio.xreview.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewReplyDAO {
    private final DatabaseConnection databaseConnection;
    
    public ReviewReplyDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public ReviewReply postReply(int reviewId, int ownerId, String replyText) throws SQLException {
        String sql = "INSERT INTO review_replies (review_id, owner_id, reply_text) VALUES (?, ?, ?)";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, ownerId);
            stmt.setString(3, replyText);
            stmt.executeUpdate();

            return new ReviewReply(reviewId, ownerId, replyText);
        }
    }

    public ReviewReply getReply(int reviewId, int ownerId) throws SQLException {
        String sql = "SELECT * FROM review_replies WHERE review_id = ? AND owner_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String replyText = rs.getString("reply_text");
                    return new ReviewReply(reviewId, ownerId, replyText);
                } else {
                    return null;
                }
            }
        }
    }

    public void deleteReply(int reviewId, int ownerId) throws SQLException {
        String sql = "DELETE FROM review_replies WHERE review_id = ? AND owner_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewId);
            stmt.setInt(2, ownerId);
            stmt.executeUpdate();
        }
    }
}
