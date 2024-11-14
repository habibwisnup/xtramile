package com.xtramile.assignment.backend.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.xtramile.assignment.backend.enums.SortDirection;
import com.xtramile.assignment.backend.enums.SortFieldTemplate;
import com.xtramile.assignment.backend.model.request.PatientRequest;
import com.xtramile.assignment.backend.model.response.PatientResponse;
import com.xtramile.assignment.backend.service.interfaces.IPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {
    @Autowired
    private IPatient patientService;

    @GetMapping(path = "/all")
    public Page<PatientResponse> getPatients(@RequestParam(required = false) String search,
                                             @RequestParam(required = false) Long pid,
                                             @RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             @RequestParam(defaultValue = "ID") SortFieldTemplate columnSort,
                                             @RequestParam(required = false) SortDirection sortDirection) {
        return patientService.getPatients(search, pid, page, size, columnSort, sortDirection);
    }

    @PostMapping
    public PatientResponse createPatient(@RequestBody PatientRequest patientRequest) {
        return patientService.savePatient(patientRequest);
    }

    @GetMapping
    public PatientResponse getPatientById(@RequestParam Long pid){
        return patientService.getPatientById(pid);
    }

    @PutMapping("/{pid}")
    public PatientResponse updatePatient(@PathVariable Long pid, @RequestBody PatientRequest patientRequest) throws JsonMappingException {
        return patientService.updatePatient(pid, patientRequest);
    }

    @DeleteMapping("/{pid}")
    public void deletePatient(@PathVariable Long pid) {
        patientService.deletePatient(pid);
    }
}
