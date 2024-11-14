package com.xtramile.assignment.backend.controller;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.xtramile.assignment.backend.enums.SortFieldTemplate;
import com.xtramile.assignment.backend.model.request.PatientRequest;
import com.xtramile.assignment.backend.model.response.PatientResponse;
import com.xtramile.assignment.backend.service.interfaces.IPatient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PatientControllerTest {
    @Mock
    private IPatient patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatients() {
        Page<PatientResponse> patientsPage = new PageImpl<>(Collections.singletonList(new PatientResponse()));
        when(patientService.getPatients(null, null, 0, 10, SortFieldTemplate.ID, null)).thenReturn(patientsPage);

        Page<PatientResponse> result = patientController.getPatients(null, null, 0, 10, SortFieldTemplate.ID, null);
        assertEquals(1, result.getContent().size());
        verify(patientService, times(1)).getPatients(null, null, 0, 10, SortFieldTemplate.ID, null);
    }

    @Test
    void testCreatePatient() {
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setFirstName("John");
        patientRequest.setLastName("Doe");

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("John");
        patientResponse.setLastName("Doe");

        when(patientService.savePatient(patientRequest)).thenReturn(patientResponse);

        PatientResponse result = patientController.createPatient(patientRequest);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(patientService, times(1)).savePatient(patientRequest);
    }

    @Test
    void testGetPatientById() {
        Long pid = 1L;
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setPid(pid);

        when(patientService.getPatientById(pid)).thenReturn(patientResponse);

        PatientResponse result = patientController.getPatientById(pid);
        assertEquals(pid, result.getPid());
        verify(patientService, times(1)).getPatientById(pid);
    }

    @Test
    void testUpdatePatient() throws JsonMappingException {
        Long pid = 1L;
        PatientRequest patientRequest = new PatientRequest();
        patientRequest.setFirstName("Jane");

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setPid(pid);
        patientResponse.setFirstName("Jane");

        when(patientService.updatePatient(pid, patientRequest)).thenReturn(patientResponse);

        PatientResponse result = patientController.updatePatient(pid, patientRequest);
        assertEquals("Jane", result.getFirstName());
        verify(patientService, times(1)).updatePatient(pid, patientRequest);
    }

    @Test
    void testDeletePatient() {
        Long pid = 1L;
        doNothing().when(patientService).deletePatient(pid);

        patientController.deletePatient(pid);

        verify(patientService, times(1)).deletePatient(pid);
    }
}