package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.User;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.ports.interfaces.AuthServicePort;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImpTest {

    @InjectMocks
    private DoctorServiceImp service;

    @Mock
    private DoctorRepositoryPort doctorRepositoryPort;

    @Mock
    private AuthServicePort authServicePort;

    @Test
    void testFindDoctorByCpfSuccess() {
        String cpf = "cpf";
        Doctor doctor = new Doctor();
        doctor.setCpf(cpf);
        when(doctorRepositoryPort.findByCpf(cpf)).thenReturn(Optional.of(doctor));

        DoctorDTO result = service.findDoctorByCpf(cpf);

        assertEquals(cpf, result.getCpf());
    }

    @Test
    void testFindDoctorByCpfNotFound() {
        String cpf = "cpf";
        when(doctorRepositoryPort.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findDoctorByCpf(cpf));
    }

    @Test
    void testRegisterDoctorSuccess() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setCpf("cpf");
        doctorDTO.setName("John Doe");

        when(doctorRepositoryPort.findByCpf("cpf")).thenReturn(Optional.empty());
        doNothing().when(authServicePort).signUp(any(User.class));
        doNothing().when(doctorRepositoryPort).save(any(Doctor.class));


        service.registerDoctor(doctorDTO);

        verify(authServicePort, times(1)).signUp(any(User.class));
        verify(doctorRepositoryPort, times(1)).save(any(Doctor.class));
    }


    @Test
    void testRegisterDoctorCpfAlreadyExists() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setCpf("cpf");

        Doctor existingDoctor = new Doctor();
        existingDoctor.setCpf("cpf");

        when(doctorRepositoryPort.findByCpf("cpf")).thenReturn(Optional.of(existingDoctor));

        assertThrows(BadRequestException.class, () -> service.registerDoctor(doctorDTO));

        verify(authServicePort, never()).signUp(any(User.class));
        verify(doctorRepositoryPort, never()).save(any(Doctor.class));
    }

    @Test
    void testFindAllDoctors() {
        String name = "nome";
        Doctor doctor = new Doctor();
        doctor.setName(name);

        when(doctorRepositoryPort.findAll()).thenReturn(List.of(doctor));
        DoctorDTO result = service.findAll().get(0);

        assertEquals(name, result.getName());
    }
}
