package com.model;

public class User {
    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isLogin;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean getIsLogin() {
        return this.isLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public void login() {
        DbConnect dbConnect = new DbConnect();
        String query = "SELECT * FROM users WHERE username = '" + this.username + "' AND password = crypt('" + this.password + "', password);";
        boolean[] booleans = dbConnect.executeLoginQuery(query);
        isLogin = booleans[0];
        isAdmin = booleans[1];
    }
}
