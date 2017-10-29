package com.example.asus.lab3;



public class MessageEvent{
    private String name;
    private String price;
    private String message;
    MessageEvent(String name, String price, String message){
        this.name=name;
        this.price=price;
        this.message=message;
    }

    public String getName(){
        return name;
    }
    String getPrice(){
        return price;
    }
    String getMessage(){
        return message;
    }
}
