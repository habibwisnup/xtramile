package com.xtramile.assignment.backend.repository;

import com.xtramile.assignment.backend.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends  JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
}