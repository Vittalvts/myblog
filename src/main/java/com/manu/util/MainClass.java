package com.manu.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MainClass {
    public static void main(String[] args) {
        PasswordEncoder passwordEncodere=new BCryptPasswordEncoder();
        System.out.println(passwordEncodere.encode("testng"));
    }
}
