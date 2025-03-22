package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRoles {
    private Integer userId;
    private Integer roleId;

    public UserRoles() {
    }

    public UserRoles(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    package vn.edu.hcmuaf.fit.webbanquanao.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

    public class User implements Serializable {
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
        private Integer roleId;


        public User() {
        }

        public User(Integer id, String userName, String passWord, String firstName, String lastName, String email, String avatar, String address, Integer phone, Integer status, LocalDateTime createdAt, Integer roleId) {
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
            this.roleId = roleId;
        }

        public User(Integer id, String userName, String firstName, String lastName, String email, String avatar, String address, Integer phone, Integer status, LocalDateTime createdAt, Integer roleId) {
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
            this.roleId = roleId;
        }

        public User(String s, String password, String firstName, String lastName, String gmail, String avatar, String address,Integer phone, Integer roleId) {
        }

        public User(String email, String passWord) {
            this.email = email;
            this.passWord = passWord;
        }

        public Integer getId() {
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

        public String getAvatar() {
            return avatar;
        }

        public String getAddress() {
            return address;
        }

        public Integer getPhone() {
            return phone;
        }

        public Integer getStatus() {
            return status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setId(Integer id) {
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

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(Integer phone) {
            this.phone = phone;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }


        @Override
        public String toString() {
            return "User{" + "id=" + id + ", userName='" + userName + '\'' + ", passWord='" + passWord + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", avatar='" + avatar + '\'' + ", address='" + address + '\'' + ", phone=" + phone + ", status=" + status + ", createdAt=" + createdAt + ", roleId=" + roleId + '}';
        }
    }

}
