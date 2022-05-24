package com.example.springboot.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("usermaster")
public class User {
    @Id
    public String id;

    public boolean isLogin = false;
    public String name;
    public String email;
    public String password;

    public User(String name, String email, String password, Boolean isLogin){
    super();
        this.email =email;
        this.password = password;
        this.name = name;
        this.isLogin = isLogin;
    }
}
