package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled("Teste desabilitado temporariamente")
class DoctorServiceImpTest {

    @InjectMocks
    DoctorServiceImp customerServiceImp;

    @Mock
    private DoctorRepositoryPort doctorRepositoryPort;

    @Test
    void testeConsultaPorId() {
        String cpf = "cpf";
        Doctor doctor = new Doctor();
        doctor.setCpf(cpf);
        when(doctorRepositoryPort.findByCpf(cpf)).thenReturn(Optional.of(doctor));

        DoctorDTO result = customerServiceImp.findDoctorByCpf(cpf);

        assertEquals(cpf, result.getCpf());
    }

    @Test
    void testeRegistrarCliente() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setCpf("cpf");
        doctorDTO.setName("John Doe");

        doNothing().when(doctorRepositoryPort).save(any(Doctor.class));

        customerServiceImp.registerDoctor(doctorDTO);

        verify(doctorRepositoryPort, times(1)).save(any(Doctor.class));

    }

    @Test
    void testeConsultaPorIdNotFound() {
        String cpf = "cpf";
        when(doctorRepositoryPort.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerServiceImp.findDoctorByCpf(cpf));
    }
}
