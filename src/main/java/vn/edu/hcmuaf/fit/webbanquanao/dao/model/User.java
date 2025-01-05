package vn.edu.hcmuaf.fit.webbanquanao.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Integer roleId;
    private Integer status;
    private LocalDateTime createdAt;
    private String roleName;

    public User() {
    }

    public User(int id, String firstName, String lastName, String userName, String passWord, String email, String phone, String address, Integer roleId, Integer status, LocalDateTime createdAt, String roleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.status = status;
        this.createdAt = createdAt;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
