package com.xtramile.assignment.backend.model.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String address;
    private String suburb;
    private String state;
    private String postcode;
}
