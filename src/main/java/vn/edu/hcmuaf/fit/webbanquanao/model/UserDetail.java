package vn.edu.hcmuaf.fit.webbanquanao.model;

import java.util.Date;

public class UserDetail {

    private int userId;
    private String gender;
    private Date birthDate;
    private double height;
    private double weight;

    // Constructor
    public UserDetail(int userId, String gender, Date birthDate, double height, double weight) {
        this.userId = userId;
        this.gender = gender;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
    }

    // Getter và Setter cho userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter và Setter cho gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter và Setter cho birthDate
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Getter và Setter cho height
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Getter và Setter cho weight
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Phương thức toString để in ra thông tin của UserInfo
    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
