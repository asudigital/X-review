package com.crio.xreview.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.crio.xreview.model.Company;
import com.crio.xreview.util.DatabaseConnection;

public class CompanyDAO {
    private final DatabaseConnection databaseConnection;

    public CompanyDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Company createCompany(String name, String description, int ownerId) throws SQLException {
        String sql = "INSERT INTO companies (name, description, owner_id) VALUES (?, ?, ?)";

        try (Connection conn = databaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, ownerId);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int companyId = generatedKeys.getInt(1);
                    return new Company(companyId, name, description, ownerId);
                } else {
                    throw new SQLException("Creating company failed, no ID obtained.");
                }
            }
        }
    }

    public Company getCompanyById(int companyId) throws SQLException {
        String sql = "SELECT * FROM companies WHERE company_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    int ownerId = rs.getInt("owner_id");
                    return new Company(companyId, name, description,ownerId);
                } else {
                    return null;
                }
            }
        }
    }
}
