package example.akshay.onlinebillingsystem;

import java.io.Serializable;

public class Customer implements Serializable {

    public String name, email, username, password, phoneNumber, mo_no;

    public Customer(){}

    public Customer(String name, String email, String username, String password, String mo_no, String phoneNumber){
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.mo_no = mo_no;
        this.phoneNumber = phoneNumber;
    }
}
