package com.example.springboot;

public class Home {
     public boolean status;
     public String message;
     public String token;
     public String userId;


    public Home(boolean status, String message, String token, String userId) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.userId = userId;
    }
    public Home(boolean status,String message,String userId) {
        this.status = status;
        this.message = message;
        this.userId = userId;
    }


    public Home(boolean status, String message){
        this.status = status;
        this.message = message;
    }





}
