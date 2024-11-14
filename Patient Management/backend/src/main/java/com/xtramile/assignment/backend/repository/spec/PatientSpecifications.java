package com.xtramile.assignment.backend.repository.spec;

import com.xtramile.assignment.backend.model.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecifications {
    public static Specification<Patient> firstNameContainsIgnoreCase(String firstName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Patient> lastNameContainsIgnoreCase(String lastName) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    public static Specification<Patient> pidEquals(Long pid) {
        return (root, query, cb) -> cb.equal(root.get("pid"), pid);
    }
}
