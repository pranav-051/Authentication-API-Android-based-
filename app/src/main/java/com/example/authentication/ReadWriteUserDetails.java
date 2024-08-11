package com.example.authentication;

public class ReadWriteUserDetails {
    public String dob, gender, mobile, email;

    public ReadWriteUserDetails( String textDOB, String textGender, String textMobile, String email )
    {
        this.dob = textDOB;
        this.gender = textGender;
        this.mobile = textMobile;
        this.email = email;

    }
}
