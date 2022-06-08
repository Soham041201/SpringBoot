package com.example.springboot;

public class Login {
    public String email;
    public String password;
    public String token;
    Login(String email, String password,String token){
        this.email = email;
        this.password = password;
        this.token = token;

    }
}
