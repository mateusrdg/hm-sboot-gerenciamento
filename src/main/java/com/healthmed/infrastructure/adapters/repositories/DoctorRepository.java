package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.domain.Doctor;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;
import com.healthmed.infrastructure.adapters.entities.DoctorEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DoctorRepository implements DoctorRepositoryPort {

    private final DoctorJpaRepository jpaRepository;

    public DoctorRepository(DoctorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Doctor> findByCpf(String cpf) {
        Optional<DoctorEntity> entity = jpaRepository.findByCpf(cpf);
        return entity.map(DoctorEntity::toDomain);
    }

    @Override
    public void save(Doctor doctor) {
        jpaRepository.save(new DoctorEntity(doctor));
    }

    @Override
    public List<Doctor> findAll() {
        return jpaRepository.findAll().stream().map(DoctorEntity::toDomain).collect(Collectors.toList());
    }
}
