package com.xtramile.assignment.backend.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long pid;
    private String firstName;
    private String lastName;
    private String gender;
}
