package com.example.scoops;

public class RequestV {
    private String name, location, email, date, phone, item;


    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setItem(String item){
        this.item = item;
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getLocation(){
        return location;
    }
    public String getDate(){
        return date;
    }
    public String getPhone(){
        return phone;
    }
    public String getItem(){
        return item;
    }

}
