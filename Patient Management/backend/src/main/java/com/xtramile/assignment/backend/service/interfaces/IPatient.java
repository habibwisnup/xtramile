package com.xtramile.assignment.backend.service.interfaces;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.xtramile.assignment.backend.enums.SortDirection;
import com.xtramile.assignment.backend.enums.SortFieldTemplate;
import com.xtramile.assignment.backend.model.request.PatientRequest;
import com.xtramile.assignment.backend.model.response.PatientResponse;
import org.springframework.data.domain.Page;

public interface IPatient {
    Page<PatientResponse> getPatients(String search, Long pid, int page, int size, SortFieldTemplate columnSort,
                              SortDirection sortDirection);
    PatientResponse savePatient(PatientRequest patientRequest);
    PatientResponse updatePatient(Long pid, PatientRequest patientRequest) throws JsonMappingException;

    PatientResponse getPatientById(Long pid);
    void deletePatient(Long pid);
}
