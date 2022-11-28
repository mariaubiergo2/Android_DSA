package edu.upc.dsa.andoroid_dsa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credentials {

    private String email;
    private String password;

    public Credentials(String password, String mail) {
        this.setMail(mail);
        this.setPassword(password);
    }

    public String getMail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
