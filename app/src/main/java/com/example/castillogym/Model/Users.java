package com.example.castillogym.Model;

public class Users {

    private String id, email, name, password, tipous;

    public Users() {

    }

    public Users(String id, String email, String name, String password, String tipous) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.tipous = tipous;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipous() {
        return tipous;
    }

    public void setTipous(String tipous) {
        this.tipous = tipous;
    }

}