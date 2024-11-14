package com.xtramile.assignment.backend.service.implementation;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtramile.assignment.backend.enums.SortDirection;
import com.xtramile.assignment.backend.enums.SortFieldTemplate;
import com.xtramile.assignment.backend.helper.PageHelper;
import com.xtramile.assignment.backend.model.entity.Patient;
import com.xtramile.assignment.backend.model.request.PatientRequest;
import com.xtramile.assignment.backend.model.response.PatientResponse;
import com.xtramile.assignment.backend.repository.PatientRepository;
import com.xtramile.assignment.backend.repository.spec.PatientSpecifications;
import com.xtramile.assignment.backend.service.interfaces.IPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PatientImpl implements IPatient {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<PatientResponse> getPatients(String search, Long pid, int page, int size, SortFieldTemplate columnSort,
                                     SortDirection sortDirection) {

        Pageable pageable = PageHelper.setPageable(page, size, sortDirection, columnSort.getValue());
        Specification<Patient> specification = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            specification = specification.or(PatientSpecifications.firstNameContainsIgnoreCase(search))
                    .or(PatientSpecifications.lastNameContainsIgnoreCase(search));
        }
        if (pid != null) {
            specification = specification.or(PatientSpecifications.pidEquals(pid));
        }
        Page<Patient> patientPage = patientRepository.findAll(specification, pageable);
        return new PageImpl<>(
                patientPage.getContent().stream()
                        .map(patient -> objectMapper.convertValue(patient, PatientResponse.class))
                        .collect(Collectors.toList()),
                pageable,
                patientPage.getTotalElements()
        );
    }

    @Override
    public PatientResponse savePatient(PatientRequest patientRequest) {
        Patient patient = objectMapper.convertValue(patientRequest, Patient.class);
        Patient savedPatient = patientRepository.save(patient);
        return objectMapper.convertValue(savedPatient, PatientResponse.class);
    }

    @Override
    public PatientResponse updatePatient(Long pid, PatientRequest patientRequest) throws JsonMappingException {
        Patient existingPatient = patientRepository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        objectMapper.updateValue(existingPatient, patientRequest);

        Patient updatedPatient = patientRepository.save(existingPatient);
        return objectMapper.convertValue(updatedPatient, PatientResponse.class);
    }

    @Override
    public PatientResponse getPatientById(Long pid) {
        Patient patient = patientRepository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return objectMapper.convertValue(patient, PatientResponse.class);
    }

    @Override
    public void deletePatient(Long pid) {
        patientRepository.deleteById(pid);
    }
}
