package com.example.springboot;

public class Home {
    private static boolean status;
    private static String message;


    public Home(boolean status, String message){
        this.status = status;
        this.message = message;
    }


    public String getMessage(){
       return message;
    }
    public boolean getStatus(){
        return status;
    }
}
