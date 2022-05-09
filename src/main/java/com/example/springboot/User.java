package com.example.springboot;

import java.util.ArrayList;

public class User {
    String name;
    String email;
    String password;

    static ArrayList<User> userData= new ArrayList<User>();

    User(String email,String password, String name){
        this.email = email;
        this.name = name;
        this.password = password;

    }
}
