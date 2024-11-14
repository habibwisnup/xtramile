package com.xtramile.assignment.backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

