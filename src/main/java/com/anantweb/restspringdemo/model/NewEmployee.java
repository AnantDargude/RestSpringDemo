package com.anantweb.restspringdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEmployee {
    private int empNo;
    private String DateOfBirth;
    private String firstName;
    private String lastName;
    private String gender;
    private String joinDate;
}
