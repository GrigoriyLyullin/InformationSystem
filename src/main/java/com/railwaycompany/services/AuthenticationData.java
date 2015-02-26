package com.railwaycompany.services;

public class AuthenticationData {

    private int id;
    private boolean permission;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public boolean isPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "AuthenticationData{" +
                "id=" + id +
                ", permission=" + permission +
                '}';
    }
}
