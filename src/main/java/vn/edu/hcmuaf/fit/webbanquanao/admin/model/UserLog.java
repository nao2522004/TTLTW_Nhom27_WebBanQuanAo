package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.time.LocalDateTime;

public class UserLog {
    private int id;
    private String username;
    private String level;
    private String action;
    private String ipAddress;
    private LocalDateTime createdAt;

    // Constructor
    public UserLog(String username, String level, String action, String ipAddress) {
        this.username = username;
        this.level = level;
        this.action = action;
        this.ipAddress = ipAddress;
        this.createdAt = LocalDateTime.now(); // Set current timestamp
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", level='" + level + '\'' +
                ", action='" + action + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
