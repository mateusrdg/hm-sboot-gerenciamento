package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.domain.Doctor;
import com.healthmed.infrastructure.adapters.entities.DoctorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorRepositoryTest {

    @InjectMocks
    private DoctorRepository repository;

    @Mock
    private DoctorJpaRepository jpaRepository;

    @Test
    void testFindDoctorByCpfFound() {
        String cpf = "cpf";
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setCpf(cpf);
        when(jpaRepository.findByCpf(cpf)).thenReturn(Optional.of(doctorEntity));

        Optional<Doctor> result = repository.findByCpf(cpf);

        assertTrue(result.isPresent());
        assertEquals(cpf, result.get().getCpf());
        verify(jpaRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void testFindDoctorByCpfNotFound() {
        String cpf = "cpf";
        when(jpaRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        Optional<Doctor> result = repository.findByCpf(cpf);

        assertFalse(result.isPresent());
        verify(jpaRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void testSaveDoctorSuccess() {
        Doctor doctor = new Doctor();

        repository.save(doctor);

        verify(jpaRepository, times(1)).save(any(DoctorEntity.class));
    }

    @Test
    void testFindAllDoctorsFound() {
        DoctorEntity doctorEntity1 = new DoctorEntity();
        DoctorEntity doctorEntity2 = new DoctorEntity();
        when(jpaRepository.findAll()).thenReturn(List.of(doctorEntity1, doctorEntity2));

        List<Doctor> result = repository.findAll();

        assertEquals(2, result.size());
        verify(jpaRepository, times(1)).findAll();
    }

    @Test
    void testFindAllDoctorsNotFound() {
        when(jpaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Doctor> result = repository.findAll();

        assertTrue(result.isEmpty());
        verify(jpaRepository, times(1)).findAll();
    }
}
