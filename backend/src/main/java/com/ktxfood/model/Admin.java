package com.ktxfood.model;

public class Admin extends User {
    private String password;

    public Admin() {
        super();
    }

    public Admin(String id, String fullName, String password) {
        super(id, fullName, null);
        this.password = password;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @com.fasterxml.jackson.annotation.JsonIgnore
    @Override
    public String getRole() {
        return "Quản trị viên";
    }
}