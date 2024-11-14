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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;;

class PatientImplTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PatientImpl patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatients() {
        Patient patient = new Patient();
        PatientResponse patientResponse = new PatientResponse();
        Pageable pageable = PageHelper.setPageable(0, 10, SortDirection.ASC, SortFieldTemplate.ID.getValue());

        when(patientRepository.findAll(isA(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(patient)));
        when(objectMapper.convertValue(patient, PatientResponse.class)).thenReturn(patientResponse);

        Page<PatientResponse> result = patientService.getPatients(null, null, 0, 10, SortFieldTemplate.ID, SortDirection.ASC);

        assertEquals(1, result.getContent().size());
        verify(patientRepository, times(1)).findAll(isA(Specification.class), any(Pageable.class));
    }

    @Test
    void testSavePatient() {
        PatientRequest patientRequest = new PatientRequest();
        Patient patient = new Patient();
        Patient savedPatient = new Patient();
        PatientResponse patientResponse = new PatientResponse();

        when(objectMapper.convertValue(patientRequest, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(savedPatient);
        when(objectMapper.convertValue(savedPatient, PatientResponse.class)).thenReturn(patientResponse);

        PatientResponse result = patientService.savePatient(patientRequest);

        assertEquals(patientResponse, result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testUpdatePatient() throws JsonMappingException {
        Long pid = 1L;
        PatientRequest patientRequest = new PatientRequest();
        Patient existingPatient = new Patient();
        Patient updatedPatient = new Patient();
        PatientResponse patientResponse = new PatientResponse();

        when(patientRepository.findById(pid)).thenReturn(Optional.of(existingPatient));
        when(objectMapper.updateValue(existingPatient, patientRequest)).thenReturn(existingPatient);
        when(patientRepository.save(existingPatient)).thenReturn(updatedPatient);
        when(objectMapper.convertValue(updatedPatient, PatientResponse.class)).thenReturn(patientResponse);

        PatientResponse result = patientService.updatePatient(pid, patientRequest);

        assertEquals(patientResponse, result);
        verify(patientRepository, times(1)).findById(pid);
        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    void testUpdatePatient_NotFound() {
        Long pid = 1L;
        PatientRequest patientRequest = new PatientRequest();

        when(patientRepository.findById(pid)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> patientService.updatePatient(pid, patientRequest));
        verify(patientRepository, times(1)).findById(pid);
    }

    @Test
    void testGetPatientById() {
        Long pid = 1L;
        Patient patient = new Patient();
        PatientResponse patientResponse = new PatientResponse();

        when(patientRepository.findById(pid)).thenReturn(Optional.of(patient));
        when(objectMapper.convertValue(patient, PatientResponse.class)).thenReturn(patientResponse);

        PatientResponse result = patientService.getPatientById(pid);

        assertEquals(patientResponse, result);
        verify(patientRepository, times(1)).findById(pid);
    }

    @Test
    void testGetPatientById_NotFound() {
        Long pid = 1L;

        when(patientRepository.findById(pid)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> patientService.getPatientById(pid));
        verify(patientRepository, times(1)).findById(pid);
    }

    @Test
    void testDeletePatient() {
        Long pid = 1L;
        doNothing().when(patientRepository).deleteById(pid);

        patientService.deletePatient(pid);

        verify(patientRepository, times(1)).deleteById(pid);
    }
}