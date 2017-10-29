package com.example.asus.lab3;



public class Goods{
    private String name;
    private String price;
    private String message;

    Goods(String name, String price, String message){
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