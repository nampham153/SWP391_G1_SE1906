/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class AccessPathRole {
    private String path;
    private int roleId;
    // constructors, getters, setters...

    public AccessPathRole(String path, int roleId) {
        this.path = path;
        this.roleId = roleId;
    }

    public AccessPathRole() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "AccessPathRole{" + "path=" + path + ", roleId=" + roleId + '}';
    }
    
}

