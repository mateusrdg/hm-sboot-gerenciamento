package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.infrastructure.adapters.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> findByCpf(String cpf);
}
