package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.domain.Doctor;
import com.healthmed.infrastructure.adapters.entities.DoctorEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled("Teste desabilitado temporariamente")
@ExtendWith(MockitoExtension.class)
class DoctorRepositoryTest {

    @InjectMocks
    private DoctorRepository customerRepository;

    @Mock
    private DoctorJpaRepository doctorJpaRepository;

    @Test
    void testeFindCustomerByCpf() {
        String cpf = "cpf";
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setCpf(cpf);
        when(doctorJpaRepository.findByCpf(cpf)).thenReturn(Optional.of(doctorEntity));

        Optional<Doctor> result = customerRepository.findByCpf(cpf);

        assertTrue(result.isPresent());
        assertEquals(cpf, result.get().getCpf());
    }

    @Test
    void testeSaveCustomer() {
        Doctor doctor = new Doctor();
        doctor.setCpf("cpf");
        when(doctorJpaRepository.findByCpf("cpf")).thenReturn(Optional.empty());

        customerRepository.save(doctor);

        verify(doctorJpaRepository, times(1)).save(any(DoctorEntity.class));
    }

    @Test
    void testeSaveCustomerCpfAlreadyExists() {
        Doctor doctor = new Doctor();
        doctor.setCpf("cpf");
        DoctorEntity existingCustomer = new DoctorEntity();
        when(doctorJpaRepository.findByCpf("cpf")).thenReturn(Optional.of(existingCustomer));

        assertThrows(BadRequestException.class, () -> customerRepository.save(doctor));
    }
}
