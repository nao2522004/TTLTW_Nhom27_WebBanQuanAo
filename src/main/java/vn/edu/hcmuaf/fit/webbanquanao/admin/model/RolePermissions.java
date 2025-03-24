package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;

public class RolePermissions implements Serializable {
    private Integer roleId;
    private Integer permissionId;

    public RolePermissions() {
    }

    public RolePermissions(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermissions{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}
