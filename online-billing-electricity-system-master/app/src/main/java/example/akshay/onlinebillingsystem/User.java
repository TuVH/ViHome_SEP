package example.akshay.onlinebillingsystem;

import java.io.Serializable;

public class User implements Serializable {

    public String name;
    public String email;
    public String username;
    public String password;
    public String phoneNumber;

    public User(){}

    public User(String name, String email, String username, String password, String phoneNumber){
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
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
