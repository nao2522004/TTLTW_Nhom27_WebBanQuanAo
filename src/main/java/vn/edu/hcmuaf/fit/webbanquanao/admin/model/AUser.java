package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class AUser implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String address;
    private Integer phone;
    private Integer status;
    private LocalDateTime createdAt;
    private List<String> roleName;
    private List<String> permissionName;

    public AUser() {
    }

    public AUser(Integer id, String userName, String passWord, String firstName, String lastName, String email, String avatar, String address, Integer phone, Integer status, LocalDateTime createdAt, List<String> roleName, List<String> permissionName) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.roleName = roleName;
        this.permissionName = permissionName;
    }

    public AUser(Integer id, String userName, String firstName, String lastName, String email, String avatar, String address, Integer phone, Integer status, LocalDateTime createdAt, List<String> roleName, List<String> permissionName) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.roleName = roleName;
        this.permissionName = permissionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(List<String> permissionName) {
        this.permissionName = permissionName;
    }

    public boolean hasRole(String roleName) {
        return this.roleName.contains(roleName);
    }

    public boolean hasPermission(String permissionName) {
        return this.permissionName.contains(permissionName);
    }

    public boolean hasAnyRole(List<String> roleNames) {
        for (String roleName : roleNames) {
            if (hasRole(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyPermission(List<String> permissionNames) {
        for (String permissionName : permissionNames) {
            if (hasPermission(permissionName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", roleName='" + roleName + '\'' +
                ", permissionName=" + permissionName +
                '}';
    }
}
