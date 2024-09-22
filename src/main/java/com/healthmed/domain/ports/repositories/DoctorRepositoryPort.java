package com.healthmed.domain.ports.repositories;

import com.healthmed.domain.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepositoryPort {
    Optional<Doctor> findByCpf(String cpf);
    void save(Doctor doctor);
    List<Doctor> findAll();
}
