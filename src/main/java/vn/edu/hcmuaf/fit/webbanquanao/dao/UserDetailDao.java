package vn.edu.hcmuaf.fit.webbanquanao.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class UserDetailDao {

    private Connection connection;

    public UserDetailDao(Connection connection) {
        this.connection = connection;
    }

    // Method to add user details to the database
    public boolean addUserInfo(int userId, String gender, Date birthDate, double height, double weight) {
        String query = "INSERT INTO UserDetails (user_id, gender, birth_date, height, weight) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, gender);
            stmt.setDate(3, new java.sql.Date(birthDate.getTime()));
            stmt.setDouble(4, height);
            stmt.setDouble(5, weight);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get user details by userId
    public Map<String, Object> getUserInfo(int userId) {
        String query = "SELECT gender, birth_date, height, weight FROM UserDetails WHERE user_id = ?";
        Map<String, Object> userInfo = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                userInfo.put("gender", resultSet.getString("gender"));
                userInfo.put("birth_date", resultSet.getDate("birth_date"));
                userInfo.put("height", resultSet.getDouble("height"));
                userInfo.put("weight", resultSet.getDouble("weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // Method to update user details
    public boolean updateUserInfo(int userId, String gender, Date birthDate, double height, double weight) {
        String query = "UPDATE UserDetails SET gender = ?, birth_date = ?, height = ?, weight = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, gender);
            stmt.setDate(2, new Date(birthDate.getTime()));
            stmt.setDouble(3, height);
            stmt.setDouble(4, weight);
            stmt.setInt(5, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete user details
    public boolean deleteUserInfo(int userId) {
        String query = "DELETE FROM UserDetails WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

