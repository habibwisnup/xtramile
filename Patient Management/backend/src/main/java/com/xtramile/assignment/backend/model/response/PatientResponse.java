package com.xtramile.assignment.backend.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientResponse {
    private Long pid;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String suburb;
    private String state;
    private String postcode;
    private String phoneNo;
}
