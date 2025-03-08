package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer roleid;
    private String nameRole;


    public Role() {
    }

    public Role(String nameRole, Integer roleid) {
        this.nameRole = nameRole;
        this.roleid = roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public String getNameRole() {
        return nameRole;
    }


}
