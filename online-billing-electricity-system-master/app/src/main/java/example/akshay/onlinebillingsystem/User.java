package example.akshay.onlinebillingsystem;

import java.io.Serializable;

public class User implements Serializable {

    public String name;
    public String email;
    public String username;
    public String password;
    public String phoneNumber;
    public String water_amount;
    public String electric_amount;
    public String home_amount;
    public String water_amount_new;
    public String electric_amount_new;
    public String home_amount_new;

    public User(){}

    public User(String name, String email, String username, String password, String phoneNumber){
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public User(String water_amount,String electric_amount,String home_amount,String water_amount_new,String electric_amount_new,String home_amount_new){
        this.water_amount = water_amount;
        this.electric_amount = electric_amount;
        this.home_amount = home_amount;
        this.water_amount_new = water_amount_new;
        this.electric_amount_new = electric_amount_new;
        this.home_amount_new = home_amount_new;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
