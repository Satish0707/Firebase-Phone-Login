package com.satish07.firebasephonelogindemo.Profile;

import java.util.Objects;
import java.util.jar.Attributes;

public class User_Profile_Adapter {

    private String pflName;
    private String pflEmail;
    private String pflAddress;

    public User_Profile_Adapter() {
    }

    public User_Profile_Adapter(String Name, String Email, String Address) {


        this.pflName = Name;
        this.pflEmail = Email;
        this.pflAddress = Address;
    }

    String getPflName() {
        return pflName;
    }

    public void setPflName(String Name) {
        this.pflName = Name;
    }

    String getPflEmail() {
        return pflEmail;
    }

    public void setPflEmail(String pflEmail) {
        this.pflEmail = pflEmail;
    }

    String getPflAddress() {
        return pflAddress;
    }

    public void setPflAddress(String pflAddress) {
        this.pflAddress = pflAddress;
    }
}