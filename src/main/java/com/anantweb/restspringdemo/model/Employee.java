package com.anantweb.restspringdemo.model;


import org.springframework.stereotype.Component;

@Component
public class Employee {
    int id = 1;
    String name = "Anant Dargude";
    String role = "Software Dev";

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
