package ru.kpfu.itis.entities;

public class User {
    private Integer user_id;
    private String username;
    private String password;
    private String email;
    private String url;

    public User() {}

    public User(Integer user_id, String username, String password, String email, String url) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.url = url;
    }

    public User(String username, String password, String email, String url) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.url = url;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return user_id;
    }

    public void setId(Integer id) {
        this.user_id = id;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
