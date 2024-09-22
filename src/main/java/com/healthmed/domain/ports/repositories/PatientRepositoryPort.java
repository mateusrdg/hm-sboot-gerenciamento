package com.healthmed.domain.ports.repositories;

import com.healthmed.domain.Patient;

import java.util.Optional;

public interface PatientRepositoryPort {
    Optional<Patient> findByCpf(String cpf);
    void save(Patient doctor);
}
