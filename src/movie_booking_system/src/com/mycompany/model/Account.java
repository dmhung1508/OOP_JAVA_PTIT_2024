package com.mycompany.model;

public class Account {
    private String username;
    private String password;
    private String email;
    private String role;
    public Account(String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public Account(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public String getRole()
    {
        return role;
    }
}
