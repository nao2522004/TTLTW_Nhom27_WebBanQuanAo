package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;

public class Permission implements Serializable {
    Integer id;
    String permissionName;

    public Permission(String permissionName) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
