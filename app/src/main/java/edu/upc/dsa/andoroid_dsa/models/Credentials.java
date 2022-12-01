package edu.upc.dsa.andoroid_dsa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credentials {
    String Email;
    String password;

    public Credentials(String email, String password){
        this.Email=email;
        this.password=password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
