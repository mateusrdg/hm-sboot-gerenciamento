package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.Patient;
import com.healthmed.domain.User;
import com.healthmed.domain.dtos.patient.PatientDTO;
import com.healthmed.domain.ports.interfaces.AuthServicePort;
import com.healthmed.domain.ports.repositories.PatientRepositoryPort;
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
class PatientServiceImpTest {

    @InjectMocks
    private PatientServiceImp service;

    @Mock
    private PatientRepositoryPort patientRepositoryPort;

    @Mock
    private AuthServicePort authServicePort;

    @Test
    void testFindPatientByCpfSuccess() {
        String cpf = "12345678901";
        Patient patient = new Patient();
        patient.setCpf(cpf);
        when(patientRepositoryPort.findByCpf(cpf)).thenReturn(Optional.of(patient));

        PatientDTO result = service.findPatientByCpf(cpf);

        assertEquals(cpf, result.getCpf());
    }

    @Test
    void testFindPatientByCpfNotFound() {
        String cpf = "12345678901";
        when(patientRepositoryPort.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findPatientByCpf(cpf));
    }

    @Test
    void testRegisterPatientSuccess() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setCpf("12345678901");
        patientDTO.setName("Alice");

        when(patientRepositoryPort.findByCpf("12345678901")).thenReturn(Optional.empty());
        doNothing().when(authServicePort).signUp(any(User.class));
        doNothing().when(patientRepositoryPort).save(any(Patient.class));

        service.registerPatient(patientDTO);

        verify(authServicePort, times(1)).signUp(any(User.class));
        verify(patientRepositoryPort, times(1)).save(any(Patient.class));
    }

    @Test
    void testRegisterPatientCpfAlreadyExists() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setCpf("12345678901");

        Patient existingPatient = new Patient();
        existingPatient.setCpf("12345678901");

        when(patientRepositoryPort.findByCpf("12345678901")).thenReturn(Optional.of(existingPatient));

        assertThrows(BadRequestException.class, () -> service.registerPatient(patientDTO));

        verify(authServicePort, never()).signUp(any(User.class));
        verify(patientRepositoryPort, never()).save(any(Patient.class));
    }
}
