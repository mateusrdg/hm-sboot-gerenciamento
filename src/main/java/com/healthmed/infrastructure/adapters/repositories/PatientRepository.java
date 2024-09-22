package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.domain.Patient;
import com.healthmed.domain.ports.repositories.PatientRepositoryPort;
import com.healthmed.infrastructure.adapters.entities.PatientEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientRepository implements PatientRepositoryPort {

    private final PatientJpaRepository jpaRepository;

    public PatientRepository(PatientJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        Optional<PatientEntity> entity = jpaRepository.findByCpf(cpf);
        return entity.map(PatientEntity::toDomain);
    }

    @Override
    public void save(Patient patient) {
        jpaRepository.save(new PatientEntity(patient));
    }
}
