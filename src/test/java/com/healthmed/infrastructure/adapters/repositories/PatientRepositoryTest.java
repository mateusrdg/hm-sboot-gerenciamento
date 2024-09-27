package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.domain.Patient;
import com.healthmed.infrastructure.adapters.entities.PatientEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

    @InjectMocks
    private PatientRepository repository;

    @Mock
    private PatientJpaRepository jpaRepository;

    @Test
    void testFindPatientByCpfFound() {
        String cpf = "cpf";
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setCpf(cpf);
        when(jpaRepository.findByCpf(cpf)).thenReturn(Optional.of(patientEntity));

        Optional<Patient> result = repository.findByCpf(cpf);

        assertTrue(result.isPresent());
        assertEquals(cpf, result.get().getCpf());
        verify(jpaRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void testFindPatientByCpfNotFound() {
        String cpf = "cpf";
        when(jpaRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        Optional<Patient> result = repository.findByCpf(cpf);

        assertFalse(result.isPresent());
        verify(jpaRepository, times(1)).findByCpf(cpf);
    }

    @Test
    void testSavePatientSuccess() {
        Patient patient = new Patient();

        repository.save(patient);

        verify(jpaRepository, times(1)).save(any(PatientEntity.class));
    }
}
